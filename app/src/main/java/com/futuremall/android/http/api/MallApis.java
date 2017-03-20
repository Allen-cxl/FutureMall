package com.futuremall.android.http.api;



import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.model.bean.ShopBean;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.bean.VersionBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by codeest on 16/8/19.
 */

public interface MallApis {

    String HOST = "http://114.215.19.98/api/web/Rest/";

    /**
     * 获取版本号
     */
    @FormUrlEncoded
    @POST("getcardversion")
    Flowable<MyHttpResponse<VersionBean>> getVersion(@Field("version_name") String versionName, @Field("version_code") String versionCode);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("auth/login")
    Flowable<MyHttpResponse<UserInfo>> login(@Field("mobile_phone") String phone, @Field("password") String password);

    /**
     * 转账
     */
    @FormUrlEncoded
    @POST("user/accounts")
    Flowable<MyHttpResponse<Object>> transfer(@Field("access_token") String token, @Field("mobile_phone") String phone, @Field("user_money") String money, @Field("pay_pass") String password);

    /**
     * 检查用户获取用户姓名（A类转账）
     */
    @FormUrlEncoded
    @POST("user/getrealname")
    Flowable<MyHttpResponse<UserInfo>> userName(@Field("access_token") String token, @Field("mobile_phone") String phone);


    /**
     * 获取店铺名称
     */
    @FormUrlEncoded
    @POST("user/getshopname")
    Flowable<MyHttpResponse<ShopBean>> shopName(@Field("access_token") String token, @Field("mobile_phone") String phone);

    /**
     * 获取用户余额
     */
    @FormUrlEncoded
    @POST("user/getusermoney")
    Flowable<MyHttpResponse<BalanceBean>> getBalance(@Field("access_token") String token);

    /**
     * 支付结算
     */
    @FormUrlEncoded
    @POST("user/payment")
    Flowable<MyHttpResponse<Object>> payment(@Field("access_token") String token, @Field("mobile_phone") String phone, @Field("user_money") String money, @Field("pay_pass") String payPass);

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("auth/obtaincode")
    Flowable<MyHttpResponse<Object>> getCode(@Field("mobile_phone") String phone, @Field("type") String type);

    /**
     * 修改支付密码
     */
    @FormUrlEncoded
    @POST("auth/updatepaypass")
    Flowable<MyHttpResponse<Object>> updatePayPassword(@Field("access_token") String token, @Field("new_pass") String newPassword, @Field("code") String code);
}
