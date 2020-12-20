package com.embed.skin.util;

import android.graphics.Bitmap;
import android.util.Base64;

import com.hyphenate.util.HanziToPinyin;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *  上传
 */
public class UpImage {


    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        //Base64算法加密，当字符串过长（一般超过76）时会自动在中间加一个换行符，字符串最后也会加一个换行符。
        // 导致和其他模块对接时结果不一致。所以不能用默认Base64.DEFAULT，而是Base64.NO_WRAP不换行
        return new String(Base64.encode(bytes, Base64.NO_WRAP));
    }

    public static void  updataImage(String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final JSONObject json = new JSONObject();
                    json.put("image", str);
                    String data=json.toString().replace("\\","");
                    OkHttpClient client = new OkHttpClient();

                    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                    RequestBody body = RequestBody.create(mediaType, "");
                    final Request request = new Request.Builder()
                            .url("")
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            LogUtils.e("infoss", response.body().string());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
