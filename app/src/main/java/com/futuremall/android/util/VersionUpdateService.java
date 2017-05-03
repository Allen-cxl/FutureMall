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

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;

import java.io.File;

import okhttp3.Request;

/**
 * Created by victor on 2016/5/31.
 */
public class VersionUpdateService extends IntentService {

    private static final int FLAG_UPDATE_VERSION = 1;
    private static final int PROGRESS = 0x00;
    private static final int COMPLETE = 0x01;
    private static final int ERROR = 0x02;

    private static final String TAG = "VersionUpdateService";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    public VersionUpdateService() {
        super("VersionUpdateService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
//        createPendingIntent(intent);
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

        Log.d(TAG, "goDownload");
        createNotification();
        final String fileName = "24hmb.apk";
        final String fileDir = FileUtil.getDownloadFileDirName(this);
        FileUtil.removeFile(fileDir + File.separator + fileName);
        HmbClient.Download(this,
                url,
//                "http://180.153.100.153/imtt.dd.qq.com/16891/03BEC427178481E9367EC9E5DB95C09E.apk?mkey=589c0220aa8ec281&f=5e8c&c=0&fsname=com.hmb.eryida_1.0.0_4.apk&csr=4d5s&p=.apk",
                fileName,
                FileUtil.getDownloadFileDirName(this),
                new ResultCallback<Object>() {

                    @Override
                    public void inProgress(float progress) {
                        super.inProgress(progress);

                        Message msg = Message.obtain();
                        msg.obj = progress;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Request request, Exception e, String srvMsg) {

                        Message msg = Message.obtain();
                        msg.what = ERROR;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Object response) {

                        Message msg = Message.obtain();
                        msg.what = COMPLETE;
                        msg.obj = response;
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
                    ToastUtil.show(getString(R.string.download_fail));
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
