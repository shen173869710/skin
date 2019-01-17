package com.kier.companytest.util;

import android.app.Dialog;
import android.content.Context;

import com.kier.companytest.R;
import com.kier.companytest.custom.WaitingDialog;


public class ActivityHelper {

	public static Dialog createLoadingDialog(Context context)
	{
		WaitingDialog dialog = new WaitingDialog(context, R.style.dialog);
		return dialog;
	}



}
