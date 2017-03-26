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

import java.util.List;

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
}
