package android.futuremall.com.http.api;



import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by codeest on 16/8/19.
 */

public interface MallApis {

    String HOST = "http://gank.io/api/";

    /**
     * 技术文章列表
     *//*
    @GET("data/{tech}/{num}/{page}")
    Observable<GankHttpResponse<List<GankItemBean>>> getTechList(@Path("tech") String tech, @Path("num") int num, @Path("page") int page);

    *//**
     * 微信精选列表
     *//*
    @GET("wxnew")
    Observable<WXHttpResponse<List<WXItemBean>>> getWXHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);*/

}
