package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.SystemMsgData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class SystemMsgAdapter extends BaseAdapter {
    private Context context;
    private List<SystemMsgData.DataBean> list;
    private LayoutInflater inflater;

    public SystemMsgAdapter(Context context, List<SystemMsgData.DataBean> list) {
        this.context = context;
        if(list==null){
            list=new ArrayList<>();
        }
        this.list = list;
        inflater=LayoutInflater.from(context);
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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.system_msg_item,null);
            holder.content= (TextView) convertView.findViewById(R.id.system_msg_item_content);
            holder.time= (TextView) convertView.findViewById(R.id.system_msg_item_time);
            holder.tv= (TextView) convertView.findViewById(R.id.system_msg_item_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv.setTextColor(Color.parseColor("#000000"));
        holder.content.setText(list.get(position).getContent());
        holder.time.setText(list.get(position).getAdd_time());
        holder.time.setTextColor(Color.parseColor("#000000"));
        return convertView;
    }
    class ViewHolder{
        TextView content;
        TextView time;
        TextView tv;
    }
}
