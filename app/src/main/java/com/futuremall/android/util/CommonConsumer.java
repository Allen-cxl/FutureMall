package com.futuremall.android.util;

import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseView;
import com.futuremall.android.http.ApiException;
import com.futuremall.android.ui.activity.LoginActivity;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.text.TextUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


public class CommonConsumer<T> implements Consumer<T> {

    private BaseView mView;
    private String mErrorMsg;
    private Activity mActivity;

    public CommonConsumer(BaseView view){
        this(view, null);
    }

    public CommonConsumer(BaseView view, Activity activity){
        this(view, activity, null);
    }

    protected CommonConsumer(BaseView view, Activity activity, String errorMsg){
        this.mView = view;
        this.mActivity = activity;
        this.mErrorMsg = errorMsg;
    }

    @Override
    public void accept(T t) {

        onError();
        try {
            if (mView == null)
                return;
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
                mView.showErrorMsg(mErrorMsg);
            } else if (t instanceof ApiException) {

                if(((ApiException) t).state == Constants.SERVER_TOKEN_FAIL){
                    LoginActivity.enter(mActivity);
                }else{
                    mView.showErrorMsg(((ApiException) t).srvMsg);
                }
            } else if (t instanceof SocketTimeoutException) {
                mView.showErrorMsg("网络超时，请检查网络");
            } else if (t instanceof ConnectException) {
                mView.showErrorMsg("网络连接失败，请检查网络");
            }else if (t instanceof UnknownHostException) {
                mView.showErrorMsg("网络异常，请检查网络");
            }else if (t instanceof JsonSyntaxException) {
                mView.showErrorMsg("数据解析错误");
            }else if (t instanceof HttpException) {
                mView.showErrorMsg("数据加载失败");
            }else {
                mView.showErrorMsg("未知错误");
            }

        }catch (Exception e){
        }
    }

    public void onError(){}
}
