package com.futuremall.android.ui.fragment;

import com.futuremall.android.R;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.ShoppingCarContract;
import com.futuremall.android.presenter.ShoppingCarPresenter;
import android.view.View;


public class ShoppingCarFragment extends BaseFragment<ShoppingCarPresenter> implements ShoppingCarContract.View, View.OnClickListener  {


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shoppingcart;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onClick(View view) {

    }
}
