package com.embed.skin.ui;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.embed.skin.R;

/**
 * Created by Administrator on 2019/1/21.
 */

public class MainViewManager  {

    private View main_item_1;
    private View main_item_2;
    private View main_item_3;
    private View main_item_4;

    public int onItemSelect = 0;
    public OnItemClick onItemClick;


    public MainViewManager(Activity content, OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        main_item_1 = content.findViewById(R.id.main_item_1);
        main_item_2 = content.findViewById(R.id.main_item_2);
        main_item_3 = content.findViewById(R.id.main_item_3);
        main_item_4 = content.findViewById(R.id.main_item_4);
        setOnclicklisten();
    }

    private void setOnclicklisten() {
        main_item_1.findViewById(R.id.main_item_zero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelect = 1;
                if (onItemClick != null) {
                    onItemClick.onItemClick(onItemSelect);
                }
            }
        });

        main_item_2.findViewById(R.id.main_item_zero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelect = 2;
                if (onItemClick != null) {
                    onItemClick.onItemClick(onItemSelect);
                }
            }
        });

        main_item_3.findViewById(R.id.main_item_zero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelect = 3;
                if (onItemClick != null) {
                    onItemClick.onItemClick(onItemSelect);
                }
            }
        });

        main_item_4.findViewById(R.id.main_item_zero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelect = 4;
                if (onItemClick != null) {
                    onItemClick.onItemClick(onItemSelect);
                }
            }
        });
    }


    public void showEnd(String water, String oil, String elastic) {
        View view = null;
        if (onItemSelect == 1) {
            view = main_item_1.findViewById(R.id.main_item_layout);
        }else if (onItemSelect == 2) {
            view =  main_item_2.findViewById(R.id.main_item_layout);
        }else if (onItemSelect == 3) {
            view = main_item_3.findViewById(R.id.main_item_layout);
        }else if (onItemSelect == 4) {
            view = main_item_4.findViewById(R.id.main_item_layout);
        }
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.main_item_water)).setText(water);
            ((TextView)view.findViewById(R.id.main_item_oil)).setText(oil);
            ((TextView)view.findViewById(R.id.main_item_elastic)).setText(elastic);
        }
     }

     public interface OnItemClick {
       public void onItemClick(int pos);
    }

}
