package com.futuremall.android.http;

import com.futuremall.android.app.Constants;
import com.futuremall.android.util.Md5Utils;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.PartMap;

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

       /* if(request.body() instanceof MultipartBody){
            Request.Builder requestBuilder = request.newBuilder();
            MultipartBody.Builder partBody = new MultipartBody.Builder();
            MultipartBody formBody = (MultipartBody) request.body();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < formBody.size(); i++) {

                MultipartBody.Part part = formBody.part(i);
                Headers headers = part.headers();
                String name = headers.name(i);
                String value = headers.value(i);

                sb.append(name);
                sb.append("=");
                sb.append(value);
                sb.append("&");
                partBody.addFormDataPart(name,value);
            }
            String sign = Md5Utils.getSign(sb);
            partBody.addFormDataPart(Constants.IT_SIGN, sign);

            requestBuilder.method(request.method(), partBody.build());
            request = requestBuilder.build();
        }*/

        return chain.proceed(request);
    }
}
