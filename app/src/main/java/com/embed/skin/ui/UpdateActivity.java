package com.embed.skin.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.embed.skin.R;
import com.embed.skin.adapter.DetectAdapter;
import com.embed.skin.custom.QcordDialog;
import com.embed.skin.event.ImageEvent;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.UpdatePresenter;
import com.embed.skin.view.IUpdateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class UpdateActivity extends LBaseActivity <UpdatePresenter>implements IUpdateView, View.OnClickListener{


    private RecyclerView mRecyclerView;
    private DetectAdapter mAdapter;

    private ImageView update_image_1;
    private ImageView update_image_2;
    private Button update_detect;
    private Button update_comit;

    @Override
    protected int setLayout() {
        return R.layout.activity_update;
    }

    protected void init() {
        EventBus.getDefault().register(this);
        update_image_1 = findViewById(R.id.update_image_1);
        update_image_2 = findViewById(R.id.update_image_2);
        mRecyclerView = findViewById(R.id.update_list);
        update_detect = findViewById(R.id.update_detect);
        update_comit = findViewById(R.id.update_comit);
        if (BaseApp.canShow()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            update_detect.setVisibility(View.GONE);
        }else {
            mRecyclerView.setVisibility(View.GONE);
            update_detect.setVisibility(View.VISIBLE);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new DetectAdapter(R.layout.detect_item, BaseApp.infos);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        update_comit.setOnClickListener(this);
        update_detect.setOnClickListener(this);
        update_image_1.setOnClickListener(this);
        update_image_2.setOnClickListener(this);
    }

    @Override
    public void createPresenter() {
        mPresenter = new UpdatePresenter();
    }


    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_detect:
                break;
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
}
