package com.futuremall.android.ui.fragment;


import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.futuremall.android.R;
import com.futuremall.android.app.App;
import com.futuremall.android.app.Constants;
import com.futuremall.android.base.BaseFragment;
import com.futuremall.android.presenter.Contract.FutureAddContract;
import com.futuremall.android.presenter.FutureAddPresenter;
import com.futuremall.android.ui.activity.MallH5Activity;
import com.futuremall.android.ui.activity.QrCodeActivity;
import com.futuremall.android.ui.activity.SearchActivity;
import com.futuremall.android.util.LogUtil;
import com.futuremall.android.util.baiduService.LocationService;
import com.futuremall.android.widget.MallWebClient;
import com.futuremall.android.widget.MallWebView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;


public class FutureAddFragment extends BaseFragment<FutureAddPresenter> implements FutureAddContract.View{


    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.webView)
    MallWebView mWebView;
    LocationService locationService;
    private static final int ADDRESS = 0x00;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADDRESS:
                    mTvAddress.setText((String)msg.obj);
                    break;
            }
        }
    };

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

        mPresenter.checkGpsPermissions(new RxPermissions(getActivity()));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MallWebClient());
        mWebView.addJavascriptInterface(this,"linkH5Interface");
        loadUrl();
    }

    private void loadUrl(){
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
        LogUtil.i("Link"+url);
        if(null != url){
            MallH5Activity.enter(getContext(), url);
        }
    }

    @Override
    public void startQrCodeActivity() {
        QrCodeActivity.enter(getContext());
    }

    @Override
    public void openGpsSuccess() {
        locationService = ((App) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();// 定位SDK
    }

    /***
     * Stop location service
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onDestroy();
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                mHandler.obtainMessage(ADDRESS,location.getCity()).sendToTarget();
                LogUtil.i("locType ::"+location.getLocType());
            }
        }

        public void onConnectHotSpotMessage(String s, int i){
        }
    };
}
