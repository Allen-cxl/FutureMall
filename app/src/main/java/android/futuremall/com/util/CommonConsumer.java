package android.futuremall.com.util;

import android.futuremall.com.base.BaseView;
import android.futuremall.com.http.ApiException;
import android.text.TextUtils;

import io.reactivex.Emitter;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SafeSubscriber;
import retrofit2.HttpException;


/**
 * Created by codeest on 2017/2/23.
 */

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
    public void accept(T t) throws Exception{
        if (mView == null)
            return;
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showError(mErrorMsg);
        } else if (t instanceof ApiException) {
            mView.showError(t.toString());
        } else if (t instanceof HttpException) {
            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
        } else {
            mView.showError("未知错误ヽ(≧Д≦)ノ");
        }
    }
}
