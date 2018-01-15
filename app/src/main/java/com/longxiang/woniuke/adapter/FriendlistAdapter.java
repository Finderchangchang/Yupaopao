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
import com.longxiang.woniuke.utils.Friend;

import org.xutils.x;

import java.util.List;

/**
 * Created by jackiechan on 16/1/15.
 */
public class FriendlistAdapter extends BaseAdapter {
    private List<Friend> friends;//数据源
    private Context context;

    public FriendlistAdapter(List<Friend> friends, Context context) {
        this.friends = friends;
        this.context = context;
    }

    @Override
    public int getCount() {
        return friends == null ? 0 : friends.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friend friend = friends.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_add_friend, null);
        }
        viewHolder = getHolder(convertView);
        //设置内容  .Resources$NotFoundException: String resource ID #0x41
        if (position == 0) {//如果是第一个,不应该判断
            viewHolder.index.setVisibility(View.VISIBLE);
            char currentFirst = friend.getPinYin().charAt(0);
            Log.i("friendlistadapter", "getView: "+currentFirst);
            if (currentFirst>= 'a' && currentFirst <= 'z') {
                currentFirst -= 32;
            }
            if(('1'>=currentFirst&&currentFirst<='9')||currentFirst==92){
                currentFirst=35;
            }
            if((currentFirst< 'a' && currentFirst > 'z')&&('1'<currentFirst&&currentFirst>'9')&&(currentFirst<65&&currentFirst>90)){
                currentFirst=35;
            }
            viewHolder.index.setText(currentFirst+"");
        } else{
            char currentFirst = friend.getPinYin().charAt(0);//获取当前条目的首字母
            char lastFist = friends.get(position - 1).getPinYin().charAt(0);
            //如果当前的首字母和上一个一致,则不再显示索引,否则显示索引
            if (currentFirst == lastFist) {
                viewHolder.index.setVisibility(View.GONE);
            }else{
                if (currentFirst>= 'a' && currentFirst <= 'z') {
                    currentFirst -= 32;
                }
                if(('1'>=currentFirst&&currentFirst<='9')||currentFirst==92){
                    currentFirst=35;
                }
                if((currentFirst< 'a' && currentFirst > 'z')&&('1'<currentFirst&&currentFirst>'9')&&(currentFirst<65&&currentFirst>90)){
                    currentFirst=35;
                }
                viewHolder.index.setVisibility(View.VISIBLE);
                viewHolder.index.setText(currentFirst+"");
            }
        }


        viewHolder.name.setText(friend.getName());
        x.image().bind(viewHolder.icon,friend.getHead_pic());
        viewHolder.distance.setText(String.valueOf(friend.getDistance())+"km");
        viewHolder.time.setText(friend.getTime());
        if(friend.getSex().equals("男")){
            viewHolder.gender.setSelected(true);
            viewHolder.gender.setText(" "+friend.getAge());
            viewHolder.gender.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            viewHolder.gender.setSelected(false);
            viewHolder.gender.setText(" "+friend.getAge());
            viewHolder.gender.setBackgroundResource(R.drawable.conner_women_bg);
        }
        viewHolder.gender.setTextColor(Color.parseColor("#ffffff"));
        viewHolder.sign.setText(friend.getSign());
        return convertView;
    }

    /**
     * 获取 holder 的方法
     *
     * @param convertView
     * @return
     */
    private static ViewHolder getHolder(View convertView) {
        ViewHolder viewHolder = null;
        viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(convertView);
        }
        return viewHolder;
    }

    static class ViewHolder {
        TextView index, name,distance,time,gender,sign;
        ImageView icon;
        public ViewHolder(View convertView) {
            index = (TextView) convertView.findViewById(R.id.pinyin_item_index);
            name = (TextView) convertView.findViewById(R.id.pinyin_item_tvname);
            distance= (TextView) convertView.findViewById(R.id.pinyin_item_distance);
            time= (TextView) convertView.findViewById(R.id.pinyin_item_tvtime);
            gender= (TextView) convertView.findViewById(R.id.pinyin_item_tvgender);
            sign= (TextView) convertView.findViewById(R.id.pinyin_item_tvgexing);
            icon= (ImageView) convertView.findViewById(R.id.pinyin_item_iv);
            convertView.setTag(this);
        }
    }
}
