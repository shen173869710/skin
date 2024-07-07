package com.embed.skin.ui;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.location.LocationManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.embed.skin.R;
import com.embed.skin.adapter.DeviceAdapter;
import com.embed.skin.custom.TitleLayout;
import com.embed.skin.presenter.BasePresenter;
import com.embed.skin.util.ClientManager;
import com.embed.skin.util.ConnectResponse;
import com.embed.skin.util.LogUtils;
import com.embed.skin.util.NoFastClickUtils;
import com.embed.skin.util.ToastUtils;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;

public class BindPetiyaakActivity extends BaseActivity {

    private final int REQUEST_CODE_OPEN_GPS = 1;
    private final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    @BindView(R.id.bluelist)
    RecyclerView bluelist;
    @BindView(R.id.bind_bluetooth)
    TextView bindBluetooth;
    @BindView(R.id.bind_bluetooth_1)
    TextView bindBluetooth1;
    @BindView(R.id.bind_title)
    TitleLayout bindTitle;
    private List<BluetoothDevice> data = new ArrayList<>();
    private DeviceAdapter mDeviceAdapter;

    private String bluetoothName;
    private String bluetoothMac;
    private boolean isBind;

    private boolean click = false;
    private boolean click1 = false;


    @Override
    protected int setLayout() {
        return R.layout.activity_bind_petiyaak;
    }

    @Override
    protected void init() {
        bluelist.setLayoutManager(new LinearLayoutManager(this));
        mDeviceAdapter = new DeviceAdapter(data);
        mDeviceAdapter.setOnDeviceClickListener(new DeviceAdapter.OnDeviceClickListener() {
            @Override
            public void onConnect(BluetoothDevice bleDevice) {
                connect(bleDevice);
            }

            @Override
            public void onDisConnect(BluetoothDevice bleDevice) {
                ClientManager.getInstance().disconnect(bleDevice.getAddress());
                mDeviceAdapter.clearScanDevice();
            }

            @Override
            public void onDetail(BluetoothDevice bleDevice) {
            }
        });
        bluelist.setAdapter(mDeviceAdapter);
    }

    @Override
    protected void setListener() {
        bindBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }

                if (TextUtils.isEmpty(bluetoothMac) || TextUtils.isEmpty(bluetoothName)) {
                    ToastUtils.showToast("Please connect buletooth");
                    return;
                }
                click = !click;
                byte[] WriteBytes = new byte[20];
//                mPresenter.bindDeviced(info.getDeviceName(), bluetoothName, bluetoothMac, BaseApp.userInfo.getId());
                String cmd = "";
                if (click) {
                    cmd = "{#1:N0000N}";
                } else {
                    cmd = "{#1:Y0000Y}";
                }
                WriteBytes = hex2byte(cmd.getBytes());
                ClientManager.getInstance().writeData(bluetoothMac, WriteBytes, new BleWriteResponse() {
                    @Override
                    public void onResponse(int code) {
                        LogUtils.e("bind", "code = " + code);
                    }
                });

            }
        });


        bindBluetooth1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }

                if (TextUtils.isEmpty(bluetoothMac) || TextUtils.isEmpty(bluetoothName)) {
                    ToastUtils.showToast("Please connect buletooth");
                    return;
                }
                click1 = !click1;
//                mPresenter.bindDeviced(info.getDeviceName(), bluetoothName, bluetoothMac, BaseApp.userInfo.getId());
                byte[] WriteBytes = new byte[20];
                String cmd = "";
                if (click1) {
                    cmd = "C110";
                } else {
                    cmd = "C100";
                }
                WriteBytes = hex2byte(cmd.getBytes());
                ClientManager.getInstance().writeData(bluetoothMac, WriteBytes, new BleWriteResponse() {
                    @Override
                    public void onResponse(int code) {
                        LogUtils.e("bind", "code = " + code);
                    }
                });
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    public static byte[] hex2byte(byte[] b) {
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

    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            ToastUtils.showToast("Please turn on Bluetooth ");
            return;
        }
        startScan();
    }

    private void startScan() {
        showDialog();
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
                mDeviceAdapter.addDevice(result.device);
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




    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void connect(final BluetoothDevice bleDevice) {
        showDialog();
        ClientManager.getInstance().connectDevice(bleDevice.getAddress(), new ConnectResponse() {
            @Override
            public void onResponse(boolean isConnect) {
                LogUtils.e("bind", "isConnect = " + isConnect);

                if (isConnect) {
                    Toast.makeText(BindPetiyaakActivity.this, "链接成功", Toast.LENGTH_LONG).show();
                    bluetoothName = bleDevice.getName();
                    bluetoothMac = bleDevice.getAddress();
                    mDeviceAdapter.addDevice(bleDevice);
                    mDeviceAdapter.notifyDataSetChanged();
                    bluelist.scrollToPosition(0);

                    ClientManager.getInstance().notifyData(bluetoothMac, new BleNotifyResponse() {
                        @Override
                        public void onNotify(UUID service, UUID character, byte[] value) {
                                LogUtils.e(TAG, ""+new String(value));
                        }

                        @Override
                        public void onResponse(int code) {
                            LogUtils.e(TAG, "notifyData = "+code);
                        }
                    });

                } else {
                    Toast.makeText(BindPetiyaakActivity.this, "链接失败", Toast.LENGTH_LONG).show();
                }
                dismissDialog();
            }
        });

    }

    public void back(View view) {
        finish();
    }

    public void right2Click(View view) {
        checkPermissions();
    }


}
