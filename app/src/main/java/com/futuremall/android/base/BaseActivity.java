package com.futuremall.android.base;

import android.app.Activity;

import com.futuremall.android.R;
import com.futuremall.android.app.App;
import com.futuremall.android.di.component.ActivityComponent;
import com.futuremall.android.di.component.DaggerActivityComponent;
import com.futuremall.android.di.module.ActivityModule;
import com.futuremall.android.util.SnackbarUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by codeest on 2016/8/2.
 * MVP activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        App.getInstance().addActivity(this);
        initEventAndData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setToolBar(Toolbar toolbar, String title, boolean showBackButton) {
        TextView textView= (TextView)(findViewById(R.id.super_title));

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

        textView.setText(title);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        App.getInstance().removeActivity(this);
    }

    @Override
    public void showError(String msg) {

        SnackbarUtil.show(getCurrentFocus(),msg);
    }

    protected abstract void initInject();
    protected abstract int getLayout();
    protected abstract void initEventAndData();
}