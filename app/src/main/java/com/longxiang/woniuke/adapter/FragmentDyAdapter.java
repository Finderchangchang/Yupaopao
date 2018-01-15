package com.longxiang.woniuke.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.MainActivity;
import com.longxiang.woniuke.activity.ZoomInPhotoActivity;
import com.longxiang.woniuke.bean.DynamicData;
import com.longxiang.woniuke.bean.FragmentDyData;
import com.longxiang.woniuke.fragment.JhFragment;
import com.longxiang.woniuke.utils.DashangDialog;
import com.longxiang.woniuke.utils.FavorLayout;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class FragmentDyAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<FragmentDyData.DataBean> list;
//    private List<ImageView> imgs=new ArrayList<>();
    private LayoutInflater inflater;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(800), DensityUtil.dip2px(500))
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
    public FragmentDyAdapter(Context context, List<FragmentDyData.DataBean> list) {
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
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.fragment_dynamic_item,null);
            holder.ivIcon= (ImageView) convertView.findViewById(R.id.fragment_dynamic_item_icon);
            holder.tvName= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_name);
            holder.tvGender= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_sex);
            holder.tvGz= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_gz);
            holder.tvdistance= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_distance);
            holder.tvtime= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_time);
            holder.tvcontent= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_tvcontent);
            holder.pinglun= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_pinglun);
            holder.dianzan= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_zan);
            holder.ivpic= (ImageView) convertView.findViewById(R.id.fragment_dynamic_item_pic);
            holder.dashang= (TextView) convertView.findViewById(R.id.fragment_dynamic_item_dashang);
            holder.mFavorLayout = (FavorLayout) convertView.findViewById(R.id.fragment_dynamic_item_favorlayout);
            holder.iv01= (ImageView) convertView.findViewById(R.id.iv01_item_fragment_dynamic);
            holder.iv02= (ImageView) convertView.findViewById(R.id.iv02_item_fragment_dynamic);
            holder.iv03= (ImageView) convertView.findViewById(R.id.iv03_item_fragment_dynamic);
            holder.iv04= (ImageView) convertView.findViewById(R.id.iv04_item_fragment_dynamic);
            holder.iv05= (ImageView) convertView.findViewById(R.id.iv05_item_fragment_dynamic);
            holder.iv06= (ImageView) convertView.findViewById(R.id.iv06_item_fragment_dynamic);
            holder.iv07= (ImageView) convertView.findViewById(R.id.iv07_item_fragment_dynamic);
            holder.iv08= (ImageView) convertView.findViewById(R.id.iv08_item_fragment_dynamic);
//            imgs=new ArrayList<>();
//            holder.imgs.clear();
            holder. imgs.add(holder.iv01);
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

        x.image().bind(holder.ivIcon,list.get(position).getHead_pic());
        holder.tvName.setText(list.get(position).getNickname());
        holder.tvName.setTextColor(Color.parseColor("#000000"));
        if(list.get(position).getSex().equals("男")){
            holder.tvGender.setSelected(true);
            holder.tvGender.setText(" "+list.get(position).getAge()+" ");
            holder.tvGender.setTextColor(Color.parseColor("#ffffff"));
            holder.tvGender.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            holder.tvGender.setSelected(false);
            holder.tvGender.setText(" "+list.get(position).getAge()+" ");
            holder.tvGender.setTextColor(Color.parseColor("#ffffff"));
            holder.tvGender.setBackgroundResource(R.drawable.conner_women_bg);
        }
        if(list.get(position).getFollowstate()==1){
            holder.tvGz.setText("已关注");
            holder.tvGz.setTextColor(Color.parseColor("#31C0F4"));
            holder.tvGz.setVisibility(View.VISIBLE);
        }
        if(list.get(position).getFollowstate()==2){
            holder.tvGz.setText("关注");
            holder.tvGz.setTextColor(Color.parseColor("#31C0F4"));
            holder.tvGz.setVisibility(View.VISIBLE);
        }
        if(list.get(position).getFollowstate()==3){
           holder.tvGz.setVisibility(View.GONE);
        }
        holder.tvGz.setTag(position);
        holder.tvdistance.setText(list.get(position).getDistance()+"km");
        holder.tvdistance.setTextColor(Color.parseColor("#888888"));
        holder.tvtime.setText(list.get(position).getTime());
        holder.tvtime.setTextColor(Color.parseColor("#888888"));
        holder.tvcontent.setText(list.get(position).getContent());
        holder.tvcontent.setTextColor(Color.parseColor("#000000"));
        if(list.get(position).getCommenttimes().equals("0")){
            holder.pinglun.setText("评论");
        }else {
            holder.pinglun.setText(list.get(position).getCommenttimes());
        }
        if(list.get(position).getRewardtimes().equals("0")){
            holder.dashang.setText("打赏");
        }else{
            holder.dashang.setText(list.get(position).getRewardtimes());
        }
        holder.dashang.setTextColor(Color.parseColor("#999999"));
        holder.dashang.setTag(position);
        holder.dashang.setOnClickListener(this);
        holder.pinglun.setTextColor(Color.parseColor("#999999"));
        holder.dianzan.setText(list.get(position).getLandtimes());
        holder.dianzan.setTextColor(Color.parseColor("#999999"));
        holder.dianzan.setTag(position);
        holder.dianzan.setTag(R.id.fragment_dynamic_item_favorlayout,holder.mFavorLayout);
        x.image().bind(holder.ivpic,list.get(position).getPic(),imageOptions);
        holder.ivpic.setTag(position);
        holder.ivpic.setOnClickListener(this);
