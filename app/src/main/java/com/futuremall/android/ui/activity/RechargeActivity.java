package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.base.SimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeActivity extends SimpleActivity {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.tv_recharge)
    TextView mTvRecharge;

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

        String money = mEtMoney.getText().toString();
    }
}
