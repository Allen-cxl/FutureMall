package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.presenter.Contract.OrderDetailContract;
import com.futuremall.android.presenter.OrderDetailPresenter;
import com.futuremall.android.ui.adapter.DividerItemDecoration;
import com.futuremall.android.ui.adapter.OrderDetailAdapter;

import butterknife.BindView;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;

    OrderDetailAdapter mAdapter;

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

        setToolBar(mSuperToolbar, getString(R.string.order_detail), true);

        mAdapter = new OrderDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OrderDetailAdapter(this);
        mRecycleView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        mPresenter.orderDetail();
    }

    @Override
    public void showContent(OrderDetail dataList) {

        mAdapter.setData(dataList);
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        context.startActivity(intent);
    }
}
