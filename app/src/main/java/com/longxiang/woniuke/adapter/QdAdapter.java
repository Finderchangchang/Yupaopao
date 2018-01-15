package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.FriendDataActivity;
import com.longxiang.woniuke.bean.OrderData;
import com.longxiang.woniuke.bean.QdData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class QdAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<QdData.DataBean> list;
    private LayoutInflater inflater;

    public QdAdapter(Context context, List<QdData.DataBean> list) {
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
//        View view=inflater.inflate(R.layout.progressbar_qd,null);
//        TextView qdtv= (TextView) view.findViewById(R.id.progressbar_qd_tv);
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_dingdan,null);
            holder.iv= (ImageView) convertView.findViewById(R.id.item_dingdan_pic);
            holder.tvTime= (TextView) convertView.findViewById(R.id.item_dingdan_time);
            holder.tvTitle= (TextView) convertView.findViewById(R.id.item_dingdan_title);
            holder.tvType= (TextView) convertView.findViewById(R.id.item_dingdan_tvtype);
            holder.ivType= (ImageView) convertView.findViewById(R.id.item_dingdan_ivtype);
            holder.tvDate= (TextView) convertView.findViewById(R.id.item_dingdan_tvdate);
            holder.tvCount= (TextView) convertView.findViewById(R.id.item_dingdan_tvcount);
            holder.tvState= (TextView) convertView.findViewById(R.id.item_dingdan_tvstate);
            holder.qdlayout= (LinearLayout) convertView.findViewById(R.id.item_dingdan_progressbar);
            holder.tvqiang= (TextView) convertView.findViewById(R.id.progressbar_qd_tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.iv.setTag(position);
        holder.iv.setOnClickListener(this);
        holder.tvTime.setText(list.get(position).getAdd_time());
        x.image().bind(holder.iv,list.get(position).getUsers().getHead_pic());
        x.image().bind(holder.ivType,list.get(position).getOrder().getTypeimg());
        holder.tvType.setText(list.get(position).getOrder().getTypename());
        holder.tvDate.setText(list.get(position).getOrder().getRealtime());
        holder.tvCount.setText(list.get(position).getOrder().getTimes()+list.get(position).getOrder().getUnit());
        String state = list.get(position).getOrder().getState();
        String robstate=list.get(position).getRobstate();
        //3 待确定 4 已完成 5 已取消 1 已接单
        if(state!=null||!state.equals("")) {
            if (state.equals("0")) {
                if (robstate.equals("0")) {
                    holder.tvState.setVisibility(View.GONE);
                    holder.qdlayout.setVisibility(View.VISIBLE);
                    holder.tvqiang.setTextColor(Color.parseColor("#FF1111"));
//                holder.tvState.setText("未抢单");
//                holder.tvState.setTextColor(Color.parseColor("#50CB00"));
                } else {
                    holder.tvState.setVisibility(View.VISIBLE);
                    holder.qdlayout.setVisibility(View.GONE);
                    holder.tvState.setText("已抢单");
                }
            } else {
                if(state.equals("5")){
                    holder.tvState.setText("已取消");
                }else {
                    if (list.get(position).getOrder().getUiduid() != null) {
                        if (list.get(position).getOrder().getUiduid().equals(MyApp.uid)) {
                            if (state.equals("1")) {
                                holder.tvState.setText("待服务");
                                holder.tvState.setTextColor(Color.parseColor("#50CB00"));
                            }
                            if (state.equals("3")) {
                                holder.tvState.setText("待服务");
                                holder.tvState.setTextColor(Color.parseColor("#50CB00"));
                            }
                            if (state.equals("4")) {
                                holder.tvState.setText("已完成");
                            }
                            if (state.equals("5")) {
                                holder.tvState.setText("已取消");
                            }
                        } else {
                            holder.tvState.setText("已被抢");
                        }
                    }
                }
            }
        }
        return convertView;
    }

    @Override
    public void onClick(View view) {
        int position= (int) view.getTag();
        Intent intent=new Intent(context, FriendDataActivity.class);
        intent.putExtra("fid",list.get(position).getOrder().getUid());
        context.startActivity(intent);
    }

    class ViewHolder{
        ImageView iv;
        TextView tvTime;
        TextView tvTitle;
        ImageView ivType;
        TextView tvType;
        TextView tvDate;
        TextView tvCount;
        TextView tvState;
        LinearLayout qdlayout;
        TextView tvqiang;
    }
}
