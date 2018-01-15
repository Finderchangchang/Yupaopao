package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.DashangpeopleAdapter;
import com.longxiang.woniuke.bean.DashangData;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class DashangPeopleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private ImageView ivBack;
    private String dmid;
    private DashangData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashang_people);
        Intent intent=getIntent();
        dmid=intent.getStringExtra("dmid");
        setView();
        setListener();
        getRewardedList();
    }

    private void getRewardedList() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getRewardedList");
        params.addBodyParameter("dmid",dmid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("dashangpeople", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            data=new Gson().fromJson(result,DashangData.class);
                            listView.setAdapter(new DashangpeopleAdapter(DashangPeopleActivity.this,data.getData()));
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

    private void setView() {
        listView= (ListView) findViewById(R.id.dashang_pepole_listview);
        ivBack= (ImageView) findViewById(R.id.dashang_pepole_iv_back);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dashang_pepole_iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fid = data.getData().get(position).getUid();
        Intent intent=new Intent(this,FriendDataActivity.class);
        intent.putExtra("fid",fid);
        startActivity(intent);
    }
}
