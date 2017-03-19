package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.presenter.Contract.OrderCenterContract;
import com.futuremall.android.presenter.Contract.OrderDetailContract;
import com.futuremall.android.util.TestData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by PVer on 2017/3/7.
 */

public class OrderDetailPresenter extends RxPresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    OrderDetailPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void orderDetail() {
//        OrderDetail deatail = TestData.getOrderDeatail();
//        mView.showContent(deatail);
    }
}
