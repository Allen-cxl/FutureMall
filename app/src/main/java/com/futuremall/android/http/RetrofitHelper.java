package com.futuremall.android.http;


import com.futuremall.android.http.api.MallApis;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.model.bean.ChangeShoppingCart;
import com.futuremall.android.model.bean.OperationRecordBean;
import com.futuremall.android.model.bean.OrderDetail;
import com.futuremall.android.model.bean.OrderList;
import com.futuremall.android.model.bean.ShopBean;
import com.futuremall.android.model.bean.ShoppingCartBean;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.bean.VersionBean;
import com.futuremall.android.util.Md5Utils;
import com.futuremall.android.util.StringUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;


public class RetrofitHelper {

    private MallApis mMallApiService;

    public RetrofitHelper(MallApis mallApiService) {
        this.mMallApiService = mallApiService;
    }

    public Flowable<MyHttpResponse<VersionBean>> getVersionInfo(String versionName, String versionCode) {
        return mMallApiService.getVersion(versionName, versionCode);
    }

    public Flowable<MyHttpResponse<UserInfo>> userInfo(String token) {
        return mMallApiService.userInfo(token);
    }

    public Flowable<MyHttpResponse<UserInfo>> login(String phone, String password) {
        return mMallApiService.login(phone, password);
    }

    public Flowable<MyHttpResponse<Object>> transfer(String token, String phone, String money, String password) {
        return mMallApiService.transfer(token, phone, money, password);
    }

    public Flowable<MyHttpResponse<UserInfo>> userName(String token, String phone) {
        return mMallApiService.userName(token, phone);
    }

    public Flowable<MyHttpResponse<ShopBean>> shopName(String token, String phone) {
        return mMallApiService.shopName(token, phone);
    }

    public Flowable<MyHttpResponse<BalanceBean>> getBalance(String token) {
        return mMallApiService.getBalance(token);
    }

    public Flowable<MyHttpResponse<Object>> payment(String token, String phone, String money, String password) {
        return mMallApiService.payment(token, phone, money, password);
    }

    public Flowable<MyHttpResponse<Object>> getCode(String phone,String type) {
        return mMallApiService.getCode(phone, type);
    }

    public Flowable<MyHttpResponse<Object>> updatePayPassword(String token, String newPassword, String code) {
        return mMallApiService.updatePayPassword(token, newPassword, code);
    }

    public Flowable<MyHttpResponse<UserInfo>> updateLoginPassword(String token, String oldPassword, String newPassword) {
        return mMallApiService.updateLoginPassword(token, oldPassword, newPassword);
    }

    public Flowable<MyHttpResponse<List<OperationRecordBean>>> operationRecord(String token, String p, String num, String time) {
        return mMallApiService.operationRecord(token, p, num, time);
    }

    public Flowable<MyHttpResponse<List<ShoppingCartBean>>> shoppingCar(String token) {
        return mMallApiService.shoppingCar(token);
    }

    public Flowable<MyHttpResponse<ChangeShoppingCart>> changeShoppingCar(String token, String recID, String num) {
        return mMallApiService.changeShoppingCar(token, recID, num);
    }

    public Flowable<MyHttpResponse<List<OrderList>>> orderList(String token, String p, String num, String state) {
        return mMallApiService.orderList(token, p, num, state);
    }

    public Flowable<MyHttpResponse<OrderDetail>> orderDetail(String token, String orderID) {
        return mMallApiService.orderDetail(token, orderID);
    }

    public Flowable<MyHttpResponse<Object>> affirmOrder(String token, String orderID, String payPassword) {
        return mMallApiService.affirmOrder(token, orderID, payPassword);
    }

    public Flowable<MyHttpResponse<Object>> updateUser(String token, File file, int sex, String birthday, String realName) {

        RequestBody requestFile = null;
        String fileStr = null;
        if(file !=null){
            requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            fileStr = StringUtil.File2byte(file);
        }

        StringBuilder sb = new StringBuilder();
        if(!StringUtil.isEmpty(token)){

            sb.append("access_token");
            sb.append("=");
            sb.append(token);
            sb.append("&");
        }

        if(fileStr != null){

            sb.append("user_picture");
            sb.append("=");
            sb.append(fileStr);
            sb.append("&");
        }

        if(sex != -1){

            sb.append("sex");
            sb.append("=");
            sb.append(sex);
            sb.append("&");
        }

        if(!StringUtil.isEmpty(birthday)){

            sb.append("birthday");
            sb.append("=");
            sb.append(birthday);
            sb.append("&");
        }

        if(!StringUtil.isEmpty(realName)){
            sb.append("realName");
            sb.append("=");
            sb.append(realName);
        }

        String sign = Md5Utils.getSign(sb);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("access_token", token);
        builder.addFormDataPart("user_picture", System.currentTimeMillis()+"", requestFile);
        builder.addFormDataPart("sex", sex+"");
        builder.addFormDataPart("birthday", birthday);
        builder.addFormDataPart("real_name", realName);
        builder.addFormDataPart("sign", sign);
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();

        return mMallApiService.updateUser(multipartBody);
    }
}
