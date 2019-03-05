package com.embed.skin.event;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2019/3/5.
 */

public class ImageEvent {
    public int image_type;

    public ImageEvent(int image_type, byte[] image) {
        this.image_type = image_type;
        this.image = image;
    }

    public byte[] image;

    public ImageEvent(int image_type) {
        this.image_type = image_type;
    }
}
