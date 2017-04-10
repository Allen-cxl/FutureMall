package com.futuremall.android.presenter;

import android.app.Activity;
import android.content.Intent;
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
import com.futuremall.android.util.RxUtil;
import com.futuremall.android.util.StringUtil;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

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
                        if(null != value && !StringUtil.isEmpty(value.getUrl())){
                            mView.qrCodeResponse(value.getUrl());
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void saveQrCode(String imageUrl) {

        saveImageView(imageUrl);
    }

    private void saveImageView(final String imageUrl) {

        Flowable.just(imageUrl)
                .flatMap(new Function<String, Publisher<?>>() {
                    @Override
                    public Publisher<Object> apply(final String imgUrl) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(FlowableEmitter<Object> e)throws Exception {

                                File imageFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() +".jpg");
                                FileOutputStream fos = new FileOutputStream(imageFile);
                                URL url = new URL(imgUrl);
                                InputStream in = url.openStream();

                                int len = -1;
                                byte[] b = new byte[1024];
                                while ((len = in.read(b)) != -1) {
                                    fos.write(b, 0, len);
                                }
                                fos.close();
                                in.close();
                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                Uri uri = Uri.fromFile(imageFile);
                                intent.setData(uri);
                                mContext.sendBroadcast(intent);
                                e.onNext(Environment.getExternalStorageDirectory().getPath());
                                e.onComplete();
                            }
                        },BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object integer){
                        mView.saveSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable){
                        mView.saveFail();
                    }
                });
    }
}
