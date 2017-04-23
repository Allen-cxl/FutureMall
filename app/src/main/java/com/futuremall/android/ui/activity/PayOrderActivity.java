package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.futuremall.android.ui.listener.OnItemViewClickListener;
import com.futuremall.android.ui.listener.OnTextDialogListener;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.widget.BottomSheetDialog;
import com.futuremall.android.widget.LoadingLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class PayOrderActivity extends BaseActivity<PayOrderPresenter> implements PayOrderContract.View, OnTextDialogListener, OnItemViewClickListener {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.loading_layout)
    LoadingLayout loadingLayout;
    PayOrderInfoAdapter mAdapter;
    String mRecID, mAddressID;
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
        mAdapter = new PayOrderInfoAdapter(this, this);
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
        mAddressID = mAddressBean.getAddress_id();
        mAdapter.setFirstViewData(mAddressBean);
        mAdapter.setData(payOrderInfoBean.getCart());
    }

    @Override
    public void paySuccess() {
        PayResultActivity.enter(this, Constants.ACTIVITY_PAY, Constants.SUCCESS, null);
        finish();
    }

    @Override
    public void payFail(String msg) {
        PayResultActivity.enter(this, Constants.ACTIVITY_PAY, Constants.FAIL, msg);
    }

    @Override
    public void defaultAddress(AddressBean bean) {
        mAddressBean = bean;
        mAddressID = mAddressBean.getAddress_id();
        mAdapter.setFirstViewData(mAddressBean);
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
        if(checkParam(mRecID, mAddressID, txt)){
            mPresenter.submitOrder(mRecID, mAddressID, txt);
        }

    }

    private boolean checkParam(String recID, String addressID, String payPass){

        if(StringUtil.isEmpty(recID)){
            SnackbarUtil.show(loadingLayout, getString(R.string.add_product));
            return false;
        }

        if(StringUtil.isEmpty(addressID)){
            SnackbarUtil.show(loadingLayout, getString(R.string.add_address));
            return false;
        }

        if(StringUtil.isEmpty(payPass)){
            SnackbarUtil.show(loadingLayout, getString(R.string.enter_pay_password));
            return false;
        }
        return  true;
    }

    @Override
    public void onViewClick() {

        mPresenter.goAddress();
    }
}
