package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.XmData;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class HomeGridviewAdapter extends BaseAdapter{
    private Context context;
    private List<XmData.DataBean> list;
    private LayoutInflater inflater;

    public HomeGridviewAdapter(Context context, List<XmData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.home_gridview_item,null);
            holder.iv= (ImageView) convertView.findViewById(R.id.home_gridview_item_iv);
            holder.tv= (TextView) convertView.findViewById(R.id.home_gridview_item_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        Log.e("12345", "getView: "+list.get(position).getTitle() );
        Log.e("12345", "getView: ---"+position+"time===="+list.get(position).getPrice().size() );
        holder.tv.setText(list.get(position).getTitle());
        x.image().bind(holder.iv,list.get(position).getIcon());
        if(position==9){
            holder.tv.setText("全部");
            holder.iv.setImageResource(R.mipmap.alltype);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}