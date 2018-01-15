package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.BlacklistData;
import com.longxiang.woniuke.bean.DsData;
import com.longxiang.woniuke.bean.XmData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class BlacklistAdapter extends BaseAdapter{
    private Context context;
    private List<BlacklistData.DataBean> list;
    private List<DsData> dslist;
    private LayoutInflater inflater;

    public BlacklistAdapter(Context context, List<BlacklistData.DataBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.blacklist_item,null);
            holder.iv= (ImageView) convertView.findViewById(R.id.blacklist_item_icon);
            holder.tv= (TextView) convertView.findViewById(R.id.blacklist_item_tvname);
            holder.tvyichu= (TextView) convertView.findViewById(R.id.blacklist_item_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(list.get(position).getNickname());
        x.image().bind(holder.iv,list.get(position).getHead_pic());
        holder.tvyichu.setTextColor(Color.parseColor("#ffffff"));
        return convertView;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv;
        TextView tvyichu;
    }
}
