package com.futuremall.android.presenter.Contract;

import android.widget.ImageView;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.UserInfo;


public class InviteRegisterContract {

    public interface View extends BaseView {

        void qrCodeResponse(String imgUrl);
    }

    public interface  Presenter extends BasePresenter<View> {

        void qrCode();


        void saveQrCode(ImageView imageView);
    }
}
