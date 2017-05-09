package com.futuremall.android.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by PVer on 2017/3/5.
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str) ||
                "null".equals(str))
            return true;
        return false;
    }

    public static boolean isStartFromZero(String str) {

        Pattern pattern = Pattern.compile("^0\\d*$");
        return pattern.matcher(str).matches();
    }

    public static SpannableStringBuilder getPrice(Context context, String str) {

        if (!isEmpty(str)) {
            String rmb = context.getString(R.string.rmb);
            String tempStr = rmb + str;
            final SpannableStringBuilder sp = new SpannableStringBuilder(tempStr);
            sp.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.font_small)), 0, rmb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sp;
        } else {
            return new SpannableStringBuilder("");
        }

    }

    public static void setOpreationRecordTextColor(String price, TextView textView, Context context) {

        if (isEmpty(price)) {
            return;
        }
        if (Float.valueOf(price) > 0) {
            textView.setText("+" + price);
            textView.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            textView.setText(price);
            textView.setTextColor(context.getResources().getColor(R.color.orange));
        }
    }

    public static String scanResult(String result) {

        if (result.contains("pay:") ) {
            int index = result.indexOf(":");
            return result.substring(index, result.length());
        } else if (result.contains("weccmall.com/h5/#/register")) {
            return result;
        } else {
            return null;
        }
    }

    public static boolean matchUrl(String url) {

        if (null == url) {
            return false;
        }
        url = url.trim();

        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        if (url.contains("goodsinfo")){
            return true;
        }
        return false;

    }

    public static String getGoodsInfoID(String url) {

        if (null == url) {
            return null;
        }
//        url = url.trim();
//        url = "http://meetlive.24hmb.com/Play?id=7aba72d5-beef-44a9-9110-1dc1889799c4";
        url = "http://139.196.124.0/goodsinfo?id=558";

        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        String id  = uri.getQueryParameter("id");

        return id;

    }

    public static int scanResultType(String result) {

        if (result.contains("pay:")) {

            return Constants.ACTIVITY_PAYMENT;
        } else if (result.contains("weccmall.com/h5/#/register")) {

            return Constants.ACTIVITY_REGIST;
        } else {
            return -1;
        }
    }


}
