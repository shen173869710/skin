package com.embed.skin.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.usb.UsbDevice;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.embed.skin.R;
import com.embed.skin.presenter.BasePresenter;
import com.embed.skin.util.ClientManager;
import com.embed.skin.util.ConnectResponse;
import com.embed.skin.util.LogUtils;
import com.embed.skin.util.ShareUtil;
import com.embed.skin.util.ToastUtils;
//import com.herohan.uvcapp.CameraHelper;
//import com.herohan.uvcapp.ICameraHelper;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.serenegiant.usb.Size;
//import com.serenegiant.widget.AspectRatioSurfaceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;


public class UCameraActivity extends BaseActivity {
    private static final String TAG = "UCameraActivity";
//    @BindView(R.id.camera_view)
//    public AspectRatioSurfaceView mTextureView;
    //    @BindView(R.id.seekbar_brightness)
//    public SeekBar mSeekBrightness;
//    @BindView(R.id.seekbar_contrast)
//    public SeekBar mSeekContrast;
    @BindView(R.id.take_photo)
    ImageButton takePhoto;
    @BindView(R.id.camera_left)
    ImageView cameraLeft;
    @BindView(R.id.camera_right)
    ImageView cameraRight;
    @BindView(R.id.camera_light_1)
    Button cameraLight1;
    @BindView(R.id.camera_light_2)
    Button cameraLight2;

    private boolean lightClick1 = false;
    private boolean lightClick2 = false;
    /**
     * 蓝牙的mac
     */
    private String bluetoothMac1;
    private String bluetoothMac2;

//    private ICameraHelper mCameraHelper;

    @Override
    protected int setLayout() {
        return R.layout.activity_usbcamera_bg;
    }

    @Override
    protected void init() {

        LogUtils.e(TAG, "init()");
        initView();
//        mTextureView = findViewById(R.id.camera_view);
//        mTextureView.setAspectRatio(640, 480);
//        mTextureView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(@NonNull SurfaceHolder holder) {
//                if (mCameraHelper != null) {
//                    mCameraHelper.addSurface(holder.getSurface(), false);
//                }
//            }
//
//            @Override
//            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
//                if (mCameraHelper != null) {
//                    mCameraHelper.removeSurface(holder.getSurface());
//                }
//            }
//        });
        // step.1 initialize UVCCameraHelper
//        FrameLayout camera_root = findViewById(R.id.camera_root);
//        camera_root.removeAllViews();
//        camera_root.addView(mTextureView);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initView() {
        checkPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (mCameraHelper == null) {
//            mCameraHelper = new CameraHelper();
//            mCameraHelper.setStateCallback(mStateListener);
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mCameraHelper != null) {
//            mCameraHelper.release();
//            mCameraHelper = null;
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        FileUtils.releaseFile();

    }

//    private final ICameraHelper.StateCallback mStateListener = new ICameraHelper.StateCallback() {
//        @Override
//        public void onAttach(UsbDevice device) {
//
//            mCameraHelper.selectDevice(device);
//        }
//
//        @Override
//        public void onDeviceOpen(UsbDevice device, boolean isFirstOpen) {
//            mCameraHelper.openCamera();
//        }
//
//        @Override
//        public void onCameraOpen(UsbDevice device) {
//            mCameraHelper.startPreview();
//            Size size = mCameraHelper.getPreviewSize();
//            if (size != null) {
//                int width = size.width;
//                int height = size.height;
//                //auto aspect ratio
//                mTextureView.setAspectRatio(width, height);
//            }
//            mCameraHelper.addSurface(mTextureView.getHolder().getSurface(), false);
//        }
//
//        @Override
//        public void onCameraClose(UsbDevice device) {
//            if (mCameraHelper != null) {
//                mCameraHelper.removeSurface(mTextureView.getHolder().getSurface());
//            }
//        }
//
//        @Override
//        public void onDeviceClose(UsbDevice device) {
//            LogUtils.e(TAG, "onDeviceClose:");
//        }
//
//        @Override
//        public void onDetach(UsbDevice device) {
//            LogUtils.e(TAG, "onDetach:");
//        }
//
//        @Override
//        public void onCancel(UsbDevice device) {
//            LogUtils.e(TAG, "onCancel:");
//        }
//
//    };

    private void showShortMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            ToastUtils.showToast("请打开蓝牙 ");
            return;
        }

        bluetoothMac1 = ShareUtil.getLightMac(UCameraActivity.this);

