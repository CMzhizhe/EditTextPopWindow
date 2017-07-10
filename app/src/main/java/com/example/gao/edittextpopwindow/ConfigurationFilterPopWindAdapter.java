package com.example.gao.edittextpopwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间: 2017/7/7
 * Gaoxx
 * 注释描述:过滤文字
 */

public class ConfigurationFilterPopWindAdapter extends BaseAdapter {
    private List<String> mArrayList;
    private List<String> mFilteredArrayList;
    private LayoutInflater mLayoutInflater;

    public ConfigurationFilterPopWindAdapter(Context context, List<String> arrayList) {
        this.mArrayList = arrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mFilteredArrayList = new ArrayList<String>();
    }


    @Override
    public int getCount() {
        if (mArrayList == null || mArrayList.size() <= 0) {
            return 0;
        } else {
            return mArrayList.size();
        }

    }

    @Override
    public String getItem(int position) {
        return mArrayList.get(position);

    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_configuration_filter_pop_wind, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_item_configuration_filter_pop_wind);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (mArrayList != null) {
            if (viewHolder.textView != null) {
                viewHolder.textView.setText((mArrayList.get(position)));
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }




}
