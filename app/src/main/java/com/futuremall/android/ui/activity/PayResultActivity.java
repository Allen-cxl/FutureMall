package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;

import butterknife.BindView;

public class PayResultActivity extends SimpleActivity {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.tv_txt)
    TextView mTvTxt;
    private int mStatusActivity, mStatus;

    @Override
    protected int getLayout() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initEventAndData() {

        mStatusActivity = getIntent().getIntExtra(Constants.IT_TYPE, Constants.ACTIVITY_PAY);
        mStatus = getIntent().getIntExtra(Constants.IT_STATUS, Constants.FAIL);

        initView(mStatusActivity, mStatus);
    }

    private void initView(int statusActivity, int status) {

        if (statusActivity == Constants.ACTIVITY_PAY) {

            if (status == Constants.SUCCESS) {
                setToolBar(mSuperToolbar, getString(R.string.pay_result), false);
                mIvImg.setImageResource(R.drawable.success);
                mTvTxt.setText(R.string.pay_success);
            } else {
                setToolBar(mSuperToolbar, getString(R.string.pay_result), true);
                mIvImg.setImageResource(R.drawable.fail);
                mTvTxt.setText(R.string.pay_fail);
            }
        } else if (statusActivity == Constants.ACTIVITY_TRANSFER) {

            if (status == Constants.SUCCESS) {
                setToolBar(mSuperToolbar, getString(R.string.pay_result), false);
                mIvImg.setImageResource(R.drawable.success);
                mTvTxt.setText(R.string.transfer_success);
            } else {
                setToolBar(mSuperToolbar, getString(R.string.pay_result), true);
                mIvImg.setImageResource(R.drawable.fail);
                mTvTxt.setText(R.string.transfer_fail);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mStatusActivity == Constants.ACTIVITY_PAY && mStatus == Constants.SUCCESS){
            getMenuInflater().inflate(R.menu.menu_done, menu);
            return true;
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:

                finish();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mStatus == Constants.SUCCESS){
            return;
        }
        super.onBackPressed();
    }

    public static void enter(Context context, int statusActivity, int status) {

        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra(Constants.IT_TYPE, statusActivity);
        intent.putExtra(Constants.IT_STATUS, status);
        context.startActivity(intent);
    }
}