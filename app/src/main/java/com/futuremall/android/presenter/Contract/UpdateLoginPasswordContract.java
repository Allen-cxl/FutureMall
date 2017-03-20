package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.UserInfo;


public class UpdateLoginPasswordContract {

    public interface View extends BaseView {

        void updateResponse(UserInfo info);
    }

    public interface  Presenter extends BasePresenter<View> {

        void updatePassword(String oldPassword, String newPassword);
    }
}
