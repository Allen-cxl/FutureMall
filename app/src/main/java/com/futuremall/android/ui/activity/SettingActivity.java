package com.futuremall.android.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.ui.ViewHolder.LoginHelper;
import com.futuremall.android.util.FileUtil;
import com.futuremall.android.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends SimpleActivity {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;

    @BindView(R.id.tv_login_out)
    TextView mTvLoginOut;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mSuperToolbar, getString(R.string.setup), true);
        mTvLoginOut.setVisibility(PreferencesFactory.getUserPref().isLogin() ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.tv_update_login_password, R.id.tv_update_pay_password, R.id.tv_clean_cache, R.id.tv_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_login_password:

                if(LoginHelper.ensureLogin(this)){
                    UpdateLoginPasswordActivity.enter(this);
                }
                break;
            case R.id.tv_update_pay_password:

                if(LoginHelper.ensureLogin(this)){
                    UpdatePayPasswordActivity.enter(this);
                }
                break;
            case R.id.tv_clean_cache:

                FileUtil.deleteDir(FileUtil.getFileDirName(this));
                SnackbarUtil.show(mTvLoginOut, "清除完成，一点痕迹都没有留下");
                break;
            case R.id.tv_login_out:

                PreferencesFactory.getUserPref().removeUserInfo();
                finish();
                break;
        }
    }

    public static void enter(Context context) {

        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
}
