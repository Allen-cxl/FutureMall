package com.futuremall.android.presenter;

import android.Manifest;
import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.presenter.Contract.FutureAddContract;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Allen on 2017/3/2.
 * 主页
 */

public class FutureAddPresenter extends RxPresenter<FutureAddContract.View> implements FutureAddContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    FutureAddPresenter(RetrofitHelper mRetrofitHelper, Activity context) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = context;
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {

        Disposable rxSubscription = rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean){
                        if (aBoolean) {
                            mView.startQrCodeActivity();
                        } else {
                            mView.showErrorMsg("扫描需要读写摄像头权限哦~");
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void checkGpsPermissions(RxPermissions rxPermissions) {

        Disposable rxSubscription = rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean){
                        if (aBoolean) {
                            mView.openGpsSuccess();
                        } else {
                            mView.showErrorMsg("获取当前位置，需要打开GPS");
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
