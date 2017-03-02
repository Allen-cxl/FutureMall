package android.futuremall.com.ui.activity;

import android.futuremall.com.R;
import android.futuremall.com.base.BaseActivity;
import android.futuremall.com.http.MyHttpResponse;
import android.futuremall.com.http.RetrofitHelper;
import android.futuremall.com.model.bean.VersionBean;
import android.futuremall.com.presenter.Contract.MainContract;
import android.futuremall.com.presenter.MainPresenter;
import android.futuremall.com.util.LogUtil;
import android.futuremall.com.util.RxUtil;
import android.futuremall.com.util.SnackbarUtil;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

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

        mPresenter.checkVersion();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(getWindow().getDecorView(),msg);
    }

    @Override
    public void showUpdateDialog(String versionContent) {
        LogUtil.i(versionContent);
    }
}
