package com.futuremall.android.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.AddressBean;
import com.futuremall.android.model.bean.PayOrderInfoBean;
import com.futuremall.android.presenter.Contract.PayOrderContract;
import com.futuremall.android.presenter.PayOrderPresenter;
import com.futuremall.android.ui.adapter.DividerItemDecoration;
import com.futuremall.android.ui.adapter.PayOrderInfoAdapter;
import com.futuremall.android.ui.listener.OnTextDialogListener;
import com.futuremall.android.widget.BottomSheetDialog;
import com.futuremall.android.widget.LoadingLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class PayOrderActivity extends BaseActivity<PayOrderPresenter> implements PayOrderContract.View, OnTextDialogListener{

        @BindView(R.id.super_toolbar)
        Toolbar mSuperToolbar;
        @BindView(R.id.recycleView)
        RecyclerView mRecycleView;
        @BindView(R.id.tv_total_price)
        TextView mTvTotalPrice;
        @BindView(R.id.loading_layout)
        LoadingLayout loadingLayout;
        PayOrderInfoAdapter mAdapter;
        String mRecID, addressID;
        AddressBean mAddressBean;

        @Override
        protected void initInject() {
            getActivityComponent().inject(this);
        }

        @Override
        protected int getLayout() {
            return R.layout.activity_pay_order;
        }

        @Override
        protected void initEventAndData() {

            mRecID = getIntent().getStringExtra(Constants.IT_RECID);
            setToolBar(mSuperToolbar, getString(R.string.pay_order), true);
            mLoadingLayout = loadingLayout;

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mAdapter = new PayOrderInfoAdapter(this);
            mRecycleView.addItemDecoration(new DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL_LIST));
            mRecycleView.setLayoutManager(linearLayoutManager);

            mAdapter.setIsFirst(true);
            mRecycleView.setAdapter(mAdapter);

            mPresenter.getPayOrderInfo(mRecID);
            mLoadingLayout.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.getPayOrderInfo(mRecID);
                }
            });
        }

        @Override
        public void payOrderInfo(PayOrderInfoBean payOrderInfoBean) {

            String price = String.format(getString(R.string.price), payOrderInfoBean.getTotal_price());
            mTvTotalPrice.setText(String.format(getString(R.string.amount_price), price));
            mAddressBean = payOrderInfoBean.getAddress();
            mAdapter.setFirstViewData(mAddressBean);
            mAdapter.setData(payOrderInfoBean.getCart());
        }

        @Override
        public void submitOrderResponse() {


        }

        public static void enter(Context context, String recID) {
            Intent intent = new Intent(context, PayOrderActivity.class);
            intent.putExtra(Constants.IT_RECID, recID);
            context.startActivity(intent);
        }

        @OnClick(R.id.tv_submit)
        public void onClick() {

            new BottomSheetDialog(this, R.style.transparentDialog, this).show();
        }

        @Override
        public void onText(String txt) {
            mPresenter.submitOrder(mRecID, "", txt);
        }

}
