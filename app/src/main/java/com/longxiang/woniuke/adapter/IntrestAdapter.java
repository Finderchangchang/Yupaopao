package com.longxiang.woniuke.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.IntrestActivity;
import com.longxiang.woniuke.bean.IntrestData;
import com.longxiang.woniuke.bean.JobData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/17.
 */
public class IntrestAdapter extends BaseAdapter{
    private Context context;
    private List<IntrestData.DataBean> list;
    private LayoutInflater inflater;
    private List<String> intrestList =new ArrayList<>();
    private List<Boolean> booleanList=new ArrayList<>();
    private String str;
    private int count=0;
    public IntrestAdapter(Context context, List<IntrestData.DataBean> list) {
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
            convertView=inflater.inflate(R.layout.item_intrest,null);
            holder.ck= (CheckBox) convertView.findViewById(R.id.item_ck);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.ck.setText(list.get(position).getInterest());
        holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(intrestList.size()<5) {
                        intrestList.add(list.get(position).getInterest());
                        holder.ck.setChecked(true);
                    }else{
                        Toast.makeText(context,"最多可选择5个",Toast.LENGTH_SHORT).show();
                        holder.ck.setChecked(false);
                    }
                }else{
                    intrestList.remove(list.get(position).getInterest());
                }
                str="";
                for (int i=0;i<intrestList.size();i++){
                    str+=intrestList.get(i)+"/";
                }
                if(intrestList.size()!=0) {
                    str = str.substring(0, str.length() - 1);
                }else{
                    str="";
                }

                IntrestActivity.editText.setText(str);
                Log.i("inadapter", "onCheckedChanged: "+str);
            }
        });
        return convertView;
    }
    class ViewHolder{
        CheckBox ck;
    }
}
