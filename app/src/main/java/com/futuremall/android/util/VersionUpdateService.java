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
import com.futuremall.android.http.ProgressResponseBody;
import com.futuremall.android.http.api.MallApis;
import com.futuremall.android.ui.listener.ProgressListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Interceptor;
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

        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        MallApis retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(MallApis.class);
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("onProgress",String.format("%d%% done\n",(100 * bytesRead) / contentLength));
                Log.e("done","--->" + String.valueOf(done));
                int progress = (int) (100 * (float) bytesRead);
                mBuilder.setProgress(100, progress, false)
                        .setContentText(progress + "%");
                mNotificationManager.notify(FLAG_UPDATE_VERSION, mBuilder.build());
                if(done){
                    mBuilder.setProgress(0, 0, false);
                    mNotificationManager.cancel(FLAG_UPDATE_VERSION);
                }
            }
        });

        Call<ResponseBody> call = retrofit.downloadApk(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            if(response.isSuccessful()){
                                InputStream is = response.body().byteStream();
                                File file = new File(FileUtil.getDownloadFileDirName(mContext), APK_NAME);
                                FileOutputStream fos = new FileOutputStream(file);
                                BufferedInputStream bis = new BufferedInputStream(is);
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = bis.read(buffer)) != -1) {
                                    fos.write(buffer, 0, len);
                                    fos.flush();
                                }
                                fos.close();
                                bis.close();
                                is.close();

                                openApk(file);


                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Message msg = Message.obtain();
                msg.what = ERROR;
                mHandler.sendMessage(msg);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(mContext, "下载失败请重试", Toast.LENGTH_LONG);
                    mNotificationManager.cancel(FLAG_UPDATE_VERSION);
                    break;

                case PROGRESS:
                    int progress = (int) (100 * (float) msg.obj);
                    mBuilder.setProgress(100, progress, false)
                            .setContentText(progress + "%");
                    mNotificationManager.notify(FLAG_UPDATE_VERSION, mBuilder.build());
                    break;

                case COMPLETE:
                    mBuilder.setProgress(0, 0, false);
                    mNotificationManager.cancel(FLAG_UPDATE_VERSION);
                    File file = new File(msg.obj.toString());
                    openApk(file);
                    break;
            }
        }
    };

    private void openApk(File file) {

        if (file.exists()) {

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
