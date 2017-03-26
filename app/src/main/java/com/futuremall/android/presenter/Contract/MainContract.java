package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by Allen on 2017/3/2.
 */

public class MainContract {

    public interface View extends BaseView {

        void startQrCodeActivity();

        void showUpdateDialog(String versionContent);
    }

    public interface  Presenter extends BasePresenter<View> {

        void checkVersion();

        void checkPermissions(RxPermissions rxPermissions);
    }
}
