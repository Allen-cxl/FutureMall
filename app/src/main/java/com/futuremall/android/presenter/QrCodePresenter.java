package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.AesBean;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.QrCodeContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/3/7.
 */

public class QrCodePresenter extends RxPresenter<QrCodeContract.View> implements QrCodeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    QrCodePresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void scan(String result) {

        LoadingStateUtil.show(mContext);
        Disposable rxSubscription = mRetrofitHelper.encryptPhone(result)
                .compose(RxUtil.<MyHttpResponse<AesBean>>rxSchedulerHelper())
                .compose(RxUtil.<AesBean>handleMyResult())
                .subscribe(new Consumer<AesBean>() {
                    @Override
                    public void accept(AesBean value) {
                        LoadingStateUtil.close();
                        mView.encryptSuccess(value);
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
