package com.embed.skin.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.embed.skin.R;
import com.embed.skin.custom.QcordDialog;
import com.embed.skin.event.ImageEvent;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.UpdatePresenter;
import com.embed.skin.ui.viewManager.DetectViewManager;
import com.embed.skin.view.IUpdateView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class UpdateActivity extends BaseActivity <UpdatePresenter>implements IUpdateView, View.OnClickListener{

    private ImageView update_image_1;
    private ImageView update_image_2;
    private Button update_comit;
    private DetectViewManager detectViewManager;

    @Override
    protected int setLayout() {
        return R.layout.activity_update;
    }

    protected void init() {
        update_image_1 = (ImageView) findViewById(R.id.update_image_1);
        update_image_2 = (ImageView) findViewById(R.id.update_image_2);
        update_comit = (Button) findViewById(R.id.update_comit);
        detectViewManager = new DetectViewManager(this);
    }

    @Override
    protected void setListener() {
        update_comit.setOnClickListener(this);
        update_image_1.setOnClickListener(this);
        update_image_2.setOnClickListener(this);
    }

    @Override
    public UpdatePresenter createPresenter() {
       return new UpdatePresenter();
    }


    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_comit:
                showUpdateDialog();
                break;
            case R.id.update_image_1:
                Intent intent = new Intent(UpdateActivity.this, CameraActivity.class);
                intent.putExtra("image_type", 1);
                startActivity(intent);
                break;
            case R.id.update_image_2:
                Intent intent2 = new Intent(UpdateActivity.this, CameraActivity.class);
                intent2.putExtra("image_type", 2);
                startActivity(intent2);
                break;
        }
    }

    private void showUpdateDialog() {
        QcordDialog dialog = new QcordDialog(this);
        dialog.show();
    }

    @Override
    public void updateSuccess(BaseRespone respone) {

    }

    @Override
    public void updateFail(Throwable error, String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageEvent event) {
        /* Do something */
        Bitmap bitmap = BitmapFactory.decodeByteArray(event.image, 0, event.image.length);
        if (event.image_type == 1) {
            update_image_1.setImageBitmap(bitmap);
        }else {
            update_image_2.setImageBitmap(bitmap);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        detectViewManager.onResume();
    }

}
