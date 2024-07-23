package com.embed.skin.ui;

import android.content.Intent;
import android.graphics.Matrix;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.embed.skin.R;
import com.embed.skin.entity.PreviewResult;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.PreviewPresenter;
import com.embed.skin.util.GlideUtils;
import com.embed.skin.util.LogUtils;
import com.embed.skin.view.IPreviewView;

import butterknife.BindView;

/**
 * 预览图片
 */
public class PreviewActivity extends BaseActivity<PreviewPresenter> implements IPreviewView {

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
        LogUtils.e(TAG, "path= "+path);


        GlideUtils.loadFile(this, path, image);

//        Matrix matrix = image.getImageMatrix();
//// 旋转90度
//        matrix.postRotate(90);

// 应用新的Matrix到ImageView
//        image.setImageMatrix(matrix);
    }

    @Override
    protected void setListener() {
        comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
                mPresenter.uploadImgByOkHttp(PreviewActivity.this, path);
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
//        dismissDialog();


//        showToastMsg("上报成功");
        PreviewResult result = (PreviewResult) respone.getResult();
        LogUtils.e(TAG, "result"+result);
        if (result != null) {
            LogUtils.e(TAG, "result.getMeta()"+result.getMeta());
            if (result.getMeta() != null) {

            }else {
                Intent intent = new Intent(PreviewActivity.this, ResultActivity.class);
                intent.putExtra("result",result);
                intent.putExtra("path",path);
                startActivity(intent);
            }
        }else {
//            Toast.makeText(PreviewActivity.this, "皮肤检测失败，请重试",Toast.LENGTH_SHORT).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToastMsg("皮肤检测失败，请重试");
                }
            });
//            showToastMsg(""+"皮肤检测失败，请重试");
        }
    }

    @Override
    public void updateFail(Throwable error, String msg) {
        dismissDialog();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToastMsg(msg);
            }
        });


        LogUtils.e(TAG, "msg ="+msg);

//        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
    }


}
