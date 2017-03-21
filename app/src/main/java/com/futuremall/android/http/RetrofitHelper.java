package com.futuremall.android.http;


import com.futuremall.android.http.api.MallApis;
import com.futuremall.android.model.bean.BalanceBean;
import com.futuremall.android.model.bean.ShopBean;
import com.futuremall.android.model.bean.UserInfo;
import com.futuremall.android.model.bean.VersionBean;

import io.reactivex.Flowable;


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
}
