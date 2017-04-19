package com.futuremall.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Base64;
import android.widget.TextView;

import com.futuremall.android.R;
import com.futuremall.android.app.Constants;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;

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

        if (result.indexOf("pay:") > 0) {
            int index = result.indexOf("pay:");
            return result.substring(index, result.length());
        } else if (result.indexOf("weilai8088.com") > 0) {
            return result;
        } else {
            return null;
        }
    }

    public static int scanResultType(String result) {

        if (result.contains("pay:")) {

            return Constants.ACTIVITY_PAYMENT;
        } else if (result.contains("weilai8088.com")) {

            return Constants.ACTIVITY_REGIST;
        } else {
            return -1;
        }
    }


}
