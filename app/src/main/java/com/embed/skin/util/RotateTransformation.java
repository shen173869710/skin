package com.embed.skin.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;

public class RotateTransformation implements Transformation<Bitmap> {
    private int rotationInDegrees;

    public RotateTransformation(int rotationInDegrees) {
        this.rotationInDegrees = rotationInDegrees;
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotationInDegrees);
        Bitmap rotatedBitmap = Bitmap.createBitmap(resource.get(), 0, 0, resource.get().getWidth(), resource.get().getHeight(), matrix, true);
        return new BitmapResource(rotatedBitmap, Glide.get(context).getBitmapPool());
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
