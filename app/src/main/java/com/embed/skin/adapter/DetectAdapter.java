package com.embed.skin.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.embed.skin.R;
import com.embed.skin.entity.DetectInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */

public class DetectAdapter extends BaseQuickAdapter<DetectInfo,BaseViewHolder> {
    public DetectAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetectInfo item) {
        if (item.isSel) {
            helper.getView(R.id.detect_layout).setVisibility(View.VISIBLE);
            helper.setText(R.id.detect_value_1, item.waterValue);
            helper.setText(R.id.detect_value_2, item.oilValue);
            helper.setText(R.id.detect_value_3, item.elasticValue);
        }else {
            helper.getView(R.id.detect_layout).setVisibility(View.GONE);
        }


    }
}
