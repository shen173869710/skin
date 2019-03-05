package com.embed.skin.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.embed.skin.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class NewActivity extends Activity {

	private static final String procpath = "/proc/gpio_set/rp_gpio_set";
	
	Button btn_write001, btn_write002, btn_write003, btn_write004,
		   btn_read001, btn_read002, btn_read003, btn_read004,
		   btn_show_gpio_level001, btn_show_gpio_level002, btn_show_gpio_level003,
		   btn_show_gpio_level004, btn_read_all_gpio;
		   
	EditText et_gpio_function001, et_gpio_function002, 
			 et_gpio_function003, et_gpio_function004;
	
	TextView tv_read_current_gpio_level;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.new_activity_main);

		initUI();
	}
	
	private void initUI() {
		// TODO Auto-generated method stub
		
		et_gpio_function001 = (EditText) findViewById(R.id.et_gpio_function001);
		btn_write001 = (Button) findViewById(R.id.btn_write001);
		btn_write001.setOnClickListener(myclick);
		
		et_gpio_function002 = (EditText) findViewById(R.id.et_gpio_function002);
		btn_write002 = (Button) findViewById(R.id.btn_write002);
		btn_write002.setOnClickListener(myclick);
		
		et_gpio_function003 = (EditText) findViewById(R.id.et_gpio_function003);
		btn_write003 = (Button) findViewById(R.id.btn_write003);
		btn_write003.setOnClickListener(myclick);
		
		et_gpio_function004 = (EditText) findViewById(R.id.et_gpio_function004);
		btn_write004 = (Button) findViewById(R.id.btn_write004);
		btn_write004.setOnClickListener(myclick);
		
		btn_read001 = (Button) findViewById(R.id.btn_read001);
		btn_read001.setOnClickListener(myclick);
		btn_read002 = (Button) findViewById(R.id.btn_read002);
		btn_read002.setOnClickListener(myclick);
		btn_read003 = (Button) findViewById(R.id.btn_read003);
		btn_read003.setOnClickListener(myclick);
		btn_read004 = (Button) findViewById(R.id.btn_read004);
		btn_read004.setOnClickListener(myclick);
		
		btn_read_all_gpio = (Button) findViewById(R.id.btn_read_all_gpio);
		btn_read_all_gpio.setOnClickListener(myclick);
		
		btn_show_gpio_level001 = (Button) findViewById(R.id.btn_show_gpio_level001);
		btn_show_gpio_level002 = (Button) findViewById(R.id.btn_show_gpio_level002);
		btn_show_gpio_level003 = (Button) findViewById(R.id.btn_show_gpio_level003);
		btn_show_gpio_level004 = (Button) findViewById(R.id.btn_show_gpio_level004);
		
		tv_read_current_gpio_level = (TextView) findViewById(R.id.tv_read_current_gpio_level);
	}

	OnClickListener myclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
	
			case R.id.btn_write001: 
				String str_wtemp001 = et_gpio_function001.getText().toString();
				if(str_wtemp001.length()<1)
					break;
				writeProc(procpath, str_wtemp001.getBytes());
			break;
				
			case R.id.btn_write002:
				String str_wtemp002 = et_gpio_function002.getText().toString();
				if(str_wtemp002.length()<1)
					break;
				writeProc(procpath, str_wtemp002.getBytes());
			break;
			
			case R.id.btn_write003:
				String str_wtemp003 = et_gpio_function003.getText().toString();
				if(str_wtemp003.length()<1)
					break;
				writeProc(procpath, str_wtemp003.getBytes());
			break;
			
			case R.id.btn_write004:
				String str_wtemp004 = et_gpio_function004.getText().toString();
				if(str_wtemp004.length()<1)
					break;
				writeProc(procpath, str_wtemp004.getBytes());
			break;
			
			
			case R.id.btn_read001:
				String str_rtemp001 = et_gpio_function001.getText().toString();
				if(str_rtemp001.length()<1)
					break;
				String str1 = getProcValue(procpath, str_cut_off(str_rtemp001).getBytes());
				char[] char_str1 = str1.toCharArray();
				String temp1 = "GPIO�ĵ�ƽ: " + (char_str1[26] + "");
				btn_show_gpio_level001.setText(temp1);
				break;
				
			case R.id.btn_read002:
				String str_rtemp002 = et_gpio_function002.getText().toString();
				if(str_rtemp002.length()<1)
					break;
				String str2 = getProcValue(procpath, str_cut_off(str_rtemp002).getBytes());
				char[] char_str2 = str2.toCharArray();
				String temp2 = "GPIO�ĵ�ƽ: " + (char_str2[26] + "");
				btn_show_gpio_level002.setText(temp2);
				break;
				
			case R.id.btn_read003:
				String str_rtemp003 = et_gpio_function003.getText().toString();
				if(str_rtemp003.length()<1)
					break;
				String str3 = getProcValue(procpath, str_cut_off(str_rtemp003).getBytes());
				char[] char_str3 = str3.toCharArray();
				String temp3 = "GPIO�ĵ�ƽ: " + (char_str3[26] + "");
				btn_show_gpio_level003.setText(temp3);
				break;
				
			case R.id.btn_read004:
				String str_rtemp004 = et_gpio_function004.getText().toString();
				if(str_rtemp004.length()<1)
					break;
				String str4 = getProcValue(procpath, str_cut_off(str_rtemp004).getBytes());
				char[] char_str4 = str4.toCharArray();
				String temp4 = "GPIO�ĵ�ƽ: " + (char_str4[26] + "");
				btn_show_gpio_level004.setText(temp4);
				break;
				
			case R.id.btn_read_all_gpio:
				String str_all = getProcValue(procpath, "all".toString().getBytes());
				tv_read_current_gpio_level.setText(str_all);
				break;
			}
		}
	};
	
	private String str_cut_off(String str){
		char[] char_str = str.toString().toCharArray();
		int count_line=0;
		int flag_line=0;
		String tras_str = null;
		
		tras_str = String.valueOf(char_str[0]);
		for(int i=0; i<str.length(); i++){
			String str009 = String.valueOf(char_str[i]);
			if(str009.indexOf("_")!=-1){
				count_line++;
				if(2 == count_line){
					break;
				}
				
			}
			flag_line++;
		}
		
		if(2 == count_line){
			for(int j=1; j<flag_line; j++){
				tras_str = tras_str + String.valueOf(char_str[j]);
			}
			
			return tras_str;
		}else if(1 == count_line){
			
			return str;
		}
		
		return "cut off error";
	}

	private String writeProc(String path, byte[] buffer) {
        try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buffer);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "write error!";
		}
        return (buffer.toString());
    } 
	
//	private String getProcValue(String procPath) {  
//		char[] buffer = new char[32];
//		try {
//			FileReader mReader = new FileReader(new File(procPath)); 
//			mReader.read(buffer, 0, 32);
//			mReader.close();
//		} catch (Exception e) {
//
//			return "read error!!";  	
//		}
//		String str = new String(buffer);
//		return str;
//	}
	
	private String getProcValue(String procPath, byte[] buf_pag) {  
	char[] buffer = new char[32];
	 try {
			File file = new File(procPath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buf_pag);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "write error!";
		}
	
	try {
		FileReader mReader = new FileReader(new File(procPath)); 
		mReader.read(buffer, 0, 32);
		mReader.close();
	} catch (Exception e) {

		return "read error!!";  	
	}
	String str = new String(buffer);
	return str;
  }
}
