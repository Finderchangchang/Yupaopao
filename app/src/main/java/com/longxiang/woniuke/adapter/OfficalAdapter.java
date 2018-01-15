package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.JobData;
import com.longxiang.woniuke.bean.OfficalData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class OfficalAdapter extends BaseAdapter{
    private Context context;
    private List<OfficalData.DataBean> list;
    private LayoutInflater inflater;
    public OfficalAdapter(Context context, List<OfficalData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.offical_item,null);
            holder.ivActivity= (ImageView) convertView.findViewById(R.id.offical_item_iv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        x.image().bind(holder.ivActivity,list.get(position).getPic());
        return convertView;
    }
    class ViewHolder{
        ImageView ivActivity;
    }
}
