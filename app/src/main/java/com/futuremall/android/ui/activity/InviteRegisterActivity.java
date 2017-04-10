package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.InviteRegisterContract;
import com.futuremall.android.presenter.InviteRegisterPresenter;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteRegisterActivity extends BaseActivity<InviteRegisterPresenter> implements InviteRegisterContract.View {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.tv_save_img)
    TextView mTvSaveImg;
    String mImgUrl;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_invite_register;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mSuperToolbar, getString(R.string.invite_register), true);
        mPresenter.qrCode();
    }

    @Override
    public void qrCodeResponse(String imgUrl) {

        mImgUrl = imgUrl;
        mTvSaveImg.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext()).load(mImgUrl).into(mIvImg);
    }

    @Override
    public void saveSuccess() {
        SnackbarUtil.show(mIvImg, "保存成功");
    }

    @Override
    public void saveFail() {
        SnackbarUtil.show(mIvImg, "保存失败");
    }

    @OnClick(R.id.tv_save_img)
    public void onClick() {

        mPresenter.saveQrCode(mImgUrl);
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, InviteRegisterActivity.class);
        context.startActivity(intent);
    }
}
