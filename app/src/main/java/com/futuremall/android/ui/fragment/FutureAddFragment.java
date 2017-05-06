package com.futuremall.android.ui.fragment;


import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.futuremall.android.R;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.FutureAddContract;
import com.futuremall.android.presenter.FutureAddPresenter;
import com.futuremall.android.ui.activity.MallH5Activity;
import com.futuremall.android.ui.activity.QrCodeActivity;
import com.futuremall.android.ui.activity.SearchActivity;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.StringUtil;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


public class FutureAddFragment extends BaseFragment<FutureAddPresenter> implements FutureAddContract.View, AMapLocationListener {


    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.webView)
    MallWebView mWebView;
    private static final int ADDRESS = 0x00;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADDRESS:
                    mTvAddress.setText((String) msg.obj);
                    break;
            }
        }
    };

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_futureadd;
    }

    @Override
    protected void initEventAndData() {



        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MallWebClient());
        mWebView.addJavascriptInterface(this, "linkH5Interface");
        loadUrl();
    }

    private void loadUrl() {
        mWebView.loadUrl(Constants.FUTURE_URL);
    }

    @OnClick({R.id.iv_scan, R.id.layout_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:

                mPresenter.checkPermissions(new RxPermissions(getActivity()));
                break;
            case R.id.layout_search:

                SearchActivity.enter(getContext());
                break;
        }
    }

    @SuppressWarnings("unused")
    @android.webkit.JavascriptInterface
    public void OpenLinkH5(String url) {
        LogUtil.i("Link" + url);
        if (null != url) {
            MallH5Activity.enter(getContext(), url);
        }
    }

    @Override
    public void startQrCodeActivity() {
        QrCodeActivity.enter(getContext());
    }

    @Override
    public void openGpsSuccess() {
        locationClient = new AMapLocationClient(getContext().getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);

        //设置返回地址信息，默认为true
        locationOption.setNeedAddress(true);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(2000);
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 此方法为每隔固定时间会发起一次定位请求，locationOption，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        locationClient.startLocation();
    }


    /***
     * Stop location service
     */
    @Override
    public void onStart() {
        mPresenter.checkGpsPermissions(new RxPermissions(getActivity()));
        super.onStart();
    }

    /***
     * Stop location service
     */
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        locationClient.unRegisterLocationListener(this);
        locationClient.stopLocation();
        super.onStop();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度

                mHandler.obtainMessage(ADDRESS, aMapLocation.getCity()).sendToTarget();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil.i("AmapError:" + "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
}
