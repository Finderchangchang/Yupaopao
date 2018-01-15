package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.GodFlData;
import com.longxiang.woniuke.bean.HomeListData2;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class GodflAdapter extends BaseAdapter {
    private Context context;
    private List<GodFlData.DataBean> datas;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100))
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
    public GodflAdapter(Context context, List<GodFlData.DataBean> datas) {
        this.context = context;
        if(datas==null){
            datas=new ArrayList<>();
        }
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
        ViewHolder holder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.dsfl_item,null);
            holder=new ViewHolder();
            holder.tv1= (TextView) convertView.findViewById(R.id.dsfl_item_tv_name);
            holder.tv2= (TextView) convertView.findViewById(R.id.dsfl_item_tv_age);
            holder.tv3= (TextView) convertView.findViewById(R.id.dsfl_item_tv_level);
            holder.tv4= (TextView) convertView.findViewById(R.id.dsfl_item_tv_money);
            holder.tv5= (TextView) convertView.findViewById(R.id.dsfl_item_tv_jdcount);
            holder.tv6= (TextView) convertView.findViewById(R.id.dsfl_item_tv_distance);
            holder.tv7= (TextView) convertView.findViewById(R.id.dsfl_item_tv_time);
            holder.iv= (ImageView) convertView.findViewById(R.id.dsfl_item_icon);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        GodFlData.DataBean data=datas.get(position);
        holder.tv1.setText(data.getNickname());
        if(data.getSex().equals("男")){
            holder.tv2.setSelected(true);
            holder.tv2.setText(" "+data.getAge()+" ");
            holder.tv2.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            holder.tv2.setSelected(false);
            holder.tv2.setText(" "+data.getAge()+" ");
            holder.tv2.setBackgroundResource(R.drawable.conner_women_bg);
        }
        holder.tv2.setTextColor(Color.parseColor("#ffffff"));
        holder.tv3.setText(data.getLevelname());
        holder.tv4.setText(data.getPrice()+"元/"+data.getUnit());
        holder.tv5.setText("接单"+data.getTimes()+"次");
        holder.tv6.setText((Double)data.getDistance()+"km");
        holder.tv7.setText(data.getTime());
        x.image().bind(holder.iv,data.getHead_pic(),imageOptions);
        return convertView;
    }

    class ViewHolder{
        TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
        ImageView iv;
    }
}
