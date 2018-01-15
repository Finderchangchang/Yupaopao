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
import com.longxiang.woniuke.bean.DsData;
import com.longxiang.woniuke.bean.OrderData;
import com.longxiang.woniuke.bean.XmData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class OrderAdapter extends BaseAdapter{
    private Context context;
    private List<OrderData.DataBean> list;
    private LayoutInflater inflater;

    public OrderAdapter(Context context,  List<OrderData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.item_dingdan,null);
            holder.iv= (ImageView) convertView.findViewById(R.id.item_dingdan_pic);
            holder.tvTime= (TextView) convertView.findViewById(R.id.item_dingdan_time);
            holder.tvTitle= (TextView) convertView.findViewById(R.id.item_dingdan_title);
            holder.tvType= (TextView) convertView.findViewById(R.id.item_dingdan_tvtype);
            holder.ivType= (ImageView) convertView.findViewById(R.id.item_dingdan_ivtype);
            holder.tvDate= (TextView) convertView.findViewById(R.id.item_dingdan_tvdate);
            holder.tvCount= (TextView) convertView.findViewById(R.id.item_dingdan_tvcount);
            holder.tvState= (TextView) convertView.findViewById(R.id.item_dingdan_tvstate);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvTime.setText(list.get(position).getAdd_time());
        holder.tvTitle.setText("订单:"+list.get(position).getContent());
        x.image().bind(holder.iv,list.get(position).getGod().getHead_pic());
        x.image().bind(holder.ivType,list.get(position).getOrder().getTypeimg());
        holder.tvType.setText(list.get(position).getOrder().getTypename());
        holder.tvDate.setText(list.get(position).getOrder().getRealtime());
        holder.tvCount.setText(list.get(position).getOrder().getTimes()+list.get(position).getOrder().getUnit());
        String state = list.get(position).getOrder().getState();
        //3 待确定 4 已完成 5 已取消 1 已接单
        if(!state.equals("")||state!=null) {
            if (state.equals("1")) {
                holder.tvState.setText("待服务");
                holder.tvState.setTextColor(Color.parseColor("#50CB00"));
            }
            if (state.equals("3")) {
                if(list.get(position).getOrder().getUid().equals(MyApp.uid)){
                        holder.tvState.setText("待服务");
                }else {
                    holder.tvState.setText("待确定");
                }
                holder.tvState.setTextColor(Color.parseColor("#50CB00"));
            }
            if (state.equals("4")) {
                holder.tvState.setText("已完成");
            }
            if (state.equals("5")) {
                holder.tvState.setText("已取消");
            }
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tvTime;
        TextView tvTitle;
        ImageView ivType;
        TextView tvType;
        TextView tvDate;
        TextView tvCount;
        TextView tvState;
    }
}
