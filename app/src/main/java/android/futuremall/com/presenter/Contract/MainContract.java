package android.futuremall.com.presenter.Contract;

import android.futuremall.com.base.BasePresenter;
import android.futuremall.com.base.BaseView;

import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * Created by Allen on 2017/3/2.
 */

public class MainContract {

    public interface View extends BaseView {

        void showUpdateDialog(String versionContent);
    }

    public interface  Presenter extends BasePresenter<View> {

        void checkVersion();

        void checkPermissions(RxPermissions rxPermissions);
    }
}
