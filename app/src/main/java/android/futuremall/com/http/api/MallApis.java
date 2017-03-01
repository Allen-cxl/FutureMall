package android.futuremall.com.http.api;



import android.futuremall.com.http.MyHttpResponse;
import android.futuremall.com.model.VersionBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by codeest on 16/8/19.
 */

public interface MallApis {

    String HOST = "http://meetliveapi.24hmb.com/api/";

    /**
     * 获取版本号
     */
    @GET("GetVersionNo")
    Observable<MyHttpResponse<VersionBean>> getVersion(@Query("os") String os);

}
