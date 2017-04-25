package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.UserInfo;


public class ForgetPasswordContract {

    public interface View extends BaseView {

        void updateSuccess();
    }

    public interface  Presenter extends BasePresenter<View> {

        void updatePassword(String phone, String code, String newPassword);
    }
}
