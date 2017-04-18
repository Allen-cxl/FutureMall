package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.TransferContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/3/19.
 */

public class TransferPresenter extends RxPresenter<TransferContract.View> implements TransferContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    TransferPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void userName(String phone) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.userName(accessToken, phone)
                .compose(RxUtil.<MyHttpResponse<UserInfo>>rxSchedulerHelper())
                .compose(RxUtil.<UserInfo>handleMyResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo value) {
                        LoadingStateUtil.close();
                        mView.userName(value.getReal_name());
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getBalance() {

        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.getBalance(accessToken)
                .compose(RxUtil.<MyHttpResponse<BalanceBean>>rxSchedulerHelper())
                .compose(RxUtil.<BalanceBean>handleMyResult())
                .subscribe(new Consumer<BalanceBean>() {
                    @Override
                    public void accept(BalanceBean value) {
                        mView.balance(value);
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void transfer(String account, String name, String integral, String password) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.transfer(accessToken, account, integral, password)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LoadingStateUtil.close();
                        mView.transferSuccess();
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();

                    }
                    public void onErrorMsg(String msg) {
                        mView.transferFail(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
