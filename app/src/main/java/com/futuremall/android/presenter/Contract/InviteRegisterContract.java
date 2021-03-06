package com.futuremall.android.presenter.Contract;

import android.graphics.Bitmap;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;


public class InviteRegisterContract {

    public interface View extends BaseView {

        void qrCodeResponse(String imgUrl);

        void qrCodeBitmap(Bitmap bitmap);

        void saveSuccess();

        void saveFail();
    }

    public interface  Presenter extends BasePresenter<View> {

        void qrCode();

        void saveQrCode(Bitmap bitmap);

        void canvasQrCode(String content);
    }
}
