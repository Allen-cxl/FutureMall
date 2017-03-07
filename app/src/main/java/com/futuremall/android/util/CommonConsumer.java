package com.futuremall.android.util;

import com.futuremall.android.base.BaseView;
import com.futuremall.android.http.ApiException;
import com.google.gson.JsonSyntaxException;

import android.text.TextUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


public  class CommonConsumer<T> implements Consumer<T> {

    private BaseView mView;
    private String mErrorMsg;

    public CommonConsumer(BaseView view){
        this.mView = view;
    }

    protected CommonConsumer(BaseView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    @Override
    public void accept(T t) {

        try {
            if (mView == null)
                return;
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
                mView.showError(mErrorMsg);
            } else if (t instanceof ApiException) {
                mView.showError(((ApiException) t).getMessage());
            } else if (t instanceof SocketTimeoutException) {
                mView.showError("网络超时，请检查网络");
            } else if (t instanceof ConnectException) {
                mView.showError("网络连接失败，请检查网络");
            }else if (t instanceof UnknownHostException) {
                mView.showError("网络异常，请检查网络");
            }else if (t instanceof JsonSyntaxException) {
                mView.showError("数据解析错误");
            }else if (t instanceof HttpException) {
                mView.showError("数据加载失败");
            }else {
                mView.showError("未知错误");
            }

        }catch (Exception e){
            mView.showError(e.toString());
        }

    }


}
