package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.event.InfoEvent;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.UserInfoContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.util.RxUtil;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UserInfoPresenter extends RxPresenter<UserInfoContract.View> implements UserInfoContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    UserInfoPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
        registerEvent();
    }

    void registerEvent() {
        Disposable rxSubscription = RxBus.getDefault().toObservable(InfoEvent.class)
                .compose(RxUtil.<InfoEvent>rxSchedulerHelper())
                .map(new Function<InfoEvent, String>() {
                    @Override
                    public String apply(InfoEvent InfoEvent) {
                        return InfoEvent.info;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String info) {
                        mView.setInfo(info);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void saveUserInfo(File file, int sex, String birthday, String realName) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.updateUser(accessToken, file, sex, birthday, realName)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LogUtil.d("UserInfo:"+value);
                    }
                }, new CommonConsumer<Object>(mView) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
