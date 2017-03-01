package android.futuremall.com.ui.activity;

import android.futuremall.com.R;
import android.futuremall.com.base.BaseActivity;
import android.futuremall.com.http.MyHttpResponse;
import android.futuremall.com.http.RetrofitHelper;
import android.futuremall.com.model.VersionBean;
import android.futuremall.com.util.LogUtil;
import android.futuremall.com.util.RxUtil;
import android.os.Bundle;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @Inject
    RetrofitHelper mRetrofitHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

        Disposable rxSubscription = mRetrofitHelper.getVersionInfo("android")
                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
                .compose(RxUtil.<VersionBean>handleMyResult())
                .subscribe(new Consumer<VersionBean>() {
                    @Override
                    public void accept(VersionBean versionBean) throws Exception {
                        LogUtil.i(versionBean.toString());
                    }
                });
        new CompositeDisposable().add(rxSubscription);
    }

    @Override
    public void showError(String msg) {

    }
}
