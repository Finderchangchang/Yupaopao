package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.QdSettingData;
import com.longxiang.woniuke.bean.JdsettingData;

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
public class QdsettingAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private List<QdSettingData.DataBean> list;
    private List<JdsettingData.DataBean> jdlist;
    private LayoutInflater inflater;
    private String bgid;
    private String state;
    private int positions=999;
//    ViewHolder holder;
    private List<LinearLayout> lllist=new ArrayList<>();
    private ListView listView;
    private List<Integer> positionlist = new ArrayList<>();
    public QdsettingAdapter(Context context, List<QdSettingData.DataBean> list,List<JdsettingData.DataBean> jdlist) {
        this.context = context;
        if(list==null){
            list=new ArrayList<>();
        }
        this.list = list;
        this.jdlist=jdlist;
        inflater=LayoutInflater.from(context);
//        for(int i = 0;i<list.size();i++){
//            positionlist.add(999);
//        }
//        Log.i("jdsettingposition", "onCheckedChanged: "+"aaaaaa"+"1");
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
        View view=inflater.inflate(R.layout.item_qdsz,null);
        TextView tv = (TextView) view.findViewById(R.id.item_qdsz_tv);
        Switch mswitch = (Switch) view.findViewById(R.id.item_qdsz_switch);
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
        mswitch.setTag(position);
        QdSettingData.DataBean data = list.get(position);
//        mswitch.setTag(data.getSwitchX());
//        if(data.getSwitchX().equals("1")){
//            positionlist.set(position,position);
//        }else{
//        }
        if(data.getRobswitch().equals("1")){
            mswitch.setChecked(true);
            state="1";
            bgid=jdlist.get(position).getBgid();
            setSwitch();
        }else{
            state="2";
            bgid=jdlist.get(position).getBgid();
            mswitch.setChecked(false);
            setSwitch();
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
        mswitch.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position= (int) buttonView.getTag();
        bgid=list.get(position).getBgid();
        state=list.get(position).getRobswitch();
        if(isChecked){
            state="1";
            list.get(position).setRobswitch(state);
            setSwitch();
//            this.notifyDataSetChanged();

                this.notifyDataSetChanged();

//            layout.setVisibility(View.VISIBLE);
//            lllist.get(position).setVisibility(View.VISIBLE);
            Log.i("jdsettingposition", "onCheckedChanged: "+position+"1");
        }else{
            state="2";
            list.get(position).setRobswitch(state);
            setSwitch();
//            this.notifyDataSetChanged();
//            positionlist.set(position,999);

                this.notifyDataSetChanged();

//            layout.setVisibility(View.GONE);
//            lllist.get(position).setVisibility(View.GONE);
            Log.i("jdsettingposition", "onCheckedChanged: "+position+"2");
        }
//        setListViewHeightBasedOnChildren(listView);
    }

    private void setSwitch() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/setRobSwitch");
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("robswitch",state);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("qdsettomgadapter", "onSuccess: "+result);
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

//    class ViewHolder{
//        ImageView iv;
//        TextView tv;
//        Switch mswitch;
//        LinearLayout layout;
//        TextView tvUnit;
//        TextView tvMoney;
//    }
}
