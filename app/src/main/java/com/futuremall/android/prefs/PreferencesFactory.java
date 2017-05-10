
package com.futuremall.android.prefs;


public class PreferencesFactory {

    static UserPreferences mUserPref;
    static ConfigPreferences mConfigPre;

    public static UserPreferences getUserPref() {
        if (null == mUserPref) {
            mUserPref = new UserPreferences();
        }
        return mUserPref;
    }

    public static ConfigPreferences getConfigPre() {
        if (null == mConfigPre) {
            mConfigPre = new ConfigPreferences();
        }
        return mConfigPre;
    }



}
