package com.github.hualuomoli.password.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.hualuomoli.password.R;
import com.github.hualuomoli.password.entity.PasswordKeyboardData;

import java.util.List;

// 密码键盘适配器
public class PasswordKeyboardAdapter extends BaseAdapter {

    private final Context context;
    private final List<PasswordKeyboardData> datas;

    public PasswordKeyboardAdapter(Context context, List<PasswordKeyboardData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.password_keybord_item, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.item = (TextView) convertView.findViewById(R.id.password_keyboard_item_key);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.password_keyboard_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 如果设置了显示内容
        PasswordKeyboardData data = datas.get(position);

        switch (data.getType()) {
            case DATA:
                viewHolder.item.setText(data.getText());
                viewHolder.item.setBackgroundResource(R.drawable.password_keyboard_item_number_background_selector);
                break;
            case DELETE:
                viewHolder.item.setBackgroundResource(R.drawable.password_keyboard_item_delete_background_selector);
                viewHolder.image.setImageResource(R.mipmap.password_delete_image);
                viewHolder.image.setVisibility(View.VISIBLE);
                break;
            case BLANK:
                viewHolder.item.setBackgroundResource(R.drawable.password_keyboard_item_blank_background_selector);
                break;
        }

        return convertView;
    }

    private class ViewHolder {
        // 文本
        private TextView item;
        // 图
        private ImageView image;

    }

}
