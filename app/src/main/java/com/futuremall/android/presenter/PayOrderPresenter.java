package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.R;
import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.PayOrderInfoBean;
import com.futuremall.android.model.event.AddressEvent;
import com.futuremall.android.model.event.ShoppingCartPayEvent;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.PayOrderContract;
import com.futuremall.android.ui.activity.AddressWebViewActivity;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/4/2.
 */

public class PayOrderPresenter extends RxPresenter<PayOrderContract.View> implements PayOrderContract.Presenter  {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    PayOrderPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }

    @Override
    public void getPayOrderInfo(String recID) {

        mView.showLoading();
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.payOrderInfo(accessToken, recID)
                .compose(RxUtil.<MyHttpResponse<PayOrderInfoBean>>rxSchedulerHelper())
                .compose(RxUtil.<PayOrderInfoBean>handleMyResult())
                .subscribe(new Consumer<PayOrderInfoBean>() {
                    @Override
                    public void accept(PayOrderInfoBean value) {
                        LoadingStateUtil.close();
                        mView.showLoading();
                        if(null == value){
                            mView.showEmpty();
                        }else{
                            mView.showContent();
                            mView.payOrderInfo(value);
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                        mView.showError();
                    }

                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void submitOrder(String recID, String addressID, String payPass) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.submitOrder(accessToken, recID, addressID, payPass)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LoadingStateUtil.close();
                        mView.paySuccess();
                        RxBus.getDefault().post(new ShoppingCartPayEvent());
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();

                    }
                    public void onErrorMsg(String msg) {
                        mView.payFail(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void goAddress() {
        String accessToken = PreferencesFactory.getUserPref().getToken();
        AddressWebViewActivity.enter(mContext, mContext.getString(R.string.receive_address), accessToken);
        registerEvent();
    }

    private void registerEvent() {
        Disposable rxSubscription = RxBus.getDefault().toObservable(AddressEvent.class)
                .compose(RxUtil.<AddressEvent>rxSchedulerHelper())
                .subscribe(new Consumer<AddressEvent>() {
                    @Override
                    public void accept(AddressEvent address) {
                        mView.defaultAddress();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
