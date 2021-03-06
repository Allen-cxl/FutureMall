package com.futuremall.android.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.AesBean;
import com.futuremall.android.presenter.Contract.QrCodeContract;
import com.futuremall.android.presenter.QrCodePresenter;
import com.futuremall.android.ui.ViewHolder.LoginHelper;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.widget.zxing.camera.CameraManager;
import com.futuremall.android.widget.zxing.decoding.CaptureActivityHandler;
import com.futuremall.android.widget.zxing.decoding.InactivityTimer;
import com.futuremall.android.widget.zxing.decoding.RGBLuminanceSource;
import com.futuremall.android.widget.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import butterknife.BindView;


public class QrCodeActivity extends BaseActivity<QrCodePresenter> implements QrCodeContract.View, SurfaceHolder.Callback{

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.preview_view)
    SurfaceView mPreviewView;
    @BindView(R.id.viewfinder_view)
    ViewfinderView mViewfinderView;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private boolean isFlash = false;


    private static final int REQUEST_CODE = 100;
    private static final int PARSE_BARCODE_SUC = 300;
    private static final int PARSE_BARCODE_FAIL = 303;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mProgress.dismiss();
            switch (msg.what) {
                case PARSE_BARCODE_SUC:
                    onResultHandler((String) msg.obj, scanBitmap);
                    break;
                case PARSE_BARCODE_FAIL:
                    Toast.makeText(QrCodeActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                    break;

            }
        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();

                    mProgress = new ProgressDialog(QrCodeActivity.this);
                    mProgress.setMessage("正在扫描...");
                    mProgress.setCancelable(false);
                    mProgress.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Result result = scanningImage(photo_path);
                            if (result != null) {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_SUC;
                                m.obj = result.getText();
                                mHandler.sendMessage(m);
                            } else {
                                Message m = mHandler.obtainMessage();
                                m.what = PARSE_BARCODE_FAIL;
                                m.obj = "Scan failed!";
                                mHandler.sendMessage(m);
                            }
                        }
                    }).start();

                    break;

            }
        }
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        initScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        destroyScan();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        destroyScan();
        super.onDestroy();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initEventAndData() {

        CameraManager.init(getApplication());

        setToolBar(mSuperToolbar, getString(R.string.scan), true);

        mViewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    /**
     * 处理扫描结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        onResultHandler(resultString, barcode);
    }

    /**
     * 跳转到上一个页面
     *
     * @param resultString
     * @param bitmap
     */
    private void onResultHandler(String resultString, Bitmap bitmap) {
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(QrCodeActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
            return;
        }

        int resultType = StringUtil.scanResultType(resultString);
        String result = StringUtil.scanResult(resultString);
        if(resultType == Constants.ACTIVITY_PAYMENT){
            if(LoginHelper.ensureLogin(this)){
                mPresenter.scan(result);
            }
        }else if(resultType == Constants.ACTIVITY_REGIST){

            UserWebViewActivity.enter(this, getString(R.string.register_account), result);
            finish();
        }else{
            SnackbarUtil.show(mPreviewView, "暂不提供扫描");
            destroyScan();
            initScan();
        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            GenPermissionFailDialog(this, "", "打开相机失败");
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(QrCodeActivity.this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return mViewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        mViewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public static void enter(Context context) {

        context.startActivity(new Intent(context, QrCodeActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_light, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_light:

                if (!isFlash) {
                    CameraManager.get().enableFlashLight();

                } else {
                    CameraManager.get().disableFlashLight();
                }
                isFlash = !isFlash;
                break;
        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void initScan() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    private void destroyScan() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();

    }

    private void GenPermissionFailDialog(final Context context, String title, String msg) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if (!StringUtil.isEmpty(title)) {
            dialog.setTitle(title);
        }
        dialog.setMessage(msg);
        dialog.create();
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                QrCodeActivity.this.finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void encryptSuccess(AesBean bean) {

        PaymentActivity.enter(this, bean.getPhone());
        finish();
    }

    @Override
    public void encryptFail() {
        destroyScan();
        initScan();
    }
}
