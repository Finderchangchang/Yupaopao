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
import com.longxiang.woniuke.adapter.JobAdapter;
import com.longxiang.woniuke.bean.JobData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class JobActivity extends AppCompatActivity {
private ImageView ivBack;
    private ListView listView;
    private JobData data;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        getJoblist();
        ivBack= (ImageView) findViewById(R.id.job_iv_back);
        title= (TextView) findViewById(R.id.job_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        listView= (ListView) findViewById(R.id.job_listview);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("job",data.getData().get(position).getOccupat());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private void getJoblist() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getoccupation");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                data=new Gson().fromJson(result,JobData.class);
                listView.setAdapter(new JobAdapter(JobActivity.this,data.getData()));
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
}
