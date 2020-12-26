package com.embed.skin.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.embed.skin.R;
import com.embed.skin.presenter.BasePresenter;
import com.embed.skin.util.ClientManager;
import com.embed.skin.util.ConnectResponse;
import com.embed.skin.util.LogUtils;
import com.embed.skin.util.ShareUtil;
import com.embed.skin.util.ToastUtils;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.jiangdg.usbcamera.UVCCameraHelper;
import com.jiangdg.usbcamera.utils.FileUtils;
import com.serenegiant.usb.CameraDialog;
import com.serenegiant.usb.Size;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.common.AbstractUVCCameraHandler;
import com.serenegiant.usb.widget.CameraViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * UVCCamera use demo
 * <p>
 * Created by jiangdongguo on 2017/9/30.
 */

public class USBCameraActivity extends BaseActivity implements CameraDialog.CameraDialogParent, CameraViewInterface.Callback {
    private static final String TAG = "USBCameraActivity";
    @BindView(R.id.camera_view)
    public View mTextureView;
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


    private UVCCameraHelper mCameraHelper;
    private CameraViewInterface mUVCCameraView;

    private boolean isRequest;
    private boolean isPreview;

    private boolean lightClick1 = false;
    private boolean lightClick2 = false;

    /**
     * 蓝牙的mac
     */
    private String bluetoothMac;

