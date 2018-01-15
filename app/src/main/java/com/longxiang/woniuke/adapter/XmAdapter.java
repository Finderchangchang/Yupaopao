package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.IntrestActivity;
import com.longxiang.woniuke.bean.DsData;
import com.longxiang.woniuke.bean.IntrestData;
import com.longxiang.woniuke.bean.JnidData;
import com.longxiang.woniuke.bean.XmData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class XmAdapter extends BaseAdapter{
    private Context context;
    private List<XmData.DataBean> list;
    private List<DsData> dslist;
    private LayoutInflater inflater;

    public XmAdapter(Context context, List<DsData> dslist, List<XmData.DataBean> list) {
        this.context = context;
        if(list==null){
            list=new ArrayList<>();
        }
        this.list = list;
        this.dslist=dslist;
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
            convertView=inflater.inflate(R.layout.item_sqxm,null);
            holder.iv= (ImageView) convertView.findViewById(R.id.item_sqxm_iv);
            holder.tv= (TextView) convertView.findViewById(R.id.item_sqxm_tv);
            holder.tvstate= (TextView) convertView.findViewById(R.id.sqxm_tv_state);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
//        if (position==0){
//            holder.tvstate.setVisibility(View.GONE);
//        }
        for (int i=0;i<dslist.size();i++){
            if(dslist.get(i).getXmid().equals(list.get(position).getId())&&dslist.get(i).getState().equals("2")){
                Log.i("xmadapter", "getView: "+dslist.get(i).getXmid()+","+list.get(position).getId()+","+dslist.get(i).getState()+"time="+i+"----position==="+position);
                holder.tvstate.setTextColor(Color.parseColor("#31C0F4"));
                holder.tvstate.setVisibility(View.VISIBLE);
            }
            if(dslist.get(i).getXmid().equals(list.get(position).getId())&&dslist.get(i).getState().equals("1")){
                Log.i("xmadapter", "getView: "+dslist.get(i).getXmid()+","+list.get(position).getId()+","+dslist.get(i).getState()+"time="+i+"----position==="+position);
                holder.tvstate.setVisibility(View.VISIBLE);
                holder.tvstate.setText("正在审核");
                holder.tvstate.setTextColor(Color.parseColor("#31C0F4"));
            }

        }
        holder.tv.setText(list.get(position).getTitle());
        x.image().bind(holder.iv,list.get(position).getIcon());
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        for (int i = 0; i < dslist.size(); i++) {
            if (dslist.get(i).getXmid().equals(list.get(position).getId()) && dslist.get(i).getState().equals("1")) {
                return false;
            }
        }
            return super.isEnabled(position);
        }

    class ViewHolder{
        ImageView iv;
        TextView tv;
        TextView tvstate;
    }
}
