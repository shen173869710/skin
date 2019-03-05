package com.embed.skin.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.embed.skin.R;


public class QcordDialog extends Dialog {

    private Context context;

    public QcordDialog(Context context, int theme) {
        super(context, theme);
    }

    protected QcordDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public QcordDialog(Context context) {
        super(context);
        this.context = context;
    }
      
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_qcord, null);
        setContentView(view);

//        Window win = getWindow();
//        WindowManager.LayoutParams lp = win.getAttributes();
//        lp.height = dip2px(context,270);
//        lp.width = dip2px(context,200);
//        win.setAttributes(lp);
    }  
      
    @Override
    public void show() {  
        super.show();
    }
      
    @Override
    public void dismiss() {  
        super.dismiss();  
    }


    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

}  
