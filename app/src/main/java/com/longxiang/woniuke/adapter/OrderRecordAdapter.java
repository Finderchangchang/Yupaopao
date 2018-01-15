package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.OrderData;
import com.longxiang.woniuke.bean.OrderRecorderData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class OrderRecordAdapter extends BaseAdapter{
    private Context context;
    private List<OrderRecorderData.DataBean> list;
    private LayoutInflater inflater;

    public OrderRecordAdapter(Context context, List<OrderRecorderData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.order_recorder_item,null);
            holder.iv= (ImageView) convertView.findViewById(R.id.order_record_item_pic);
            holder.ivType= (ImageView) convertView.findViewById(R.id.order_record_item_ivtype);
            holder.tvDate= (TextView) convertView.findViewById(R.id.order_record_item_tvdate);
            holder.tvCount= (TextView) convertView.findViewById(R.id.order_record_item_tvcount);
            holder.tvMoney= (TextView) convertView.findViewById(R.id.order_record_item_money);
            holder.ratingBar= (RatingBar) convertView.findViewById(R.id.order_record_item_ratingbar);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        x.image().bind(holder.iv,list.get(position).getHead_pic());
        x.image().bind(holder.ivType,list.get(position).getSkill_pic());
        holder.tvDate.setText(list.get(position).getRealtime());
        holder.tvCount.setText(list.get(position).getTimes()+list.get(position).getUnit());
        holder.tvMoney.setText("已到账:"+(list.get(position).getAmount()-list.get(position).getExpense())+"元");
        holder.tvMoney.setTextColor(Color.parseColor("#000000"));
        holder.tvDate.setTextColor(Color.parseColor("#000000"));
        holder.tvCount.setTextColor(Color.parseColor("#000000"));
        if(list.get(position).getLevel()!=null) {
            Log.i("orderrecodrad", "getView: " + Float.valueOf(list.get(position).getLevel()));
        holder.ratingBar.setRating(Float.valueOf(list.get(position).getLevel()));
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        ImageView ivType;
        TextView tvDate;
        TextView tvCount;
        TextView tvMoney;
        RatingBar ratingBar;
    }
}
