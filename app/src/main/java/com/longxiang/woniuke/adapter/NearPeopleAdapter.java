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
import com.longxiang.woniuke.bean.NearPeopleData;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class NearPeopleAdapter extends BaseAdapter {
    private Context context;
//    private List<ImageView> imgs;
    private List<NearPeopleData.DataBean> datas;
    private LayoutInflater inflater;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(500), DensityUtil.dip2px(500))
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
    public NearPeopleAdapter(Context context, List<NearPeopleData.DataBean> datas) {
        this.context = context;
        this.datas = datas;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
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
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.item_near_people,null);
            holder.tv1= (TextView) convertView.findViewById(R.id.near_item_name);
            holder.tv2= (TextView) convertView.findViewById(R.id.near_item_age);
            holder.tv3= (TextView) convertView.findViewById(R.id.near_item_tv_distance);
            holder.tv4= (TextView) convertView.findViewById(R.id.near_item_tv_time);
            holder.tv5= (TextView) convertView.findViewById(R.id.near_item_tv_sign);
            holder.iv= (ImageView) convertView.findViewById(R.id.near_item_icon);
            holder.iv01= (ImageView) convertView.findViewById(R.id.iv01_item_near);
            holder.iv02= (ImageView) convertView.findViewById(R.id.iv02_item_near);
            holder.iv03= (ImageView) convertView.findViewById(R.id.iv03_item_near);
            holder.iv04= (ImageView) convertView.findViewById(R.id.iv04_item_near);
            holder.iv05= (ImageView) convertView.findViewById(R.id.iv05_item_near);
            holder.iv06= (ImageView) convertView.findViewById(R.id.iv06_item_near);
            holder.iv07= (ImageView) convertView.findViewById(R.id.iv07_item_near);
            holder.iv08= (ImageView) convertView.findViewById(R.id.iv08_item_near);
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
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
//        View view=View.inflate(context,R.layout.item_near_people,null);
//        TextView tv1 = (TextView) view.findViewById(R.id.near_item_name);
//        TextView tv2 = (TextView) view.findViewById(R.id.near_item_age);
//        TextView tv3 = (TextView) view.findViewById(R.id.near_item_tv_distance);
//        TextView tv4 = (TextView) view.findViewById(R.id.near_item_tv_time);
//        TextView tv5 = (TextView) view.findViewById(R.id.near_item_tv_sign);
//        ImageView iv= (ImageView) view.findViewById(R.id.near_item_icon);
//        ImageView iv01= (ImageView) view.findViewById(R.id.iv01_item_near);
//        ImageView iv02= (ImageView) view.findViewById(R.id.iv02_item_near);
//        ImageView iv03= (ImageView) view.findViewById(R.id.iv03_item_near);
//        ImageView iv04= (ImageView) view.findViewById(R.id.iv04_item_near);
//        ImageView iv05= (ImageView) view.findViewById(R.id.iv05_item_near);
//        ImageView iv06= (ImageView) view.findViewById(R.id.iv06_item_near);
//        ImageView iv07= (ImageView) view.findViewById(R.id.iv07_item_near);
//        ImageView iv08= (ImageView) view.findViewById(R.id.iv08_item_near);
//            imgs=new ArrayList<>();

        NearPeopleData.DataBean data=datas.get(position);
        holder.tv1.setText(data.getNickname());
        if(data.getSex().equals("男")){
            holder.tv2.setSelected(true);
            holder.tv2.setText(" "+data.getAge()+" ");
            holder.tv2.setBackgroundResource(R.drawable.conner_man_bg);
//            tv5.setBackgroundColor(Color.parseColor("#31C0F4"));
        }else{
            holder.tv2.setSelected(false);
            holder.tv2.setText(" "+data.getAge()+" ");
            holder.tv2.setBackgroundResource(R.drawable.conner_women_bg);
//            tv5.setBackgroundColor(Color.parseColor("#FC88AF"));
        }
        holder.tv2.setTextColor(Color.parseColor("#ffffff"));
        holder.tv3.setText(data.getDistance()+"km");
        holder.tv4.setText(data.getTime());

        holder.tv5.setText(data.getSign());
        x.image().bind(holder.iv,data.getHead_pic(),imageOptions);
        for (int i=0;i<holder.imgs.size();i++){
            holder.imgs.get(i).setVisibility(View.INVISIBLE);
        }
        if(data.getGodpic()!=null) {
            if(data.getGodpic().size()<=4) {
                for (int i = 0; i < data.getGodpic().size(); i++) {
                    holder.imgs.get(i).setVisibility(View.VISIBLE);
                    x.image().bind(holder.imgs.get(i), data.getGodpic().get(i));
                }
            }else{
                for(int i=0;i<4;i++){
                    holder.imgs.get(i).setVisibility(View.VISIBLE);
                    x.image().bind(holder.imgs.get(i), data.getGodpic().get(i));
                }
            }
        }
        return convertView;
    }
    class ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5;
        ImageView iv,iv01,iv02,iv03,iv04,iv05,iv06,iv07,iv08;
        List<ImageView> imgs=new ArrayList<>();
    }
}
