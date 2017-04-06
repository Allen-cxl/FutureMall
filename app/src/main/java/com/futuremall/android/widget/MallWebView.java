package com.futuremall.android.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.futuremall.android.util.LogUtil;
import com.tencent.smtt.sdk.WebView;


public class MallWebView extends WebView {


    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MallWebView(Context context) {
        super(context);
    }
    public MallWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MallWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRefreshLayout(SwipeRefreshLayout swipeRefreshLayout){
        mSwipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtil.i("onScrollChanged:"+  t);
        if(null != mSwipeRefreshLayout){
            if (t > 0){
                mSwipeRefreshLayout.setEnabled(false);
            }else {
                mSwipeRefreshLayout.setEnabled(true);
            }
        }

    }
}
