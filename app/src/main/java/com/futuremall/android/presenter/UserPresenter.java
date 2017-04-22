package com.futuremall.android.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.UserContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.util.RxUtil;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * Created by Allen on 2017/3/3.
 */

public class UserPresenter extends RxPresenter<UserContract.View> implements UserContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    UserPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;

        registerEvent();
    }

    private void registerEvent() {
        Disposable rxSubscription = RxBus.getDefault().toObservable(UserInfo.class)
                .compose(RxUtil.<UserInfo>rxSchedulerHelper()).subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) {
                        if(PreferencesFactory.getUserPref().isLogin()){
                            userInfo(false);
                        }else{
                            mView.showRegisterLayout();
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void userInfo(boolean isLogin) {
        if(!isLogin){
            LoadingStateUtil.show(mContext);
        }
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable disposable = mRetrofitHelper.userInfo(token)
                .compose(RxUtil.<MyHttpResponse<UserInfo>>rxSchedulerHelper())
                .compose(RxUtil.<UserInfo>handleMyResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo value) {
                        LoadingStateUtil.close();
                        if(value!=null){
                            mView.showLoginLayout();
                            mView.showUserInfo(value);
                            PreferencesFactory.getUserPref().saveUserInfo(value);
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(disposable);
    }

    @Override
    public void showLayout() {

        if(PreferencesFactory.getUserPref().isLogin()){
            mView.showLoginLayout();
            userInfo(true);
        }else{
            mView.showRegisterLayout();
        }
    }

    @Override
    public void saveImageView(String imgUrl, final String fileName) {

        Flowable.just(imgUrl)
                .flatMap(new Function<String, Publisher<?>>() {
                    @Override
                    public Publisher<String> apply(final String bmp) throws Exception {

                        return Flowable.create(new FlowableOnSubscribe<String>() {
                            @Override
                            public void subscribe(FlowableEmitter<String> e) throws Exception {

                                URL url = new URL(bmp);
                                InputStream ips = url.openStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(ips);

                                File imageFile = new File(mContext.getExternalCacheDir().getPath(), fileName + ".jpg");
                                FileOutputStream outStream;
                                outStream = new FileOutputStream(imageFile);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                outStream.flush();
                                outStream.close();
                                e.onNext(imageFile.getAbsolutePath());
                                e.onComplete();
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object filePath) {
                        PreferencesFactory.getUserPref().saveMallUserAvatarFile(filePath.toString());
                    }
                });
    }

}
