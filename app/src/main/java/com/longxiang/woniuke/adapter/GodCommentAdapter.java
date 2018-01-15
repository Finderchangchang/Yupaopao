package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.DynamicDetailData;
import com.longxiang.woniuke.bean.GodcommentData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class GodCommentAdapter extends BaseAdapter{
    private Context context;
    private List<GodcommentData.DataBean> list;
    private LayoutInflater inflater;
    public GodCommentAdapter(Context context, List<GodcommentData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.pingjia_listview_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.pingjia_listview_item_name);
            holder.content= (TextView) convertView.findViewById(R.id.pingjia_listview_item_content);
            holder.ivpic= (ImageView) convertView.findViewById(R.id.pingjia_listview_item_icon);
            holder.time= (TextView) convertView.findViewById(R.id.pingjia_listview_item_time);
            holder.ratingBar= (RatingBar) convertView.findViewById(R.id.pingjia_listview_item_ratingbar);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        GodcommentData.DataBean data = list.get(position);
        if(data.getNoname().equals("1")){
            holder.ivpic.setImageResource(R.mipmap.nimingtouxiang);
        }else {
            x.image().bind(holder.ivpic, list.get(position).getHead_pic());
        }
        holder.time.setText(list.get(position).getRealtime());
        holder.name.setText(data.getNickname());
        if(data.getLevel()!=null){
            holder.ratingBar.setRating(Float.valueOf(data.getLevel()));
        }
        holder.content.setText(data.getComment());
        return convertView;
    }
    class ViewHolder{
        ImageView ivpic;
        TextView content;
        TextView time;
        RatingBar ratingBar;
        TextView name;
    }
}
