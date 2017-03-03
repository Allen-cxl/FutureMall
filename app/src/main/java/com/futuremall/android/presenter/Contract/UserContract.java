package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;


/**
 * Created by Allen on 2017/3/3.
 */

public class UserContract {

    public interface View extends BaseView {

        void showContent();
    }

    public interface  Presenter extends BasePresenter<View> {


        void userInfo();
    }
}
