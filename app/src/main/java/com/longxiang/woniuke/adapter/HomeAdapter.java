package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.HomeListData;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<HomeListData> datas;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(60), DensityUtil.dip2px(60))
            .setRadius(DensityUtil.dip2px(5))
            // 加载中或错误图片的ScaleType
            //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
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
    public  HomeAdapter(Context context, List<HomeListData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size()>0?datas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.item_lv_home,null);
            holder=new ViewHolder();
//        View view=View.inflate(context,R.layout.item_lv_home,null);
//        TextView tv1 = (TextView) view.findViewById(R.id.tv_name_lv_home);
//        TextView tv2 = (TextView) view.findViewById(R.id.tv_distance_lv_home);
//        TextView tv3 = (TextView) view.findViewById(R.id.tv_time_lv_home);
//        TextView tv4 = (TextView) view.findViewById(R.id.tv_constellation_lv_home);
//        TextView tv5 = (TextView) view.findViewById(R.id.tv_age_lv_home);
//        TextView tv6 = (TextView) view.findViewById(R.id.tv_start_lv_home);
//        TextView tv7 = (TextView) view.findViewById(R.id.tv_getoffer_lv_home);
            holder.tv1= (TextView) convertView.findViewById(R.id.tv_name_lv_home);
            holder.tv2= (TextView) convertView.findViewById(R.id.tv_distance_lv_home);
            holder.tv3= (TextView) convertView.findViewById(R.id.tv_time_lv_home);
            holder.tv4= (TextView) convertView.findViewById(R.id.tv_constellation_lv_home);
            holder.tv5= (TextView) convertView.findViewById(R.id.tv_age_lv_home);
            holder.tv6= (TextView) convertView.findViewById(R.id.tv_start_lv_home);
            holder.tv7= (TextView) convertView.findViewById(R.id.tv_getoffer_lv_home);
//        ImageView iv= (ImageView) view.findViewById(R.id.iv_lv_home);
//        ImageView iv01= (ImageView) view.findViewById(R.id.iv01_item_home);
//        ImageView iv02= (ImageView) view.findViewById(R.id.iv02_item_home);
//        ImageView iv03= (ImageView) view.findViewById(R.id.iv03_item_home);
//        ImageView iv04= (ImageView) view.findViewById(R.id.iv04_item_home);
//        ImageView iv05= (ImageView) view.findViewById(R.id.iv05_item_home);
//        ImageView iv06= (ImageView) view.findViewById(R.id.iv06_item_home);
//        ImageView iv07= (ImageView) view.findViewById(R.id.iv07_item_home);
//        ImageView iv08= (ImageView) view.findViewById(R.id.iv08_item_home);
            holder.iv= (ImageView) convertView.findViewById(R.id.iv_lv_home);
            holder.iv01= (ImageView) convertView.findViewById(R.id.iv01_item_home);
            holder.iv02= (ImageView) convertView.findViewById(R.id.iv02_item_home);
            holder.iv03= (ImageView) convertView.findViewById(R.id.iv03_item_home);
            holder.iv04= (ImageView) convertView.findViewById(R.id.iv04_item_home);
            holder.iv05= (ImageView) convertView.findViewById(R.id.iv05_item_home);
            holder.iv06= (ImageView) convertView.findViewById(R.id.iv06_item_home);
            holder.iv07= (ImageView) convertView.findViewById(R.id.iv07_item_home);
            holder.iv08= (ImageView) convertView.findViewById(R.id.iv08_item_home);
            holder.imgs.add(holder.iv01);
            holder.imgs.add(holder.iv02);
            holder.imgs.add(holder.iv03);
            holder.imgs.add(holder.iv04);
            holder.imgs.add(holder.iv05);
            holder.imgs.add(holder.iv06);
            holder.imgs.add(holder.iv07);
            holder.imgs.add(holder.iv08);
            for (int i=0;i<holder.imgs.size();i++){
                holder.imgs.get(i).setVisibility(View.INVISIBLE);
            }
            holder.layout= (LinearLayout) convertView.findViewById(R.id.ll_lv_home);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        HomeListData data=datas.get(position);
//        holder.tv1.setTextColor(context.getResources().getColor(R.color.colorWorddark));
//        holder.tv4.setTextColor(context.getResources().getColor(R.color.colorWorddark));
//        holder.tv5.setTextColor(context.getResources().getColor(R.color.colorWorddark));
//        holder.tv7.setTextColor(context.getResources().getColor(R.color.colorWorddark));
//        holder.tv2.setTextColor(context.getResources().getColor(R.color.colorWordlight));
//        holder.tv3.setTextColor(context.getResources().getColor(R.color.colorWordlight));
//        holder.tv6.setTextColor(context.getResources().getColor(R.color.colorWordlight));
        holder.tv1.setText(data.getNickname());
        holder.tv3.setText(data.getTime());
        holder.tv4.setText(data.getStarchar());
        if(data.getSex().equals("男")){
            holder.tv5.setText(" "+data.getAge()+" ");
            holder.tv4.setTextColor(Color.parseColor("#96beec"));
            holder.tv5.setTextColor(Color.parseColor("#96beec"));
        }else{
            holder.tv5.setText(" "+data.getAge()+" ");
            holder.tv4.setTextColor(Color.parseColor("#EEA9B8"));
            holder.tv5.setTextColor(Color.parseColor("#EEA9B8"));
        }
        holder.tv6.setText((Double)data.getLevel()+"");
        holder.tv7.setText("接单"+data.getSumtiems()+"次");
        x.image().bind(holder.iv,data.getHead_pic(),imageOptions);
        for (int i=0;i<holder.imgs.size();i++){
            holder.imgs.get(i).setVisibility(View.INVISIBLE);
        }
        if(data.getGodpic().size()<=4){
            for (int i=0;i<data.getGodpic().size();i++) {
                holder.imgs.get(i).setVisibility(View.VISIBLE);
                x.image().bind(holder.imgs.get(i), data.getGodpic().get(i));
            }
        }else{
            for(int i=0;i<4;i++){
                holder.imgs.get(i).setVisibility(View.VISIBLE);
                x.image().bind(holder.imgs.get(i), data.getGodpic().get(i));
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
        ImageView iv,iv01,iv02,iv03,iv04,iv05,iv06,iv07,iv08;
        LinearLayout layout;
        List<ImageView> imgs=new ArrayList<>();
    }
}
