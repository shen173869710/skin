package com.embed.skin.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.embed.skin.R;
import com.embed.skin.entity.ResultFeature;
import com.embed.skin.ui.BaseApp;

import java.util.List;

import io.reactivex.annotations.Nullable;


public class ResultAdapter extends BaseQuickAdapter<ResultFeature, BaseViewHolder> {

    public ResultAdapter(@Nullable List<ResultFeature> data) {
        super(R.layout.result_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, ResultFeature item) {
        holder.setText(R.id.item_name, ""+item.getName());
        holder.setText(R.id.item_description, ""+item.getDescription());
        holder.setText(R.id.item_evaluate, ""+item.getEvaluate());
    }

}
