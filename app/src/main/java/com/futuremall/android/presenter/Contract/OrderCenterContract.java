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

        void showData(List<OrderList> dataList);

        void showMoreData(List<OrderList> dataList);

        void showNoMore();
    }

    public interface  Presenter extends BasePresenter<View> {

        void orderList(int p, int num, String state,  boolean isFirst);
    }
}
