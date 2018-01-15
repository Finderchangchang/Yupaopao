package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.AddFriendData;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/7/23.
 */
public class FriendAdapter extends BaseAdapter {
private Context context;
    private List<AddFriendData.DataBean> list;
    private LayoutInflater inflater;
    public FriendAdapter(Context context, List<AddFriendData.DataBean> list) {
        this.context = context;
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
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_list_add_friend,null);
            holder.index = (TextView) convertView.findViewById(R.id.pinyin_item_index);
            holder.name = (TextView) convertView.findViewById(R.id.pinyin_item_tvname);
            holder.distance= (TextView) convertView.findViewById(R.id.pinyin_item_distance);
            holder.time= (TextView) convertView.findViewById(R.id.pinyin_item_tvtime);
            holder.gender= (TextView) convertView.findViewById(R.id.pinyin_item_tvgender);
            holder.sign= (TextView) convertView.findViewById(R.id.pinyin_item_tvgexing);
            holder.icon= (ImageView) convertView.findViewById(R.id.pinyin_item_iv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        AddFriendData.DataBean friend=list.get(position);
        holder.index.setVisibility(View.GONE);
        x.image().bind(holder.icon,friend.getHead_pic());
        holder.distance.setText(String.valueOf(friend.getDistance())+"km");
        holder.time.setText(friend.getTime());
        holder.name.setText(friend.getNickname());
        if(friend.getSex().equals("男")){
            holder.gender.setText(" "+friend.getAge());
            holder.gender.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            holder.gender.setText(" "+friend.getAge());
            holder.gender.setBackgroundResource(R.drawable.conner_women_bg);
        }
        holder.sign.setText(friend.getSign());
        return convertView;
    }
    class ViewHolder{
        TextView index,name,distance,time,gender,sign;
        ImageView icon;
        
    }
}
