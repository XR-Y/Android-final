package com.example.finalproject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListBaseAdapter extends BaseAdapter {

    private static final int NUM_LIST_ITEMS = 2;
    private String[] args = {"亲爱的用户您好，\n小抖已经在这里等您好久了，\n请放心使用该APP，\n再次祝您使用愉快！",
            "以下帮助可以协助您快速熟悉本应用,\n1........\n2.......\n3........"};
    private String[] title = {"恭喜您注册成功", "使用说明"};

    @Override
    public int getCount() {
        return NUM_LIST_ITEMS;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.component_store_list, null);
            holder.listItemImageView = (ImageView) convertView.findViewById(R.id.tv_item_img);
            holder.viewHolderIndex = (TextView) convertView.findViewById(R.id.tv_view_holder_instance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.listItemImageView.setBackgroundResource(R.drawable.ic_user0);
        holder.viewHolderIndex.setText(String.valueOf(title[position]));
        return convertView;
    }

    private static class ViewHolder {
        private TextView viewHolderIndex;
        private ImageView listItemImageView;
    }
}
