package com.futuremall.android.ui.fragment;


import com.futuremall.android.R;
import com.futuremall.android.base.SimpleFragment;
import com.futuremall.android.util.LogUtil;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;

/**
 * Created by PVer on 2017/3/2.
 */

public class MainFragment extends SimpleFragment {


    @BindView(R.id.iv_scan)
    ImageView mIvScan;
    @BindView(R.id.layout_search)
    LinearLayout mLayoutSearch;
    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.iv_category)
    ImageView mIvCategory;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initEventAndData() {
        LogUtil.i("sdfdsf");
    }

}
