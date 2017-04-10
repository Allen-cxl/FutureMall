package com.futuremall.android.http.api;



import com.futuremall.android.http.MyHttpResponse;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.model.bean.ChangeShoppingCart;
import com.futuremall.android.model.bean.OperationRecordBean;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.model.bean.PayOrderInfoBean;
import com.futuremall.android.model.bean.ShopBean;
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.bean.VersionBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface MallApis {

    String HOST = "http://114.215.19.98/api/web/Rest/";

    /**
     * 获取版本号
     */
    @FormUrlEncoded
    @POST("getcardversion")
    Flowable<MyHttpResponse<VersionBean>> getVersion(@Field("version_name") String versionName, @Field("version_code") String versionCode);

    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST("user/getinfo")
    Flowable<MyHttpResponse<UserInfo>> userInfo(@Field("access_token") String token);

    /**
     * 用户手机号aes解密
     */
    @FormUrlEncoded
    @POST("user/aesdecrypt")
    Flowable<MyHttpResponse<UserInfo>> encryptPhone(@Field("encrypt_phone") String phone);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("auth/login")
    Flowable<MyHttpResponse<UserInfo>> login(@Field("mobile_phone") String phone, @Field("password") String password);

    /**
     * 邀请注册
     */
    @FormUrlEncoded
    @POST("user/invitecode")
    Flowable<MyHttpResponse<UserInfo>> inviteRegister(@Field("access_token") String token);

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
     * 修改登录密码
     */
    @FormUrlEncoded
    @POST("user/accountlog")
    Flowable<MyHttpResponse<List<OperationRecordBean>>> operationRecord(@Field("access_token") String token, @Field("p") String p, @Field("num") String num, @Field("time") String time);

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

    /**
     * 修改登录密码
     */
    @FormUrlEncoded
    @POST("auth/updatepass")
    Flowable<MyHttpResponse<UserInfo>> updateLoginPassword(@Field("access_token") String token, @Field("old_pass") String oldPassword, @Field("new_pass") String newPassword);

    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST("order/orderlist")
    Flowable<MyHttpResponse<List<OrderList>>> orderList(@Field("access_token") String token, @Field("p") String p, @Field("num") String num, @Field("state") String state);

    /**
     * 购物车
     */
    @FormUrlEncoded
    @POST("order/cartlist")
    Flowable<MyHttpResponse<List<ShoppingCartBean>>> shoppingCar(@Field("access_token") String token);

    /**
     * 修改购物车
     */
    @FormUrlEncoded
    @POST("order/changenum")
    Flowable<MyHttpResponse<ChangeShoppingCart>> changeShoppingCar(@Field("access_token") String token, @Field("rec_id") String recID, @Field("num") String num);

    /**
     * 删除购物车
     */
    @FormUrlEncoded
    @POST("order/cartdel")
    Flowable<MyHttpResponse<Object>> delShoppingCar(@Field("access_token") String token, @Field("rec_id") String recID);

    /**
     * 订单结算
     */
    @FormUrlEncoded
    @POST("order/cargolist")
    Flowable<MyHttpResponse<PayOrderInfoBean>> payOrderInfo(@Field("access_token") String token, @Field("rec_id") String recID);

    /**
     * 提交订单
     */
    @FormUrlEncoded
    @POST("order/suborder")
    Flowable<MyHttpResponse<Object>> submitOrder(@Field("access_token") String token, @Field("rec_id") String recID, @Field("address_id") String addressID, @Field("pay_pass") String payPass);

    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST("order/orderinfo")
    Flowable<MyHttpResponse<OrderDetail>> orderDetail(@Field("access_token") String token, @Field("order_id") String orderID);

    /**
     * 确认收货
     */
    @FormUrlEncoded
    @POST("order/affirmorder")
    Flowable<MyHttpResponse<Object>> affirmOrder(@Field("access_token") String token, @Field("order_id") String orderID, @Field("pay_pass") String payPassord);

    /**
     * 修改个人信息
     */
    @POST("user/updateuser")
    Flowable<MyHttpResponse<Object>> updateUser(@Body RequestBody Body);
}
