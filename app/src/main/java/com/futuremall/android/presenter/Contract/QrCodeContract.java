package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.AesBean;
import com.futuremall.android.model.bean.UserInfo;


public class QrCodeContract {

    public interface View extends BaseView {

        void encryptSuccess(AesBean bean);

        void encryptFail();
    }

    public interface  Presenter extends BasePresenter<View> {


        void scan(String result);
    }
}
