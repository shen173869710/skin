package com.kier.companytest.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.kier.companytest.R;

public class HistoryInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history_info);

		initUI();
	}
	
	private void initUI() {

	}


	public void back(View view) {
		finish();
	}


}
