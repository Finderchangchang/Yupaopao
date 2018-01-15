package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.QdAdapter;
import com.longxiang.woniuke.bean.QdData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class QdCenterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView ivBack;
    private ListView listView;
    Handler handler=new Handler();
    private boolean isRefresh=false;
    private QdData data;
    private TextView title;
    private int size=0;
    private boolean onitemclick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qd_center);
        setView();
        setListner();
        getRobOrder();
        setRobMessageReadState();
        if(!isRefresh) {
            handler.postDelayed(runnable, 5000);
        }
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.qd_center_iv_back);
        listView= (ListView) findViewById(R.id.qd_center_listview);
        title= (TextView) findViewById(R.id.qd_center_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListner() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String robstate = data.getData().get(position).getRobstate();
        String state = data.getData().get(position).getOrder().getState();
        String msgid=data.getData().get(position).getId();
        //3 待确定 4 已完成 5 已取消 1 已接单
        if(state.equals("0")){
            if(robstate.equals("0")){
                roborder(msgid);
            }
        }
    }
    private void setRobMessageReadState()
    {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/setRobMessageReadState");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("state","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
    private void roborder(String msgid) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/roborder");
        params.addBodyParameter("msgid",msgid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                 JSONObject obj = new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            onitemclick=true;
                            Toast.makeText(getApplicationContext(),obj.getString("msg")+",请等待用户确认",Toast.LENGTH_SHORT).show();
                            getRobOrder();
                            break;
                        case "4001":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
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

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try{
                    handler.postDelayed(this, 3500);
                    getRobOrder();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private void getRobOrder() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getRobOrder");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("runcount", "onSuccess: "+"222");
                data=new Gson().fromJson(result,QdData.class);
                if(data.getData().size()>size||onitemclick==true) {
                    QdAdapter adapter = new QdAdapter(QdCenterActivity.this, data.getData());
                    listView.setAdapter(adapter);
                    size=data.getData().size();
                    Log.i("runcount", "onSuccess: "+"1111");
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
    protected void onPause() {
        super.onPause();
        isRefresh=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRefresh=true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRefresh=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRefresh=true;
        handler.removeCallbacks(runnable);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            isRefresh=true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
