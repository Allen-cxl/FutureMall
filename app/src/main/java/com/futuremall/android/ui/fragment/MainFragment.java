package com.futuremall.android.ui.fragment;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebSettings;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.model.bean.VersionBean;
import com.futuremall.android.presenter.Contract.MainContract;
import com.futuremall.android.presenter.MainPresenter;
import com.futuremall.android.ui.activity.MallH5Activity;
import com.futuremall.android.ui.activity.QrCodeActivity;
import com.futuremall.android.ui.activity.SearchActivity;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.VersionUpdateService;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View{

    @BindView(R.id.webView)
    MallWebView mWebView;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initEventAndData() {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MallWebClient());
        mWebView.addJavascriptInterface(this,"linkH5Interface");
        loadUrl();
        mPresenter.checkVersion();
    }

    @OnClick({R.id.iv_scan, R.id.layout_search, R.id.iv_category})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:

                mPresenter.checkPermissions(new RxPermissions(getActivity()));
                break;
            case R.id.layout_search:

                SearchActivity.enter(getContext());
                break;
            case R.id.iv_category:
                MallH5Activity.enter(getContext(), Constants.TYPE_URL);
                break;
        }
    }

    @SuppressWarnings("unused")
    @android.webkit.JavascriptInterface
    public void OpenLinkH5(String url) {
        LogUtil.i("Link"+url);
        if(null != url){
            MallH5Activity.enter(getContext(), url);
        }
    }

    @Override
    public void startQrCodeActivity() {
        QrCodeActivity.enter(getContext());
    }

    @Override
    public void showUpdateDialog(final VersionBean versionContent) {
        if( versionContent.getRenew() == 0 ){
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert_Self)
                    .setTitle("发现新版本" +versionContent.getVersion())
                    .setMessage(versionContent.getContent())
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (versionContent.getType() ==1) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            VersionUpdateService.startService(getContext(), versionContent.getUrl());
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }
    }

    private void loadUrl(){
        mWebView.loadUrl(Constants.MAIN_URL);
    }
}
