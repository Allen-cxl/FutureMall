package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.UpdateLoginPasswordContract;
import com.futuremall.android.presenter.UpdateLoginPasswordPresenter;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.util.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdateLoginPasswordActivity extends BaseActivity<UpdateLoginPasswordPresenter> implements UpdateLoginPasswordContract.View, TextWatcher{


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.et_second_new_password)
    EditText mEtSecondNewPassword;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    String mNewPassword;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_update_login_password;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.update_login_password), true);
        mEtOldPassword.addTextChangedListener(this);
        mEtNewPassword.addTextChangedListener(this);
        mEtSecondNewPassword.addTextChangedListener(this);
    }

    @Override
    public void updateResponse(UserInfo value) {

        PreferencesFactory.getUserPref().saveUserInfo(value);
        finish();
    }

    @OnClick(R.id.tv_submit)
    public void onClick() {

        SystemUtil.hideKeyboard(this);
        String oldPassword = mEtOldPassword.getText().toString();
        String newPassword = mEtNewPassword.getText().toString();
        String surePassword = mEtSecondNewPassword.getText().toString();
        if(checkPara(oldPassword, newPassword, surePassword)){
            mPresenter.updatePassword(oldPassword,  mNewPassword);
        }

    }

    private boolean checkPara(String oldPassword, String newPassword, String surePassword) {


        if (StringUtil.isEmpty(oldPassword)) {
            SnackbarUtil.show(mTvSubmit, getString(R.string.enter_old_password));
            return false;
        }

        if (StringUtil.isEmpty(newPassword)) {
            SnackbarUtil.show(mTvSubmit, getString(R.string.enter_new_password));
            return false;
        }

        if (StringUtil.isEmpty(surePassword)) {
            SnackbarUtil.show(mTvSubmit, getString(R.string.again_enter_new_password));
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String oldPassword = mEtOldPassword.getText().toString();
        String newPassword = mEtNewPassword.getText().toString();
        String surePassword = mEtSecondNewPassword.getText().toString();

        if (!StringUtil.isEmpty(oldPassword) &&
                !StringUtil.isEmpty(newPassword) &&
                !StringUtil.isEmpty(surePassword)) {
            mTvSubmit.setSelected(true);
        }else{
            mTvSubmit.setSelected(false);
        }
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, UpdateLoginPasswordActivity.class);
        context.startActivity(intent);
    }
}
