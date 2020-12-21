package com.embed.skin.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.embed.skin.R;
import com.embed.skin.entity.PreviewResult;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.PreviewPresenter;
import com.embed.skin.util.GlideUtils;
import com.embed.skin.view.IPreviewView;

import butterknife.BindView;

/**
 * 预览图片
 */
public class PreviewActivity extends IBaseActivity<PreviewPresenter> implements IPreviewView {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.comit)
    Button comit;

    private String path = "";

    @Override
    protected int setLayout() {
        return R.layout.activity_preview;
    }

    @Override
    protected void init() {
        path = getIntent().getStringExtra("path");
        GlideUtils.loadResource(this, R.mipmap.test3, image);
    }

    @Override
    protected void setListener() {
        comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                mPresenter.uploadImgByOkHttp(PreviewActivity.this);
            }
        });
    }

    @Override
    protected PreviewPresenter createPresenter() {
        return new PreviewPresenter();
    }

    public void back(View view) {
        finish();
    }


    @Override
    public void updateSuccess(BaseRespone respone) {
        dismissDialog();
        showToastMsg("上报成功");
        PreviewResult result = (PreviewResult) respone.getResult();
        if (result != null) {
            Intent intent = new Intent(PreviewActivity.this, ResultActivity.class);
            intent.putExtra("result",result);
            intent.putExtra("path",path);
            startActivity(intent);
        }
    }

    @Override
    public void updateFail(Throwable error, String msg) {
        dismissDialog();
        showToastMsg(msg);
    }


}
