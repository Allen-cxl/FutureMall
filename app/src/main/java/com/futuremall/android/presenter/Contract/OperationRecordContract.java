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

        void showContent(List<OperationRecordBean> recordBeanList);
    }

    public interface  Presenter extends BasePresenter<View> {


        void recordList(String p, String num, String time);
    }
}
