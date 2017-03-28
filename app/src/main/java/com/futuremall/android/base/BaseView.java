package com.futuremall.android.base;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface BaseView {

    void showErrorMsg(String msg);

    void showContent();

    void showEmpty();

    void showError();

    void showLoading();
}
