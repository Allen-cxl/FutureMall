package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.UserInfo;


/**
 * Created by Allen on 2017/3/3.
 */

public class UserContract {

    public interface View extends BaseView {

        void showRegisterLayout();

        void showLoginLayout(UserInfo info);
    }

    public interface  Presenter extends BasePresenter<View> {


        void userInfo();

        void showLayout();

    }
}
