package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.presenter.Contract.QrCodeContract;

import javax.inject.Inject;

/**
 * Created by PVer on 2017/3/7.
 */

public class QrCodePresenter extends RxPresenter<QrCodeContract.View> implements QrCodeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    QrCodePresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void scan(String code) {

    }
}
