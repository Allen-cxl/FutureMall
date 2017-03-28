package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.OperationRecordBean;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.OperationRecordContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Allen on 2017/3/3.
 */

public class OperationRecordPresenter extends RxPresenter<OperationRecordContract.View> implements OperationRecordContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    OperationRecordPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }

    @Override
    public void recordList(final int p, int num, String time, final boolean isFirst) {
        if(isFirst){
            mView.showLoading();
        }
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable disposable = mRetrofitHelper.operationRecord(token, p+"", num+"", time)
                .compose(RxUtil.<MyHttpResponse<List<OperationRecordBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<OperationRecordBean>>handleMyResult())
                .subscribe(new Consumer<List<OperationRecordBean>>() {
                    @Override
                    public void accept(List<OperationRecordBean> value) {
                        if((null == value || value.isEmpty()) && p == 1){
                            mView.showEmpty();
                        }else{

                            if(p >1 && (value==null || value.size()<=0)){
                                mView.showNoMore();
                            }else{
                                mView.showContent();
                                mView.showData(value);
                            }
                        }

                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        if(p == 1){
                            mView.showError();
                        }
                    }
                });
        addSubscrebe(disposable);
    }

}
