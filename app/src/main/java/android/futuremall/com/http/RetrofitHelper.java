package android.futuremall.com.http;


import android.futuremall.com.http.api.MallApis;

import java.util.List;

/**
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper {

    private MallApis mMallApiService;

    public RetrofitHelper(MallApis mallApiService) {
        this.mMallApiService = mallApiService;
    }

    /*public Observable<DailyListBean> fetchDailyListInfo() {
        return mMallApiService.getDailyList();
    }*/

   /* public Observable<WXHttpResponse<List<WXItemBean>>> fetchWechatListInfo(int num, int page) {
        return mWechatApiService.getWXHot(Constants.KEY_API, num, page);
    }*/
}
