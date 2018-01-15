package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.DashangData;
import com.longxiang.woniuke.bean.ZanpeopleData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class DashangpeopleAdapter extends BaseAdapter{
    private Context context;
    private List<DashangData.DataBean> list;
    private LayoutInflater inflater;
    public DashangpeopleAdapter(Context context, List<DashangData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.dashang_pepole_item,null);
            holder.tvname= (TextView) convertView.findViewById(R.id.dashang_people_item_name);
            holder.ivpic= (ImageView) convertView.findViewById(R.id.dashang_people_item_icon);
            holder.tvsex= (TextView) convertView.findViewById(R.id.dashang_people_item_sex);
            holder.tvmoney= (TextView) convertView.findViewById(R.id.dashang_people_item_money);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(list.get(position).getNickname()!=null) {
            holder.tvname.setText(list.get(position).getNickname());
        }
        x.image().bind(holder.ivpic,list.get(position).getHead_pic());
        if(list.get(position).getSex()!=null&&list.get(position).getAge()!=null) {
            if (list.get(position).getSex().equals("男")) {
                holder.tvsex.setSelected(true);
                holder.tvsex.setText(" " + list.get(position).getAge());
                holder.tvsex.setBackgroundResource(R.drawable.conner_man_bg);
            } else {
                holder.tvsex.setSelected(false);
                holder.tvsex.setText(" " + list.get(position).getAge());
                holder.tvsex.setBackgroundResource(R.drawable.conner_women_bg);
            }
        }
        holder.tvsex.setTextColor(Color.parseColor("#ffffff"));
        holder.tvmoney.setText("打赏"+list.get(position).getAmount()+"元");
        return convertView;
    }
    class ViewHolder{
        ImageView ivpic;
        TextView tvname;
        TextView tvsex;
        TextView tvmoney;
    }
}
