package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;


public class QrCodeContract {

    public interface View extends BaseView {

        void scanResponse();
    }

    public interface  Presenter extends BasePresenter<View> {


        void scan(String code);
    }
}
