package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;

/**
 * Created by Allen on 2017/5/3.
 */

public class MallH5Contract {

    public interface View extends BaseView {

        void showPayButton();

        void hidePayButton();

        void showShoppingCartCount(String count);
    }

    public interface  Presenter extends BasePresenter<View> {

        void addShoppingCart(String id);

        void matchUrl(String url);

        void getShoppingCart();
    }
}
