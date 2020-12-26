package com.embed.skin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.embed.skin.BuildConfig;

/**
 * Created by Administrator on 2019/1/18.
 */

public class ShareUtil {

    private static final String LOGIN_NAME = "login_name";
    private static final String LOGIN_NAME_KEY = "login_name_key";

    private static final String SKIN_NAME = "skin_name";
    private static final String SKIN_MAC = "skin_mac";
    private static final String LIGHT_NAME = "light_name";
    private static final String LIGHT_MAC = "light_mac";
    /**
     *        登录名称
     * @param context
     * @param value
     */
    public static void saveName(Context context, String value) {
        saveValue(context,LOGIN_NAME_KEY,value);
    }

    public static String getName(Context context) {
        return getValue(context, LOGIN_NAME_KEY);
    }

    public static void setSkinName(Context context, String value) {
        saveValue(context,SKIN_NAME,value);
    }
    public static String getSkinName(Context context) {
        String value = getValue(context, SKIN_NAME);
        if (TextUtils.isEmpty(value)) {
            return BuildConfig.skin_name;
        }
        return value;
    }

    public static void setSkinMac(Context context, String value) {
        saveValue(context,SKIN_MAC,value);
    }

    public static String getSkinMac(Context context) {
        String value = getValue(context, SKIN_MAC);
        if (TextUtils.isEmpty(value)) {
            return BuildConfig.skin_mac;
        }
        return value;
    }


    public static void setLightName(Context context, String value) {
        saveValue(context,LIGHT_NAME,value);
    }

    public static String getLightName(Context context) {
        String value = getValue(context, LIGHT_NAME);
        if (TextUtils.isEmpty(value)) {
            return BuildConfig.light_name;
        }
        return value;
    }


    public static void setLightMac(Context context, String value) {
        saveValue(context,LIGHT_MAC,value);
    }

    public static String getLightMac(Context context) {
        String value = getValue(context, LIGHT_MAC);
        if (TextUtils.isEmpty(value)) {
            return BuildConfig.light_mac;
        }
        return value;
    }



    private static void saveValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static String getValue(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_NAME, context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString(key, null);
    }

}
