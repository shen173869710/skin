package com.kier.companytest.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kier.companytest.R;
import com.kier.companytest.custom.TitleLayout;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivityBg extends Activity {

    private boolean mScanning = false;
    private boolean mIsConnect = false;
    private ArrayList<BluetoothDevice> mLeDevices = new ArrayList<BluetoothDevice>();
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    public final static String ACTION_DATA_AVAILABLE = "com.kier.bluetooth.le" + "" + ".ACTION_DATA_AVAILABLE";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.kier.bluetooth.le" + "" + ".ACTION_GATT_SERVICES_DISCOVERED";
//    private ImageView mBluetooth_change_show;
//    private TextView mBlue_name;
//    private TextView mBlue_state;
//    private TextView mWater_date;
//    private TextView mOil_date;
//    private TextView mElastic_date;
//    private TextView mRssi;
//    private TextView mMacAddress;
//    private TextView mCount;
//    private Button mSaveMac;
//    private Button mZero_btn;
//    private Button mFinish_btn;
    BluetoothDevice device;
    double water;
    double oil;
    double flex;
    String deviceTitle;
    int count ;

    private MainViewManager mainViewManager;
    private ImageView main_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bg);

//        mBluetooth_change_show = (ImageView) findViewById(R.id.bluetooth_change_show);
//        mBlue_name = (TextView) findViewById(R.id.blue_name1);
//        mBlue_state = (TextView) findViewById(R.id.blue_state1);
//        mWater_date = (TextView) findViewById(R.id.water_date);
//        mOil_date = (TextView) findViewById(R.id.oil_date);
//        mElastic_date = (TextView) findViewById(R.id.elastic_date);
//        mRssi = (TextView) findViewById(R.id.reei);
//        mMacAddress = (TextView) findViewById(R.id.macAddress);
//        mCount = (TextView) findViewById(R.id.count);
//        mZero_btn = (Button) findViewById(R.id.zero_btn);
//        mFinish_btn = (Button) findViewById(R.id.finish_btn);
//        mSaveMac = (Button) findViewById(R.id.saveMac);
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        main_status = findViewById(R.id.main_status);

        mainViewManager = new MainViewManager(this, new MainViewManager.OnItemClick() {
            @Override
            public void onItemClick(int pos) {
                /**清零操作**/
                noWorking();
            }
        });

//        mSaveMac.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FileAddress();
//                Toast.makeText(getApplication(),"已保存",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mZero_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mWater_date.setText("0%");
//                mOil_date.setText("0%");
//                mElastic_date.setText("0");
//            }
//        });
//        mFinish_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMacAddress.setText("");
//                MyShareprefrence.getInstance(getApplication()).setCount(mCount.getText().toString());
//                startActivity(new Intent(getApplication(), MainActivityBg.class));
//                finish();
//            }
//        });
//
//        String count1 = MyShareprefrence.getInstance(this).getCount();
//
//        if(count1.equals("")){
//            mCount.setText("0");
//        }else{
//            mCount.setText(count1);
//        }
    }
