package com.embed.skin.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.embed.skin.BuildConfig;
import com.embed.skin.R;
import com.embed.skin.util.NoFastClickUtils;
import com.embed.skin.util.ShareUtil;


public class ChooseTimeDialog extends Dialog {

	public Button sure_load_cancle;
	public Button sure_load_sure;
	public TextView sure_load_title;
	public EditText choose_skin_name;
	public EditText choose_skin_mac;
	public EditText choose_light_name;
	public EditText choose_light_mac;

	public View mView;
	public ChooseTimeDialog(Context context) {
		super(context, R.style.UpdateDialog);
		setCustomView();
	}

	public ChooseTimeDialog(Context context, boolean cancelable,
                            OnCancelListener cancelListener) {
		super(context, R.style.UpdateDialog);
		this.setCancelable(cancelable);
		this.setOnCancelListener(cancelListener);
		setCustomView();
	}

	public ChooseTimeDialog(Context context, int theme) {
		super(context, R.style.UpdateDialog);
		setCustomView();
	}

	private void setCustomView() {
		mView = LayoutInflater.from(getContext()).inflate(R.layout.choose_time_dialog, null);
		sure_load_title =  (TextView) mView.findViewById(R.id.sure_load_title);
		choose_skin_name = (EditText) mView.findViewById(R.id.choose_skin_name);
		choose_skin_mac = (EditText) mView.findViewById(R.id.choose_skin_mac);
		choose_light_name = (EditText) mView.findViewById(R.id.choose_light_name);
		choose_light_mac = (EditText) mView.findViewById(R.id.choose_light_mac);

		sure_load_sure = (Button) mView.findViewById(R.id.sure_load_sure);
		sure_load_cancle = (Button) mView.findViewById(R.id.sure_load_cancle);


		choose_skin_name.setText(BuildConfig.skin_name);
		choose_skin_mac.setText(BuildConfig.skin_mac);
		choose_light_name.setText(BuildConfig.light_name);
		choose_light_mac.setText(BuildConfig.light_mac);

		if (!TextUtils.isEmpty(ShareUtil.getSkinName(getContext()))) {
			choose_skin_name.setText(ShareUtil.getSkinName(getContext()));
		}

		if (!TextUtils.isEmpty(ShareUtil.getSkinMac(getContext()))) {
			choose_skin_mac.setText(ShareUtil.getSkinMac(getContext()));
		}

		if (!TextUtils.isEmpty(ShareUtil.getLightName(getContext()))) {
			choose_light_name.setText(ShareUtil.getLightName(getContext()));
		}

		if (!TextUtils.isEmpty(ShareUtil.getLightMac(getContext()))) {
			choose_light_mac.setText(ShareUtil.getLightMac(getContext()));
		}
		super.setContentView(mView);
	}

	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		super.setCanceledOnTouchOutside(cancel);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}

	public void setOnPositiveListener(View.OnClickListener listener) {
		sure_load_sure.setOnClickListener(listener);
	}

	public void setOnNegativeListener(View.OnClickListener listener) {
		sure_load_cancle.setOnClickListener(listener);
	}

	public static void ShowDialog(final Activity context, final View.OnClickListener listener) {
		final ChooseTimeDialog dialog = new ChooseTimeDialog(context);
		dialog.setOnPositiveListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(NoFastClickUtils.isFastClick()){
					return;
				}
				if (listener != null) {
					String start = dialog.choose_skin_name.getText().toString().trim();
					String end = dialog.choose_skin_mac.getText().toString().trim();

					String name = dialog.choose_light_name.getText().toString().trim();
					String mac = dialog.choose_light_mac.getText().toString().trim();

					if(TextUtils.isEmpty(start) || TextUtils.isEmpty(end) || TextUtils.isEmpty(name) || TextUtils.isEmpty(mac)) {
						Toast.makeText(context, "请输入名称和mac地址", Toast.LENGTH_LONG).show();
						return;
					}
					ShareUtil.setSkinName(context,start);
					ShareUtil.setSkinMac(context,end);
					ShareUtil.setLightName(context,name);
					ShareUtil.setLightMac(context,mac);
				}
				dialog.dismiss();
			}
		});

		dialog.setOnNegativeListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		LayoutParams lay = dialog.getWindow().getAttributes();
		DisplayMetrics dm = new DisplayMetrics();// 获取屏幕分辨率
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);//
		Rect rect = new Rect();
		View view = context.getWindow().getDecorView();
		view.getWindowVisibleDisplayFrame(rect);
		lay.width = dm.widthPixels * 9 / 10;
		dialog.show();
	}


}
