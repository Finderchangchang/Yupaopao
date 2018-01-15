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

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.ZanpeopleAdapter;
import com.longxiang.woniuke.bean.ZanpeopleData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ZanPepoleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
private ImageView ivBack;
    private ListView listView;
    private String dmid;
    private ZanpeopleData data;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan_pepole);
        setView();
        setListener();
        Intent intent=getIntent();
        dmid=intent.getStringExtra("dmid");
        getZanpeople();
    }

    private void getZanpeople() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getlandmen");
        params.addBodyParameter("dmid",dmid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("zanpeople", "onSuccess: "+result);
                data=new Gson().fromJson(result,ZanpeopleData.class);
                if(data.getData()!=null) {
                    listView.setAdapter(new ZanpeopleAdapter(ZanPepoleActivity.this, data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.zan_pepole_iv_back);
        listView= (ListView) findViewById(R.id.zan_pepole_listview);
        title= (TextView) findViewById(R.id.zan_pepole_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fid = data.getData().get(position).getUid();
        Intent intent=new Intent(this,FriendDataActivity.class);
        intent.putExtra("fid",fid);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
