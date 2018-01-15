package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import com.longxiang.woniuke.adapter.XmAdapter;
import com.longxiang.woniuke.bean.DsData;
import com.longxiang.woniuke.bean.XmData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SqxmActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
private ImageView ivBack;
    private TextView tvTitle;
    private ListView listView;
    private XmData data;
    public static SqxmActivity instance;
    private List<DsData> dslist=new ArrayList<>();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    getXm();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqxm);
        instance=this;
        setView();
        Intent intent=getIntent();
        String flag=intent.getStringExtra("flag");
        if(flag.equals("1")){
            tvTitle.setText("大神资质");
            tvTitle.setTextColor(Color.parseColor("#ffffff"));
        }else{
            tvTitle.setText("申请项目");
            tvTitle.setTextColor(Color.parseColor("#ffffff"));
        }
        setListener();
        getGodstate();

    }
    private void getGodstate() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getgodstate");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ewqewq", "onSuccess: "+result);
                try {
                    JSONObject json =new JSONObject(result);
                    JSONArray array=json.getJSONArray("data");
                    for (int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        String state = obj.getString("state");
                        String xmid = obj.getString("jnid");
                        String bgid=obj.getString("bgid");
                        dslist.add(new DsData(bgid,xmid,state));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
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
    private void getXm() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getAlltype");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getxmxmxm", "onSuccess: "+result);
                data=new Gson().fromJson(result,XmData.class);
                listView.setAdapter(new XmAdapter(SqxmActivity.this,dslist,data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.sqxm_iv_back);
        listView= (ListView) findViewById(R.id.sqxm_listview);
        tvTitle= (TextView) findViewById(R.id.sqxm_tv_title);

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

        String pid=data.getData().get(position).getId();
        String title=data.getData().get(position).getTitle();
//        String bgid=dslist.get(position).getBgid();
//        Log.i("sqxmactivity", "onItemClick: "+bgid);
        Intent intent=new Intent(this,SqdsLastActivity.class);
//        intent.putExtra("bgid",bgid);
        intent.putExtra("pid",pid);
        intent.putExtra("title",title);
        startActivity(intent);
    }
}
