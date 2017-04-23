package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.AddressBean;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.model.bean.PayOrderInfoBean;


public class PayOrderContract {

    public interface View extends BaseView {

        void payOrderInfo(PayOrderInfoBean payOrderInfoBean);

        void paySuccess();

        void payFail(String msg);

        void defaultAddress(AddressBean bean);
    }

    public interface  Presenter extends BasePresenter<View> {

        void getPayOrderInfo(String recID);

        void submitOrder(String recID, String addressID, String payPass);

        void goAddress();
    }
}
