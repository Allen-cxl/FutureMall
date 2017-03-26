package com.futuremall.android.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.SimpleActivity;
import com.futuremall.android.ui.adapter.MyPagerAdapter;
import com.futuremall.android.ui.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderCenterActivity extends SimpleActivity {


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
    protected int getLayout() {
        return R.layout.activity_order_center;
    }

    @Override
    protected void initEventAndData() {

        setToolBar(mSuperToolbar, getString(R.string.order_center), true);
        creatTabLayout();
    }

    private void creatTabLayout() {

        String[] tabStr = getResources().getStringArray(R.array.order_status_txt);
        List<Fragment> list = new ArrayList<>();
        mAdapter = new MyPagerAdapter(this, getSupportFragmentManager());
        if (tabStr.length <= 0) {
            mLineView.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            mViewPager.setOffscreenPageLimit(1);
            mViewPager.setAdapter(mAdapter);
        } else {
            for (int i = 0; i < tabStr.length; i++) {
                OrderFragment f =null;
                if(tabStr[i].equals(R.string.deliver)){
                    f = OrderFragment.newInstance(Constants.DELIVERING);
                }else if(tabStr[i].equals(R.string.receipting)){
                    f = OrderFragment.newInstance(Constants.RECEIPTING);
                }else if(tabStr[i].equals(R.string.receipted)){
                    f = OrderFragment.newInstance(Constants.RECEIPTED);
                }else{
                    f = new OrderFragment();
                }
                list.add(f);
            }
            mAdapter.addItem(tabStr, list);
            mTabLayout.addTab(mTabLayout.newTab());
            mViewPager.setOffscreenPageLimit(1);
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }


    }

    public static void enter(Context context) {
        Intent intent = new Intent(context, OrderCenterActivity.class);
        context.startActivity(intent);
    }
}
