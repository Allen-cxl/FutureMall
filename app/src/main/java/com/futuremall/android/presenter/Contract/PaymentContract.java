package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.BalanceBean;
import com.tbruyelle.rxpermissions2.RxPermissions;


public class PaymentContract {

    public interface View extends BaseView {

        void shopName(String name);

        void balance(BalanceBean bean);

        void paySuccess();

        void payFail(String msg);

        void showQrCode();

        void startQrCodeActivity();
    }

    public interface  Presenter extends BasePresenter<View> {


        void getShopName(String phone);

        void getBalance();

        void payment(String phone, String money, String password);

        void checkPermissions(RxPermissions rxPermissions);
    }
}
