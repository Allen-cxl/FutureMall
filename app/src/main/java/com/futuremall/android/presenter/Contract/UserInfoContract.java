package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.UserInfo;


public class UserInfoContract {

    public interface View extends BaseView {

        void setInfo(String info);
    }

    public interface  Presenter extends BasePresenter<View> {

        void saveUserInfo(String file, int sex, String birthday, String realName);
    }
}
