package com.embed.skin.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import static com.hyphenate.chat.EMClient.TAG;

public class GlideUtils {
    /**
     *          加载本地图片
     * @param path
     * @param imageView
     */
    public static void loadFile(Context context, String path, ImageView imageView) {
        LogUtils.e(TAG, "loadFile= "+path);
        Glide.with(context).load(new File(path)).into(imageView);
    }
    /**
     *        本身res 图片
     * @param context
     * @param resId
     * @param imageView
     */
    public static void loadResource(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).into(imageView);
    }
}
