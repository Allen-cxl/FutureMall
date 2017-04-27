package com.futuremall.android.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.InviteRegisterContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.QRCodeUtil;
import com.futuremall.android.util.RxUtil;
import com.futuremall.android.util.StringUtil;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PVer on 2017/4/3.
 */

public class InviteRegisterPresenter extends RxPresenter<InviteRegisterContract.View> implements InviteRegisterContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    InviteRegisterPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void qrCode() {

        LoadingStateUtil.show(mContext);
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.inviteRegister(token)
                .compose(RxUtil.<MyHttpResponse<UserInfo>>rxSchedulerHelper())
                .compose(RxUtil.<UserInfo>handleMyResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo value) {
                        LoadingStateUtil.close();
                        if (null != value && !StringUtil.isEmpty(value.getInvite_url())) {
                            mView.qrCodeResponse(value.getInvite_url());
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void saveQrCode(Bitmap bitmap) {

        if (null == bitmap) {
            return;
        }
        saveImageView(bitmap);
    }

    @Override
    public void canvasQrCode(String content) {

        String avatarFile = PreferencesFactory.getUserPref().getMallUserAvatarFile();
        avatarBitmap(content, StringUtil.isEmpty(avatarFile)?"":avatarFile);
    }

    private void saveImageView(Bitmap bitmap) {

        Flowable.just(bitmap)
                .flatMap(new Function<Bitmap, Publisher<?>>() {
                    @Override
                    public Publisher<Object> apply(final Bitmap bitmap) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(FlowableEmitter<Object> e) throws Exception {

                                File imageFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                                FileOutputStream outStream;
                                outStream = new FileOutputStream(imageFile);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                outStream.flush();
                                outStream.close();
                                e.onNext(Environment.getExternalStorageDirectory().getPath());
                                e.onComplete();

                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                Uri uri = Uri.fromFile(imageFile);
                                intent.setData(uri);
                                mContext.sendBroadcast(intent);
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer) {
                        mView.saveSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.saveFail();
                    }
                });
    }

    private void avatarBitmap(final String content, String avatar) {

        Flowable.just(avatar)
                .flatMap(new Function<String, Publisher<Bitmap>>() {
                    @Override
                    public Publisher<Bitmap> apply(final String imgUrl) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<Bitmap>() {
                            @Override
                            public void subscribe(FlowableEmitter<Bitmap> e) throws Exception {
                                Bitmap bitmap = null;
                                if(null != imgUrl){
                                    bitmap = BitmapFactory.decodeFile(imgUrl);
                                }
                                e.onNext(bitmap);
                                e.onComplete();
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) {

                        Bitmap bmp = QRCodeUtil.createQRImage(content, 200, 200, bitmap);
                        if (null != bitmap) {
                            mView.qrCodeBitmap(bmp);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Bitmap bmp = QRCodeUtil.createQRImage(content, 200, 200, null);
                        if (null != bmp) {
                            mView.qrCodeBitmap(bmp);
                        }
                    }
                });

    }


}
