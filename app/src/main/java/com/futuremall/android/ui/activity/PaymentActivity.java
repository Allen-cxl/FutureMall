package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.presenter.Contract.PaymentContract;
import com.futuremall.android.presenter.PaymentPresenter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity<PaymentPresenter> implements PaymentContract.View, TextWatcher {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_cash_money)
    EditText mEtCashMoney;
    @BindView(R.id.tv_pay_multiple)
    TextView mTvPayMultiple;
    @BindView(R.id.tv_pay_integral)
    TextView mTvPayIntegral;
    @BindView(R.id.tv_current_integral)
    TextView mTvCurrentIntegral;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    String mIntegral;

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_payment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_scan:


                break;

        }
        return false;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.pay_ment), true);
        mIntegral = getIntent().getStringExtra(Constants.IT_INTEGRAL);
        mEtAccount.addTextChangedListener(this);
        mEtCashMoney.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
    }

    @Override
    public void shopName(String name) {

    }

    @Override
    public void balance(BalanceBean bean) {
        mTvPayIntegral.setText(mIntegral);
        mTvPayMultiple.setText(String.format(getString(R.string.current_os_multiple), bean.getPay_ratio()));
        mTvCurrentIntegral.setText(String.format(getString(R.string.current_integral), bean.getUser_money()));
    }

    @OnClick(R.id.tv_next)
    public void onClick() {

        String account = mEtAccount.getText().toString();
        String name = mTvName.getText().toString();
        String cashMoney = mEtCashMoney.getText().toString();
        String password = mEtPassword.getText().toString();
        if (checkPara(account, name, cashMoney, password)) {
            mPresenter.payment(account, cashMoney, password);
        }
    }

    private boolean checkPara(String phone, String name, String cashMoney, String password) {

        if (StringUtil.isEmpty(phone)) {
            SnackbarUtil.show(mTvNext, getString(R.string.enter_other_account));
            return false;
        }

        if (StringUtil.isEmpty(name)) {
            SnackbarUtil.show(mTvNext, getString(R.string.get_other_name));
            return false;
        }

        if (StringUtil.isEmpty(cashMoney)) {
            SnackbarUtil.show(mTvNext, getString(R.string.enter_cash_money));
            return false;
        }

        if (StringUtil.isEmpty(password)) {
            SnackbarUtil.show(mTvNext, getString(R.string.enter_pay_password));
            return false;
        }

        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String account = mEtAccount.getText().toString();
        String name = mTvName.getText().toString();
        String cashMoney = mEtCashMoney.getText().toString();
        String password = mEtPassword.getText().toString();
        if (!StringUtil.isEmpty(account) &&
                !StringUtil.isEmpty(name) &&
                !StringUtil.isEmpty(cashMoney) &&
                !StringUtil.isEmpty(password)) {
            mTvNext.setSelected(true);
        } else {
            mTvNext.setSelected(false);
        }
    }

    public static void enter(Context context, String integral) {

        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(Constants.IT_INTEGRAL, integral);
        context.startActivity(intent);
    }
}
