package com.embed.skin.ui;

import android.Manifest;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.embed.skin.R;
import com.embed.skin.ui.viewManager.MainViewManager;
import com.embed.skin.util.ByteUtil;
import com.embed.skin.util.ClientManager;
import com.embed.skin.util.ConnectResponse;
import com.embed.skin.util.LogUtils;
import com.embed.skin.util.ToastUtil;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivityBg extends Activity {

    private String TAG = "MainActivityBg";
    private boolean mScanning = false;
    private boolean mIsConnect = false;
    private ArrayList<BluetoothDevice> mLeDevices = new ArrayList<BluetoothDevice>();
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    public final static String ACTION_DATA_AVAILABLE = "com.kier.bluetooth.le" + "" + ".ACTION_DATA_AVAILABLE";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.kier.bluetooth.le" + "" + ".ACTION_GATT_SERVICES_DISCOVERED";

    BluetoothDevice device;
    double water;
    double oil;
    double flex;
    String deviceTitle;

    private MainViewManager mainViewManager;
    private ImageView main_status;
    private Button main_start;
    private Button main_end;

    private TextView scan_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bg);


        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        main_status = findViewById(R.id.main_status);
        main_start = findViewById(R.id.main_start);
        main_end = findViewById(R.id.main_end);
        scan_result = findViewById(R.id.scan_result);
        mainViewManager = new MainViewManager(this);
