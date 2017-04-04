package com.futuremall.android.widget;

import android.support.v4.widget.SwipeRefreshLayout;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by PVer on 2017/4/3.
 */

public class MallWebChromeClient extends WebChromeClient {

    private SwipeRefreshLayout mRefreshLayout;

    public MallWebChromeClient(SwipeRefreshLayout progressBar) {
        this.mRefreshLayout = progressBar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        mRefreshLayout.setRefreshing(true);
        if (newProgress == 100) {

            mRefreshLayout.setRefreshing(false);

        } else {
            mRefreshLayout.setRefreshing(true);
        }
    }
}
