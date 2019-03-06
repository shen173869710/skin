package com.embed.skin.ui;

import android.app.Application;

import com.embed.skin.entity.DetectInfo;
import com.embed.skin.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */

public class BaseApp extends Application{

    public static List<DetectInfo> infos;
    public static UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        infos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            infos.add(new DetectInfo(i,"","","",false));
        }
    }


    public static boolean canShow() {
        boolean canShow = false;
        for (int i = 0; i < 4; i++) {
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
        for (int i = 0; i < 4; i++) {
            infos.add(new DetectInfo(i,"","","",false));
        }
    }


}
