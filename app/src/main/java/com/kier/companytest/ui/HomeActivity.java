package com.kier.companytest.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.kier.companytest.R;

public class HomeActivity extends Activity {
	private Button base_top;
	private Button base_bom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		initUI();
	}
	
	private void initUI() {
		// TODO Auto-generated method stub
		base_top = findViewById(R.id.base_top);
		base_bom = findViewById(R.id.base_bom);

		base_top.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
				startActivity(intent);
			}
		});
		base_bom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}


	public void rightClick(View view) {
		startActivity(new Intent(HomeActivity.this, HistoryListActivity.class));
	}

}
