package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.JobData;
import com.longxiang.woniuke.bean.SqlvData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class SqlvAdapter extends BaseAdapter{
    private Context context;
    private List<SqlvData.DataBean> list;
    private LayoutInflater inflater;
    public SqlvAdapter(Context context, List<SqlvData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.item_job,null);
            holder.tvJob= (TextView) convertView.findViewById(R.id.item_job_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvJob.setText(list.get(position).getTitle());
        return convertView;
    }
    class ViewHolder{
        TextView tvJob;
    }
}
