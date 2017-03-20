package com.futuremall.android.http;

import com.futuremall.android.app.Constants;
import com.futuremall.android.util.Md5Utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpBaseParamsLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if("GET".equals(request.method())){
            HttpUrl.Builder urlBuilder = request.url().newBuilder();
            urlBuilder= urlBuilder.addQueryParameter(Constants.IT_KEY, Constants.MALL_API_KEY);
            HttpUrl url = urlBuilder.build();
            String sign = Md5Utils.MD5_32bit(url.query());
            urlBuilder.removeAllQueryParameters(Constants.IT_KEY);
            urlBuilder.addQueryParameter(Constants.IT_SIGN, sign);

            request = request.newBuilder()
                    .url(urlBuilder.build())
                    .build();
        }

        if(request.body() instanceof FormBody){
            Request.Builder requestBuilder = request.newBuilder();
            FormBody.Builder formBodybuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < formBody.size(); i++) {
                String name = formBody.name(i);
                String value = formBody.value(i);
                sb.append(name);
                sb.append("=");
                sb.append(value);
                sb.append("&");
                formBodybuilder.add(name,value);
            }
            String sign = Md5Utils.getSign(sb);
            formBodybuilder.add(Constants.IT_SIGN, sign);

            requestBuilder.method(request.method(), formBodybuilder.build());
            request = requestBuilder.build();
        }

        return chain.proceed(request);
    }
}
