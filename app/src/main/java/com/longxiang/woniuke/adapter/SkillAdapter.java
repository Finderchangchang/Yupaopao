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
import com.longxiang.woniuke.bean.JobData;
import com.longxiang.woniuke.bean.SkillData;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class SkillAdapter extends BaseAdapter{
    private Context context;
    private List<SkillData.DataBean> list;
    private LayoutInflater inflater;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(800), DensityUtil.dip2px(600))
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
    public SkillAdapter(Context context, List<SkillData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.skill_item,null);
            holder.tvname= (TextView) convertView.findViewById(R.id.skill_item_name);
            holder.tvlevel= (TextView) convertView.findViewById(R.id.skill_item_type);
            holder.tvmoney= (TextView) convertView.findViewById(R.id.skill_item_money);
            holder.tvcount= (TextView) convertView.findViewById(R.id.skill_item_jdcount);
            holder.ivpic= (ImageView) convertView.findViewById(R.id.skill_item_ivpic);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tvname.setText(list.get(position).getSkill());
        holder.tvname.setTextColor(Color.parseColor("#000000"));
        holder.tvlevel.setText(list.get(position).getLevel());
        holder.tvlevel.setTextColor(Color.parseColor("#DCA54D"));
        holder.tvmoney.setText((int)list.get(position).getPrice()+"元/"+list.get(position).getUnit());
        holder.tvcount.setText("接单"+list.get(position).getTimes()+"次");
        x.image().bind(holder.ivpic,list.get(position).getPic(),imageOptions);
        return convertView;
    }
    class ViewHolder{
        ImageView ivpic;
        TextView tvname;
        TextView tvlevel;
        TextView tvmoney;
        TextView tvcount;
    }
}
