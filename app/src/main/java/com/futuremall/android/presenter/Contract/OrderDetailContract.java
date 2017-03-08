package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.OrderDetail;


public class OrderDetailContract {

    public interface View extends BaseView {

        void showContent(OrderDetail dataList);
    }

    public interface  Presenter extends BasePresenter<View> {


        void orderDetail();
    }
}
