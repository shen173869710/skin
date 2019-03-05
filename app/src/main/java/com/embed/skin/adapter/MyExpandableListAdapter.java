package com.embed.skin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.embed.skin.R;
import com.embed.skin.entity.SettingInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/3/4.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<SettingInfo>infos;

    public MyExpandableListAdapter(Context mContext, ArrayList<SettingInfo> infos) {
        this.mContext = mContext;
        this.infos = infos;
    }


    @Override
    public int getGroupCount() {
        return infos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return infos.get(groupPosition).info.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentHolder holder = null;
        if (convertView == null) {
            holder = new ParentHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expandable_group, null, false);
            holder.group_icon = convertView.findViewById(R.id.group_icon);
            holder.group_name = convertView.findViewById(R.id.group_name);
            convertView.setTag(holder);
        }else {
           holder = (ParentHolder) convertView.getTag();
        }

        holder.group_name.setText(infos.get(groupPosition).name);
        if (isExpanded) {
            holder.group_icon.setBackgroundResource(R.mipmap.down);
        }else {
            holder.group_icon.setBackgroundResource(R.mipmap.up);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expandable_child, null, false);
            holder.child_sel = convertView.findViewById(R.id.child_sel);
            holder.child_title = convertView.findViewById(R.id.child_title);
            convertView.setTag(holder);
        }else {
            holder = (ChildHolder) convertView.getTag();
        }

        SettingInfo.MachineInfo info = infos.get(groupPosition).info.get(childPosition);
        holder.child_title.setText(info.title);
        if (info.isSel) {
            holder.child_sel.setBackgroundResource(R.mipmap.check);
        }else {
            holder.child_sel.setBackgroundResource(R.mipmap.uncheck);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class  ParentHolder {
        public ImageView group_icon;
        public TextView group_name;
    }

    public class ChildHolder {
        public ImageView child_sel;
        public TextView child_title;
    }

}
