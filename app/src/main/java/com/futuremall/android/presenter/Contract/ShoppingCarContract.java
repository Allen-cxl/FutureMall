package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.ShoppingCartBean;

import java.util.List;


/**
 * Created by Allen on 2017/3/3.
 */

public class ShoppingCarContract {

    public interface View extends BaseView {

        void showData(List<ShoppingCartBean> data);

        void deleteSuccess();

        void showPayLayout(int visibility);

        void showDeleteButton(int visibility);

        void updateAdapter();

        void updateShoppingCartCount(String recID, int count);

        void showTotalPrice(String totalPrice, String totalCount);
    }

    public interface  Presenter extends BasePresenter<View> {


        void shoppingCar(boolean isFirst);

        void selectOrCancelAll(List<ShoppingCartBean> list, boolean isSelectAll);

        void delete(android.view.View view, List<ShoppingCartBean> data);

        void menuDone(List<ShoppingCartBean> list, boolean isSelectAll);

        void menuEdit(List<ShoppingCartBean> list, boolean isSelectAll);

        void dataChange(String id, String count);

        void calculateTotalPrice(List<ShoppingCartBean> data);

        void toPay(android.view.View view, List<ShoppingCartBean> data);

    }
}
