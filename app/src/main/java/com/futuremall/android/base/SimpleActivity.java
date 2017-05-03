package com.futuremall.android.base;

import android.app.Activity;

import com.futuremall.android.R;
import com.futuremall.android.app.App;
import com.futuremall.android.di.component.ActivityComponent;
import com.futuremall.android.di.component.DaggerActivityComponent;
import com.futuremall.android.di.module.ActivityModule;
import com.futuremall.android.util.SystemUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by codeest on 16/8/11.
 * 无MVP的activity基类
 */

public abstract class SimpleActivity extends AppCompatActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        App.getInstance().addActivity(this);
        initEventAndData();
    }

    protected void setToolBar(Toolbar toolbar, String title, boolean showBackButton) {
        mTextView = (TextView)(findViewById(R.id.super_title));

        if(title != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }
        if(showBackButton){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

        setTitle(title);

    }

    protected void setTitle(String title){
        if(null != mTextView && null != title){
            mTextView.setText(title);
        }
    }

    protected ActivityComponent getActivityComponent(){
        return  DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    protected void initInject(){
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        SystemUtil.hideKeyboard(this);
        mUnBinder.unbind();
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
