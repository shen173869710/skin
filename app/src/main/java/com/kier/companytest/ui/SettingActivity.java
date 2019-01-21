package com.kier.companytest.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kier.companytest.R;
import com.kier.companytest.adapter.HistoryAdapter;
import com.kier.companytest.model.respone.HistoryRespone;
import com.kier.companytest.util.ShareUtil;

import java.util.ArrayList;

public class SettingActivity extends Activity {

	private ListView history_list;
	private HistoryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);

		initUI();
	}
	
	private void initUI() {
		history_list = findViewById(R.id.history_list);

		final ArrayList<HistoryRespone>respones = new ArrayList<>();
		respones.add(new HistoryRespone("龙华店"));
		respones.add(new HistoryRespone("福田店"));
		respones.add(new HistoryRespone("民治店"));
		respones.add(new HistoryRespone("清湖店"));
		respones.add(new HistoryRespone("坂田店"));
		respones.add(new HistoryRespone("华强店"));
		history_list.setAdapter(new HistoryAdapter(this, respones));
		history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showChoose(respones.get(position).name);
			}
		});
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
