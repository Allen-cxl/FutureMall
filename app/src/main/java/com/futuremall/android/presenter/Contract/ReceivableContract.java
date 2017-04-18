package com.futuremall.android.presenter.Contract;

import android.graphics.Bitmap;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;


public class ReceivableContract {

    public interface View extends BaseView {

        void qrCodeBitmap(Bitmap bitmap);

        void saveSuccess();

        void saveFail();
    }

    public interface  Presenter extends BasePresenter<View> {

        void saveQrCode(Bitmap bitmap);

        void canvasQrCode(String content);
    }
}
