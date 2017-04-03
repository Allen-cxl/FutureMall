package com.futuremall.android.presenter;

import android.app.Activity;
import android.view.View;

import com.futuremall.android.R;
import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.ChangeShoppingCart;
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.presenter.Contract.ShoppingCarContract;
import com.futuremall.android.ui.ViewHolder.ShoppingCartHepler;
import com.futuremall.android.ui.activity.PayOrderActivity;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxUtil;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.StringUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Allen on 2017/3/3.
 */

public class ShoppingCartPresenter extends RxPresenter<ShoppingCarContract.View> implements ShoppingCarContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    ShoppingCartPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void shoppingCar(boolean isFirst) {

        if(isFirst){
            mView.showLoading();
        }
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.shoppingCar(accessToken)
                .compose(RxUtil.<MyHttpResponse<List<ShoppingCartBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ShoppingCartBean>>handleMyResult())
                .subscribe(new Consumer<List<ShoppingCartBean>>() {
                    @Override
                    public void accept(List<ShoppingCartBean> value) {
                        mView.showContent();
                        if(null == value || value.size() <= 0){
                            mView.showEmpty();
                        }else{
                            mView.showData(value);
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        mView.showError();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void selectOrCancelAll(List<ShoppingCartBean> list, boolean isSelectAll) {

        ShoppingCartHepler.selectOrCancelAll(list,isSelectAll);
        String[] info = ShoppingCartHepler.getShoppingCount(list);
        mView.showTotalPrice(info[1], info[0]);
    }

    @Override
    public void delete(View view, final List<ShoppingCartBean> data) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        String recID = ShoppingCartHepler.getGoodsID(data);
        if(StringUtil.isEmpty(recID)){
            SnackbarUtil.show(view, mContext.getString(R.string.no_data_shopping_cart));
            return;
        }
        Disposable rxSubscription = mRetrofitHelper.delShoppingCar(accessToken, recID)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LoadingStateUtil.close();
                        ShoppingCartHepler.deleteGoods(data);
                    }
                },  new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void menuDone(List<ShoppingCartBean> list, boolean isSelectAll) {
        ShoppingCartHepler.selectOrCancelAll(list,isSelectAll);
        String[] info = ShoppingCartHepler.getShoppingCount(list);
        mView.showTotalPrice(info[1], info[0]);
        mView.showPayLayout(View.GONE);
        mView.showDeleteButton(View.VISIBLE);
    }

    @Override
    public void menuEdit(List<ShoppingCartBean> list, boolean isSelectAll) {
        ShoppingCartHepler.selectOrCancelAll(list,isSelectAll);
        String[] info = ShoppingCartHepler.getShoppingCount(list);
        mView.showTotalPrice(info[1], info[0]);
        mView.showPayLayout(View.VISIBLE);
        mView.showDeleteButton(View.GONE);
    }

    @Override
    public void dataChange(final String id, String count) {

        LoadingStateUtil.show(mContext);
        String accessToken = PreferencesFactory.getUserPref().getToken();
        Disposable rxSubscription = mRetrofitHelper.changeShoppingCar(accessToken, id, count)
                .compose(RxUtil.<MyHttpResponse<ChangeShoppingCart>>rxSchedulerHelper())
                .compose(RxUtil.<ChangeShoppingCart>handleMyResult())
                .subscribe(new Consumer<ChangeShoppingCart>() {
                    @Override
                    public void accept(ChangeShoppingCart value) {
                        LoadingStateUtil.close();
                        mView.updateShoppingCartCount(id, value.getNum());
                    }
                },  new CommonConsumer<Object>(mView, mContext) {
                    public void onError() {
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void calculateTotalPrice(List<ShoppingCartBean> data) {

        String[] info = ShoppingCartHepler.getShoppingCount(data);
        mView.showTotalPrice(info[1], info[0]);
    }

    @Override
    public void toPay(View view, List<ShoppingCartBean> data) {

        String recID = ShoppingCartHepler.getGoodsID(data);
        if(StringUtil.isEmpty(recID)){
            SnackbarUtil.show(view, mContext.getString(R.string.no_data_shopping_cart));
            return;
        }
        PayOrderActivity.enter(mContext, recID);
    }
}
