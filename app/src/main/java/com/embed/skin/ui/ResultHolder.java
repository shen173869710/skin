package com.embed.skin.ui;


import android.support.annotation.Nullable;

import com.camerakit.CameraKitView;


import java.io.File;


public class ResultHolder {

    private static byte[] image;
    private static File video;
    private static CameraKitView.Size nativeCaptureSize;
    private static long timeToCallback;
    private static int string;

    public static void setImage(@Nullable byte[] image) {
        ResultHolder.image = image;
    }

    @Nullable
    public static byte[] getImage() {
        return image;
    }

    public static void setVideo(@Nullable File video) {
        ResultHolder.video = video;
    }

    @Nullable
    public static File getVideo() {
        return video;
    }

    public static void setNativeCaptureSize(@Nullable CameraKitView.Size nativeCaptureSize) {
        ResultHolder.nativeCaptureSize = nativeCaptureSize;
    }

    @Nullable
    public static CameraKitView.Size getNativeCaptureSize() {
        return nativeCaptureSize;
    }

    public static void setTimeToCallback(long timeToCallback) {
        ResultHolder.timeToCallback = timeToCallback;
    }

    public static long getTimeToCallback() {
        return timeToCallback;
    }

    public static void dispose() {
        setImage(null);
        setNativeCaptureSize(null);
        setTimeToCallback(0);
        setText(-1);
    }

    public static void setText(int text) {
        string = text;
    }

    public static int getText() {
        return string;
    }

}
