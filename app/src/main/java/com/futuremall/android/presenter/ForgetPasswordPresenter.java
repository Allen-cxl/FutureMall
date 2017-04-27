package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.presenter.Contract.ForgetPasswordContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class ForgetPasswordPresenter extends RxPresenter<ForgetPasswordContract.View> implements ForgetPasswordContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    ForgetPasswordPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }

    @Override
    public void getCode(String phone, String type) {

        LoadingStateUtil.show(mContext);
        Disposable rxSubscription = mRetrofitHelper.getCode(phone, type)
                .compose(RxUtil.<MyHttpResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.handleMyResult())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object value) {
                        LoadingStateUtil.close();
                        mView.codeResponse();
                    }
                }, new CommonConsumer<Object>(mView){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void updatePassword(String phone, String code, String newPassword) {

        LoadingStateUtil.show(mContext);
        Disposable rxSubscription = mRetrofitHelper.findPassword(phone, code, newPassword)
                .compose(RxUtil.<MyHttpResponse<UserInfo>>rxSchedulerHelper())
                .compose(RxUtil.<UserInfo>handleMyResult())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo value) {
                        LoadingStateUtil.close();
                        mView.updateSuccess(value);
                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
