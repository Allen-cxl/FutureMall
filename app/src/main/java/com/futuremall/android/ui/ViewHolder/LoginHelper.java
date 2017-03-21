package com.futuremall.android.ui.ViewHolder;

import android.content.Context;

import com.futuremall.android.prefs.PreferencesFactory;
import com.futuremall.android.ui.activity.LoginActivity;


public class LoginHelper {

    public static boolean ensureLogin(Context context) {

        if (!PreferencesFactory.getUserPref().isLogin()) {
            LoginActivity.enter(context);
            return false;
        }
        return true;
    }

}
