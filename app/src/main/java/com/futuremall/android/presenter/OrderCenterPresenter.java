package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.OrderCenterContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxUtil;
import com.futuremall.android.util.TestData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/3/7.
 */

public class OrderCenterPresenter extends RxPresenter<OrderCenterContract.View> implements OrderCenterContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    OrderCenterPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void orderList(String p, String num, String state, boolean isFirst) {
        if(isFirst){
            LoadingStateUtil.show(mContext);
        }

        String token = PreferencesFactory.getUserPref().getToken();
        Disposable disposable = mRetrofitHelper.orderList(token, p, num, state)
                .compose(RxUtil.<MyHttpResponse<List<OrderList>>>rxSchedulerHelper())
                .compose(RxUtil.<List<OrderList>>handleMyResult())
                .subscribe(new Consumer<List<OrderList>>() {
                    @Override
                    public void accept(List<OrderList> value) {
                        LoadingStateUtil.close();
                        mView.showContent(value);
                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(disposable);
    }
}