//    private void mayRequestLocation() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplication(),
//                    Manifest.permission.ACCESS_COARSE_LOCATION);
//            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
//                //判断是否需要 向用户解释，为什么要申请该权限
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
//                        .ACCESS_COARSE_LOCATION))
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
//                            .ACCESS_COARSE_LOCATION}, REQUEST_FINE_LOCATION);
//                return;
//            } else {
//                scanLeDevice(true);
//            }
//        } else {
//            scanLeDevice(true);
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_FINE_LOCATION:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager
//                        .PERMISSION_GRANTED) {
//                    // The requested permission is granted.
//                    if (mScanning == false) {
//                        scanLeDevice(true);
//                    }
//                } else {
//                    // The user disallowed the requested permission.
//                }
//                break;
//            default:
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

    //蓝牙打开回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "蓝牙已启用", Toast.LENGTH_SHORT).show();
                scanLeDevice(true);
            }
        }
    }
    //Zero
    private void noWorking() {
        byte[] b = new byte[20];
        for (int i = 1; i < 20; i++) {
            b[i] = 0;
        }
        WriteBleData(SampleGattAttributes.SKIN_SERIVCE, SampleGattAttributes.SKIN_WRITE_CHARAC, b, 20);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        //打开蓝牙\
////        mayRequestLocation();
//        checkBluetooth();
//        if (mIsConnect == false) {
////            mayRequestLocation();
//            checkBluetooth();
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsConnect == false) {
            checkBluetooth();
//            mayRequestLocation();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
//        MyShareprefrence.getInstance(this).setCount(mCount.getText().toString());
        scanLeDevice(false);
    }

    //Bluetooth connection
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mLeDevices.clear();
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mHandler.removeCallbacks(runnable);
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    private void checkBluetooth() {
        // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        mHandler.removeCallbacks(runnable);
        if (!getApplication().getPackageManager().hasSystemFeature(PackageManager
                .FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(getApplication(), "当前手机不支持BLE蓝牙", Toast.LENGTH_SHORT).show();
        } else {
            // 检查设备上是否支持蓝牙
            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            } else {
                scanLeDevice(true);
            }
        }
    }

    private void FileAddress() {
        String filePath = "/sdcard/Test/";
        String fileName = "log.txt";
        writeTxtToFile(device.toString(), filePath, fileName);
    }

    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.i("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            String s = readFileOnLine(strFilePath);
//            String s1[] = s.split("\n");
            String s2 = null;
            if (s.equals("")) {
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(strContent.getBytes());
                raf.close();
                countAdd();
            }else{
                    if(s.contains(device.toString())){
                        return;
                    }else{
                        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                        raf.seek(file.length());
                        raf.write(strContent.getBytes());
                        raf.close();
                        countAdd();
                }
            }
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    private void countAdd(){
//        count = Integer.parseInt(mCount.getText().toString());
//        count++;
//        mCount.setText(String.valueOf(count));
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    public String readFileOnLine(String filePath) {//输入文件路径
        String as = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);//打开文件输入流
            StringBuffer sBuffer = new StringBuffer();
            DataInputStream dataIO = new DataInputStream(fis);//读取文件数据流
            String strLine = null;
            while ((strLine = dataIO.readLine()) != null) {//通过readline按行读取
                sBuffer.append(strLine + "\n");//strLine就是一行的内容
            }
            as = sBuffer.toString();
            dataIO.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return as;
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback
            () {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            mLeDevices.add(device);
//            mRssi.setText(String.valueOf(rssi));
            mHandler.post(runnable);
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            if (mLeDevices.size() > 0) {
                device = mBluetoothAdapter.getRemoteDevice(mLeDevices.get(0).getAddress());
                Log.e("Apian", device.toString());
                deviceTitle = mBluetoothAdapter.getRemoteDevice(mLeDevices.get(0).getAddress()).getName() + "";

                if (deviceTitle.equals("XYL-BT") || deviceTitle.equals("het-31-8")) {
                    if (device != null) {
//                        mBlue_name.setText(deviceTitle);
                        mBluetoothGatt = device.connectGatt(getApplication(), false, mGattCallback);
                    }
                } else {
//                    mayRequestLocation();
                    checkBluetooth();
                }
            }
        }
    };

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                if (mBluetoothGatt != null) {
                    displayGattServices(mBluetoothGatt.getServices());
                    main_status.setImageResource(R.mipmap.bluetooth_show);
//                    mBlue_state.setText(getApplication().getResources().getString(R.string.bt_state1));
//                    mMacAddress.setText(device.toString());
                }
            } else if (msg.what == 2) {
//                mBluetooth_change_show.setImageResource(R.mipmap.bluetooth_blank);
                main_status.setImageResource(R.mipmap.bluetooth_blank);
//                mBlue_state.setText(getApplication().getResources().getString(R.string.bt_state));
                mLeDevices.clear();
//                mMacAddress.setText("");
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.bt_over), Toast.LENGTH_SHORT);
                checkBluetooth();
            } else if (msg.what == 3) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_1), Toast.LENGTH_SHORT);
            } else if (msg.what == 4) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_2), Toast.LENGTH_SHORT);
