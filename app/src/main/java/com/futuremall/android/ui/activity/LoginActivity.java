package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.LoginContract;
import com.futuremall.android.presenter.LoginPresenter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mSuperToolbar, getString(R.string.login), true);
    }

    @Override
    public void loginResponse(UserInfo userInfo) {
        PreferencesFactory.getUserPref().saveUserInfo(userInfo);
        finish();
    }

    @OnClick({R.id.tv_login, R.id.tv_register, R.id.tv_forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                String phone = mEtPhone.getText().toString();
                String password = mEtPassword.getText().toString();
                //15868320634   123123
                if(checkPara(phone, password)){
                    mPresenter.login(phone, password);/*
                    UserInfo userInfo= new UserInfo();
                    userInfo.setAccess_token("97b1ead989d63eeeb93f053aa05dc2e7");
                    PreferencesFactory.getUserPref().saveUserInfo(userInfo);*/
                }
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_forget_password:
                break;
        }
    }

    private boolean checkPara(String phone, String password){

        if(StringUtil.isEmpty(phone)){
            SnackbarUtil.show(mTvLogin, getString(R.string.enter_id_phone));
            return false;
        }

        if(StringUtil.isEmpty(password)){
            SnackbarUtil.show(mTvLogin, getString(R.string.enter_password));
            return false;
        }

        return true;
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
