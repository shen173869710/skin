package com.kier.companytest.ui;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.camerakit.CameraKitView;
import com.kier.companytest.R;
import com.kier.companytest.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;


public class CameraActivity extends AppCompatActivity {


    private static final String procpath = "/proc/gpio_set/rp_gpio_set";

    private final String OPEN_1 = "7_a4_1_1";
    private final String CLOSE_1 = "7_a4_1_0";
    private final String OPEN_2 = "5_C1_1_1";
    private final String CLOSE_2 = "5_C1_1_0";

    private CameraKitView cameraKitView;
    private ImageView btn1;
    private ImageView btn2;
    private ImageView camera_ok;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = "";
            if (msg.what == 1) {
                message = "第一张";
                btn1.setSelected(false);
                doOption(CLOSE_1);
                imageCaptured((byte[]) msg.obj, message);
            } else if (msg.what == 2) {
                message = "第二张";
                btn2 = findViewById(R.id.btn2);
                doOption(CLOSE_2);
                imageCaptured((byte[]) msg.obj, message);
            } else if (msg.what == 0) {
                Toast.makeText(CameraActivity.this, "请打开灯光", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraKitView = findViewById(R.id.camera);

        btn1 = findViewById(R.id.btn1);
        btn1.setSelected(false);
        btn2 = findViewById(R.id.btn2);
        btn2.setSelected(false);
        camera_ok = findViewById(R.id.camera_ok);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setSelected(!btn1.isSelected());
                btn2.setSelected(false);
                doOption(CLOSE_2);
                if (btn1.isSelected()) {
                    doOption(CLOSE_1);
                } else {
                    doOption(OPEN_1);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setSelected(!btn2.isSelected());
                btn1.setSelected(false);
                doOption(CLOSE_1);
                if (btn2.isSelected()) {
                    doOption(CLOSE_2);
                } else {
                    doOption(OPEN_2);
                }
            }
        });

        camera_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
                        Log.e("--------", "--" + bytes.length);
                        Message message = new Message();
                        if (!btn1.isSelected() && !btn2.isSelected()) {
                            message.what = 0;
                        } else if (btn1.isSelected()) {
                            message.what = 1;
                        } else if (btn2.isSelected()) {
                            message.what = 2;
                        }
                        message.obj = bytes;
                        mHandler.sendMessage(message);
                    }
                });
            }
        });
    }

    private void doOption(String option) {
        writeProc(procpath, option.getBytes());
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

        String str1 = getProcValue(procpath, str_cut_off(OPEN_1).getBytes());
        char[] char_str1 = str1.toCharArray();
        LogUtils.e("", "" + char_str1.toString());
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

    private String str_cut_off(String str) {
        char[] char_str = str.toString().toCharArray();
        int count_line = 0;
        int flag_line = 0;
        String tras_str = null;

        tras_str = String.valueOf(char_str[0]);
        for (int i = 0; i < str.length(); i++) {
            String str009 = String.valueOf(char_str[i]);
            if (str009.indexOf("_") != -1) {
                count_line++;
                if (2 == count_line) {
                    break;
                }

            }
            flag_line++;
        }

        if (2 == count_line) {
            for (int j = 1; j < flag_line; j++) {
                tras_str = tras_str + String.valueOf(char_str[j]);
            }

            return tras_str;
        } else if (1 == count_line) {

            return str;
        }

        return "cut off error";
    }
}
