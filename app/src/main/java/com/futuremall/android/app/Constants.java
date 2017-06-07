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

    public static  final int ACTIVITY_RECHARGE = 0x0022;// 支付recharge

    public static  final int SUCCESS = 0x0016;// 成功

    public static  final int FAIL = 0x0017;// 失败

    public static  final int ACTIVITY_PAYMENT = 0x0018;// 结算支付

    public static  final int ACTIVITY_REGIST = 0x0019;// 邀请注册

    public static  final int ACTIVITY_PAY_MENT_UPDATE = 0x0020;

    public static  final int ACTIVITY_TYPE_SERACH = 0x0021;

    public static  final int ACTIVITY_LOCAL_SERACH = 0x0022;

    //================= STATUS ====================

    public static  final String DELIVERING = "0";//待发货 Deliver

    public static  final String RECEIPTING = "1";//待收货 receipt

    public static  final String RECEIPTED = "2";//已收货

    public static  final String ALL = "4";//全部

    //================= alipay ====================

    public static final String RESULT_STATUS_SUCCESS = "9000";   // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档

    public static final String RESULT_STATUS_LOADING = "8000";   // 判断resultStatus 为非"9000"则代表可能支付失败 "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）

    public static final String RESULT_NOT_INSTALLED = "4000";       // 未安装支付宝

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

    public static final String MAIN_URL = "http://139.196.124.0/h5/#/";//http://139.196.124.0/h5/#/

    public static final String REGISTER_URL = MAIN_URL + "register?invite_code=";

    public static final String ADDRESS_URL = MAIN_URL + "address/index?token=";

    public static final String FUTURE_URL = MAIN_URL + "local?long=%s&lat=%s";

    public static final String TYPE_URL = MAIN_URL + "classify";

    public static final String SEARCH_URL = MAIN_URL + "classifyList?keyword=";

    public static final String ABOUT_URL = MAIN_URL + "about";


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

    public static final String IT_AES_PHONE = "aesPhone";

    public static final String IT_WEB_URL = "url";

    public static final String IT_TOKEN = "token";

    public static final String IT_MSG = "msg";

    public static final String IT_LATITUDE = "latitude";

    public static final String IT_LONGITUDE = "longitude";
}
