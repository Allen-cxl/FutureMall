package com.futuremall.android.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtil {

    public static boolean regexPhone(String phoneNum){

        if (StringUtil.isEmpty(phoneNum)) return false;
        Pattern p = Pattern.compile("^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        System.out.println(m.matches()+"---");
        return m.matches();
    }

    public static boolean regexPhoneAndMobile(String phoneNum){

        if (StringUtil.isEmpty(phoneNum)) return false;
        Pattern p = Pattern.compile("^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$");
        Pattern p2 = Pattern.compile("(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})$");
        Pattern p3 = Pattern.compile("(0[0-9]{2,3})?([2-9][0-9]{6,7})$");
        Matcher m = p.matcher(phoneNum);
        Matcher m2 = p2.matcher(phoneNum);
        Matcher m3 = p3.matcher(phoneNum);
        System.out.println(m.matches()+"---m1");
        System.out.println(m2.matches()+"---m2");
        System.out.println(m3.matches()+"---m3");
        return m.matches() || m2.matches() || m3.matches();
    }

    public static boolean regexEmail(String email){

        if (StringUtil.isEmpty(email)) return false;
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(email);
        System.out.println(m.matches()+"---");
        return m.matches();
    }

    public static boolean regexURL(String result){

        if (StringUtil.isEmpty(result)) return false;
        Pattern p = Pattern.compile("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
        Matcher m = p.matcher(result);
        System.out.println(m.matches()+"---");
        return m.matches();
    }

    public static boolean regexPwd(String pwd){

        if (StringUtil.isEmpty(pwd)) return false;
        Pattern p = Pattern.compile("^[a-zA-Z\\d]{6,16}$");
        Matcher m = p.matcher(pwd);
        System.out.println(m.matches()+"---");
        return m.matches();
    }

    /**
     * 验证身份证
     * @param cardNo
     * @return
     */
    public static boolean regexCardNo(String cardNo){

        if (StringUtil.isEmpty(cardNo)) return false;
        Pattern p = Pattern.compile("(^\\d{18}$)|(^\\d{15}$)|(^\\d{17}(\\d|X|x)$)");
        Matcher m = p.matcher(cardNo);
        System.out.println(m.matches()+"---");
        return m.matches();
    }

}
