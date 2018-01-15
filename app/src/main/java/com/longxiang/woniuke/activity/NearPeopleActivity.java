package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.NearPeopleAdapter;
import com.longxiang.woniuke.bean.NearPeopleData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class NearPeopleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
private ImageView ivBack;
private ImageView ivSx;
    private TextView tvTitle;
    private ListView listView;
    private String sex="";
    private String timearea="";
    private List<NearPeopleData.DataBean> nearlist=new ArrayList<>();
    private NearPeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_people);
        setView();
        setListener();
        getNearPeople();
    }

    private void getNearPeople() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getnearmanbygeohaxi");
        params.addBodyParameter("lat", MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        params.addBodyParameter("sex",sex);
        params.addBodyParameter("timearea",timearea);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("nearpeople", "onSuccess: "+result);
                NearPeopleData data=new Gson().fromJson(result,NearPeopleData.class);
                if(data.getRetcode()==2000) {
                    nearlist.addAll(data.getData());
                    adapter=new NearPeopleAdapter(NearPeopleActivity.this, nearlist);
                    listView.setAdapter(adapter);
                }
                if(data.getRetcode()==4001){
                    nearlist.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
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

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.near_iv_back);
        ivSx= (ImageView) findViewById(R.id.near_iv_shaixuan);
        listView= (ListView) findViewById(R.id.near_listview);
        tvTitle= (TextView) findViewById(R.id.near_tv_title);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivSx.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.near_iv_back:
                finish();
                break;
            case R.id.near_iv_shaixuan:
                Intent intent=new Intent(this,NearSxActivity.class);
                startActivityForResult(intent,490);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fid = nearlist.get(position).getUid();
        Intent intent=new Intent(this,FriendDataActivity.class);
        intent.putExtra("fid",fid);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==490&&resultCode==RESULT_OK){
            sex=data.getStringExtra("sex");
            timearea=data.getStringExtra("time");
            nearlist.clear();
            getNearPeople();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
