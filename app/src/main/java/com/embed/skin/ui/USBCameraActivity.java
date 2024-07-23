package com.embed.skin.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.usb.UsbDevice;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.embed.skin.R;
import com.embed.skin.presenter.BasePresenter;
import com.embed.skin.util.ClientManager;
import com.embed.skin.util.ConnectResponse;
import com.embed.skin.util.LogUtils;
import com.embed.skin.util.ToastUtils;
import com.herohan.uvcapp.CameraHelper;
import com.herohan.uvcapp.ICameraHelper;
import com.herohan.uvcapp.IImageCapture;
import com.herohan.uvcapp.ImageCapture;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.serenegiant.usb.Size;
import com.serenegiant.utils.FileUtils;
import com.serenegiant.utils.UriHelper;
import com.serenegiant.widget.AspectRatioSurfaceView;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * UVCCamera use demo
 * <p>
 * Created by jiangdongguo on 2017/9/30.
 */

public class USBCameraActivity extends BaseActivity{
    private static final String TAG = "USBCameraActivity";
    @BindView(R.id.camera_view)
    public AspectRatioSurfaceView mCameraViewMain;
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

    private boolean isRequest;
    private boolean isPreview;

    /**
     * 蓝牙的mac
     */
    //LA_MODAC5B5C4AF
    private String bluetoothMac1 = "EC:B1:C3:00:0E:F0";
    //LA_MODAC418D7E7
    private String bluetoothMac2 = "EC:B1:C2:00:19:BB";
    private static final int DEFAULT_WIDTH = 2069;
    private static final int DEFAULT_HEIGHT = 1080;
    private int mPreviewWidth = DEFAULT_WIDTH;
    /**
     * Camera preview height
     */
    private int mPreviewHeight = DEFAULT_HEIGHT;

    private int mPreviewRotation = 0;
    private ICameraHelper mCameraHelper;
    @Override
    protected int setLayout() {
        return R.layout.activity_usbcamera;
    }

    @Override
    protected void init() {
        WindowManager windowManager = (WindowManager) USBCameraActivity.this.getSystemService(USBCameraActivity.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        Log.e(TAG, "metrics.widthPixels = "+metrics.widthPixels);
        Log.e(TAG, "metrics.heightPixels = "+metrics.heightPixels);
        mPreviewWidth = metrics.widthPixels;
        mPreviewHeight = metrics.heightPixels;
        mCameraViewMain.setAspectRatio(mPreviewWidth, mPreviewHeight);

        mCameraViewMain.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

                Log.e(TAG, "surfaceCreated    mCameraHelper =" +mCameraHelper);
                if (mCameraHelper != null) {
                    mCameraHelper.addSurface(holder.getSurface(), false);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                Log.e(TAG, "surfaceChanged");
//                showVideoFormatDialog();
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                Log.e(TAG, "surfaceDestroyed");
                if (mCameraHelper != null) {
                    mCameraHelper.removeSurface(holder.getSurface());
                }
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

        checkPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initCameraHelper();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // step.3 unregister USB event broadcast
        clearCameraHelper();
    }

    public void initCameraHelper() {
        if (mCameraHelper == null) {
            mCameraHelper = new CameraHelper();
            mCameraHelper.setStateCallback(mStateListener);
        }
    }

    private void clearCameraHelper() {
        if (mCameraHelper != null) {
            mCameraHelper.release();
            mCameraHelper = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void selectDevice(final UsbDevice device) {
        Log.v(TAG, "selectDevice:device=" + device.getDeviceName());
        mCameraHelper.selectDevice(device);
    }

    private final ICameraHelper.StateCallback mStateListener = new ICameraHelper.StateCallback() {
        @Override
        public void onAttach(UsbDevice device) {
            selectDevice(device);
        }

        @Override
        public void onDeviceOpen(UsbDevice device, boolean isFirstOpen) {
            mCameraHelper.openCamera();
        }

        @Override
        public void onCameraOpen(UsbDevice device) {
            mCameraHelper.startPreview();
            Size size = mCameraHelper.getPreviewSize();
//            if (size != null) {
//                resizePreviewView(size);
//            }
            mCameraHelper.addSurface(mCameraViewMain.getHolder().getSurface(), false);
            invalidateOptionsMenu();
        }

        @Override
        public void onCameraClose(UsbDevice device) {

            if (mCameraHelper != null) {
                mCameraHelper.removeSurface(mCameraViewMain.getHolder().getSurface());
            }

            invalidateOptionsMenu();
        }

        @Override
        public void onDeviceClose(UsbDevice device) {
             Log.v(TAG, "onDeviceClose:");
        }

        @Override
        public void onDetach(UsbDevice device) {
            Log.v(TAG, "onDetach:");
        }

        @Override
        public void onCancel(UsbDevice device) {
            Log.v(TAG, "onCancel:");
        }

    };

    private void showShortMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            ToastUtils.showToast("请打开蓝牙 ");
            return;
        }

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
        File file = FileUtils.getCaptureFile(this, Environment.DIRECTORY_DCIM, ".jpg");
        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(file).build();



        mCameraHelper.takePicture(options, new ImageCapture.OnImageCaptureCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {


                String path = UriHelper.getPath(USBCameraActivity.this, outputFileResults.getSavedUri());
                saveBmpToPath(rotatePicture(path,90),path);
                LogUtils.e(TAG, "path = "+path);
                Intent intent = new Intent(USBCameraActivity.this, PreviewActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }

            @Override
            public void onError(int imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
                Toast.makeText(USBCameraActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

//        String path = "/sdcard/DCIM/WeiXin/111.jpg";
//        Intent intent = new Intent(USBCameraActivity.this, PreviewActivity.class);
//        intent.putExtra("path", path);
//        startActivity(intent);
        LogUtils.e(TAG, "takePhoto");


//        String picPath = UVCCameraHelper.ROOT_PATH + "USBCamera" + "/" + System.currentTimeMillis() + UVCCameraHelper.SUFFIX_JPEG;


//                boolean save = saveBmpToPath(rotatePicture(BitmapFactory.decodeFile(path), 90), path);
//                Intent intent = new Intent(USBCameraActivity.this, PreviewActivity.class);
//                intent.putExtra("path", path);
//                startActivity(intent);

    }


    public Bitmap rotatePicture(String  path, final int degree) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
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
