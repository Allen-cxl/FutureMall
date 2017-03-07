package com.futuremall.android.http;


import com.futuremall.android.http.api.MallApis;
import com.futuremall.android.model.bean.VersionBean;

import io.reactivex.Flowable;

/**
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper {

    private MallApis mMallApiService;

    public RetrofitHelper(MallApis mallApiService) {
        this.mMallApiService = mallApiService;
    }

    public Flowable<MyHttpResponse<VersionBean>> getVersionInfo(String versionName, String versionCode, String sign) {
        return mMallApiService.getVersion(versionName, versionCode, sign);
    }

   /* public Observable<WXHttpResponse<List<WXItemBean>>> fetchWechatListInfo(int num, int page) {
        return mWechatApiService.getWXHot(Constants.KEY_API, num, page);
    }*/
}
