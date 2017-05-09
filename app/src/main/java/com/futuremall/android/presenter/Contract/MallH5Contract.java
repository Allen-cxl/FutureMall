package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;

/**
 * Created by Allen on 2017/5/3.
 */

public class MallH5Contract {

    public static int ADD_SHOPPINGCART = 0X11;
    public static int ADD_ENTER_SHOPPINGCART = 0X12;

    public interface View extends BaseView {

        void showPayButton();

        void hidePayButton();

        void showShoppingCartCount(String count);

        void startShoppingCart();
    }

    public interface  Presenter extends BasePresenter<View> {

        void addShoppingCart(String id, int type);

        void matchUrl(String url);

        void getShoppingCart();
    }
}
