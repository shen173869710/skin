package com.kier.companytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.camerakit.CameraKitView;


public class CameraActivity extends AppCompatActivity{


    private CameraKitView cameraKitView;
    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraKitView = findViewById(R.id.camera);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
                        Log.e("--------", "--"+bytes.length);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageCaptured(bytes, "保存第一张");
                            }
                        });

                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
                        Log.e("--------", "--"+bytes.length);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageCaptured(bytes,"保存第二张");
                            }
                        });
                    }
                });
            }
        });

    }


    public void imageCaptured(byte[] bytes, String text) {
        ResultHolder.dispose();
        ResultHolder.setImage(bytes);
        ResultHolder.setText(text);
        Intent intent = new Intent(this, PreviewActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
