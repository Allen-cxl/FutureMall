package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.UpdatePasswordContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class UpdatePayPasswordPresenter extends RxPresenter<UpdatePasswordContract.View> implements UpdatePasswordContract.Presenter  {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    UpdatePayPasswordPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void getCode(String phone, String type) {

        Disposable rxSubscription = mRetrofitHelper.getCode(phone, type)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        mView.codeResponse();
                        LogUtil.d("UserInfo:"+value);
                    }
                }, new CommonConsumer(mView));
        addSubscrebe(rxSubscription);
    }

    @Override
    public void updatePassword(String code, String password) {

        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.updatePayPassword(accessToken, password, code)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LogUtil.d("UserInfo:"+value);
                    }
                }, new CommonConsumer(mView));
        addSubscrebe(rxSubscription);
    }
}
