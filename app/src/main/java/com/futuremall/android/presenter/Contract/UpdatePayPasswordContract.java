package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;


public class UpdatePayPasswordContract {

    public interface View extends BaseView {

        void codeResponse();
    }

    public interface  Presenter extends BasePresenter<View> {


        void getCode(String phone, String type);

        void updatePassword(String password, String code);
    }
}