//                /**清零操作**/
//                noWorking();

        main_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mainViewManager.onItemSelect >= 0) {
//                    noWorking();
//                }
                mainViewManager.startAnim();
                startScan();
            }
        });

        main_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseApp.canShow()) {
                    ToastUtil.showToast(MainActivityBg.this, "您还没有开始检测", Toast.LENGTH_SHORT);
                    return;
                }
                finish();
            }
        });

        main_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scanLeDevice(true);
                startScan();
            }
        });
    }

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
//        scanLeDevice(false);
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
//            if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, 1);
//            } else {
//                scanLeDevice(true);
//            }


        }
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            LogUtils.e(TAG, "name = " + device.getName());
            mLeDevices.add(device);
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
                deviceTitle = mBluetoothAdapter.getRemoteDevice(mLeDevices.get(0).getAddress()).getName() + "";

                LogUtils.e(TAG, "deviceTitle =" + deviceTitle);
                if (deviceTitle.equals("XYLT") || deviceTitle.equals("het-31-8")) {
                    if (device != null) {
                        mBluetoothGatt = device.connectGatt(getApplication(), false, mGattCallback);
                    }
                } else {
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
                }
            } else if (msg.what == 2) {
                main_status.setImageResource(R.mipmap.bluetooth_blank);
                mLeDevices.clear();
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.bt_over), Toast.LENGTH_SHORT);
                checkBluetooth();
            } else if (msg.what == 3) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_1), Toast.LENGTH_SHORT);
            } else if (msg.what == 4) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_2), Toast.LENGTH_SHORT);
            } else if (msg.what == 5) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_3), Toast.LENGTH_SHORT);
            } else if (msg.what == 6) {
                ToastUtil.showToast(getApplication(), getApplication().getResources().getString(R.string.testpro_4), Toast.LENGTH_SHORT);
            } else if (msg.what == 7) {
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

            LogUtils.e(TAG, "onDescriptorWriteonDescriptorWrite = " + status
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
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
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


    private void startScan() {
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
                LogUtils.e(TAG, "result2 = " + result.scanRecord);
                if (result.device != null) {
//                    if (ActivityCompat.checkSelfPermission(MainActivityBg.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
                    String name = result.device.getName();
                    String bluetoothMac = result.device.getAddress();
                    LogUtils.e(TAG, "name = " + name);
                    LogUtils.e(TAG, "bluetoothMac = " + bluetoothMac);
                    LogUtils.e(TAG, "scanRecord = " + result.scanRecord);

                    if (!TextUtils.isEmpty(name) && name.contains("XYLT")) {
                        LogUtils.e(TAG, "开始监听广播"+bluetoothMac);

                       // 40.1%   18.9%  7.4

                       // 0201 0605 0958 594C 5414 FFFF FFFE 0109 010F 7F1B 5300 68ED 9101 BD00 4A00

                        LogUtils.e(TAG, "scanRecord = " + result.scanRecord);

                        String msg = ByteUtil.bytes2HexStr(result.scanRecord);


                        ClientManager.getInstance().getClient().stopSearch();
                        LogUtils.e(TAG, "scanRecord = " + msg);


                        final byte[] data = result.scanRecord;

                        // 4A00  flex
                        double index56, index57, index58 = 0, index59 = 0;
                        index56 =  ByteUtil.hexStr2decimal(msg.substring(56,57));
                        index57 =  ByteUtil.hexStr2decimal(msg.substring(57,58));
                        index58 =  ByteUtil.hexStr2decimal(msg.substring(58,59));
                        index59 =  ByteUtil.hexStr2decimal(msg.substring(59,60));
                        LogUtils.e(TAG, "index56 = "+index56);
                        LogUtils.e(TAG, "index57 = "+index57);
                        LogUtils.e(TAG, "index58 = "+index58);
                        LogUtils.e(TAG, "index59 = "+index59);
                        //
                        double h = index58 *16 + index59;
                        double l = index56* 16 + index57;
                        double flex = l + h * 256;
                        flex = flex / 10l;
                        LogUtils.e(TAG, "flex = "+flex);

                        double index52, index53, index54 = 0, index55 = 0;
                        index52 =  ByteUtil.hexStr2decimal(msg.substring(52,53));
                        index53 =  ByteUtil.hexStr2decimal(msg.substring(53,54));
                        index54 =  ByteUtil.hexStr2decimal(msg.substring(54,55));
                        index55 =  ByteUtil.hexStr2decimal(msg.substring(55,56));
                        LogUtils.e(TAG, "index52 = "+index52);
                        LogUtils.e(TAG, "index53 = "+index53);
                        LogUtils.e(TAG, "index54 = "+index54);
                        LogUtils.e(TAG, "index55 = "+index55);
                        double l1 = index52 *16 + index53;
                        double h1 = index54* 16 + index55;
                        double oil = (l1 + h1 * 256)/10l;



                        double index48, index49, index50 = 0, index51 = 0;
                        index48 =  ByteUtil.hexStr2decimal(msg.substring(48,49));
                        index49 =  ByteUtil.hexStr2decimal(msg.substring(49,50));
                        index50 =  ByteUtil.hexStr2decimal(msg.substring(50,51));
                        index51 =  ByteUtil.hexStr2decimal(msg.substring(51,52));
                        LogUtils.e(TAG, "index48 = "+index48);
                        LogUtils.e(TAG, "index49 = "+index49);
                        LogUtils.e(TAG, "index50 = "+index50);
                        LogUtils.e(TAG, "index51 = "+index51);
                        double l2 = index48 *16 + index49;
                        double h2 = index50* 16 + index51;
                        double water = (l2 +h2 * 256)/10l;
//                        oil = oil / 10l;
                        LogUtils.e(TAG, "oil = "+oil);
                        String str = "water " + water + "%" + " oil " + oil + "%" + " flex " + flex;
                        LogUtils.e(TAG, " str ="+str);
                        scan_result.setText(""+str);

                        mainViewManager.showEnd(water+"%",oil+"%",flex+"");

//                        ClientManager.getInstance().notifyData(bluetoothMac, new BleNotifyResponse() {
//                            @Override
//                            public void onNotify(UUID service, UUID character, byte[] value) {
//                                LogUtils.e(TAG, "value ="+value);
//                            }
//
//                            @Override
//                            public void onResponse(int code) {
//                                LogUtils.e(TAG, "code ="+code);
//                            }
//                        });
                    }

                    //ED:68:00:53:1B:7F

                }
            }

            @Override
            public void onSearchStopped() {
            }

            @Override
            public void onSearchCanceled() {
            }
        });
    }

//    public void add(int index1, int index2, int index3, int index4, String msg) {
//        double index52, index53, index54 = 0, index55 = 0;
//        index52 =  ByteUtil.hexStr2decimal(msg.substring(index1,index2));
//        index53 =  ByteUtil.hexStr2decimal(msg.substring(53,54));
//        index54 =  ByteUtil.hexStr2decimal(msg.substring(54,55));
//        index55 =  ByteUtil.hexStr2decimal(msg.substring(55,56));
//
//    }


}
