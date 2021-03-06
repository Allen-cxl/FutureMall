package com.futuremall.android.base;

import android.app.Activity;
import android.content.Context;

import com.futuremall.android.R;
import com.futuremall.android.app.App;
import com.futuremall.android.di.component.DaggerFragmentComponent;
import com.futuremall.android.di.component.FragmentComponent;
import com.futuremall.android.di.module.FragmentModule;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.SystemUtil;
import com.futuremall.android.widget.LoadingLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by codeest on 2016/8/2.
 * MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends BaseLazyFragment implements BaseView{

    @Inject
    protected T mPresenter;
    protected View mView;
    protected LoadingLayout mLoadingLayout;
    protected AppCompatActivity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected boolean isInited = false;
    private TextView mTextView;

    @Override
    public void onAttach(Context context) {
        mActivity = (AppCompatActivity) context;
        mContext = context;
        super.onAttach(context);
    }

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule(){
        return new FragmentModule(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        if (!isHidden()) {
            isInited = true;
            initEventAndData();
        }
    }



    protected void setToolBar(Toolbar toolbar, String title, boolean showBackButton) {

        mTextView = (TextView)(toolbar.findViewById(R.id.super_title));

        mActivity.setSupportActionBar(toolbar);

        if(title != null){
            mActivity.getSupportActionBar().setTitle(null);
        }

        if(showBackButton){
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        setTitle(title);

    }

    protected void setTitle(String title){
        if(null != mTextView && null != title){
            mTextView.setText(title);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInited && !hidden) {
            isInited = true;
            initEventAndData();
        }
    }

    @Override
    public void showErrorMsg(String msg) {

        SnackbarUtil.show(mView,msg);
    }

    @Override
    public void showContent() {

        mLoadingLayout.showContent();
    }

    @Override
    public void showEmpty() {

        mLoadingLayout.showEmpty();
    }

    @Override
    public void showError() {

        mLoadingLayout.showError();
    }

    @Override
    public void showLoading() {

        mLoadingLayout.showLoading();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        SystemUtil.hideKeyboard(mActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    protected abstract void initInject();
    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}