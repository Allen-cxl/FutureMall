package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.presenter.Contract.ShoppingCarContract;

import javax.inject.Inject;

/**
 * Created by Allen on 2017/3/3.
 */

public class ShoppingCarPresenter extends RxPresenter<ShoppingCarContract.View> implements ShoppingCarContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    ShoppingCarPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void shoppingCar() {

    }
}
