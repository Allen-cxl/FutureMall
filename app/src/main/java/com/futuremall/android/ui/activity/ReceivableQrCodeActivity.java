package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.ReceivableContract;
import com.futuremall.android.presenter.ReceivablePresenter;
import com.futuremall.android.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceivableQrCodeActivity extends BaseActivity<ReceivablePresenter> implements ReceivableContract.View  {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.tv_save_img)
    TextView mTvSaveImg;
    Bitmap mBitmap;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_receivable_qr_code;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.receivable), true);
        String aesPhone = getIntent().getStringExtra(Constants.IT_AES_PHONE);
        mPresenter.canvasQrCode(aesPhone);
    }

    @Override
    public void qrCodeBitmap(Bitmap bitmap) {

        mBitmap = bitmap;
        mTvSaveImg.setVisibility(View.VISIBLE);
        mIvImg.setImageBitmap(bitmap);
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

        mPresenter.saveQrCode(mBitmap);
    }

    public static void enter(Context context, String aesPhone) {

        Intent intent = new Intent(context, ReceivableQrCodeActivity.class);
        intent.putExtra(Constants.IT_AES_PHONE, aesPhone);
        context.startActivity(intent);
    }
}