//        holder.pinglun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, DynamicDetailActivity.class);
//                intent.putExtra("uid",list.get(position).getUid());
//                context.startActivity(intent);
//            }
//        });
//        holder.imgs.clear();
//        holder. imgs.add(holder.iv01);
//        holder.imgs.add(holder.iv02);
//        holder.imgs.add(holder.iv03);
//        holder.imgs.add(holder.iv04);
//        holder.imgs.add(holder.iv05);
//        holder.imgs.add(holder.iv06);
//        holder.imgs.add(holder.iv07);
//        holder.imgs.add(holder.iv08);
        for (int i=0;i<holder.imgs.size();i++){
            holder.imgs.get(i).setVisibility(View.INVISIBLE);
        }
        holder.dianzan.setOnClickListener(this);
        if(list.get(position).getGodpic()!=null) {
            if(list.get(position).getGodpic().size()<=4) {
                for (int i = 0; i < list.get(position).getGodpic().size(); i++) {
                    holder.imgs.get(i).setVisibility(View.VISIBLE);
                    x.image().bind(holder.imgs.get(i), list.get(position).getGodpic().get(i));
                }
            }else{
                for(int i=0;i<4;i++){
                    holder.imgs.get(i).setVisibility(View.VISIBLE);
                    x.image().bind(holder.imgs.get(i), list.get(position).getGodpic().get(i));
                }
            }
        }
        holder.tvGz.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_dynamic_item_zan:
                int position= (int) v.getTag();
                int landtime= Integer.parseInt(list.get(position).getLandtimes());
                Log.i("zanzanzan", "onClick: "+position);
                FavorLayout mfavorlayout= (FavorLayout) v.getTag(R.id.fragment_dynamic_item_favorlayout);
                mfavorlayout.addFavor();
                landtime++;
                list.get(position).setLandtimes(landtime+"");
                TextView tv= (TextView) v;
                tv.setText(landtime+"");
                notifyDataSetChanged();
                break;
            case R.id.fragment_dynamic_item_gz:
                int positions= (int) v.getTag();
                String otheruid=list.get(positions).getUid();
                if(list.get(positions).getFollowstate()==1){
                   list.get(positions).setFollowstate(2);
                    overfollow(otheruid);
                    Log.i("gzzouange", "onClick: "+"1");
//                    this.notifyDataSetChanged();
                }else{
                    list.get(positions).setFollowstate(1);
                    followOne(otheruid);
                    Log.i("gzzouange", "onClick: "+"2");
//                    this.notifyDataSetChanged();
                }
                break;
            case R.id.fragment_dynamic_item_dashang:
                    int dsposition= (int) v.getTag();
                    String dmid=list.get(dsposition).getDmid();
                     //打赏
                if(list.get(dsposition).getUid().equals(MyApp.uid)){
                    Toast.makeText(context.getApplicationContext(),"您不能打赏自己",Toast.LENGTH_SHORT).show();
                }else {
                    DashangDialog dialog = new DashangDialog(context, dmid);
                }
                break;
            case R.id.fragment_dynamic_item_pic:
                int picposition= (int) v.getTag();
                Intent intent=new Intent(context, ZoomInPhotoActivity.class);
                intent.putExtra("imgurl",list.get(picposition).getPic());
                context.startActivity(intent);
                break;
        }
    }


    private void overfollow(String otheruid) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/overfollow");
        params.addBodyParameter("selfuid",MyApp.uid);
        params.addBodyParameter("otheruid",otheruid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("retcode").equals("2000")){
                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("fragmentdyadapter", "onSuccess: "+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void followOne(String otheruid) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/followOne");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("fuid",otheruid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("retcode").equals("2000")){
                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("fragmentdyadapter", "onSuccess: "+result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    class ViewHolder{
        ImageView ivpic;
        ImageView ivIcon;
        TextView tvName;
        TextView tvGender;
        TextView tvGz;
        TextView tvdistance;
        TextView tvtime;
        TextView tvcontent;
        TextView pinglun;
        TextView dianzan;
        TextView dashang;
        FavorLayout mFavorLayout;
        ImageView iv01,iv02,iv03,iv04,iv05,iv06,iv07,iv08;
        List<ImageView> imgs=new ArrayList<>();
    }
}
