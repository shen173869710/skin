package com.embed.skin.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.embed.skin.R;


public class WaitingDialog extends Dialog {
	  
    public WaitingDialog(Context context, int theme) {
        super(context, theme);  
    }  
  
    protected WaitingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);  
    }  
  
    public WaitingDialog(Context context) {
        super(context);  
    }
      
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        setContentView(view);

    }  
      
    @Override
    public void show() {  
        super.show();
    }
      
    @Override
    public void dismiss() {  
        super.dismiss();  
    }

}  
