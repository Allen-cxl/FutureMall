package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.OrderCenterContract;
import com.futuremall.android.presenter.Contract.OrderDetailContract;
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

public class OrderDetailPresenter extends RxPresenter<OrderDetailContract.View> implements OrderDetailContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    OrderDetailPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void orderDetail(String orderID) {

        LoadingStateUtil.show(mContext);
        String token = PreferencesFactory.getUserPref().getToken();
        Disposable disposable = mRetrofitHelper.orderDetail(token, orderID)
                .compose(RxUtil.<MyHttpResponse<OrderDetail>>rxSchedulerHelper())
                .compose(RxUtil.<OrderDetail>handleMyResult())
                .subscribe(new Consumer<OrderDetail>() {
                    @Override
                    public void accept(OrderDetail value) {
                        LoadingStateUtil.close();
                        mView.showContent(value);
                    }
                }, new CommonConsumer<Object>(mView){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(disposable);
    }
}
