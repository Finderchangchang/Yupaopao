package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.DynamicDetailActivity;
import com.longxiang.woniuke.bean.DynamicData;
import com.longxiang.woniuke.bean.SkillData;
import com.longxiang.woniuke.utils.FavorLayout;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class DynamicAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<DynamicData.DataBean> list;
    private LayoutInflater inflater;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(800), DensityUtil.dip2px(700))
            .setRadius(DensityUtil.dip2px(5))
//            .setCrop(true)
            // 加载中或错误图片的ScaleType
//            .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            //设置加载过程中的图片
            .setLoadingDrawableId(R.mipmap.woniu)
            //设置加载失败后的图片
            .setFailureDrawableId(R.mipmap.woniu)
            //设置使用缓存
            .setUseMemCache(true)
            //设置支持gif
            .setIgnoreGif(false)
            //设置显示圆形图片
            .setCircular(false)
            .setSquare(true)
            .build();
    public DynamicAdapter(Context context, List<DynamicData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.dynamic_item,null);
            holder.tvdistance= (TextView) convertView.findViewById(R.id.dynamic_item_distance);
            holder.tvtime= (TextView) convertView.findViewById(R.id.dynamic_item_time);
            holder.tvcontent= (TextView) convertView.findViewById(R.id.dynamic_item_tvcontent);
            holder.pinglun= (TextView) convertView.findViewById(R.id.dynamic_item_pinglun);
            holder.dianzan= (TextView) convertView.findViewById(R.id.dynamic_item_zan);
            holder.ivpic= (ImageView) convertView.findViewById(R.id.dynamic_item_pic);
            holder.mFavorLayout = (FavorLayout) convertView.findViewById(R.id.mfavorlayout);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvdistance.setText(list.get(position).getDistance()+"km");
        holder.tvtime.setText(list.get(position).getTime());
        holder.tvcontent.setText(list.get(position).getContent());
        holder.tvcontent.setTextColor(Color.parseColor("#000000"));
        if(list.get(position).getCommenttimes().equals("0")){
            holder.pinglun.setText("评论");
        }else {
            holder.pinglun.setText(list.get(position).getCommenttimes());
        }
        holder.pinglun.setTextColor(Color.parseColor("#999999"));
        holder.dianzan.setText(list.get(position).getLandtimes());
        holder.dianzan.setTextColor(Color.parseColor("#999999"));
        holder.dianzan.setTag(position);
        holder.dianzan.setTag(R.id.mfavorlayout,holder.mFavorLayout);
        x.image().bind(holder.ivpic,list.get(position).getPic(),imageOptions);
//        holder.pinglun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, DynamicDetailActivity.class);
//                intent.putExtra("uid",list.get(position).getUid());
//                context.startActivity(intent);
//            }
//        });
        holder.dianzan.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dynamic_item_zan:
                int position= (int) v.getTag();
                int landtime= Integer.parseInt(list.get(position).getLandtimes());
                Log.i("zanzanzan", "onClick: "+position);
                FavorLayout mfavorlayout= (FavorLayout) v.getTag(R.id.mfavorlayout);
                mfavorlayout.addFavor();
                landtime++;
                list.get(position).setLandtimes(landtime+"");
                TextView tv= (TextView) v;
                tv.setText(landtime+"");
                notifyDataSetChanged();
                break;
        }
    }

    class ViewHolder{
        ImageView ivpic;
        TextView tvdistance;
        TextView tvtime;
        TextView tvcontent;
        TextView pinglun;
        TextView dianzan;
        FavorLayout mFavorLayout;
    }
}
