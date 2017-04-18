package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.presenter.Contract.UserInfoContract;
import com.futuremall.android.presenter.UserInfoPresenter;
import com.futuremall.android.widget.GlideCircleTransform;
import com.pizidea.imagepicker.AndroidImagePicker;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View,  AndroidImagePicker.OnImageCropCompleteListener {

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
    @BindView(R.id.tv_update_user)
    TextView mTvUpdateUser;

    TextView mTextView;
    File mPicFile;
    AndroidImagePicker mImgPicker;

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

        UserInfo userInfo = getIntent().getParcelableExtra(Constants.IT_USER_INFO);
        mImgPicker = AndroidImagePicker.getInstance();
        mImgPicker.addOnImageCropCompleteListener(this);
        setUserInfo(userInfo);
    }

    @OnClick({R.id.ll_user_avatar, R.id.tv_name, R.id.tv_birthday, R.id.tv_sex, R.id.tv_update_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_user_avatar:
                mPresenter.galleryPic(mImgPicker);
                break;
            case R.id.tv_name:
                mTextView = mTvName;
                ModifyInfoActivity.enter(this, getString(R.string.other_name), mTvName.getText().toString(), Constants.ACTIVITY_TEXT);

                break;
            case R.id.tv_birthday:
                mTextView = mTvBirthday;
                ModifyInfoActivity.enter(this, getString(R.string.birthday), mTvBirthday.getText().toString(), Constants.ACTIVITY_PICKER);
                break;
            case R.id.tv_sex:
                mTextView = mTvSex;
                ModifyInfoActivity.enter(this, getString(R.string.sex), mTvSex.getText().toString(), Constants.ACTIVITY_CHECKBOX);
                break;

            case R.id.tv_update_user:
                mTextView = mTvPhone;
                String name = mTvName.getText().toString();
                String birthday = mTvBirthday.getText().toString();
                String set = mTvSex.getText().toString();

                int sex = -1;
                if(set.equalsIgnoreCase(getString(R.string.man))){
                    sex = UserInfo.SEX_MAN;
                }else if(set.equalsIgnoreCase(getString(R.string.woman))){
                    sex = UserInfo.SEX_WOMAN;
                }
                mPresenter.saveUserInfo(mPicFile, sex, birthday, name);
                break;
        }
    }

    public static void enter(Context context, UserInfo userInfo) {

        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(Constants.IT_USER_INFO, userInfo);
        context.startActivity(intent);
    }

    private void setUserInfo(UserInfo userInfo) {
        if(null == userInfo){
            return;
        }
        Glide.with(mContext.getApplicationContext())
                .load(userInfo.getUser_picture())
                .crossFade()
                .transform(new GlideCircleTransform(mContext.getApplicationContext()))
                .placeholder(R.drawable.default_user)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mIvUserAvatar);
        mTvName.setText(userInfo.getReal_name());
        mTvSex.setText(userInfo.getSex() == UserInfo.SEX_MAN ? R.string.man : R.string.woman);
        mTvBirthday.setText(userInfo.getBirthday());
        mTvEmail.setText(userInfo.getEmail());
        mTvPhone.setText(userInfo.getMobile_phone());
    }

    @Override
    public void setInfo(String info) {

        if(info != null){
            mTextView.setText(info);
            mTvUpdateUser.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void saveSuccess() {
        finish();
    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        if(null != bmp){
            mTvUpdateUser.setVisibility(View.VISIBLE);
            mPicFile = mImgPicker.bitmap2File(bmp, this);
            Glide.with(mContext.getApplicationContext())
                    .load(mPicFile.getAbsolutePath())
                    .crossFade()
                    .transform(new GlideCircleTransform(mContext.getApplicationContext()))
                    .placeholder(R.drawable.default_user)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(mIvUserAvatar);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImgPicker.removeOnImageCropCompleteListener(this);
    }
}
