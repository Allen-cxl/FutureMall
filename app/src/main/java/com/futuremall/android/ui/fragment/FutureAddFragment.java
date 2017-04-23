package com.futuremall.android.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleFragment;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;

import butterknife.BindView;


public class FutureAddFragment extends SimpleFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.webView)
    MallWebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_futureadd;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mSuperToolbar, getString(R.string.future_add), false);

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

    @Override
    public void onRefresh() {
        loadUrl();
    }

    private void loadUrl(){
        mWebView.loadUrl(Constants.FUTURE_URL);
    }


}
