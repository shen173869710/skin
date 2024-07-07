package com.embed.skin.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtil {
	private static Toast mToast = null;
	private static Handler mHandler= new Handler();
	private static Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			 mToast.cancel();
		}
	};

	public static void showToast(Context context, String text, int duration) {
		mHandler.removeCallbacks(runnable);
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
		}
		mHandler.postDelayed(runnable, 3000);
		mToast.show();
	}
	
	public static void showToast(Context context, int resId, int duration) {
		showToast(context, context.getResources().getString(resId), duration);
	}
}
