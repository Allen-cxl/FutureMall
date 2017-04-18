package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.BalanceBean;


public class TransferContract {

    public interface View extends BaseView {

        void transferSuccess();

        void transferFail(String msg);

        void balance(BalanceBean bean);

        void userName(String name);
    }

    public interface  Presenter extends BasePresenter<View> {


        void userName(String phone);

        void getBalance();

        void transfer(String account, String name, String integral, String password);
    }
}
