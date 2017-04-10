package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.presenter.Contract.OrderDetailContract;
import com.futuremall.android.presenter.OrderDetailPresenter;
import com.futuremall.android.ui.adapter.DividerItemDecoration;
import com.futuremall.android.ui.adapter.OrderDetailAdapter;
import com.futuremall.android.ui.listener.OnTextDialogListener;
import com.futuremall.android.ui.listener.OnTextListener;
import com.futuremall.android.widget.BottomSheetDialog;
import com.futuremall.android.widget.LoadingLayout;

import butterknife.BindView;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View,OnTextDialogListener, OnTextListener {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;

    OrderDetailAdapter mAdapter;
    String mOrderID;

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

        mOrderID = getIntent().getStringExtra(Constants.IT_ORDER_ID);
        setToolBar(mSuperToolbar, getString(R.string.order_detail), true);
        mLoadingLayout = loadingLayout;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OrderDetailAdapter(this, this);
        mRecycleView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL_LIST));
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(mAdapter);
        mLoadingLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.orderDetail(mOrderID);
            }
        });
        mPresenter.orderDetail(mOrderID);
    }

    @Override
    public void affirmOrderResponse() {
        mPresenter.orderDetail(mOrderID);
    }

    @Override
    public void showData(OrderDetail dataList) {

        mAdapter.setData(dataList);
    }

    public static void enter(Context context, String orderID) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(Constants.IT_ORDER_ID, orderID);
        context.startActivity(intent);
    }

    @Override
    public void onTextClick() {

        new BottomSheetDialog(this, R.style.transparentDialog, this).show();
    }

    @Override
    public void onText(String txt) {
        mPresenter.affirmOrder(mOrderID, txt);
    }
}