        if (!TextUtils.isEmpty(bluetoothMac1)) {
//            showDialog();
            connect(bluetoothMac1);
        } else {
            ToastUtils.showToast("请设置灯光的蓝牙名称和mac地址");
        }
//        startScan();
    }

    private void startScan(int index) {

//        showDialog();
        LogUtils.e(TAG, "startScan111111111111");
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        ClientManager.getInstance().getClient().search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onDeviceFounded(SearchResult result) {
                LogUtils.e(TAG, "result11111111111111 = " + result);
                if (result.device != null) {
                    String name = result.device.getName();
//                    bluetoothMac = result.device.getAddress();
                    LogUtils.e(TAG, "name = " + name);
                    if (!TextUtils.isEmpty(name) && name.contains("LA_MODAC5B5C4AF") && index == 1) {
                        LogUtils.e(TAG, "链接index 1");
                        ClientManager.getInstance().getClient().stopSearch();
                        bluetoothMac1 = result.device.getAddress();
                        connect(bluetoothMac1);
                    }else if (!TextUtils.isEmpty(name) && name.contains("LA_MODAC418D7E7") && index == 2) {
                        LogUtils.e(TAG, "链接index 2");
                        ClientManager.getInstance().getClient().stopSearch();
                        bluetoothMac2 = result.device.getAddress();
                        connect(bluetoothMac2);
                    }
                }
            }

            @Override
            public void onSearchStopped() {
              dismissDialog();
            }

            @Override
            public void onSearchCanceled() {
                dismissDialog();
            }
        });
    }

    private void connect(final String mac) {
        ClientManager.getInstance().connectDevice(mac, new ConnectResponse() {
            @Override
            public void onResponse(boolean isConnect) {
                LogUtils.e("bind", "isConnect = " + isConnect);
                dismissDialog();
                if (isConnect) {
                    cameraRight.setBackground(getResources().getDrawable(R.mipmap.bluetooth_show));

                    Toast.makeText(UCameraActivity.this, "连接灯光成功", Toast.LENGTH_LONG).show();
                    ClientManager.getInstance().notifyData(mac, new BleNotifyResponse() {
                        @Override
                        public void onNotify(UUID service, UUID character, byte[] value) {
                            LogUtils.e(TAG, "" + new String(value));
                        }

                        @Override
                        public void onResponse(int code) {
                            LogUtils.e(TAG, "notifyData = " + code);
                        }
                    });

                } else {
                    Toast.makeText(UCameraActivity.this, "连接灯光失败", Toast.LENGTH_LONG).show();
                    cameraRight.setBackground(getResources().getDrawable(R.mipmap.bluetooth_blank));
                }
            }
        });
    }

    @OnClick({R.id.camera_left, R.id.camera_right, R.id.camera_light_1, R.id.take_photo, R.id.camera_light_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_light_1:
                lightClick2 = false;
                lightClick1 = !lightClick1;
                String cmd1 = "";
                if (lightClick1) {
                    cmd1 = "{#1:Y0000Y}";
                } else {
                    cmd1 = "{#1:N0000N}";
                }
                onLightClick(cmd1,1);
                break;
            case R.id.camera_light_2:
                lightClick1 = false;
                lightClick2 = !lightClick2;
                String cmd2 = "";
                if (lightClick2) {
                    cmd2 = "{#1:Y0000Y}";
                } else {
                    cmd2 = "{#1:N0000N}";;
                }
                onLightClick(cmd2,2);
                break;
            case R.id.take_photo:
                takePhoto();
                break;
            case R.id.camera_left:
                finish();
                break;
            case R.id.camera_right:
//                Intent intent = new Intent(USBCameraActivity.this, BindPetiyaakActivity.class);
//                startActivity(intent);
//                bluetoothMac = ShareUtil.getLightMac(USBCameraActivity.this);
//                if (!TextUtils.isEmpty(bluetoothMac)) {
//                    showDialog();
//                    connect(bluetoothMac);
//                }else {
//                    ToastUtils.showToast("请设置灯光的蓝牙名称和mac地址");
//                }
                break;
        }
    }

    public void onLightClick(String cmd, int index) {
        String bluetoothMac = "";
        if (index == 1) {
            bluetoothMac = bluetoothMac1;
        }else {
            bluetoothMac = bluetoothMac2;
        }

        if (TextUtils.isEmpty(bluetoothMac) || !ClientManager.getInstance().getConnectStatus(bluetoothMac)) {
            ToastUtils.showToast("蓝牙未连接");
            startScan(index);
            return;
        }

        LogUtils.e(TAG, "onLightClick ="+cmd);
//        byte[] writeBytes = new byte[20];
//        writeBytes = hex2byte(cmd.getBytes());
        ClientManager.getInstance().writeData(bluetoothMac, cmd.getBytes(), new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                LogUtils.e("bind", "code = " + code);
            }
        });
    }


    public byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        b = null;
        return b2;
    }

    public void takePhoto() {

//        String path = "/sdcard/DCIM/WeiXin/111.jpg";
//        Intent intent = new Intent(USBCameraActivity.this, PreviewActivity.class);
//        intent.putExtra("path", path);
//        startActivity(intent);
//        if (mCameraHelper == null || !mCameraHelper.isCameraOpened()) {
//            showShortMsg("sorry,camera open failed");
//            return;
//        }
//        String picPath = UVCCameraHelper.ROOT_PATH + "USBCamera" + "/" + System.currentTimeMillis() + UVCCameraHelper.SUFFIX_JPEG;

//        mCameraHelper.capturePicture(picPath, new AbstractUVCCameraHandler.OnCaptureListener() {
//            @Override
//            public void onCaptureResult(String path) {
//                if (TextUtils.isEmpty(path)) {
//                    return;
//                }
////                new Handler(getMainLooper()).post(new Runnable() {
////                    @Override
////                    public void run() {
////                        Toast.makeText(USBCameraActivity.this, "save path:" + path, Toast.LENGTH_SHORT).show();
////                    }
////                });
//                boolean save = saveBmpToPath(rotatePicture(BitmapFactory.decodeFile(path), 90), path);
//                Intent intent = new Intent(UCameraActivity.this, PreviewActivity.class);
//                intent.putExtra("path", path);
//                startActivity(intent);
//            }
//        });
    }


    public Bitmap rotatePicture(final Bitmap bitmap, final int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return  Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public boolean saveBmpToPath(final Bitmap bitmap, final String filePath) {
        if (bitmap == null || filePath == null) {
            return false;
        }
        boolean result = false; //默认结果
        File file = new File(filePath);
        OutputStream outputStream = null; //文件输出流
        try {
            outputStream = new FileOutputStream(file);
            result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); //将图片压缩为JPEG格式写到文件输出流，100是最大的质量程度
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close(); //关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
