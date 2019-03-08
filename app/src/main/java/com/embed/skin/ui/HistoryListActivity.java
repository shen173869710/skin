package com.embed.skin.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.embed.skin.R;
import com.embed.skin.adapter.HistoryAdapter;
import com.embed.skin.model.respone.HistoryRespone;

import java.util.ArrayList;

public class HistoryListActivity extends Activity {

	private ListView history_list;
	private HistoryAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history);

		initUI();
	}
	
	private void initUI() {
		history_list = findViewById(R.id.history_list);

		ArrayList<HistoryRespone>respones = new ArrayList<>();
		respones.add(new HistoryRespone("2019/1/9"));
		respones.add(new HistoryRespone("2019/2/9"));
		respones.add(new HistoryRespone("2019/3/9"));
		respones.add(new HistoryRespone("2019/4/9"));
		respones.add(new HistoryRespone("2019/5/9"));
		respones.add(new HistoryRespone("2019/6/9"));
		history_list.setAdapter(new HistoryAdapter(this, respones));
		history_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				startActivity(new Intent(HistoryListActivity.this, HistoryInfoActivity.class));
			}
		});
	}


	public void back(View view) {
		finish();
	}


}
