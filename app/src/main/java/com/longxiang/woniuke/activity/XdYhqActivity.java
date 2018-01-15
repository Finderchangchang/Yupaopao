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
import com.longxiang.woniuke.adapter.KyYhqAdapter;
import com.longxiang.woniuke.bean.KyYhqData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class XdYhqActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
private ImageView ivBack;
    private ListView listView;
    private String type;
    private KyYhqData data;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xd_yhq);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        setView();
        setLisnter();
        getCouponOn();
    }

    private void getCouponOn() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getCouponOn");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("type",type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("654123", "onSuccess: "+result);
                data=new Gson().fromJson(result,KyYhqData.class);
                switch (data.getRetcode()){
                    case 2000:
                        listView.setAdapter(new KyYhqAdapter(XdYhqActivity.this,data.getData()));
                        break;
                    case 4001:
                        Toast.makeText(getApplicationContext(),data.getMsg(),Toast.LENGTH_SHORT).show();
                        break;
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
        ivBack= (ImageView) findViewById(R.id.xdyhq_iv_back);
        listView= (ListView) findViewById(R.id.xdyhq_listview);
        title= (TextView) findViewById(R.id.xdyhq_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setLisnter() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String money=data.getData().get(position).getMoney();
            String ucid=data.getData().get(position).getUcid();
            Intent intent=new Intent();
            intent.putExtra("money",money);
            intent.putExtra("ucid",ucid);
            setResult(480,intent);
            finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
