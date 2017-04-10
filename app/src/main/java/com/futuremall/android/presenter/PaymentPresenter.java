package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.model.bean.ShopBean;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.PaymentContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/3/19.
 */

public class PaymentPresenter extends RxPresenter<PaymentContract.View> implements PaymentContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    PaymentPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void getShopName(String phone) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.shopName(accessToken, phone)
                .compose(RxUtil.<MyHttpResponse<ShopBean>>rxSchedulerHelper())
                .compose(RxUtil.<ShopBean>handleMyResult())
                .subscribe(new Consumer<ShopBean>() {
                    @Override
                    public void accept(ShopBean value) {
                        mView.shopName(value.getShop_name());
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

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.getBalance(accessToken)
                .compose(RxUtil.<MyHttpResponse<BalanceBean>>rxSchedulerHelper())
                .compose(RxUtil.<BalanceBean>handleMyResult())
                .subscribe(new Consumer<BalanceBean>() {
                    @Override
                    public void accept(BalanceBean value) {
                        LoadingStateUtil.close();
                        mView.balance(value);
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void payment(String phone, String money, String password) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.payment(accessToken, phone, money, password)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LoadingStateUtil.close();
                        mView.paySuccess();
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                        mView.payFail();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
