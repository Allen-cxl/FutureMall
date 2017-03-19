package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;


public class TransferContract {

    public interface View extends BaseView {

        void transferResponse();

        void userName(String name);
    }

    public interface  Presenter extends BasePresenter<View> {


        void userName(String phone);

        void transfer(String account, String name, String integral, String password);
    }
}
