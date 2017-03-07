package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;

import javax.inject.Inject;

/**
 * Created by PVer on 2017/3/7.
 */

public class OrderCenterPresenter extends RxPresenter<OrderCenterContract.View> implements OrderCenterContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    OrderCenterPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void orderList() {

    }
}
