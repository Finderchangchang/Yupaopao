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
import com.longxiang.woniuke.bean.SelcetGodData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SelectGodAdapter extends BaseAdapter{
    private Context context;
    private List<SelcetGodData.DataBean> list;
    private LayoutInflater inflater;

    public SelectGodAdapter(Context context, List<SelcetGodData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.select_god_item,null);
            holder.icon= (ImageView) convertView.findViewById(R.id.select_god_item_icon);
            holder.name= (TextView) convertView.findViewById(R.id.select_god_item_name);
            holder.gender= (TextView) convertView.findViewById(R.id.select_god_item_gender);
            holder.level= (TextView) convertView.findViewById(R.id.select_god_item_level);
            holder.money= (TextView) convertView.findViewById(R.id.select_god_item_money);
            holder.jdcount= (TextView) convertView.findViewById(R.id.select_god_item_jdcount);
            holder.pingfen= (TextView) convertView.findViewById(R.id.select_god_item_pingfen);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        SelcetGodData.DataBean data = list.get(position);
        x.image().bind(holder.icon,data.getHead_pic());
        holder.name.setText(data.getNickname());
        if(data.getSex().equals("男")){
            holder.gender.setText("  "+data.getAge()+" ");
            holder.gender.setSelected(true);
            holder.gender.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            holder.gender.setText("  "+data.getAge()+" ");
            holder.gender.setSelected(false);
            holder.gender.setBackgroundResource(R.drawable.conner_women_bg);
        }
        holder.gender.setTextColor(Color.parseColor("#ffffff"));
        holder.level.setText(data.getLevel());
        holder.money.setText(data.getAmount()+"元");
        holder.jdcount.setText("接单"+data.getAlltimes()+"次");
        holder.pingfen.setText(data.getStarlevel()+"");
        return convertView;
    }

    class ViewHolder{
        ImageView icon;
        TextView name;
        TextView gender;
        TextView level;
        TextView money;
        TextView jdcount;
        TextView pingfen;
    }
}
