package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.OrderDetail;


public class LoginContract {

    public interface View extends BaseView {

        void loginResponse();
    }

    public interface  Presenter extends BasePresenter<View> {


        void login(String phone, String password);
    }
}
