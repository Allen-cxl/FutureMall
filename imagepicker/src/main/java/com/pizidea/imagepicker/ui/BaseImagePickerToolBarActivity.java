package com.pizidea.imagepicker.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pizidea.imagepicker.R;


/**
 * Created by Allen on 2015/11/27.
 */
public class BaseImagePickerToolBarActivity extends AppCompatActivity {

    private static final String TAG = "BaseToolBarActivity";

    protected TextView mToolbarTitle;
    protected Toolbar mToolbar;
    public Class activityClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar = toolbar;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    protected void setDisplayShowTitleEnabled(boolean enable) {
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ensureTitle();
        if (null != mToolbarTitle) {
            mToolbarTitle.setVisibility(enable ? View.VISIBLE : View.GONE);
        }
    }

    void ensureTitle() {
        if (null == mToolbarTitle) {
            mToolbarTitle = (TextView) mToolbar.findViewById(R.id.super_title);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        setTitle(title, true);
    }

    public void setTitle(CharSequence title, boolean isShowHome) {
        setToolbar(isShowHome);
        ensureTitle();
        if (null != mToolbarTitle) {
            mToolbarTitle.setText(title);
        }
    }
    protected void setToolbar(boolean isShowHome) {
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            if (isShowHome) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }
    }

    public void setTitleEnable(boolean enable) {
        ensureTitle();
        if (null != mToolbarTitle) {
            mToolbarTitle.setEnabled(enable);
        }
    }

    public void setTitleCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        ensureTitle();
        if (null != mToolbarTitle) {
            mToolbarTitle.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
    }

}
