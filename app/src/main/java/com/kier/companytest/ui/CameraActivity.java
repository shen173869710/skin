package com.kier.companytest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.camerakit.CameraKitView;
import com.kier.companytest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;


public class CameraActivity extends AppCompatActivity{


    private static final String procpath = "/proc/gpio_set/rp_gpio_set";

    private final String OPEN_1 = "7_a4_1_0";
    private final String CLOSE_1 = "7_a4_1_1";
    private final String OPEN_2 = "7_a4_1_0";
    private final String CLOSE_2 = "7_a4_1_1";

    private CameraKitView cameraKitView;
    private ImageView btn1;
    private ImageView btn2;
    private ImageView camera_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraKitView = findViewById(R.id.camera);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        camera_ok = findViewById(R.id.camera_ok);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doOption();
//                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
//                    @Override
//                    public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
//                        Log.e("--------", "--"+bytes.length);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                imageCaptured(bytes, "保存第一张");
//                            }
//                        });
//
//                    }
//                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
//                    @Override
//                    public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
//                        Log.e("--------", "--"+bytes.length);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                imageCaptured(bytes,"保存第二张");
//                            }
//                        });
//                    }
//                });
            }
        });

        camera_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
//                    @Override
//                    public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
//                        Log.e("--------", "--"+bytes.length);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                imageCaptured(bytes,"保存第二张");
//                            }
//                        });
//                    }
//                });
            }
        });
    }

    private void doOption() {
        writeProc(procpath, OPEN_1.getBytes());
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

    public void back(View view) {
        finish();
    }

    private String writeProc(String path, byte[] buffer) {
        try {
            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "write error!";
        }
        return (buffer.toString());


    }

    private String getProcValue(String procPath, byte[] buf_pag) {
        char[] buffer = new char[32];
        try {
            File file = new File(procPath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buf_pag);
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "write error!";
        }

        try {
            FileReader mReader = new FileReader(new File(procPath));
            mReader.read(buffer, 0, 32);
            mReader.close();
        } catch (Exception e) {

            return "read error!!";
        }
        String str = new String(buffer);
        return str;
    }

}
