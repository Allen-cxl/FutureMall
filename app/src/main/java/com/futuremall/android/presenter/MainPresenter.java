package com.futuremall.android.presenter;

import android.Manifest;
import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.VersionBean;
import com.futuremall.android.presenter.Contract.MainContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.RxUtil;
import com.futuremall.android.util.SystemUtil;
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
    private Activity mContext;

    @Inject
    MainPresenter(RetrofitHelper mRetrofitHelper, Activity context) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = context;
    }

    @Override
    public void checkVersion() {

        String version = SystemUtil.getAppVersionName(mContext);
        Disposable rxSubscription = mRetrofitHelper.getVersionInfo(version,"1")
                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleMyResult())
                .subscribe(new Consumer<VersionBean>() {
                    @Override
                    public void accept(VersionBean value) {
                        if(null != value){
                            mView.showUpdateDialog(value);
                        }
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
