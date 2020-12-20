package com.embed.skin.ui;

import android.view.View;
import android.widget.ImageView;
import com.embed.skin.R;
import com.embed.skin.entity.PreviewRespone;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.PreviewPresenter;
import com.embed.skin.util.LogUtils;
import com.embed.skin.view.IPreviewView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 *  预览图片
 */
public class PreviewActivity extends IBaseActivity <PreviewPresenter> implements IPreviewView {

    ImageView imageView;
    @Override
    protected int setLayout() {
        return R.layout.activity_preview;
    }

    @Override
    protected void init() {
        imageView = findViewById(R.id.image);

    }

    @Override
    protected void setListener() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.uploadImgByOkHttp(PreviewActivity.this);
                String str = "{\"code\":true,\"msg\":\"分析成功\",\"result\":{\"score\":74.4,\"metrics\":{\"rank\":19,\"faceStatus\":\"状态良好\",\"age\":32,\"skinType\":3,\"skinColor\":3},\"features\":[{\"name\":\"黑头\",\"description\":\"黑头数量6\",\"evaluate\":2},{\"name\":\"毛孔\",\"description\":\"无毛孔粗大\",\"evaluate\":1},{\"name\":\"痤疮\",\"description\":\"无痤疮\",\"evaluate\":1},{\"name\":\"痘印\",\"description\":\"痘印数量4\",\"evaluate\":3},{\"name\":\"皱纹\",\"description\":\"有抬头纹\\n有鱼尾纹\\n\",\"evaluate\":4},{\"name\":\"法令纹\",\"description\":\"有法令纹\",\"evaluate\":4},{\"name\":\"肤色\",\"description\":\"肤色自然\",\"evaluate\":2},{\"name\":\"痘痘\",\"description\":\"痘痘数量0\",\"evaluate\":1},{\"name\":\"色斑\",\"description\":\"色斑数量5\",\"evaluate\":3},{\"name\":\"黑眼圈\",\"description\":\"有黑眼圈\",\"evaluate\":4}]}}";
                PreviewRespone previewRespone = new Gson().fromJson(str,PreviewRespone.class );
                if (previewRespone != null) {
                    LogUtils.e("PreviewPresenter", ""+previewRespone);
                }
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
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void updateSuccess(BaseRespone respone) {

    }

    @Override
    public void updateFail(Throwable error, String msg) {

    }
}
