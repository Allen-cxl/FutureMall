package com.futuremall.android.ui.fragment;


import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.UserContract;
import com.futuremall.android.presenter.UserPresenter;
import android.view.View;

/**
 * Created by PVer on 2017/3/2.
 */

public class UserFragment extends BaseFragment<UserPresenter> implements UserContract.View, View.OnClickListener {


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showContent() {

    }
}
