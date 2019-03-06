package com.embed.skin.ui.viewManager;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.embed.skin.R;
import com.embed.skin.entity.DetectInfo;
import com.embed.skin.ui.BaseApp;
import com.embed.skin.ui.MainActivityBg;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/6.
 */

public class DetectViewManager {
    public Activity activity;
    public RelativeLayout update_detect_layout;
    private LinearLayout update_detect_item_layout;
    private View update_detect_item_0;
    private View update_detect_item_1;
    private View update_detect_item_2;
    private View update_detect_item_3;

    private ArrayList<View>views = new ArrayList<>();

    private Button update_detect;

    public DetectViewManager(Activity activity) {
        this.activity = activity;
        update_detect_layout = activity.findViewById(R.id.update_detect_layout);
        update_detect_item_layout = activity.findViewById(R.id.update_detect_item_layout);
        update_detect_item_0 = activity.findViewById(R.id.update_detect_item_0);
        update_detect_item_1 = activity.findViewById(R.id.update_detect_item_1);
        update_detect_item_2 = activity.findViewById(R.id.update_detect_item_2);
        update_detect_item_3 = activity.findViewById(R.id.update_detect_item_3);
        update_detect = activity.findViewById(R.id.update_detect);
        views.add(update_detect_item_0);
        views.add(update_detect_item_1);
        views.add(update_detect_item_2);
        views.add(update_detect_item_3);
        initView();
    }


    public void initView() {
        if (BaseApp.canShow()) {
            update_detect_item_layout.setVisibility(View.VISIBLE);
            update_detect.setVisibility(View.GONE);
            int size = BaseApp.infos.size();
            for (int i = 0; i < size; i++) {
                bindView(views.get(i), BaseApp.infos.get(i));
            }
        }else {
            update_detect_item_layout.setVisibility(View.GONE);
            update_detect.setVisibility(View.VISIBLE);
            update_detect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, MainActivityBg.class));
                }
            });
        }
    }


    public void bindView(View view, DetectInfo info) {
        TextView detect_value_1 = view.findViewById(R.id.detect_value_1);
        detect_value_1.setText(info.waterValue);
        TextView detect_value_2 = view.findViewById(R.id.detect_value_2);
        detect_value_2.setText(info.oilValue);
        TextView detect_value_3 = view.findViewById(R.id.detect_value_3);
        detect_value_3.setText(info.elasticValue);
    }


    public void onResume() {
        initView();
    }


}
