package com.embed.skin.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.embed.skin.R;

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
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_item_1:
				startActivity(new Intent(HomeActivity.this, PreviewActivity.class));
				break;
			case R.id.main_item_2:
				startActivity(new Intent(HomeActivity.this, MainActivity.class));
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
		final EditText et = new EditText(this);
		et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
		new AlertDialog.Builder(this).setTitle("请输入密码")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(et)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String input = et.getText().toString();
						if (input.equals("")) {
							Toast.makeText(getApplicationContext(), "密码不能为空！" + input, Toast.LENGTH_LONG).show();
						}
						else {
							Intent intent = new Intent();
							intent.putExtra("content", input);
							intent.setClass(HomeActivity.this, SettingActivity.class);
							startActivity(intent);
						}
					}
				})
				.setNegativeButton("取消", null)
				.show();
	}


	@Override protected void onStop() {
		super.onStop();
		// 移除消息监听
	}
}
