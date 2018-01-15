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
import com.longxiang.woniuke.adapter.FriendAdapter;
import com.longxiang.woniuke.bean.AddFriendData;
import com.longxiang.woniuke.bean.FriendData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class NewfriendActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
private ImageView ivBack;
    private ListView listView;
    private TextView title;
    private AddFriendData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);
        setView();
        setListener();
        getNewfriend();
    }

    private void getNewfriend() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/newfriendList");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("newfriend", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4002":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
//                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            data=new Gson().fromJson(result,AddFriendData.class);
                            listView.setAdapter(new FriendAdapter(NewfriendActivity.this,data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.newfriend_iv_back);
        listView= (ListView) findViewById(R.id.newfriend_listview);
        title= (TextView) findViewById(R.id.newfriend_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fid=data.getData().get(position).getUid();
        Intent intent=new Intent(this,FriendDataActivity.class);
        intent.putExtra("fid",fid);
        startActivity(intent);
    }
}
