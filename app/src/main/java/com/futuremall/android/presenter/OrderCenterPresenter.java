package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.presenter.Contract.OrderCenterContract;
import com.futuremall.android.util.TestData;

import java.util.List;

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
        List<OrderList> dataList = TestData.getOrderList();
        mView.showContent(dataList);
    }
}
