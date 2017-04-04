package com.futuremall.android.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.MainContract;
import com.futuremall.android.presenter.MainPresenter;
import com.futuremall.android.ui.activity.QrCodeActivity;
import com.futuremall.android.ui.activity.SearchActivity;
import com.futuremall.android.widget.MallWebChromeClient;
import com.futuremall.android.widget.MallWebView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.iv_scan)
    ImageView mIvScan;
    @BindView(R.id.layout_search)
    LinearLayout mLayoutSearch;
    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.iv_category)
    ImageView mIvCategory;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.webView)
    MallWebView mWebView;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initEventAndData() {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new MallWebChromeClient(mRefreshLayout));
        mRefreshLayout.setColorSchemeResources(R.color.orange);
        mRefreshLayout.setOnRefreshListener(this);
        mWebView.setRefreshLayout(mRefreshLayout );
        loadUrl();
    }

    @OnClick({R.id.iv_scan, R.id.layout_search, R.id.iv_category})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:

                mPresenter.checkPermissions(new RxPermissions(getActivity()));
                break;
            case R.id.layout_search:

                SearchActivity.enter(getContext());
                break;
            case R.id.iv_category:

                break;
        }
    }

    @Override
    public void startQrCodeActivity() {
        QrCodeActivity.enter(getContext());
    }

    @Override
    public void showUpdateDialog(String versionContent) {

    }

    @Override
    public void onFirstUserVisible() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        loadUrl();
    }

    private void loadUrl(){
        mWebView.loadUrl("http://139.196.124.0/#/");
    }
}
