package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.OperationRecordBean;

import java.util.List;


/**
 * Created by Allen on 2017/3/3.
 */

public class OperationRecordContract {

    public interface View extends BaseView {

        void showData(List<OperationRecordBean> recordBeanList);

        void showMoreData(List<OperationRecordBean> recordBeanList);

        void showNoMore();
    }

    public interface  Presenter extends BasePresenter<View> {

        void recordList(int p, int num, String time, boolean isFirst);
    }
}
