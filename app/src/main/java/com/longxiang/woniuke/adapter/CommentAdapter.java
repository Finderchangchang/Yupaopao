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
import com.longxiang.woniuke.activity.DynamicDetailActivity;
import com.longxiang.woniuke.bean.DynamicDetailData;
import com.longxiang.woniuke.bean.ZanpeopleData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class CommentAdapter extends BaseAdapter{
    private Context context;
    private String nickname;
    private List<DynamicDetailData.DataBean.CommentBean> list;
    private LayoutInflater inflater;
    public CommentAdapter(Context context,String nickname, List<DynamicDetailData.DataBean.CommentBean> list) {
        this.context = context;
        if(list==null){
            list=new ArrayList<>();
        }
        this.list = list;
        this.nickname=nickname;
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
            convertView=inflater.inflate(R.layout.comment_item,null);
            holder.content= (TextView) convertView.findViewById(R.id.comment_item_tv);
            holder.ivpic= (ImageView) convertView.findViewById(R.id.comment_item_iv);
            holder.time= (TextView) convertView.findViewById(R.id.comment_item_time);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(list.get(position).getOtheruid().equals("0")) {
            holder.content.setText(list.get(position).getNickname() + "说:" + list.get(position).getContent());
        }else{
            holder.content.setText(list.get(position).getNickname()+"回复"+nickname+":"+list.get(position).getContent());
        }
        x.image().bind(holder.ivpic,list.get(position).getHead_pic());
        holder.time.setText(list.get(position).getTime());
        return convertView;
    }
    class ViewHolder{
        ImageView ivpic;
        TextView content;
        TextView time;
    }
}
