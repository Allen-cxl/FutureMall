package com.futuremall.android.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    Context context;

    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mArrays;

    public void addItem(String[] arrays,Fragment fragment) {
        mArrays = arrays;
        mFragments.add(fragment);
    }

    public MyPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        context = ctx;
    }

    @Override
    public Fragment getItem(int position) {return mFragments.get(position);}

    @Override
    public int getCount() { // Return the number of pages
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArrays[position];
    }
}
