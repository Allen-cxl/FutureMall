package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.event.InfoEvent;
import com.futuremall.android.presenter.Contract.UserInfoContract;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


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
                        return InfoEvent.getInfo();
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
    public void saveUserInfo(UserInfo userInfo) {

    }
}
