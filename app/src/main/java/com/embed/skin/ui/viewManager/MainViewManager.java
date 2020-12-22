package com.embed.skin.ui.viewManager;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.embed.skin.R;
import com.embed.skin.ui.BaseApp;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/1/21.
 */

public class MainViewManager  implements View.OnClickListener{

    private Activity activity;
//    private View main_item_0;
    private View main_item_1;
    private View main_item_2;
    private View main_item_3;

    public int onItemSelect = -1;

    private ArrayList<View>views = new ArrayList<>();

    public MainViewManager(Activity ac) {
        activity = ac;
//        main_item_0 = ac.findViewById(R.id.main_item_0);
        main_item_1 = ac.findViewById(R.id.main_item_1);
        main_item_2 = ac.findViewById(R.id.main_item_2);
        main_item_3 = ac.findViewById(R.id.main_item_3);
//        views.add(main_item_0);
        views.add(main_item_1);
        views.add(main_item_2);
        views.add(main_item_3);
        setOnclicklisten();
    }

    private void setOnclicklisten() {
//        main_item_0.setOnClickListener(this);
        main_item_1.setOnClickListener(this);
        main_item_2.setOnClickListener(this);
        main_item_3.setOnClickListener(this);
    }


    public void showEnd(String water, String oil, String elastic) {
        endAnim();
        if (onItemSelect >= 0) {
            View view = views.get(onItemSelect);
            if (view != null) {
                view.findViewById(R.id.main_item_layout).setVisibility(View.VISIBLE);
                String name = "";
                if (onItemSelect == 0) {
                    name = "额头";
                }else if (onItemSelect == 1) {
                    name = "左脸";
                }else if (onItemSelect == 2) {
                    name = "右脸";
                }
                ((TextView)view.findViewById(R.id.main_item_name)).setText(name);
                ((TextView)view.findViewById(R.id.main_item_water)).setText(water);
                ((TextView)view.findViewById(R.id.main_item_oil)).setText(oil);
                ((TextView)view.findViewById(R.id.main_item_elastic)).setText(elastic);
                BaseApp.infos.get(onItemSelect).waterValue = water;
                BaseApp.infos.get(onItemSelect).elasticValue = elastic;
                BaseApp.infos.get(onItemSelect).oilValue = oil;
                BaseApp.infos.get(onItemSelect).isSel = true;
            }
        }
     }

    @Override
    public void onClick(View v) {
        View view = null;
        switch (v.getId()) {
//            case R.id.main_item_0:
//                view = main_item_0;
//                onItemSelect = 0;
//                break;
            case R.id.main_item_1:
                view = main_item_1;
                onItemSelect = 0;
                break;
            case R.id.main_item_2:
                view = main_item_2;
                onItemSelect = 1;
                break;
            case R.id.main_item_3:
                view = main_item_3;
                onItemSelect = 2;
                break;
        }
        cleanSel();
        setSel(view);
    }

    public void cleanSel() {
        for (int i = 0; i < 3; i++) {
            if (i != onItemSelect) {
                views.get(i).findViewById(R.id.main_item_sel).setSelected(false);
                views.get(i).findViewById(R.id.main_item_sel).setBackgroundResource(R.mipmap.img_normal);
            }
        }
    }

    public void setSel(View view) {
        ImageView main_item_sel = view.findViewById(R.id.main_item_sel);
        main_item_sel.setSelected(!main_item_sel.isSelected());
        if (main_item_sel.isSelected()) {
            main_item_sel.setBackgroundResource(R.mipmap.img_checked);
        }else {
            main_item_sel.setBackgroundResource(R.mipmap.img_normal);
            onItemSelect = -1;
        }
    }

    public void startAnim() {
        if (onItemSelect >= 0) {
            ImageView  imageView  = views.get(onItemSelect).findViewById(R.id.main_item_gif);
            Glide.with(activity)
                    .asGif()
                    .load(R.drawable.detect_scanf)
                    .into(imageView);
            imageView.setVisibility(View.VISIBLE);
        }

    }

    public void endAnim() {
        if (onItemSelect >= 0) {
            ImageView  imageView  = views.get(onItemSelect).findViewById(R.id.main_item_gif);
            imageView.setVisibility(View.GONE);
        }
    }

}
