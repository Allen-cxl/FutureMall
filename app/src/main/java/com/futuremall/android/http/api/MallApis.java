package com.futuremall.android.http.api;



import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.model.bean.VersionBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by codeest on 16/8/19.
 */

public interface MallApis {

    String HOST = "http://api.yudianedu.cn/v3/web/Rest/elecclasscard/";

    /**
     * 获取版本号
     */
    @FormUrlEncoded
    @POST("getcardversion")
    Flowable<MyHttpResponse<VersionBean>> getVersion(@Field("version_name") String versionName, @Field("version_code") String versionCode, @Field("sign") String sign);

}
