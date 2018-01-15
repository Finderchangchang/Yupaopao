package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.OrderRecordAdapter;
import com.longxiang.woniuke.bean.OrderRecorderData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class OrderRecordActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView ivBack;
    private ListView listView;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record);
        setView();
        setListener();
        getOrderRecord();
    }

    private void getOrderRecord() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getOrderRecord");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                OrderRecorderData data=new Gson().fromJson(result,OrderRecorderData.class);
                listView.setAdapter(new OrderRecordAdapter(OrderRecordActivity.this,data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.order_record_iv_back);
        listView= (ListView) findViewById(R.id.order_record_listview);
        title= (TextView) findViewById(R.id.order_record_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
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

    }
}
