
package com.futuremall.android.prefs;


public class PreferencesFactory {

    static UserPreferences sUserPref;

    public static UserPreferences getUserPref() {
        if (null == sUserPref) {
            sUserPref = new UserPreferences();
        }
        return sUserPref;
    }



}
