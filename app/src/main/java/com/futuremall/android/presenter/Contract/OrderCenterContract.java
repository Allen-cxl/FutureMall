package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.OrderList;

import java.util.List;


/**
 * Created by Allen on 2017/3/3.
 */

public class OrderCenterContract {

    public interface View extends BaseView {

        void showContent(List<OrderList> dataList);
    }

    public interface  Presenter extends BasePresenter<View> {


        void orderList(String p, String num, String state);
    }
}