    private UVCCameraHelper.OnMyDevConnectListener listener = new UVCCameraHelper.OnMyDevConnectListener() {

        @Override
        public void onAttachDev(UsbDevice device) {
            // request open permission
            if (!isRequest) {
                isRequest = true;
                if (mCameraHelper != null) {
                    mCameraHelper.requestPermission(0);
                }
            }
        }

        @Override
        public void onDettachDev(UsbDevice device) {
            // close camera
            if (isRequest) {
                isRequest = false;
                mCameraHelper.closeCamera();
                showShortMsg(device.getDeviceName() + " is out");
            }
        }

        @Override
        public void onConnectDev(UsbDevice device, boolean isConnected) {
            if (!isConnected) {
                showShortMsg("fail to connect,please check resolution params");
                isPreview = false;
            } else {
                isPreview = true;
                showShortMsg("connecting");
                // initialize seekbar
                // need to wait UVCCamera initialize over
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Looper.prepare();
//                        if (mCameraHelper != null && mCameraHelper.isCameraOpened()) {
//                            mSeekBrightness.setProgress(mCameraHelper.getModelValue(UVCCameraHelper.MODE_BRIGHTNESS));
//                            mSeekContrast.setProgress(mCameraHelper.getModelValue(UVCCameraHelper.MODE_CONTRAST));
//                        }
                        Looper.loop();
                    }
                }).start();
            }
        }

        @Override
        public void onDisConnectDev(UsbDevice device) {
            showShortMsg("disconnecting");
        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_usbcamera;
    }

    @Override
    protected void init() {
        initView();
        // step.1 initialize UVCCameraHelper
        mUVCCameraView = (CameraViewInterface) mTextureView;
        mUVCCameraView.setCallback(this);
        mCameraHelper = UVCCameraHelper.getInstance();
        mCameraHelper.setDefaultFrameFormat(UVCCameraHelper.FRAME_FORMAT_MJPEG);
        mCameraHelper.setDefaultPreviewSize(1920, 1080);
        mCameraHelper.initUSBMonitor(this, mUVCCameraView, listener);
        mCameraHelper.setOnPreviewFrameListener(new AbstractUVCCameraHandler.OnPreViewResultListener() {
            @Override
            public void onPreviewResult(byte[] nv21Yuv) {
                Log.d(TAG, "onPreviewResult: " + nv21Yuv.length);
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initView() {
//        mSeekBrightness.setMax(100);
//        mSeekBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (mCameraHelper != null && mCameraHelper.isCameraOpened()) {
//                    mCameraHelper.setModelValue(UVCCameraHelper.MODE_BRIGHTNESS, progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        mSeekContrast.setMax(100);
//        mSeekContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (mCameraHelper != null && mCameraHelper.isCameraOpened()) {
//                    mCameraHelper.setModelValue(UVCCameraHelper.MODE_CONTRAST, progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
        checkPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // step.2 register USB event broadcast
        if (mCameraHelper != null) {
            mCameraHelper.registerUSB();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // step.3 unregister USB event broadcast
        if (mCameraHelper != null) {
            mCameraHelper.unregisterUSB();
        }
    }

    // example: {640x480,320x240,etc}
    private List<String> getResolutionList() {
        List<Size> list = mCameraHelper.getSupportedPreviewSizes();
        List<String> resolutions = null;
        if (list != null && list.size() != 0) {
            resolutions = new ArrayList<>();
            for (Size size : list) {
                if (size != null) {
                    resolutions.add(size.width + "x" + size.height);
                }
            }
        }
        return resolutions;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileUtils.releaseFile();
        // step.4 release uvc camera resources
        if (mCameraHelper != null) {
            mCameraHelper.release();
        }
        if (!TextUtils.isEmpty(bluetoothMac)) {
            ClientManager.getInstance().getClient().disconnect(bluetoothMac);
        }
    }

    private void showShortMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public USBMonitor getUSBMonitor() {
        return mCameraHelper.getUSBMonitor();
    }

    @Override
    public void onDialogResult(boolean canceled) {
        if (canceled) {
            showShortMsg("取消操作");
        }
    }


    @Override
    public void onSurfaceCreated(CameraViewInterface view, Surface surface) {
        if (!isPreview && mCameraHelper.isCameraOpened()) {
            mCameraHelper.startPreview(mUVCCameraView);
            isPreview = true;
        }
    }

    @Override
    public void onSurfaceChanged(CameraViewInterface view, Surface surface, int width, int height) {

    }

    @Override
    public void onSurfaceDestroy(CameraViewInterface view, Surface surface) {
        if (isPreview && mCameraHelper.isCameraOpened()) {
            mCameraHelper.stopPreview();
            isPreview = false;
        }
    }

    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            ToastUtils.showToast("请打开蓝牙 ");
            return;
        }

        bluetoothMac = ShareUtil.getLightMac(USBCameraActivity.this);
        if (!TextUtils.isEmpty(bluetoothMac)) {
            showDialog();
            connect(bluetoothMac);
        }else {
            ToastUtils.showToast("请设置灯光的蓝牙名称和mac地址");
        }
//        startScan();
    }

    private void startScan() {

        showDialog();
        LogUtils.e(TAG, "startScan");
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
                if (result.device != null) {
                    String name = result.device.getName();
                    bluetoothMac = result.device.getAddress();
                    LogUtils.e(TAG, "name = " + name);
                    if (!TextUtils.isEmpty(name) && name.contains("SerialApp")) {
                        ClientManager.getInstance().getClient().stopSearch();
                        connect(bluetoothMac);
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

                    Toast.makeText(USBCameraActivity.this, "连接灯光成功", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(USBCameraActivity.this, "连接灯光失败", Toast.LENGTH_LONG).show();
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
                    cmd1 = "C110";
                } else {
                    cmd1 = "C100";
                }
                onLightClick(cmd1);
                break;
            case R.id.camera_light_2:
                lightClick1 = false;
                lightClick2 = !lightClick2;
                String cmd2 = "";
                if (lightClick2) {
                    cmd2 = "C101";
                } else {
                    cmd2 = "C100";
                }
                onLightClick(cmd2);
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
                bluetoothMac = ShareUtil.getLightMac(USBCameraActivity.this);
                if (!TextUtils.isEmpty(bluetoothMac)) {
                    showDialog();
                    connect(bluetoothMac);
                }else {
                    ToastUtils.showToast("请设置灯光的蓝牙名称和mac地址");
                }
                break;
        }
    }

    public void onLightClick(String cmd) {
        if (TextUtils.isEmpty(bluetoothMac) || !ClientManager.getInstance().getConnectStatus(bluetoothMac)) {
            ToastUtils.showToast("蓝牙未连接");
            startScan();
            return;
        }
        byte[] writeBytes = new byte[20];
        writeBytes = hex2byte(cmd.getBytes());
        ClientManager.getInstance().writeData(bluetoothMac, writeBytes, new BleWriteResponse() {
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
        if (mCameraHelper == null || !mCameraHelper.isCameraOpened()) {
            showShortMsg("sorry,camera open failed");
            return;
        }
        String picPath = UVCCameraHelper.ROOT_PATH + "USBCamera" + "/" + System.currentTimeMillis() + UVCCameraHelper.SUFFIX_JPEG;

        mCameraHelper.capturePicture(picPath, new AbstractUVCCameraHandler.OnCaptureListener() {
            @Override
            public void onCaptureResult(String path) {
                if (TextUtils.isEmpty(path)) {
                    return;
                }
//                new Handler(getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(USBCameraActivity.this, "save path:" + path, Toast.LENGTH_SHORT).show();
//                    }
//                });
                Intent intent = new Intent(USBCameraActivity.this, PreviewActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }

}
