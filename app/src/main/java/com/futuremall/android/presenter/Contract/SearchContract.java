package com.futuremall.android.presenter.Contract;

import com.futuremall.android.base.BasePresenter;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.model.bean.Tag;

import java.util.List;


public class SearchContract {

    public interface View extends BaseView {

        void showContent(List<Tag> tags);
    }

    public interface  Presenter extends BasePresenter<View> {


        void hotLabel();
    }
}
