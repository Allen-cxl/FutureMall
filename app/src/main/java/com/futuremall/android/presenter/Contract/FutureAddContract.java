package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.VersionBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by Allen on 2017/3/2.
 */

public class FutureAddContract {

    public interface View extends BaseView {

        void startQrCodeActivity();

        void openGpsSuccess();
    }

    public interface  Presenter extends BasePresenter<View> {

        void checkPermissions(RxPermissions rxPermissions);

        void checkGpsPermissions(RxPermissions rxPermissions);
    }
}
