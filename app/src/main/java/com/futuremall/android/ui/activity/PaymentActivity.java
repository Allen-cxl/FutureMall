package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity<PaymentPresenter> implements PaymentContract.View, TextWatcher, View.OnFocusChangeListener {


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
    @BindView(R.id.ll_layout)
    LinearLayout mLayout;
    String mPhone, mAesPhone;
    BalanceBean mBean;

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

                QrCodeActivity.enter(this);
                break;

        }
        return false;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.pay_ment), true);
        mAesPhone = getIntent().getStringExtra(Constants.IT_AES_PHONE);
        mPhone = getIntent().getStringExtra(Constants.IT_PHONE);
        mEtAccount.addTextChangedListener(this);
        mEtAccount.setOnFocusChangeListener(this);
        mEtCashMoney.addTextChangedListener(mTextWatcher);
        mEtPassword.addTextChangedListener(this);
        mEtAccount.setText(mPhone);
        mPresenter.getBalance();
    }

    @Override
    public void shopName(String name) {

        mTvName.setText(name);
    }

    @Override
    public void balance(BalanceBean bean) {
        mBean = bean;
        mTvPayMultiple.setText(String.format(getString(R.string.current_os_multiple), mBean.getPay_ratio()));
        mTvCurrentIntegral.setText(String.format(getString(R.string.current_integral), mBean.getUser_money()));
    }

    @Override
    public void gotoSetPayPasswordUI() {
        UpdatePayPasswordActivity.enter(this, Constants.ACTIVITY_PAY_MENT_SET);
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
    public void showQrCode() {
        mLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_next, R.id.tv_qrCode})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_next:

                if(null == mBean)return;
                String account = mEtAccount.getText().toString();
                String name = mTvName.getText().toString();
                String cashMoney = mEtCashMoney.getText().toString();
                String password = mEtPassword.getText().toString();
                double payIntegral = Double.parseDouble(mTvPayIntegral.getText().toString());
                double totalIntegral = Double.parseDouble(mBean.getUser_money());
                if (checkPara(account, name, cashMoney, password, payIntegral, totalIntegral)) {
                    mPresenter.payment(account, cashMoney, password);
                }
                break;

            case R.id.tv_qrCode:

                if(null != mBean && !StringUtil.isEmpty(mBean.getPay_phone())){
                    ReceivableQrCodeActivity.enter(this, mBean.getPay_phone());
                }
                break;
        }

    }

    private boolean checkPara(String phone, String name, String cashMoney, String password, double payIntegral, double totalIntegral) {

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

        if (payIntegral > totalIntegral) {
            SnackbarUtil.show(mTvNext, getString(R.string.money_tip));
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

    private TextWatcher mTextWatcher = new TextWatcher() {
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

            if(null != mBean && !StringUtil.isEmpty(mBean.getPay_ratio())){
                double d = Double.parseDouble(cashMoney) *  Double.parseDouble(mBean.getPay_ratio());
                mTvPayIntegral.setText(String.valueOf(d));
            }
        }
    };

    public static void enter(Context context) {

        enter(context, null);
    }

    public static void enter(Context context, String phone) {

        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(Constants.IT_PHONE, phone);
        context.startActivity(intent);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            String account = mEtAccount.getText().toString();
            if(!StringUtil.isEmpty(account)){
                mPresenter.getShopName(account);
            }
        }
    }
}
