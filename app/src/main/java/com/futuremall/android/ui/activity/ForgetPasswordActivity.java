package com.futuremall.android.ui.activity;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.ForgetPasswordContract;
import com.futuremall.android.presenter.ForgetPasswordPresenter;

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordContract.View {


    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void updateSuccess() {

    }
}
