package com.futuremall.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.MallH5Contract;
import com.futuremall.android.presenter.MallH5Presenter;
import com.futuremall.android.ui.ViewHolder.LoginHelper;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.widget.MallWebView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;

public class MallH5Activity extends BaseActivity<MallH5Presenter> implements MallH5Contract.View {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.webView)
    MallWebView mWebView;
    @BindView(R.id.bg_tv)
    BGABadgeTextView mBgTv;
    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    String mId;

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mall_h5;
    }

    @Override
    protected void initEventAndData() {

        String url = getIntent().getStringExtra(Constants.IT_WEB_URL);
        setToolBar(mSuperToolbar, null, true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportZoom(true);

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                setTitle(view.getTitle());
            }
        });
        mWebView.addJavascriptInterface(this, "goodInfoIdInterface");
        mWebView.addJavascriptInterface(this, "linkH5Interface");
        loadUrl(url);
        mPresenter.matchUrl(url);
    }

    private void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void showPayButton() {
        mLlLayout.setVisibility(View.VISIBLE);
        mPresenter.getShoppingCart();
    }

    @Override
    public void hidePayButton() {
        mLlLayout.setVisibility(View.GONE);
    }

    @Override
    public void showShoppingCartCount(String count) {
        if (Integer.valueOf(count) > 0) {
            mBgTv.showTextBadge(count);
            mBgTv.setDragDismissDelegage(new BGADragDismissDelegate() {
                @Override
                public void onDismiss(BGABadgeable bag) {
                    mBgTv.hiddenBadge();
                }
            });
        } else {
            mBgTv.hiddenBadge();
        }
    }

    @Override
    public void startShoppingCart() {
        if(LoginHelper.ensureLogin(this)){
            MainActivity.enter(this, Constants.RB_SHOPPING_CART);
        }
    }

    @JavascriptInterface
    public void OpenLinkH5(String url) {
        LogUtil.i("Link:" + url);
        if (null != url) {
            enter(this, url);
        }
    }

    @JavascriptInterface
    public void getGoodInfoId(String id) {
        LogUtil.i("Link:" + id);
        if (null != id) {
            mId = id;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public static void enter(Context context, String url) {

        Intent intent = new Intent(context, MallH5Activity.class);
        intent.putExtra(Constants.IT_WEB_URL, url);
        context.startActivity(intent);
    }

    @OnClick({R.id.bg_tv, R.id.tv_addShoppingCat, R.id.tv_now_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bg_tv:
                if(LoginHelper.ensureLogin(this)){
                    MainActivity.enter(this, Constants.RB_SHOPPING_CART);
                }
                break;
            case R.id.tv_addShoppingCat:

                if(LoginHelper.ensureLogin(this) && null != mId){
                    mPresenter.addShoppingCart(mId, MallH5Contract.ADD_SHOPPINGCART);
                }
                break;

            case R.id.tv_now_pay:

                if(LoginHelper.ensureLogin(this) && null != mId){
                    mPresenter.addShoppingCart(mId, MallH5Contract.ADD_ENTER_SHOPPINGCART);
                }
                break;
        }
    }
}
