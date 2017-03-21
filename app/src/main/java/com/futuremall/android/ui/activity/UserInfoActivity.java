package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.UserInfoContract;
import com.futuremall.android.presenter.UserInfoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.ll_user_avatar)
    LinearLayout mLlUserAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.user_info), true);
    }

    @OnClick({R.id.ll_user_avatar, R.id.tv_name, R.id.tv_birthday, R.id.tv_sex, R.id.tv_email, R.id.tv_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_user_avatar:
                break;
            case R.id.tv_name:
                ModifyInfoActivity.enter(this, getString(R.string.other_name), mTvName.getText().toString());

                break;
            case R.id.tv_birthday:

                ModifyInfoActivity.enter(this, getString(R.string.other_name), mTvBirthday.getText().toString());
                break;
            case R.id.tv_sex:

                ModifyInfoActivity.enter(this, getString(R.string.other_name), mTvSex.getText().toString());
                break;
            case R.id.tv_email:

                ModifyInfoActivity.enter(this, getString(R.string.other_name), mTvEmail.getText().toString());
                break;
            case R.id.tv_phone:

                ModifyInfoActivity.enter(this, getString(R.string.other_name), mTvPhone.getText().toString());
                break;
        }
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setInfo(String info) {

    }
}
