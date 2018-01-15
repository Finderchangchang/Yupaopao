package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.GodFlData;
import com.longxiang.woniuke.bean.WeekRankData;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class WeekRankingAdapter extends BaseAdapter {
    private Context context;
    private List<WeekRankData.DataBean> datas;
    private final ImageOptions options=new ImageOptions.Builder()
            //设置加载过程中的图片
            .setLoadingDrawableId(R.mipmap.woniukeicon)
            //设置加载失败后的图片
            .setFailureDrawableId(R.mipmap.woniukeicon)
            //    设置使用缓存
            .setUseMemCache(true)
            //设置显示圆形图片
            .setCircular(true)
            //设置支持gif
            .setIgnoreGif(false)
            .build();
    public WeekRankingAdapter(Context context, List<WeekRankData.DataBean> datas) {
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
            convertView=View.inflate(context, R.layout.ranking_item,null);
            holder=new ViewHolder();
            holder.tvcount= (TextView) convertView.findViewById(R.id.item_ranking_tv_count);
            holder.tvname= (TextView) convertView.findViewById(R.id.item_ranking_tv_name);
            holder.tvsex= (TextView) convertView.findViewById(R.id.item_ranking_tv_age);
            holder.tvjifen= (TextView) convertView.findViewById(R.id.item_ranking_tv_jifen);
            holder.iv= (ImageView) convertView.findViewById(R.id.item_ranking_iv_icon);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Log.i("weekrankingfragment", "getView: "+position);
        WeekRankData.DataBean data=datas.get(position);
        holder.tvname.setText(data.getNickname());
        if(data.getSex().equals("男")){
            holder.tvsex.setSelected(true);
            holder.tvsex.setText(" "+data.getAge()+" ");
            holder.tvsex.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            holder.tvsex.setSelected(false);
            holder.tvsex.setText(" "+data.getAge()+" ");
            holder.tvsex.setBackgroundResource(R.drawable.conner_women_bg);
        }
        holder.tvjifen.setText(data.getWeekrank());
        holder.tvjifen.setTextColor(Color.parseColor("#1EBAF3"));
        holder.tvcount.setText(position+4+"");
        holder.tvcount.setTextColor(Color.parseColor("#000000"));
        x.image().bind(holder.iv,data.getHead_pic(),options);
        return convertView;
    }

    class ViewHolder{
        TextView tvcount,tvname,tvsex,tvjifen;
        ImageView iv;
    }
}
