package com.futuremall.android.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.futuremall.android.app.App;
import com.futuremall.android.model.bean.UserInfo;


/**
 * userInfo preference
 */
public class UserPreferences extends BasePreferences {

    public String USERINFO_PREFS = "mall_userinfo";

    //平时使用ac_token来识别身份。ac_token有效期为1个月，
    //过期后需要同时使用ac_token和rf_token来取得新token，或重新登录。
    private static final long VALIDATE_TIME = 5 * 24 * 60 * 60;        //单位秒 //提前几天去验证token有效

    public static final String MALL_ACCESS_TOKEN = "access_token";

    public static final String HMB_IS_OPEN_PUSH = "isOpenPush";


    @Override
    public SharedPreferences getSharePreferences() {
        return App.getInstance().getSharedPreferences(USERINFO_PREFS, Context.MODE_PRIVATE);
    }

    /**
     * 保存用户的信息
     *
     * @param user
     */
    public void saveUserInfo(UserInfo user) {
        if (user == null)
            return;

        putString(MALL_ACCESS_TOKEN, user.getAccess_token());
    }
    /**
     * 设置是否打开推送
     */
    public void setIsOpenPush(boolean bool) {
        putBoolean(HMB_IS_OPEN_PUSH, bool);
    }

    /**
     * 获取是否打开推送
     */
    public boolean getIsOpenPush() {
        return getBoolean(HMB_IS_OPEN_PUSH, true);
    }

    /**
     * 删除用户信息
     */
    public void removeUserInfo() {
        clear();
    }

    /**
     * 是否已经登录
     */
    public boolean isLogin() {
        String token = getString(MALL_ACCESS_TOKEN, null);
        return !TextUtils.isEmpty(token);
    }

    /**
     * 获取AC_token
     */
    public String getToken() {
        return getString(MALL_ACCESS_TOKEN, null);
    }

}