//                mWater_date.setText("0%");
//                mOil_date.setText("0%");
//                mElastic_date.setText("0");
            } else if (msg.what == 5) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_3), Toast.LENGTH_SHORT);
            } else if (msg.what == 6) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_4), Toast.LENGTH_SHORT);
            } else if (msg.what == 7) {
//                mBlue_state.setText(getApplication().getResources().getString(R.string.testpro_5));
//                mWater_date.setText(BigDecimalUtil.doubleScale(water * 100, 1) + "%");
//                mOil_date.setText(BigDecimalUtil.doubleScale(oil * 100, 1) + "%");
//                mElastic_date.setText(BigDecimalUtil.doubleScale(flex, 1) + "");
                mainViewManager.showEnd(BigDecimalUtil.doubleScale(water * 100, 1) + "%",
                                        BigDecimalUtil.doubleScale(oil * 100, 1) + "%",
                                        BigDecimalUtil.doubleScale(flex, 1) + "");
            } else if (msg.what == 8) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_6), Toast.LENGTH_SHORT);
            }
            return false;
        }
    });

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Message message = mHandler.obtainMessage();
                message.what = 2;
                mHandler.sendMessage(message);
                mIsConnect = false;
            } else {
                mBluetoothGatt.discoverServices();
                mIsConnect = true;
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt,
                                      BluetoothGattDescriptor descriptor, int status) {

            System.out.println("onDescriptorWriteonDescriptorWrite = " + status
                    + ", descriptor =" + descriptor.getUuid().toString());
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic
                characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic
                characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Message message = mHandler.obtainMessage();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }

    };
    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic
            characteristic) {
        WriteLog(characteristic.getValue());

        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
            }
            final int heartRate = characteristic.getIntValue(format, 1);
        } else if (characteristic.getUuid().equals(UUID.fromString(SampleGattAttributes
                .SKIN_READ_CHARAC))) {
            String str;
            final byte[] data = characteristic.getValue();
            Message message = mHandler.obtainMessage();
            if (data == null) {
                ToastUtil.showToast(getApplication(), "data null", Toast.LENGTH_SHORT);
                return;
            }
            if ((data[0] & 0xFF) != 0xFF) {
                ToastUtil.showToast(getApplication(), "data head error", Toast.LENGTH_SHORT);
                return;
            }
            if ((data[1] & 0xAA) == 0xAA && (data[2] & 0xFF) == 0xFF) {
                message.what = 8;
                mHandler.sendMessage(message);
                return;
            }
            if ((data[1] & 0xAA) == 0xAA && (data[2] & 0xFE) == 0xFE) {//未完成清零，请擦拭测试探头后清零，正在清零中
                message.what = 3;
                mHandler.sendMessage(message);
                return;
            }
            if ((data[1] & 0xAA) == 0xAA && (data[2] & 0xFD) == 0xFD) {//已完成清零，请测试
                message.what = 4;
                mHandler.sendMessage(message);
                return;
            }
            if ((data[1] & 0xAA) == 0xAA && (data[2] & 0xFC) == 0xFC) {//测试结果出错，请将测试头和皮肤贴紧重新测试
                message.what = 5;
                mHandler.sendMessage(message);
                return;
            }
            if ((data[5] & 0xFE) == 0xFE) {//重新测试，请在测试过程中将测试头紧贴皮肤，不要抖动
                //FF 20 01 7B 00 FE 00 5B 00 00 00 00 00 00 00 00 00 00 00 00
                if (deviceTitle.equals("het-31-8")) {
                    data[5] = 0x00;
                } else {
                    message.what = 6;
                    mHandler.sendMessage(message);
                    return;
                }
            }

            int h, l;
            h = data[2] * 256;
            l = data[1] & 0xff;
            water = h + l;
//            water = water / 10.0;
            water = water / 1000.0;

            h = data[4] * 256;
            l = data[3] & 0xff;
            oil = h + l;
//            oil = oil / 10.0;
            oil = oil / 1000.0;

            h = data[6] * 256;
            l = data[5] & 0xff;
            flex = h + l;
            flex = flex / 10.0;
            str = "get data success: water " + water + "%" + " oil " + oil + "%" + " flex " + flex;
            System.out.println("=======" + str);

            //因为设备传值过来有问题，这个公式是为了解决当油分大于等于水分的时候
//            if(oil>=water){
//                oil = water*0.68;
//            }
            message.what = 7;
            mHandler.sendMessage(message);
            return;
        } else {
            WriteLog(characteristic.getValue());
        }
    }

    private void WriteLog(byte[] data) {
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
            }
            System.out.println("=======skintest" + new String(data) + "\n" + stringBuilder
                    .toString());
        } else {
            System.out.println("=======skintest no date");
        }
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {

            List<BluetoothGattCharacteristic> gattCharacteristics = gattService
                    .getCharacteristics();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                final int charaProp = gattCharacteristic.getProperties();
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    //mBluetoothLeService.readCharacteristic(characteristic);
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    if (mBluetoothAdapter == null || mBluetoothGatt == null) {
                        return;
                    }
                    if (gattCharacteristic.getUuid().equals(UUID.fromString(SampleGattAttributes
                            .SKIN_READ_CHARAC))) {
                        mBluetoothGatt.setCharacteristicNotification(gattCharacteristic, true);
                        BluetoothGattDescriptor descriptor = gattCharacteristic.getDescriptor
                                (UUID.fromString(SampleGattAttributes
                                        .CLIENT_CHARACTERISTIC_CONFIG));
                        if (descriptor != null) {
                            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                            mBluetoothGatt.writeDescriptor(descriptor);
                        }
                    }
                }
            }
        }
    }

    private void closeGatt() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
    }

    private void WriteBleData(String sUuid, String cUuid, byte[] aa, int len) {

        byte[] value = new byte[20];
        value[0] = (byte) 0x00;

        if (mIsConnect == false) {
            return;
        }
        if (mBluetoothGatt == null) {
            return;
        }
        List<BluetoothGattService> list = mBluetoothGatt.getServices();
        for (BluetoothGattService service : list) {
            if (service.getUuid().equals(UUID.fromString(sUuid))) {
                List<BluetoothGattCharacteristic> list1 = service.getCharacteristics();
                for (BluetoothGattCharacteristic cs : list1) {
                    if (cs.getUuid().equals(UUID.fromString(cUuid))) {
                        cs.setValue(aa);
                        cs.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
                        mBluetoothGatt.writeCharacteristic(cs);
                        break;
                    }
                }
                break;
            }
        }
    }


    public void back(View view) {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeGatt();
    }
}
