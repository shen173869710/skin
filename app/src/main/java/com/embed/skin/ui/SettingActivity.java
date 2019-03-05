package com.embed.skin.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;

import com.embed.skin.R;
import com.embed.skin.adapter.MyExpandableListAdapter;
import com.embed.skin.entity.SettingInfo;
import com.embed.skin.util.ShareUtil;

import java.util.ArrayList;

public class SettingActivity extends Activity {

	private ExpandableListView setting_expend;
	private MyExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);

		initUI();
	}
	

	private void initUI() {
		setting_expend = findViewById(R.id.setting_expend);

		ArrayList<SettingInfo>infos = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			infos.add(new SettingInfo("地区   "+ i));
		}

		for (int i = 0; i < 5; i++) {
			infos.get(i).info = new ArrayList<>();
			infos.get(i).info.add(new SettingInfo.MachineInfo("编号  1"));
			infos.get(i).info.add(new SettingInfo.MachineInfo("编号  2"));
			infos.get(i).info.add(new SettingInfo.MachineInfo("编号  3"));
		}
		adapter = new MyExpandableListAdapter(this, infos);

		setting_expend.setAdapter(adapter);
	}


	public void back(View view) {
		finish();
	}

	private void showChoose(final String text) {
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("设置")
				.setMessage("您将设置为"+text)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ShareUtil.saveName(SettingActivity.this, text);
						SettingActivity.this.finish();
					}
				})
				.setNegativeButton("取消",null)
				.create();
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.btn_main_p));
		dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.btn_main_p));
	}
}
