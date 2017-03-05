package com.futuremall.android.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.futuremall.android.R;

/**
 * Created by PVer on 2017/3/5.
 */

public class StringUtil {

    public static boolean isEmpty(String str){
        if (TextUtils.isEmpty(str) ||
                "null".equals(str))
            return true;
        return false;
    }

    public static SpannableStringBuilder getPrice(Context context,String str){

        if (!isEmpty(str)){
            String rmb = context.getString(R.string.rmb);
            String tempStr = rmb + str;
            final SpannableStringBuilder sp = new SpannableStringBuilder(tempStr);
            sp.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.font_small)), 0, rmb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sp;
        }else {
            return new SpannableStringBuilder("");
        }

    }
}
