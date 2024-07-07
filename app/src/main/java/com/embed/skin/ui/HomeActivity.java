package com.embed.skin.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.embed.skin.R;
import com.embed.skin.custom.ChooseTimeDialog;
import com.embed.skin.util.ClientManager;
import com.embed.skin.util.ConnectResponse;
import com.embed.skin.util.LogUtils;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;

public class HomeActivity extends Activity implements View.OnClickListener{

	private String TAG = "HomeActivity";
	private RelativeLayout main_item_1;
	private RelativeLayout main_item_2;
	private RelativeLayout main_item_3;
	private RelativeLayout main_item_4;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initUI();
	}
	
	private void initUI() {
		main_item_1 = findViewById(R.id.main_item_1);
		main_item_2 = findViewById(R.id.main_item_2);
		main_item_3 = findViewById(R.id.main_item_3);
		main_item_4 = findViewById(R.id.main_item_4);
		main_item_1.setOnClickListener(this);
		main_item_2.setOnClickListener(this);
		main_item_3.setOnClickListener(this);
		main_item_4.setOnClickListener(this);
//		String cmd1 = "{#1:N0000N}";
//		String bluetoothMac1 = "EC:B1:C3:00:0E:F0";
//		connect1(bluetoothMac1, cmd1);
//		//LA_MODAC418D7E7
	}


	private void connect1(final String mac, String cmd) {
		ClientManager.getInstance().connectDevice(mac, new ConnectResponse() {
			@Override
			public void onResponse(boolean isConnect) {
				LogUtils.e("bind", "isConnect = " + isConnect);
				if (isConnect) {
					ClientManager.getInstance().writeData(mac, cmd.getBytes(), new BleWriteResponse() {
						@Override
						public void onResponse(int code) {
							LogUtils.e("bind", "code = " + code);

							connect2( "EC:B1:C2:00:19:BB","{#1:N0000N}");
						}
					});
				} else {

				}
			}
		});
	}

	private void connect2(final String mac, String cmd) {
		ClientManager.getInstance().connectDevice(mac, new ConnectResponse() {
			@Override
			public void onResponse(boolean isConnect) {
				LogUtils.e("bind", "isConnect = " + isConnect);
				if (isConnect) {
					ClientManager.getInstance().writeData(mac, cmd.getBytes(), new BleWriteResponse() {
						@Override
						public void onResponse(int code) {
							LogUtils.e("bind", "code = " + code);
						}
					});
				} else {

				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_item_1:

				startActivity(new Intent(HomeActivity.this, USBCameraActivity.class));
				break;
			case R.id.main_item_2:
				startActivity(new Intent(HomeActivity.this, MainActivityBg.class));
				break;
			case R.id.main_item_3:
				startActivity(new Intent(HomeActivity.this, WebviewActivity.class));
				break;
			case R.id.main_item_4:
				showDialog();
				break;
		}
	}


	public void showDialog() {
		ChooseTimeDialog.ShowDialog(HomeActivity.this, new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}


	@Override protected void onStop() {
		super.onStop();
		// 移除消息监听
	}
}
