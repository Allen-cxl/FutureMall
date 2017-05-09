package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.Count;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.event.ShoppingCartPayEvent;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.MallH5Contract;
import com.futuremall.android.ui.ViewHolder.LoginHelper;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxBus;
import com.futuremall.android.util.RxUtil;
import com.futuremall.android.util.StringUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/3/7.
 */

public class MallH5Presenter extends RxPresenter<MallH5Contract.View> implements MallH5Contract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    MallH5Presenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;

        registerEvent();
    }

    private void registerEvent() {
        Disposable rxSubscription = RxBus.getDefault().toObservable(UserInfo.class)
                .compose(RxUtil.<UserInfo>rxSchedulerHelper()).subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) {
                        getShoppingCart();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void addShoppingCart(String id, final int type) {

        if (!StringUtil.isEmpty(id)) {
            LoadingStateUtil.show(mContext);
            String token = PreferencesFactory.getUserPref().getToken();
            //购物车默认数量1
            Disposable disposable = mRetrofitHelper.addShoppingCart(token, id, "1")
                    .compose(RxUtil.<MyHttpResponse<Count>>rxSchedulerHelper())
                    .compose(RxUtil.<Count>handleMyResult())
                    .subscribe(new Consumer<Count>() {
                        @Override
                        public void accept(Count value) {
                            LoadingStateUtil.close();
                            if (null != value) {
                                mView.showShoppingCartCount(value.getNum());
                                RxBus.getDefault().post(new ShoppingCartPayEvent());
                                if(type == MallH5Contract.ADD_ENTER_SHOPPINGCART){
                                    mView.startShoppingCart();
                                }
                            }
                        }
                    }, new CommonConsumer<Object>(mView, mContext) {
                        public void onError() {
                            LoadingStateUtil.close();
                        }

                        public void onErrorMsg(String msg) {
                            mView.showErrorMsg(msg);
                        }
                    });
            addSubscrebe(disposable);
        }
    }

    @Override
    public void matchUrl(String url) {

        if (StringUtil.matchUrl(url)) {

            mView.showPayButton();
        } else {
            mView.hidePayButton();
        }
    }

    @Override
    public void getShoppingCart() {

        String token = PreferencesFactory.getUserPref().getToken();
        if (!StringUtil.isEmpty(token)) {
            Disposable disposable = mRetrofitHelper.getShoppingCartNum(token)
                    .compose(RxUtil.<MyHttpResponse<Count>>rxSchedulerHelper())
                    .compose(RxUtil.<Count>handleMyResult())
                    .subscribe(new Consumer<Count>() {
                        @Override
                        public void accept(Count value) {
                            if (null != value) {
                                mView.showShoppingCartCount(value.getNum());
                            }
                        }
                    }, new CommonConsumer<Object>(mView, mContext) {
                        public void onError() {
                        }

                        public void onErrorMsg(String msg) {
                            mView.showErrorMsg(msg);
                        }
                    });
            addSubscrebe(disposable);
        }


    }

}
