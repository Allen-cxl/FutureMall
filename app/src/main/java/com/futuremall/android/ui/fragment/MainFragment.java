package com.futuremall.android.ui.fragment;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.MainContract;
import com.futuremall.android.presenter.MainPresenter;
import com.futuremall.android.ui.activity.QrCodeActivity;
import com.futuremall.android.ui.activity.SearchActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View {


    @BindView(R.id.iv_scan)
    ImageView mIvScan;
    @BindView(R.id.layout_search)
    LinearLayout mLayoutSearch;
    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.iv_category)
    ImageView mIvCategory;

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

                break;
        }
    }

    @Override
    public void startQrCodeActivity() {
        QrCodeActivity.enter(getContext());
    }

    @Override
    public void showUpdateDialog(String versionContent) {

    }
}
