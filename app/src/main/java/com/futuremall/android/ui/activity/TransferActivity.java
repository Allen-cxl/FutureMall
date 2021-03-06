package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.presenter.Contract.TransferContract;
import com.futuremall.android.presenter.TransferPresenter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class TransferActivity extends BaseActivity<TransferPresenter> implements TransferContract.View, TextWatcher {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_transfer_integral)
    TextView mTvTransferIntegral;
    @BindView(R.id.et_integral)
    EditText mEtIntegral;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_next)
    TextView mTvNext;
    BalanceBean mBean;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.transfer), true);
        mEtAccount.addTextChangedListener(mTextWatcher);
        mEtIntegral.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mPresenter.getBalance();
    }

    @Override
    public void transferSuccess() {
        PayResultActivity.enter(this, Constants.ACTIVITY_TRANSFER, Constants.SUCCESS, null);
        finish();
    }

    @Override
    public void transferFail(String msg) {
        PayResultActivity.enter(this, Constants.ACTIVITY_TRANSFER, Constants.FAIL, msg);
    }

    @Override
    public void balance(BalanceBean bean) {
        mBean = bean;
        mTvTransferIntegral.setText(String.format(getString(R.string.transfer_last_integral), mBean.getUser_money()));
    }

    @Override
    public void userName(String name) {
        mTvName.setText(name);
    }

    private boolean checkPara(String phone, String name, String integral, String password, double payIntegral, double totalIntegral) {

        if (StringUtil.isEmpty(phone)) {
            SnackbarUtil.show(mTvNext, getString(R.string.enter_other_account));
            return false;
        }

        if (StringUtil.isEmpty(name)) {
            SnackbarUtil.show(mTvNext, getString(R.string.get_other_name));
            return false;
        }

        if (StringUtil.isEmpty(integral)) {
            SnackbarUtil.show(mTvNext, getString(R.string.enter_integral));
            return false;
        }

        if (StringUtil.isEmpty(password)) {
            SnackbarUtil.show(mTvNext, getString(R.string.enter_pay_password));
            return false;
        }

        if (payIntegral > totalIntegral) {
            SnackbarUtil.show(mTvNext, getString(R.string.integral_tip));
            return false;
        }
        return true;
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, TransferActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_next)
    public void onClick() {

        if(null == mBean)return;
        String account = mEtAccount.getText().toString();
        String name = mTvName.getText().toString();
        String integral = mEtIntegral.getText().toString();
        String password = mEtPassword.getText().toString();
        double payIntegral = Double.parseDouble(integral);
        double totalIntegral = Double.parseDouble(mBean.getUser_money());
        if (checkPara(account, name, integral, password, payIntegral, totalIntegral)) {
            mPresenter.transfer(account, name, integral, password);
        }
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
        String integral = mEtIntegral.getText().toString();
        String password = mEtPassword.getText().toString();
        if (!StringUtil.isEmpty(account) &&
                !StringUtil.isEmpty(name) &&
                !StringUtil.isEmpty(integral) &&
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
            String cashMoney = mEtIntegral.getText().toString();
            String password = mEtPassword.getText().toString();
            if (!StringUtil.isEmpty(account) &&
                    !StringUtil.isEmpty(name) &&
                    !StringUtil.isEmpty(cashMoney) &&
                    !StringUtil.isEmpty(password)) {
                mTvNext.setSelected(true);
            } else {
                mTvNext.setSelected(false);
            }

            if (account.length() == 11) {
                mPresenter.userName(account);
            }
        }
    };
}
