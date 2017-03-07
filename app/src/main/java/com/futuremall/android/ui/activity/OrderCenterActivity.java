package com.futuremall.android.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseActivity;
import com.futuremall.android.presenter.Contract.OrderCenterContract;
import com.futuremall.android.presenter.Contract.OrderCenterPresenter;
import com.futuremall.android.ui.adapter.MyPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderCenterActivity extends BaseActivity<OrderCenterPresenter> implements OrderCenterContract.View {


    @BindView(R.id.super_toolbar)
    Toolbar mSuperToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.line_view)
    View mLineView;
    private MyPagerAdapter mAdapter;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_center;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.shopping_cart), false);
    }

    @Override
    public void showContent() {

    }

    private void creatTabLayout() {

        String[] tabStr = getResources().getStringArray(R.array.order_status_txt);
        mAdapter = new MyPagerAdapter(this, getSupportFragmentManager());
        if (null == tabStr || tabStr.length <= 0) {
            mLineView.setVisibility(View.GONE);
            mAdapter.addItem(tabStr, MeetingFragment.newInstance(category.getName()));
            mTabLayout.setVisibility(View.GONE);
            mViewPager.setOffscreenPageLimit(1);
            mViewPager.setAdapter(mAdapter);
        } else {
            for (int i = 0; i < tabStr.length; i++) {
                mAdapter.addItem(tabStr, MeetingFragment.newInstance(category.getName()));
                mTabLayout.addTab(mTabLayout.newTab());
            }
            mViewPager.setOffscreenPageLimit(1);
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }


    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, OrderCenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
