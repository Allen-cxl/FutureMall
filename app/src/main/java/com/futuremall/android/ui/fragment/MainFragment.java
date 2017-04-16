package com.futuremall.android.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.MainContract;
import com.futuremall.android.presenter.MainPresenter;
import com.futuremall.android.ui.activity.QrCodeActivity;
import com.futuremall.android.ui.activity.SearchActivity;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener{

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
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MallWebClient(mRefreshLayout));
        mRefreshLayout.setColorSchemeResources(R.color.orange);
        mRefreshLayout.setOnRefreshListener(this);
        mWebView.setRefreshLayout(mRefreshLayout);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
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
    public void onRefresh() {
        loadUrl();
    }

    private void loadUrl(){
        mWebView.loadUrl(Constants.MAIN_URL);
    }
}
