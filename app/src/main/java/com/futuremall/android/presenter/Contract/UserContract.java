package com.futuremall.android.presenter.Contract;

import android.graphics.Bitmap;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.UserInfo;


/**
 * Created by Allen on 2017/3/3.
 */

public class UserContract {

    public interface View extends BaseView {

        void showRegisterLayout();

        void showLoginLayout();

        void showUserInfo(UserInfo info);

        void loading();

        void complete();
    }

    public interface  Presenter extends BasePresenter<View> {


        void userInfo();

        void showLayout();

        void saveImageView(String imgUrl, String fileName);

    }
}
