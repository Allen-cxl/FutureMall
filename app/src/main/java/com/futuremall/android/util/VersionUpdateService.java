package com.futuremall.android.util;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.http.api.MallApis;

import org.reactivestreams.Publisher;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by victor on 2016/5/31.
 */
public class VersionUpdateService extends IntentService {

    private static final int FLAG_UPDATE_VERSION = 1;
    private static final int PROGRESS = 0x00;
    private static final int COMPLETE = 0x01;
    private static final int ERROR = 0x02;
    private static final String APK_NAME = "FutureMall.apk";

    private static final String TAG = "VersionUpdateService";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private Context mContext;

    public VersionUpdateService() {
        super("VersionUpdateService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
//        createPendingIntent(intent);
        mContext = this;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(Constants.IT_WEB_URL);
        goDownload(url);
    }

    private void createNotification() {

        Log.d(TAG, "createPendingIntent");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("下载更新");
        mNotificationManager.notify(FLAG_UPDATE_VERSION, mBuilder.build());
    }

    private void goDownload(String url) {

        url = "/mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk";
        createNotification();
        final String fileName = APK_NAME;
        final String fileDir = FileUtil.getDownloadFileDirName(this);
        FileUtil.removeFile(fileDir + File.separator + fileName);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://msoftdl.360.cn");

        MallApis retrofit = retrofitBuilder
                .client(new OkHttpClient.Builder().build())
                .build().create(MallApis.class);

        Call<ResponseBody> call = retrofit.downloadApk(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                if(response.isSuccessful()){

                    saveFile(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                handler.obtainMessage(ERROR,null).sendToTarget();
            }
        });
    }

    private void saveFile(Response<ResponseBody> resp) {

        Flowable.just(resp)
                .flatMap(new Function<Response<ResponseBody>, Publisher<File>>() {
                    @Override
                    public Publisher<File> apply(final Response<ResponseBody> response) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<File>() {
                            @Override
                            public void subscribe(FlowableEmitter<File> e) throws Exception {

                                InputStream ips = response.body().byteStream();
                                long total = response.body().contentLength();
                                long sum = 0;
                                File file = new File(FileUtil.getDownloadFileDirName(mContext), APK_NAME);
                                FileOutputStream fos = new FileOutputStream(file);
                                BufferedInputStream bis = new BufferedInputStream(ips);
                                byte[] buffer = new byte[1024*1024];
                                int len;
                                while ((len = bis.read(buffer)) != -1) {
                                    sum += len;
                                    fos.write(buffer, 0, len);
                                    final int progress = (int) ((100 *sum)  / total);

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            handler.obtainMessage(PROGRESS,progress).sendToTarget();
                                        }
                                    });
                                    fos.flush();
                                }
                                fos.close();
                                bis.close();
                                ips.close();
                                e.onNext(file);
                                e.onComplete();

                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {

                        handler.obtainMessage(COMPLETE,file).sendToTarget();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        handler.obtainMessage(ERROR,null).sendToTarget();
                    }
                });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(mContext, "下载失败请重试", Toast.LENGTH_LONG);
                    mNotificationManager.cancel(FLAG_UPDATE_VERSION);
                    break;

                case PROGRESS:

                    int progress = (int) msg.obj;

                    mBuilder.setProgress(100, progress, false)
                        .setContentText(progress + "%");
                    mNotificationManager.notify(FLAG_UPDATE_VERSION, mBuilder.build());
                    break;

                case COMPLETE:
                    mBuilder.setProgress(0, 0, false);
                    mNotificationManager.cancel(FLAG_UPDATE_VERSION);
                    File file = (File) msg.obj;
                    openApk(file);
                    break;
            }
        }
    };

    private void openApk(File file) {

        if (null != file && file.exists()) {

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            startActivity(intent);
        }
    }

    public static void startService(Context context, String updateUrl) {

        if (SystemUtil.isServiceRun(context, "com.futuremall.android.VersionUpdateService")) return;

        Intent intent = new Intent(context, VersionUpdateService.class);
        intent.putExtra(Constants.IT_WEB_URL, updateUrl);
        context.startService(intent);
    }
}
