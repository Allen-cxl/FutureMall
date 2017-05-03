package com.futuremall.android.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.util.SystemUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class SimpleFragment extends BaseLazyFragment {

    protected View mView;
    protected AppCompatActivity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    private boolean isInited = false;
    private TextView mTextView;

    @Override
    public void onAttach(Context context) {
        mActivity = (AppCompatActivity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
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
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        SystemUtil.hideKeyboard(mActivity);
    }

    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}
