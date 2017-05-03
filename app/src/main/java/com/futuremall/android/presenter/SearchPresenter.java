package com.futuremall.android.presenter;

import android.app.Activity;

import com.futuremall.android.base.RxPresenter;
import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.http.RetrofitHelper;
import com.futuremall.android.model.bean.HotKeyWord;
import com.futuremall.android.presenter.Contract.SearchContract;
import com.futuremall.android.util.CommonConsumer;
import com.futuremall.android.util.LoadingStateUtil;
import com.futuremall.android.util.RxUtil;
import com.futuremall.android.util.TestData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/3/7.
 */

public class SearchPresenter extends RxPresenter<SearchContract.View> implements SearchContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private Activity mContext;

    @Inject
    SearchPresenter(RetrofitHelper mRetrofitHelper, Activity mContext) {
        this.mRetrofitHelper = mRetrofitHelper;
        this.mContext = mContext;
    }


    @Override
    public void hotLabel() {

        LoadingStateUtil.show(mContext);
        Disposable disposable = mRetrofitHelper.getHotSearch()
                .compose(RxUtil.<MyHttpResponse<List<HotKeyWord>>>rxSchedulerHelper())
                .compose(RxUtil.<List<HotKeyWord>>handleMyResult())
                .subscribe(new Consumer<List<HotKeyWord>>() {
                    @Override
                    public void accept(List<HotKeyWord> value) {
                        LoadingStateUtil.close();
                        if(null != value){
                            mView.showContent(value);
                        }
                    }
                }, new CommonConsumer<Object>(mView, mContext){
                    public void onError(){
                        LoadingStateUtil.close();
                    }
                });
        addSubscrebe(disposable);
    }
}
