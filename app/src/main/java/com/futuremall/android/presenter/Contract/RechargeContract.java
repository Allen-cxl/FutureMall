package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.BalanceBean;


public class RechargeContract {

    public interface View extends BaseView {

        void rechargeSuccess();

        void rechargeFail(String msg);

        void showViewMsg(String msg);
    }

    public interface  Presenter extends BasePresenter<View> {


        void recharge(String monney);

        void alipay(String phone);

        void onCheckPaySuccess(String orderID);

    }
}
