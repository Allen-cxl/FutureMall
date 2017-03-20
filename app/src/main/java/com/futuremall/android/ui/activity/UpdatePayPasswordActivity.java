package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.UpdatePayPasswordContract;
import com.futuremall.android.presenter.UpdatePayPasswordPresenter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.util.SystemUtil;
import com.futuremall.android.util.TimeUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePayPasswordActivity extends BaseActivity<UpdatePayPasswordPresenter> implements UpdatePayPasswordContract.View, TextWatcher{


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    String mNewPassword;
    TimeUtils.TimeCount mTimeCount;

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_update_pay_password;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mSuperToolbar, getString(R.string.update_pay_password), true);
        mEtPhone.addTextChangedListener(this);
        mEtCode.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
        mEtNewPassword.addTextChangedListener(this);
    }

    @Override
    public void codeResponse() {
        isSMSResultSuccess();
    }

    private boolean checkPara(String phone, String code, String newPassword, String surePassword) {

        if (StringUtil.isEmpty(phone)) {
            SnackbarUtil.show(mTvSubmit, getString(R.string.enter_other_account));
            return false;
        }

        if (StringUtil.isEmpty(code)) {
            SnackbarUtil.show(mTvSubmit, getString(R.string.get_other_name));
            return false;
        }

        if (StringUtil.isEmpty(newPassword)) {
            SnackbarUtil.show(mTvSubmit, getString(R.string.enter_cash_money));
            return false;
        }

        if (StringUtil.isEmpty(surePassword)) {
            SnackbarUtil.show(mTvSubmit, getString(R.string.enter_pay_password));
            return false;
        }

        if(!newPassword.equals(surePassword)){
            SnackbarUtil.show(mTvSubmit, getString(R.string.password_difference));
            return false;
        }else{
            mNewPassword = surePassword;
        }

        return true;
    }

    @OnClick({R.id.tv_code, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_code:

                String phone1 = mEtPhone.getText().toString();
                SystemUtil.hideKeyboard(this);
                if(!StringUtil.isEmpty(phone1)){
                    mPresenter.getCode(phone1, Constants.UPDATE_PAY_PASSWORD);
                }
                break;
            case R.id.tv_submit:
                String phone2 = mEtPhone.getText().toString();
                String code = mEtCode.getText().toString();
                String newPassword = mEtPassword.getText().toString();
                String surePassword = mEtNewPassword.getText().toString();
                if(checkPara(phone2, code, newPassword, surePassword)){
                    mPresenter.updatePassword(mNewPassword, code);
                }
                break;
        }
    }

    private void isSMSResultSuccess() {

        mTimeCount = new TimeUtils.TimeCount(60000, 1000, mTvCode);
        mTimeCount.start();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String phone = mEtPhone.getText().toString();
        String code = mEtCode.getText().toString();
        String newPassword = mEtPassword.getText().toString();
        String surePassword = mEtNewPassword.getText().toString();

        if (!StringUtil.isEmpty(phone) &&
                !StringUtil.isEmpty(code) &&
                !StringUtil.isEmpty(newPassword) &&
                !StringUtil.isEmpty(surePassword) ) {
            mTvSubmit.setSelected(true);
        }else{
            mTvSubmit.setSelected(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mTimeCount) {
            mTimeCount.cancel();
            mTimeCount = null;
        }
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, UpdatePayPasswordActivity.class);
        context.startActivity(intent);
    }
}
