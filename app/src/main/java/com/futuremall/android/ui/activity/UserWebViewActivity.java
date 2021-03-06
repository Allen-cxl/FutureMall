package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;

import butterknife.BindView;

public class UserWebViewActivity extends SimpleActivity {

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.webView)
    MallWebView mWebView;
    String mTitle, mUrl;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_web_view;
    }

    @Override
    protected void initEventAndData() {

        mTitle = getIntent().getStringExtra(Constants.IT_TITLE);
        mUrl = getIntent().getStringExtra(Constants.IT_WEB_URL);
        setToolBar(mSuperToolbar, mTitle, true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MallWebClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.addJavascriptInterface(this,"finishH5Interface");
        loadUrl();
    }

    private void loadUrl(){

        mWebView.loadUrl(mUrl);
    }

    @SuppressWarnings("unused")
    @android.webkit.JavascriptInterface
    public void finishH5() {
        finish();
    }

    public static void enter(Context context, String title, String url) {

        Intent intent = new Intent(context, UserWebViewActivity.class);
        intent.putExtra(Constants.IT_TITLE, title);
        intent.putExtra(Constants.IT_WEB_URL, url);
        context.startActivity(intent);
    }
}
