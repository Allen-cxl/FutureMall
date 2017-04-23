package com.futuremall.android.presenter;

import android.Manifest;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.VersionBean;
import com.futuremall.android.presenter.Contract.MainContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.RxUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Allen on 2017/3/2.
 * 主页
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    MainPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void checkVersion() {

        Disposable rxSubscription = mRetrofitHelper.getVersionInfo("1.0.1","1")
                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleMyResult())
                .subscribe(new Consumer<VersionBean>() {
                    @Override
                    public void accept(VersionBean value) {
                        mView.showUpdateDialog(value.toString());
                    }
                }, new CommonConsumer<Object>(mView));
        addSubscrebe(rxSubscription);
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
