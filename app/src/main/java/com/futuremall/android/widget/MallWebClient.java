package com.futuremall.android.widget;

import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by PVer on 2017/4/3.
 */

public class MallWebClient extends WebViewClient {

    private SwipeRefreshLayout mRefreshLayout;

    public MallWebClient(SwipeRefreshLayout progressBar) {
        this.mRefreshLayout = progressBar;
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        mRefreshLayout.setRefreshing(false);
    }
}
