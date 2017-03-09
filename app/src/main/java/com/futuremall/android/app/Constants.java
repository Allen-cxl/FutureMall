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


    //================= KEY ====================

    public static final String MALL_API_KEY = "yudianedutest"; //需要APIKEY请去 http://apistore.baidu.com/ 申请,复用会减少访问可用次数
    public static final String KEY_API = "52b7ec3471ac3bec6846577e79f20e4c"; //需要APIKEY请去 http://www.tianapi.com/#wxnew 申请,复用会减少访问可用次数

    public static final String KEY_ALIPAY = "aex07566wvayrgxicnaraae";

    public static final String LEANCLOUD_ID = "mhke0kuv33myn4t4ghuid4oq2hjj12li374hvcif202y5bm6";

    public static final String LEANCLOUD_SIGN = "badc5461a25a46291054b902887a68eb,1480438132702";

    public static final String BUGLY_ID = "257700f3f8";

    //================= PATH ====================

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "codeest" + File.separator + "GeekNews";

    //================= PREFERENCE ====================

    public static final String SP_NIGHT_MODE = "night_mode";

    public static final String SP_NO_IMAGE = "no_image";

    public static final String SP_AUTO_CACHE = "auto_cache";

    public static final String SP_CURRENT_ITEM = "current_item";

    public static final String SP_LIKE_POINT = "like_point";

    public static final String SP_VERSION_POINT = "version_point";

    public static final String SP_MANAGER_POINT = "manager_point";

    //================= INTENT ====================
    public static final String IT_SIGN = "sign";

    public static final String IT_KEY = "key";

    public static final String IT_DETAIL_TITLE = "title";

    public static final String IT_DETAIL_URL = "url";

    public static final String IT_DETAIL_IMG_URL = "img_url";

    public static final String IT_DETAIL_ID = "id";

    public static final String IT_DETAIL_TYPE = "type";

    public static final String IT_GOLD_TYPE = "type";

    public static final String IT_GOLD_TYPE_STR = "type_str";

    public static final String IT_GOLD_MANAGER = "manager";

    public static final String IT_VTEX_TYPE = "type";

    public static final String IT_VTEX_TOPIC_ID = "id";

    public static final String IT_VTEX_REPLIES_TOP = "top_info";

    public static final String IT_VTEX_NODE_NAME = "node_name";
}