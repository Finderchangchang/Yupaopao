package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.OfficalAdapter;
import com.longxiang.woniuke.bean.OfficalData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class OfficalActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private ImageView ivBack;
    private TextView title;
    private OfficalData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offical);
        setView();
        setListener();
        getActivitys();
    }

    private void getActivitys() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/index.php/api/index/getActitvty");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                data=new Gson().fromJson(result,OfficalData.class);
                listView.setAdapter(new OfficalAdapter(OfficalActivity.this,data.getData()));
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
        listView= (ListView) findViewById(R.id.offical_listview);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ivBack= (ImageView) findViewById(R.id.offical_iv_back);
        title= (TextView) findViewById(R.id.offical_tv_title_name);
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
        String url= data.getData().get(position).getUrl();
        String title=data.getData().get(position).getTitle();
        Intent intent=new Intent(this,HomeWebViewActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        startActivity(intent);
    }
}
