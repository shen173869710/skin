package com.embed.skin.ui;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.usb.UsbDevice;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.embed.skin.R;
import com.embed.skin.presenter.BasePresenter;
import com.embed.skin.util.ClientManager;
import com.embed.skin.util.ConnectResponse;
import com.embed.skin.util.LogUtils;
import com.embed.skin.util.ShareUtil;
import com.embed.skin.util.ToastUtils;
import com.inuker.bluetooth.library.Constants;
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
import com.serenegiant.usb.widget.UVCCameraTextureView;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    ToggleImageButton cameraLight1;
    @BindView(R.id.camera_light_2)
    ToggleImageButton cameraLight2;


    private UVCCameraHelper mCameraHelper;
    private CameraViewInterface mUVCCameraView;

    private boolean isRequest;
    private boolean isPreview;

    private boolean lightClick1 = false;
    private boolean lightClick2 = false;

    /**
     * 蓝牙的mac
     */
    //LA_MODAC5B5C4AF
    private String bluetoothMac1 = "EC:B1:C3:00:0E:F0";
    //LA_MODAC418D7E7
    private String bluetoothMac2 = "EC:B1:C2:00:19:BB";

    static String mes = "";

    private UVCCameraHelper.OnMyDevConnectListener listener = new UVCCameraHelper.OnMyDevConnectListener() {

        @Override
        public void onAttachDev(UsbDevice device) {
            // request open permission
            if (!isRequest) {
                isRequest = true;
                if (mCameraHelper != null) {
                    int index = 0;
                    List<UsbDevice> devices = mCameraHelper.getUsbDeviceList();


                    for (int i =0; i < devices.size(); i++) {

                        String name = devices.get(i).getManufacturerName();
                        if (!TextUtils.isEmpty(name) && name.contains("CAMERA")) {
                            index = i;
                             showShortMsg(i+"");

                        }
                        LogUtils.e(TAG, "devices"+devices.get(i).getManufacturerName());

                    }

                    LogUtils.e(TAG, "index = "+index);
                    mCameraHelper.requestPermission(index);
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

            LogUtils.e(TAG, "onConnectDev" + isConnected);
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


        mTextureView = findViewById(R.id.camera_view);
        // step.1 initialize UVCCameraHelper
//        FrameLayout camera_root = findViewById(R.id.camera_root);
//        camera_root.removeAllViews();
//        camera_root.addView(mTextureView);
        mUVCCameraView = (CameraViewInterface) mTextureView;
        mUVCCameraView.setCallback(this);


        LogUtils.e(TAG, "  mUVCCameraView.getSurface() = " + mUVCCameraView.getSurface());

        mCameraHelper = UVCCameraHelper.getInstance();
        mCameraHelper.setDefaultFrameFormat(UVCCameraHelper.FRAME_FORMAT_MJPEG);

        mCameraHelper.setDefaultPreviewSize(1920, 1080);


        List<Size>  sizes = mCameraHelper.getSupportedPreviewSizes();


        mCameraHelper.initUSBMonitor(this, mUVCCameraView, listener);

//mCameraHelper
//        mUVCCameraView.setAspectRatio(1920/1080);
        mCameraHelper.setOnPreviewFrameListener(new AbstractUVCCameraHandler.OnPreViewResultListener() {
            @Override
            public void onPreviewResult(byte[] nv21Yuv) {
//                Log.e(TAG, "onPreviewResult: " + nv21Yuv.length);
            }
        });
    }

    @Override
    protected void setListener() {
        cameraLight1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String cmd1 = "";
                if (isChecked) {
                    cmd1 = "{#1:Y0000Y}";
                }else {
                    cmd1 = "{#1:N0000N}";
                }
                onLightClick(cmd1,1);
            }
        });

        cameraLight2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String cmd2 = "";
                if (isChecked) {
                    cmd2 = "{#1:Y0000Y}";
                } else {
                    cmd2 = "{#1:N0000N}";;
                }
                onLightClick(cmd2,2);
            }
        });
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
//        if (!TextUtils.isEmpty(bluetoothMac)) {
//            ClientManager.getInstance().getClient().disconnect(bluetoothMac);
//        }
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

        LogUtils.e(TAG, "onSurfaceCreated");

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
//
//        bluetoothMac1 = ShareUtil.getLightMac(USBCameraActivity.this);
//
//        if (!TextUtils.isEmpty(bluetoothMac1)) {
////            showDialog();
//            connect(bluetoothMac1);
//        } else {
//            ToastUtils.showToast("请设置灯光的蓝牙名称和mac地址");
//        }
//        startScan();
    }

    private void startScan(int index) {

//        showDialog();
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
                LogUtils.e(TAG, "result = " + result);
                if (result.device != null) {
                    String name = result.device.getName();
//                    bluetoothMac = result.device.getAddress();
                    LogUtils.e(TAG, "name = " + name);
                    if (!TextUtils.isEmpty(name) && name.contains("LA_MODAC5B5C4AF") && index == 1) {
                        LogUtils.e(TAG, "链接index 1");
                        ClientManager.getInstance().getClient().stopSearch();
                        bluetoothMac1 = result.device.getAddress();
//                        connect(bluetoothMac1);
                    }else if (!TextUtils.isEmpty(name) && name.contains("LA_MODAC418D7E7") && index == 2) {
                        LogUtils.e(TAG, "链接index 2");
                        ClientManager.getInstance().getClient().stopSearch();
                        bluetoothMac2 = result.device.getAddress();
//                        connect(bluetoothMac2);
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

    private void connect(final String mac, String cmd) {
        showDialog();
        LogUtils.e(TAG, "开始链接+" + mac);
        ClientManager.getInstance().connectDevice(mac, new ConnectResponse() {
            @Override
            public void onResponse(boolean isConnect) {
                LogUtils.e("bind", "isConnect = " + isConnect);
                dismissDialog();
                if (isConnect) {
//                    cameraRight.setBackground(getResources().getDrawable(R.mipmap.bluetooth_show));
                    Toast.makeText(USBCameraActivity.this, "连接灯光成功", Toast.LENGTH_LONG).show();
                    LogUtils.e(TAG, "onLightClick =" + cmd);
//                        byte[] writeBytes = new byte[20];
//                        writeBytes = hex2byte(cmd.getBytes());
                    ClientManager.getInstance().writeData(mac, cmd.getBytes(), new BleWriteResponse() {
                        @Override
                        public void onResponse(int code) {
                            LogUtils.e("bind", "code = " + code);
                        }
                    });
                } else {
                    Toast.makeText(USBCameraActivity.this, "连接灯光失败", Toast.LENGTH_LONG).show();
//                    cameraRight.setBackground(getResources().getDrawable(R.mipmap.bluetooth_blank));
                }
            }
        });
    }

    @OnClick({R.id.camera_left, R.id.camera_right, R.id.take_photo})
    public void onClick(View view) {
        switch (view.getId()) {
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
            boolean connect = ClientManager.getInstance().getConnectStatus(bluetoothMac1);
            if (connect) {
                writeDate(bluetoothMac1,cmd);
            }else {
                connect = ClientManager.getInstance().getConnectStatus(bluetoothMac2);
                if (connect) {
                    ClientManager.getInstance().disconnect(bluetoothMac2);
                }
                connect(bluetoothMac1, cmd);
            }
        }else {
            bluetoothMac = bluetoothMac2;
            boolean connect = ClientManager.getInstance().getConnectStatus(bluetoothMac2);
            if (connect) {
                writeDate(bluetoothMac2,cmd);
            }else {
                connect = ClientManager.getInstance().getConnectStatus(bluetoothMac1);
                if (connect) {
                    ClientManager.getInstance().disconnect(bluetoothMac1);
                }
                connect(bluetoothMac2, cmd);
            }
        }
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
        LogUtils.e(TAG, "takePhoto");

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
                boolean save = saveBmpToPath(rotatePicture(BitmapFactory.decodeFile(path), 90), path);
                Intent intent = new Intent(USBCameraActivity.this, PreviewActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
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

    /**
     *        链接蓝牙写入指定数据
     * @param bluetoothMac
     * @param cmd
     */
    private void writeDate(String bluetoothMac, String cmd) {
        ClientManager.getInstance().writeData(bluetoothMac, cmd.getBytes(), new BleWriteResponse() {
            @Override
            public void onResponse(int code) {
                LogUtils.e("bind", "code = " + code);
            }
        });
    }
}
