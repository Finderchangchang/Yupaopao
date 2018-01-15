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
import com.longxiang.woniuke.bean.SkillData;
import com.longxiang.woniuke.bean.YhqData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class YhqAdapter extends BaseAdapter{
    private Context context;
    private List<YhqData.DataBean> list;
    private LayoutInflater inflater;
    public YhqAdapter(Context context, List<YhqData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.item_yhq,null);
            holder.tvname= (TextView) convertView.findViewById(R.id.item_yhq_tvType);
            holder.tvtime= (TextView) convertView.findViewById(R.id.item_yhq_tvTime);
            holder.tvmoney= (TextView) convertView.findViewById(R.id.item_yhq_tvMoney);
            holder.tvmanjian= (TextView) convertView.findViewById(R.id.item_yhq_tvManjian);
            holder.ivpic= (ImageView) convertView.findViewById(R.id.item_yhq_ivType);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(list.get(position).getType().equals("0")){
            holder.tvname.setText(list.get(position).getTypename()+"使用");
            holder.ivpic.setVisibility(View.GONE);
        }else {
            holder.tvname.setText("仅限" + list.get(position).getTypename() + "使用");
            x.image().bind(holder.ivpic,list.get(position).getPic());
        }
        holder.tvtime.setText("有效期:"+list.get(position).getDays()+"天");
        holder.tvmoney.setText(list.get(position).getMoney()+"元");
        holder.tvmoney.setTextColor(Color.parseColor("#FF00FE"));
        holder.tvmanjian.setText("需要积分"+list.get(position).getPrice());
        return convertView;
    }
    class ViewHolder{
        ImageView ivpic;
        TextView tvname;
        TextView tvtime;
        TextView tvmoney;
        TextView tvmanjian;
    }
}
