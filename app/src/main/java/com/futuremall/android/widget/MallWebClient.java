package com.futuremall.android.widget;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by PVer on 2017/4/3.
 */

public class MallWebClient extends WebViewClient {

    private SwipeRefreshLayout mRefreshLayout;

    public MallWebClient() {
        this(null);
    }

    public MallWebClient(SwipeRefreshLayout progressBar) {
        this.mRefreshLayout = progressBar;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        if(null != mRefreshLayout){
            mRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // handler.cancel();// Android默认的处理方式
        handler.proceed();// 接受所有网站的证书
        // handleMessage(Message msg);// 进行其他处理
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if(null != mRefreshLayout){
            mRefreshLayout.setRefreshing(false);
        }
    }
}
