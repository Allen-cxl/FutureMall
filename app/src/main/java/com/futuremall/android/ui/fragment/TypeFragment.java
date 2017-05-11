package com.futuremall.android.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleFragment;
import com.futuremall.android.ui.activity.MallH5Activity;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends SimpleFragment{


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.webView)
    MallWebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(mSuperToolbar, getString(R.string.type), false);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MallWebClient());
        loadUrl();
        mWebView.addJavascriptInterface(this,"linkH5Interface");
    }

    private void loadUrl(){
        mWebView.loadUrl(Constants.TYPE_URL);
    }

    @SuppressWarnings("unused")
    @android.webkit.JavascriptInterface
    public void OpenLinkH5(String url) {
        LogUtil.i("Link"+url);
        if(null != url){
            MallH5Activity.enter(getContext(), url);
        }
    }

}
