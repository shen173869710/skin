package com.embed.skin.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2019/1/18.
 */

public class ShareUtil {

    private static final String LOGIN_NAME = "login_name";
    private static final String LOGIN_NAME_KEY = "login_name_key";

    public static void saveName(Context context, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_NAME_KEY, value);
        editor.commit();
    }

    public static String getName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_NAME, context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString(LOGIN_NAME_KEY, null);
    }

}
