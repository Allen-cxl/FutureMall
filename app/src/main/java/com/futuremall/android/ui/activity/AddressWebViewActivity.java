package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.model.bean.AddressBean;
import com.futuremall.android.model.event.AddressEvent;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;
import com.google.gson.Gson;

import butterknife.BindView;

public class AddressWebViewActivity extends SimpleActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.webView)
    MallWebView mWebView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    String mTitle, mUrl;

    @Override
    protected int getLayout() {
        return R.layout.activity_address_web_view;
    }

    @Override
    protected void initEventAndData() {

        mTitle = getIntent().getStringExtra(Constants.IT_TITLE);
        mUrl =  Constants.ADDRESS_URL + getIntent().getStringExtra(Constants.IT_TOKEN);
        setToolBar(mSuperToolbar, mTitle, true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MallWebClient(mRefreshLayout));
        mWebView.setWebChromeClient(new WebChromeClient());

        mRefreshLayout.setColorSchemeResources(R.color.orange);
        mRefreshLayout.setOnRefreshListener(this);
        mWebView.setRefreshLayout(mRefreshLayout);
        mWebView.addJavascriptInterface(this,"addressInterface");

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
        loadUrl();
    }

    private void loadUrl(){

        mWebView.loadUrl(mUrl);
//        mWebView.addJavascriptInterface(this,"addressInterface");
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {

        loadUrl();
    }

    @SuppressWarnings("unused")
    @android.webkit.JavascriptInterface
    public void Address(String address) {
        LogUtil.i("JavascriptInterface"+address);
        Gson gson = new Gson();
        AddressBean bean = gson.fromJson(address, AddressBean.class);
        if(null != bean){
            RxBus.getDefault().post(new AddressEvent(bean));
            finish();
        }

    }

    public static void enter(Context context, String title, String token) {

        Intent intent = new Intent(context, AddressWebViewActivity.class);
        intent.putExtra(Constants.IT_TITLE, title);
        intent.putExtra(Constants.IT_TOKEN, token);
        context.startActivity(intent);
    }
}
