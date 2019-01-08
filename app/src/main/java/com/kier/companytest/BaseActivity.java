package com.kier.companytest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class BaseActivity extends Activity {


	private Button base_top;
	private Button base_bom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base);

		initUI();
	}
	
	private void initUI() {
		// TODO Auto-generated method stub
		base_top = findViewById(R.id.base_top);
		base_bom = findViewById(R.id.base_bom);

		base_top.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivity.this, CameraActivity.class);
				startActivity(intent);
			}
		});
		base_bom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}


}
