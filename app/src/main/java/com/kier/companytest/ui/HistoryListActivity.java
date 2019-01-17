package com.kier.companytest.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kier.companytest.R;
import com.kier.companytest.adapter.HistoryAdapter;
import com.kier.companytest.model.respone.HistoryRespone;

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
		respones.add(new HistoryRespone("1"));
		respones.add(new HistoryRespone("2"));
		respones.add(new HistoryRespone("3"));
		respones.add(new HistoryRespone("4"));
		respones.add(new HistoryRespone("5"));
		respones.add(new HistoryRespone("6"));
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
