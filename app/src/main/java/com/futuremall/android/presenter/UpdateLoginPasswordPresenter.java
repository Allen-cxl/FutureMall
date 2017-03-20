package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.UpdateLoginPasswordContract;
import com.futuremall.android.presenter.Contract.UpdatePasswordContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class UpdateLoginPasswordPresenter extends RxPresenter<UpdateLoginPasswordContract.View> implements UpdateLoginPasswordContract.Presenter  {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    UpdateLoginPasswordPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {

        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.updateLoginPassword(accessToken, oldPassword, newPassword)
                .compose(RxUtil.<MyHttpResponse<UserInfo>>rxSchedulerHelper())
                .compose(RxUtil.<UserInfo>handleMyResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo value) {
                        LogUtil.d("UserInfo:"+value);
                        mView.updateResponse(value);
                    }
                }, new CommonConsumer(mView));
        addSubscrebe(rxSubscription);
    }
}
