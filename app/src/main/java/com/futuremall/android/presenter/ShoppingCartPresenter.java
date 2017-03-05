package com.futuremall.android.presenter;

import android.view.View;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.presenter.Contract.ShoppingCarContract;
import com.futuremall.android.ui.ViewHolder.ShoppingCartHepler;
import com.futuremall.android.util.SnackbarUtil;
import com.futuremall.android.util.TestData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Allen on 2017/3/3.
 */

public class ShoppingCartPresenter extends RxPresenter<ShoppingCarContract.View> implements ShoppingCarContract.Presenter{

    private RetrofitHelper mRetrofitHelper;
    private List<ShoppingCartBean> mShoppingCartBeen;

    @Inject
    ShoppingCartPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }


    @Override
    public void shoppingCar() {

        mShoppingCartBeen = TestData.getShoppingCartBeanLsit();
        mView.showContent(mShoppingCartBeen);
    }

    @Override
    public void selectOrCancelAll(List<ShoppingCartBean> list, boolean isSelectAll) {

        ShoppingCartHepler.selectOrCancelAll(list,isSelectAll);
        String[] info = ShoppingCartHepler.getShoppingCount(list);
        mView.showTotalPrice(info[1], info[0]);
    }

    @Override
    public void delete(List<ShoppingCartBean> data, View view) {
        SnackbarUtil.show(view, "需要请求接口才能真正删除");
    }

    @Override
    public void menuDone(List<ShoppingCartBean> list, boolean isSelectAll) {
        ShoppingCartHepler.selectOrCancelAll(list,isSelectAll);
        mView.showPayLayout(View.GONE);
        mView.showDeleteButton(View.VISIBLE);
    }

    @Override
    public void menuEdit() {
        mView.showPayLayout(View.VISIBLE);
        mView.showDeleteButton(View.GONE);
    }

    @Override
    public void dataChange(String id, String count, int type, View view) {

        SnackbarUtil.show(view, "需要请求接口才能真正增加或减少");
    }

    @Override
    public void calculateTotalPrice(List<ShoppingCartBean> data) {

        String[] info = ShoppingCartHepler.getShoppingCount(data);
        mView.showTotalPrice(info[1], info[0]);
    }

    @Override
    public void toPay(List<ShoppingCartBean> data) {

    }
}
