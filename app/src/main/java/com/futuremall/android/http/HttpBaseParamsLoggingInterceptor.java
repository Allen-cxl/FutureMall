package com.futuremall.android.http;

import java.io.IOException;

import butterknife.internal.Utils;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PVer on 2017/3/5.
 */

public class HttpBaseParamsLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        RequestBody formBody = new FormBody.Builder()
                .add("userId", "10000")
                .add("sessionToken", "E34343RDFDRGRT43RFERGFRE")
                .add("q_version", "1.1")
                .add("device_id", "android-344365")
                .add("device_os", "android")
                .add("device_osversion","6.0")
                .add("req_timestamp", System.currentTimeMillis() + "")
                .add("app_name","forums")
                .add("sign", "md5")
                .build();
        /*String postBodyString = Utils.bodyToString(request.body());
        postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  Utils.bodyToString(formBody);
        request = requestBuilder
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
                        postBodyString))
                .build();*/
        return chain.proceed(request);
    }
}
