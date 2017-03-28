package com.futuremall.android.presenter;

import android.app.Activity;

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

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
                        showLayout();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void userInfo() {
        LoadingStateUtil.show(mContext);
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable disposable = mRetrofitHelper.userInfo(token)
                .compose(RxUtil.<MyHttpResponse<UserInfo>>rxSchedulerHelper())
                .compose(RxUtil.<UserInfo>handleMyResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo value) {
                        LoadingStateUtil.close();
                        if(value!=null){
                            mView.showLoginLayout(value);
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
            userInfo();
        }else{
            mView.showRegisterLayout();
        }
    }

}
