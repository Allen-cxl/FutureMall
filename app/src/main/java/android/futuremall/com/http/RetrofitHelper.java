package android.futuremall.com.http;


import android.futuremall.com.http.api.MallApis;
import android.futuremall.com.model.bean.VersionBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper {

    private MallApis mMallApiService;

    public RetrofitHelper(MallApis mallApiService) {
        this.mMallApiService = mallApiService;
    }

    public Flowable<MyHttpResponse<VersionBean>> getVersionInfo(String os) {
        return mMallApiService.getVersion(os);
    }

   /* public Observable<WXHttpResponse<List<WXItemBean>>> fetchWechatListInfo(int num, int page) {
        return mWechatApiService.getWXHot(Constants.KEY_API, num, page);
    }*/
}
