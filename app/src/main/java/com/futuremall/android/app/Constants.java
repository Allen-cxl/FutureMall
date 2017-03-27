package com.futuremall.android.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    //================= RadioButton ====================

    public static  final int RB_HOME = -1;

    public static  final int RB_TYPE = -2;

    public static  final int RB_FUTURE_ADD = -3;

    public static  final int RB_SHOPPING_CART = -4;

    public static  final int RB_USER = 4;

    //================= server code message ===============

    public static  final int SERVER_BUSY = -1;

    public static  final String SERVER_BUSY_MSG = "系统繁忙，稍后再试";

    public static  final int SERVER_SUCCESS = 0;

    public static  final String SERVER_SUCCESS_MSG = "处理成功";

    public static  final int SERVER_FAIL = 1000;

    public static  final String SERVER_FAIL_MSG = "处理失败";

    public static  final int SERVER_TOKEN_FAIL = 1001;

    public static  final String SERVER_TOKEN_FAIL_MSG = "access_token非法";

    public static  final int SERVER_SIGN_FAIL = 1002;

    public static  final String SERVER_SIGN_FAIL_MSG = "sign验证失败";

    public static  final int SERVER_PARAMETER_LOST = 1003;

    public static  final String SERVER_PARAMETER_LOST_MSG = "请求参数缺失";

    public static  final int SERVER_NO_PERMISSIONS = 1004;

    public static  final String SERVER_NO_PERMISSIONS_MSG = "无操作权限";

    public static  final int SERVER_ACOUNT_PASSWORD_ERROR = 1005;

    public static  final String SERVER_ACOUNT_PASSWORD_ERROR_MSG = "账号或密码不正确";

    public static  final int SERVER_ACOUNT_PASSWORD_FREEZE = 1006;

    public static  final String SERVER_ACOUNT_PASSWORD_FREEZE_MSG = "账号被冻结";

    //================= TYPE ====================

    public static  final String NEW_USER = "1";//新用户注册

    public static  final String FIND_PASSWORD = "2";//找回密码

    public static  final String UPDATE_PAY_PASSWORD = "3";//修改支付密码

    //================= STATUS ====================

    public static  final String DELIVERING = "0";//待发货 Deliver

    public static  final String RECEIPTING = "1";//待收货 receipt

    public static  final String RECEIPTED = "2";//已收货


    //================= KEY ====================

    public static final String MALL_API_KEY = "weilai8088"; //sign key
    public static final String KEY_API = "52b7ec3471ac3bec6846577e79f20e4c"; //需要APIKEY请去 http://www.tianapi.com/#wxnew 申请,复用会减少访问可用次数

    public static final String KEY_ALIPAY = "aex07566wvayrgxicnaraae";

    public static final String LEANCLOUD_ID = "mhke0kuv33myn4t4ghuid4oq2hjj12li374hvcif202y5bm6";

    public static final String LEANCLOUD_SIGN = "badc5461a25a46291054b902887a68eb,1480438132702";

    public static final String BUGLY_ID = "257700f3f8";

    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";


    //================= INTENT ====================
    public static final String IT_SIGN = "sign";

    public static final String IT_KEY = "key";

    public static final String IT_INTEGRAL = "integral";

    public static final String IT_TYPE = "type";

    public static final String IT_TITLE = "title";

    public static final String IT_VALUE = "value";

    public static final String IT_STATUS = "status";

    public static final String IT_ORDER_ID = "orderID";

    public static final String IT_USER_INFO = "userInfo";

    public static final String IT_GOLD_TYPE_STR = "type_str";

    public static final String IT_GOLD_MANAGER = "manager";

    public static final String IT_VTEX_TOPIC_ID = "id";

    public static final String IT_VTEX_REPLIES_TOP = "top_info";

    public static final String IT_VTEX_NODE_NAME = "node_name";
}
