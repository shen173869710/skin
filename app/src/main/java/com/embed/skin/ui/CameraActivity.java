package com.embed.skin.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.embed.skin.R;

import org.greenrobot.eventbus.EventBus;


public class CameraActivity extends AppCompatActivity {


    private static final String procpath = "/proc/gpio_set/rp_gpio_set";

    private final String OPEN_1 = "7_a4_1_1";
    private final String CLOSE_1 = "7_a4_1_0";
    private final String OPEN_2 = "5_C1_1_1";
    private final String CLOSE_2 = "5_C1_1_0";

    private ImageView btn1;
    private ImageView btn2;
    private ImageView camera_ok;


    private int image_type;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = "";
            if (msg.what == 1) {
                message = "第一张";
//                imageCaptured((byte[]) msg.obj, message);
            } else if (msg.what == 2) {
                message = "第二张";
//                imageCaptured((byte[]) msg.obj, message);
            } else if (msg.what == 0) {
                Toast.makeText(CameraActivity.this, "请打开灯光", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        EventBus.getDefault().register(this);

        image_type = getIntent().getIntExtra("image_type", 1);

        if (image_type == 1) {
        }else {
        }

        btn1 = findViewById(R.id.btn1);
//        btn1.setSelected(false);
        btn2 = findViewById(R.id.btn2);
//        btn2.setSelected(false);
        camera_ok = findViewById(R.id.camera_ok);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn1.setSelected(!btn1.isSelected());
//                btn2.setSelected(false);
//                doOption(CLOSE_2);
//                if (btn1.isSelected()) {
//                    doOption(CLOSE_1);
//                } else {
//                    doOption(OPEN_1);
//                }
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn2.setSelected(!btn2.isSelected());
//                btn1.setSelected(false);
//                doOption(CLOSE_1);
//                if (btn2.isSelected()) {
//                    doOption(CLOSE_2);
//                } else {
//                    doOption(OPEN_2);
//                }
//            }
//        });

        camera_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
//                    @Override
//                    public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
//                        Log.e("--------", "--" + bytes.length);
//                        Message message = new Message();
//                        if (image_type == 0) {
//                            message.what = 0;
//                        } else if (image_type == 1) {
//                            message.what = 1;
//                        } else if (image_type == 2) {
//                            message.what = 2;
//                        }
//                        message.obj = bytes;
//                        mHandler.sendMessage(message);
//                    }
//                });
            }
        });
    }

    public void back(View view) {
        finish();
    }

}
