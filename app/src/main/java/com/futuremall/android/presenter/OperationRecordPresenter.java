package com.futuremall.android.presenter;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.presenter.Contract.OperationRecordContract;
import com.futuremall.android.presenter.Contract.UserContract;
import com.futuremall.android.util.TestData;

import javax.inject.Inject;

/**
 * Created by Allen on 2017/3/3.
 */

public class OperationRecordPresenter extends RxPresenter<OperationRecordContract.View> implements OperationRecordContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    OperationRecordPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void recordList() {
        mView.showContent(TestData.getRecordList());
    }

}
