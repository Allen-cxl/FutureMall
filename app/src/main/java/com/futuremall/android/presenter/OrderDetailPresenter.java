package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.OrderDetailContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/3/7.
 */

public class OrderDetailPresenter extends RxPresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    OrderDetailPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void orderDetail(String orderID) {

        mView.showLoading();
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable disposable = mRetrofitHelper.orderDetail(token, orderID)
                .compose(RxUtil.<MyHttpResponse<OrderDetail>>rxSchedulerHelper())
                .compose(RxUtil.<OrderDetail>handleMyResult())
                .subscribe(new Consumer<OrderDetail>() {
                    @Override
                    public void accept(OrderDetail value) {
                        mView.showLoading();
                        if(null == value){
                            mView.showEmpty();
                        }else{
                            mView.showContent();
                            mView.showData(value);
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        mView.showError();
                    }
                });
        addSubscrebe(disposable);
    }

    @Override
    public void affirmOrder(String orderID, String payPassword) {

        LoadingStateUtil.show(mContext);
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable disposable = mRetrofitHelper.affirmOrder(token, orderID, payPassword)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LoadingStateUtil.close();
                        mView.affirmOrderResponse();
                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(disposable);
    }
}
