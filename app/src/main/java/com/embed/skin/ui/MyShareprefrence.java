package com.embed.skin.ui;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：Wen
 * 日期：15/8/10下午10:13
 * 描述：缓存文件
 */
public class MyShareprefrence {

    private static SharedPreferences share;
    private static MyShareprefrence instance;

    public static MyShareprefrence getInstance(Context context) {
        if (instance == null) {
            instance = new MyShareprefrence();
            share = context.getSharedPreferences("MfinanceCredit", Context.MODE_PRIVATE);
        }
        return instance;
    }

    public void setScreenWidth(int width) {
        share.edit().putInt("ScreenWidth", width).commit();
    }

    public int getScreenWidth() {
        return share.getInt("ScreenWidth", 0);
    }

    public void setScreenHight(int hight) {
        share.edit().putInt("ScreenHight", hight).commit();
    }

    public int getScreenHight() {
        return share.getInt("ScreenHight", 0);
    }

    public long getRegisterSendMsgTime() {
        return share.getLong("RegisterSendMsgTime", 0);
    }

    public void setRemoteSendMsgTime(long time) {
        share.edit().putLong("RemoteSendMsgTime", time).commit();
    }

    //test
    public void setCount(String count) {
        share.edit().putString("count", count).commit();
    }

    public String getCount() {
        return share.getString("count", "");
    }

    public void setRate1(String rate1) {
        share.edit().putString("rate1", rate1).commit();
    }

    public String getRate1() {
        return share.getString("rate1", "");
    }

    public void setWidth1(String width1) {
        share.edit().putString("width1", width1).commit();
    }

    public String getWidth1() {
        return share.getString("width1", "");
    }

    public void setService1(String service1) {
        share.edit().putString("service1", service1).commit();
    }

    public String getService1() {
        return share.getString("service1", "");
    }

    public void setStop1(String stop1) {
        share.edit().putString("stop1", stop1).commit();
    }

    public String getStop1() {
        return share.getString("stop1", "");
    }

    public void setTime2(String time2) {
        share.edit().putString("time2", time2).commit();
    }

    public String getTime2() {
        return share.getString("time2", "");
    }

    public void setRate2(String rate2) {
        share.edit().putString("rate2", rate2).commit();
    }

    public String getRate2() {
        return share.getString("rate2", "");
    }

    public void setWidth2(String width2) {
        share.edit().putString("width2", width2).commit();
    }

    public String getWidth2() {
        return share.getString("width2", "");
    }

    public void setService2(String service2) {
        share.edit().putString("service2", service2).commit();
    }

    public String getService2() {
        return share.getString("service2", "");
    }

    public void setStop2(String stop2) {
        share.edit().putString("stop2", stop2).commit();
    }

    public String getStop2() {
        return share.getString("stop2", "");
    }

    public void setTime3(String time3) {
        share.edit().putString("time3", time3).commit();
    }

    public String getTime3() {
        return share.getString("time3", "");
    }

    public void setRate3(String rate3) {
        share.edit().putString("rate3", rate3).commit();
    }

    public String getRate3() {
        return share.getString("rate3", "");
    }

    public void setWidth3(String width3) {
        share.edit().putString("width3", width3).commit();
    }

    public String getWidth3() {
        return share.getString("width3", "");
    }

    public void setService3(String service3) {
        share.edit().putString("service3", service3).commit();
    }

    public String getService3() {
        return share.getString("service3", "");
    }

    public void setStop3(String stop3) {
        share.edit().putString("stop3", stop3).commit();
    }

    public String getStop3() {
        return share.getString("stop3", "");
    }

}
