package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.TransferContract;
import com.futuremall.android.presenter.TransferPresenter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.util.SystemUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class TransferActivity extends BaseActivity<TransferPresenter> implements TransferContract.View, TextWatcher{


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_integral)
    EditText mEtIntegral;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_next)
    TextView mTvNext;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_transfer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return false;
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

        setToolBar(mSuperToolbar, getString(R.string.transfer), true);
        mEtAccount.addTextChangedListener(this);
        mEtIntegral.addTextChangedListener(this);
        mEtPassword.addTextChangedListener(this);
    }

    @Override
    public void transferResponse() {

    }

    @Override
    public void userName(String name) {
        mTvName.setText(name);
    }

    private boolean checkPara(String phone, String name, String integral, String password) {

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

        return true;
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, TransferActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_next)
    public void onClick() {

        String account = mEtAccount.getText().toString();
        String name = mTvName.getText().toString();
        String integral = mEtIntegral.getText().toString();
        String password = mEtPassword.getText().toString();
        if (checkPara(account, name, integral, password)) {
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
                !StringUtil.isEmpty(password) ) {
            mTvNext.setSelected(true);
        }else{
            mTvNext.setSelected(false);
        }
    }
}
