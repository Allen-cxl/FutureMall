package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.presenter.Contract.UserContract;

import javax.inject.Inject;

/**
 * Created by Allen on 2017/3/3.
 */

public class UserPresenter extends RxPresenter<UserContract.View> implements UserContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    UserPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void userInfo() {

    }

}
