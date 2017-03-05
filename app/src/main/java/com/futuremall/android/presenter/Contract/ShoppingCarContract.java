package com.futuremall.android.presenter.Contract;

import android.content.Context;
import android.opengl.Visibility;
import android.view.View;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.ShoppingCartBean;

import java.util.List;


/**
 * Created by Allen on 2017/3/3.
 */

public class ShoppingCarContract {

    public interface View extends BaseView {

        void showContent(List<ShoppingCartBean> data);

        void showFreight(int visibility);

        void showPayLayout(int visibility);

        void showDeleteButton(int visibility);

        void showTotalPrice(String totalPrice, String totalCount);

    }

    public interface  Presenter extends BasePresenter<View> {


        void shoppingCar();

        void selectOrCancelAll(List<ShoppingCartBean> list, boolean isSelectAll);

        void delete(List<ShoppingCartBean> data, android.view.View view);

        void menuDone(List<ShoppingCartBean> list, boolean isSelectAll);

        void menuEdit();

        void dataChange(String id, String count, int type, android.view.View view);

        void calculateTotalPrice(List<ShoppingCartBean> data);

        void toPay(List<ShoppingCartBean> data);

    }
}
