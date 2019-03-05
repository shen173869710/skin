package com.embed.skin.util;

import android.app.Dialog;
import android.content.Context;

import com.embed.skin.R;
import com.embed.skin.custom.WaitingDialog;


public class ActivityHelper {

	public static Dialog createLoadingDialog(Context context)
	{
		WaitingDialog dialog = new WaitingDialog(context, R.style.dialog);
		return dialog;
	}



}
