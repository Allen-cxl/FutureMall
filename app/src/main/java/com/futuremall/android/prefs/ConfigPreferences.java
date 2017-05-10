package com.futuremall.android.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.futuremall.android.app.App;


/**
 * userInfo preference
 */
public class ConfigPreferences extends BasePreferences {

    public String USERINFO_PREFS = "mall_config";

    public static final String MALL_LOGINED_PHONE = "loginEd_phone";

    public static final String MALL_ISFIRST = "isFirst";


    @Override
    public SharedPreferences getSharePreferences() {
        return App.getInstance().getSharedPreferences(USERINFO_PREFS, Context.MODE_PRIVATE);
    }
    /**
     * 设置登录成功
     */
    public void setLoginEdPhone(String phone) {
        putString(MALL_LOGINED_PHONE, phone);
    }

    /**
     * 设置首次进入
     */
    public void setIsFirst(boolean isFirst) {
        putBoolean(MALL_ISFIRST, isFirst);
    }

    /**
     * 获取已经登录的手机号
     */
    public String getLoginEdPhone() {
        String phone = getString(MALL_LOGINED_PHONE, null);
        return phone;
    }

    /**
     * 获取是否是首次进入
     */
    public boolean getIsFirst() {
        return getBoolean(MALL_ISFIRST, false);
    }

}
