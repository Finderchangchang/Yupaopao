package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.Interfaces.CallBacksss;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.JdSettingActivity;
import com.longxiang.woniuke.bean.DsData;
import com.longxiang.woniuke.bean.JdsettingData;
import com.longxiang.woniuke.bean.XmData;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class JdsettingAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private Context context;
    private List<JdsettingData.DataBean> list;
    private LayoutInflater inflater;
    private String bgid;
    private String state;
//    ViewHolder holder;
    private List<LinearLayout> lllist=new ArrayList<>();
    private ListView listView;
    private List<Integer> positionlist = new ArrayList<>();
    private String djid;

    public JdsettingAdapter(Context context,List<JdsettingData.DataBean> list) {
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
        listView = (ListView) parent;
        View view=inflater.inflate(R.layout.item_jdsz,null);
        ImageView iv = (ImageView) view.findViewById(R.id.item_jdsz_iv);
        TextView tv = (TextView) view.findViewById(R.id.item_jdsz_tv);
        Switch mswitch = (Switch) view.findViewById(R.id.item_jdsz_switch);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.item_jdsz_layout);
        TextView tvUnit = (TextView) view.findViewById(R.id.item_jdsz_unit);
        TextView tvMoney = (TextView) view.findViewById(R.id.item_jdsz_money);
//        if(convertView==null){
//            holder=new ViewHolder();
//            convertView=inflater.inflate(R.layout.item_jdsz,null);
//            holder.iv= (ImageView) convertView.findViewById(R.id.item_jdsz_iv);
//            holder.tv= (TextView) convertView.findViewById(R.id.item_jdsz_tv);
//            holder.mswitch= (Switch) convertView.findViewById(R.id.item_jdsz_switch);
//            holder.layout= (LinearLayout) convertView.findViewById(R.id.item_jdsz_layout);
//            holder.tvUnit= (TextView) convertView.findViewById(R.id.item_jdsz_unit);
//            holder.tvMoney= (TextView) convertView.findViewById(R.id.item_jdsz_money);
//            convertView.setTag(holder);
//        }else{
//            holder= (ViewHolder) convertView.getTag();
//        }
        layout.setTag(position);
        mswitch.setTag(position);
        mswitch.setTag(R.id.item_jdsz_layout,layout);
        JdsettingData.DataBean data = list.get(position);
//        mswitch.setTag(data.getSwitchX());
//        if(data.getSwitchX().equals("1")){
//            positionlist.set(position,position);
//        }else{
//        }
        if(data.getSwitchX().equals("1")){
            layout.setVisibility(View.VISIBLE);
            mswitch.setChecked(true);
        }else{
            layout.setVisibility(View.GONE);
            mswitch.setChecked(false);
        }
//        lllist.add(layout);
//        JdsettingData.DataBean data = list.get(position);
//        if(data.getSwitchX().equals("1")){
//           mswitch.setChecked(true);
//            layout.setVisibility(View.VISIBLE);
//        }else{
//            mswitch.setChecked(false);
//            layout.setVisibility(View.GONE);
//        }
        tv.setText(data.getSkill());
        x.image().bind(iv,data.getPic());
        tvMoney.setText(data.getPrice()+"元");
        tvMoney.setTextColor(Color.parseColor("#1EBAF3"));
        tvUnit.setText("单价"+"(每"+data.getUnit()+")");
        mswitch.setOnCheckedChangeListener(this);
        layout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position= (int) buttonView.getTag();
        LinearLayout layout = (LinearLayout) buttonView.getTag(R.id.item_jdsz_layout);
        bgid=list.get(position).getBgid();
        state=list.get(position).getSwitchX();
        if(isChecked){
            state="1";
            list.get(position).setSwitchX(state);
            setSwitch();
//            this.notifyDataSetChanged();
            ((CallBacksss)context).Call();
//            layout.setVisibility(View.VISIBLE);
//            lllist.get(position).setVisibility(View.VISIBLE);
            Log.i("jdsettingposition", "onCheckedChanged: "+position+"1");
        }else{
            state="2";
            list.get(position).setSwitchX(state);
            setSwitch();
//            this.notifyDataSetChanged();
//            positionlist.set(position,999);
            ((CallBacksss)context).Call();
//            layout.setVisibility(View.GONE);
//            lllist.get(position).setVisibility(View.GONE);
            Log.i("jdsettingposition", "onCheckedChanged: "+position+"2");
        }
//        setListViewHeightBasedOnChildren(listView);
    }

    private void setSwitch() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/setSwitch");
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("switch",state);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(context.getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onClick(View v) {
        int position= (int) v.getTag();
        bgid=list.get(position).getBgid();
        djid=list.get(position).getDjid();
        ((JdSettingActivity)context).setPrice(bgid,djid,position);
    }
    // 定义一个函数将dp转换为像素

//    public int Dp2Px(Context context, float dp) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//
//        return (int) (dp * scale + 0.5f);
//    }


//   // 定义函数动态控制listView的高度
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//
////获取listview的适配器
//        ListAdapter listAdapter = listView.getAdapter(); //item的高度
//
//        if (listAdapter == null) {
//
//            return;
//        }
//        int totalHeight = 0;
//
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//
//            listItem.measure(0, 0); //计算子项View 的宽高 //统计所有子项的总高度
//            totalHeight += Dp2Px(context.getApplicationContext(),listItem.getMeasuredHeight())+listView.getDividerHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//
//        params.height = totalHeight; listView.setLayoutParams(params);
//
//    }

//    class ViewHolder{
//        ImageView iv;
//        TextView tv;
//        Switch mswitch;
//        LinearLayout layout;
//        TextView tvUnit;
//        TextView tvMoney;
//    }
}
