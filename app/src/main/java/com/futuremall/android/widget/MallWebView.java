package com.futuremall.android.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.futuremall.android.util.LogUtil;
import com.tencent.smtt.sdk.WebView;


public class MallWebView extends WebView {


    private SwipeRefreshLayout swipeRefreshLayout;

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
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtil.d("onScrollChanged:"+"l:"+l+"-t:"+t+"-oldl:"+oldl+"-oldt:"+ oldt);
        if(null != swipeRefreshLayout){
            if (this.getScrollY() == 0){
                swipeRefreshLayout.setEnabled(true);
            }else {
                swipeRefreshLayout.setEnabled(false);
            }
        }

    }
}
