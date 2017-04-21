package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.presenter.Contract.RechargeContract;
import com.futuremall.android.presenter.RechargePresenter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity<RechargePresenter> implements RechargeContract.View {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.tv_recharge)
    TextView mTvRecharge;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.recharge), true);
    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, RechargeActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_recharge)
    public void onClick() {

        String amount = mEtMoney.getText().toString();
        if(checkPara(amount)){
            mPresenter.recharge(amount);
        }
    }

    @Override
    public void rechargeSuccess() {
        PayResultActivity.enter(this, Constants.ACTIVITY_RECHARGE, Constants.SUCCESS, null);
        finish();
    }

    @Override
    public void rechargeFail(String msg) {
        PayResultActivity.enter(this, Constants.ACTIVITY_RECHARGE, Constants.FAIL, msg);
    }

    private boolean checkPara(String amount) {

        if (StringUtil.isEmpty(amount) || Double.parseDouble(amount) <= 0)  {
            SnackbarUtil.show(mTvRecharge, getString(R.string.recharge_tip));
            return false;
        }
        return true;
    }

    @Override
    public void showViewMsg(String msg) {
        SnackbarUtil.show(mTvRecharge, msg);
    }
}
