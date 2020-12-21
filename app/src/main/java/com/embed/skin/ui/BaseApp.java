package com.embed.skin.ui;

import android.app.Application;
import android.content.Context;

import com.embed.skin.entity.DetectInfo;
import com.embed.skin.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */

public class BaseApp extends Application{

    public static List<DetectInfo> infos = new ArrayList<>();
    private static BaseApp mBaseApp;

    public static BaseApp getInstance() {
        return mBaseApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApp = this;
        BaseApp.clearDate();

    }

    public static boolean canShow() {
        boolean canShow = false;
        for (int i = 0; i < 3; i++) {
            if (infos.get(i).isSel) {
                canShow = true;
                break;
            }else {
                canShow = false;
            }
        }
        return canShow;
    }

    public static void clearDate() {
        for (int i = 0; i < 3; i++) {
            infos.add(new DetectInfo(i,"未检测","未检测","未检测",false));
        }
    }


}
