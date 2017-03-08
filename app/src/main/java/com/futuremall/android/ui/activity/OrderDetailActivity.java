package com.futuremall.android.ui.activity;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.presenter.Contract.OrderDetailContract;
import com.futuremall.android.presenter.OrderDetailPresenter;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showContent(OrderDetail dataList) {

    }
}
