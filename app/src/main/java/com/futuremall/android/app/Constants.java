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

    public static  final int SERVER_TOKEN_FAIL = 1001;

    public static  final String SERVER_TOKEN_FAIL_MSG = "access_token非法";

    public static  final int SERVER_SIGN_FAIL = 1002;

    public static  final String SERVER_SIGN_FAIL_MSG = "sign验证失败";

    public static  final int SERVER_PARAMETER_LOST = 1003;

    public static  final String SERVER_PARAMETER_LOST_MSG = "请求参数缺失";

    //================= TYPE ====================

    public static  final String NEW_USER = "1";//新用户注册

    public static  final String FIND_PASSWORD = "2";//找回密码

    public static  final String UPDATE_PAY_PASSWORD = "3";//修改支付密码

    public static  final int ACTIVITY_TEXT = 0x0011;// 文本

    public static  final int ACTIVITY_CHECKBOX = 0x0012;// checkbox 性别

    public static  final int ACTIVITY_PICKER = 0x0013;//picker 生日

    public static  final int ACTIVITY_TRANSFER = 0x0014;// 转账

    public static  final int ACTIVITY_PAY = 0x0015;// 支付

    public static  final int SUCCESS = 0x0016;// 成功

    public static  final int FAIL = 0x0017;// 失败

    public static  final int ACTIVITY_PAYMENT = 0x0018;// 结算支付

    public static  final int ACTIVITY_REGIST = 0x0019;// 邀请注册

    public static  final int ACTIVITY_REQUST_CODE = 0x0020;

    //================= STATUS ====================

    public static  final String DELIVERING = "0";//待发货 Deliver

    public static  final String RECEIPTING = "1";//待收货 receipt

    public static  final String RECEIPTED = "2";//已收货

    //================= pic ====================

    public static  final int PHOTO_REQUEST_GALLERY = 1;//图片

    public static  final int PHOTO_REQUEST_CUT = 2;//剪切

    //================= KEY ====================

    public static final String MALL_API_KEY = "weilai8088"; //sign key

    public static final String KEY_API = "52b7ec3471ac3bec6846577e79f20e4c"; //需要APIKEY请去 http://www.tianapi.com/#wxnew 申请,复用会减少访问可用次数

    public static final String KEY_ALIPAY = "aex07566wvayrgxicnaraae";

    public static final String LEANCLOUD_ID = "mhke0kuv33myn4t4ghuid4oq2hjj12li374hvcif202y5bm6";

    public static final String LEANCLOUD_SIGN = "badc5461a25a46291054b902887a68eb,1480438132702";

    public static final String BUGLY_ID = "257700f3f8";

    //================= H5 ====================

    public static final String MAIN_URL = "http://139.196.124.0/#";

    public static final String REGISTER_URL = "http://weilai8088.com/user.php?act=mobile_register";

    public static final String ADRRESS_URL = MAIN_URL + "address/index/";


    //================= INTENT ====================
    public static final String IT_SIGN = "sign";

    public static final String IT_KEY = "key";

    public static final String IT_TIME = "time";

    public static final String IT_TYPE = "type";

    public static final String IT_TITLE = "title";

    public static final String IT_VALUE = "value";

    public static final String IT_STATUS = "status";

    public static final String IT_ORDER_ID = "orderID";

    public static final String IT_USER_INFO = "userInfo";

    public static final String IT_RECID = "recID";

    public static final String IT_PHONE = "phone";

    public static final String IT_WEB_URL = "url";

    public static final String IT_TOKEN = "token";

    public static final String IT_MSG = "msg";
}
