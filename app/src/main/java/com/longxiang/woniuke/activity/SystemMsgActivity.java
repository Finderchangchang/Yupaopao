package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.SystemMsgAdapter;
import com.longxiang.woniuke.bean.SystemMsgData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class SystemMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private ListView listView;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg);
        setView();
        setListener();
        getSystemMsg();
        setRobMessageReadState();
    }

    private void setRobMessageReadState()
    {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/setRobMessageReadState");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("state","3");
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
    private void getSystemMsg() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getSystemMessage");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("systemmsg", "onSuccess: "+result);
                SystemMsgData data=new Gson().fromJson(result,SystemMsgData.class);
                listView.setAdapter(new SystemMsgAdapter(SystemMsgActivity.this,data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.system_msg_iv_back);
        listView= (ListView) findViewById(R.id.system_msg_listview);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        title= (TextView) findViewById(R.id.system_msg_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
