package com.kier.companytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kier.companytest.R;
import com.kier.companytest.model.respone.HistoryRespone;

import java.util.ArrayList;

/**
 */

public class HistoryAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HistoryRespone> list;
    public HistoryAdapter(Context context, ArrayList<HistoryRespone> list){
        this.mContext = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_list_item, null);
            holder.history_name = (TextView) convertView.findViewById(R.id.history_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return  convertView;
    }
    class ViewHolder {
        public TextView history_name;
    }


}
