package android.futuremall.com.presenter;

import android.futuremall.com.base.RxPresenter;
import android.futuremall.com.http.ApiException;
import android.futuremall.com.http.MyHttpResponse;
import android.futuremall.com.http.RetrofitHelper;
import android.futuremall.com.model.bean.VersionBean;
import android.futuremall.com.presenter.Contract.MainContract;
import android.futuremall.com.util.CommonConsumer;
import android.futuremall.com.util.RxUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Allen on 2017/3/2.
 * 主页
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    MainPresenter(RetrofitHelper mRetrofitHelper) {
        this.mRetrofitHelper = mRetrofitHelper;
    }

    @Override
    public void checkVersion() {

        Disposable rxSubscription = mRetrofitHelper.getVersionInfo("android")
                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleMyResult())
                .doOnError(new CommonConsumer(mView))
                .subscribe(new Consumer<VersionBean>() {
                    @Override
                    public void accept(VersionBean value) {
                        mView.showUpdateDialog(value.toString());
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {

    }
}
