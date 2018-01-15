package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.KyYhqAdapter;
import com.longxiang.woniuke.adapter.YhqAdapter;
import com.longxiang.woniuke.bean.KyYhqData;
import com.longxiang.woniuke.bean.YhqData;
import com.longxiang.woniuke.utils.MyApp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class YhqActivity extends AppCompatActivity implements View.OnClickListener{
private ImageView ivBack;
    private TextView etDuihuan;
    private Button btnDuihuan;
    private ListView listView;
    private Button btnHistory;
    private ImageView ivHelp;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yhq);
        setView();
        setListener();
        getMyCoupon();
    }

    private void getMyCoupon() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getMyCoupon");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                KyYhqData data=new Gson().fromJson(result,KyYhqData.class);
                listView.setAdapter(new KyYhqAdapter(YhqActivity.this,data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.yhq_iv_back);
//        ivHelp= (ImageView) findViewById(R.id.yhq_iv_help);
        etDuihuan= (TextView) findViewById(R.id.yhq_et_duihuang);
        btnDuihuan= (Button) findViewById(R.id.yhq_btn_duihuan);
        listView= (ListView) findViewById(R.id.yhq_listview);
        btnHistory= (Button) findViewById(R.id.yhq_btn_lishi);
        title= (TextView) findViewById(R.id.yhq_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btnDuihuan.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
//        ivHelp.setOnClickListener(this);
        btnDuihuan.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.yhq_iv_back:
                finish();
                break;
//            case R.id.yhq_iv_help:
//                intent=new Intent(this,YhqHelpActivity.class);
//                startActivity(intent);
//                break;
            case R.id.yhq_btn_duihuan:
                intent=new Intent(this,DhYhqActivity.class);
                startActivity(intent);
                break;
            case R.id.yhq_btn_lishi:
                intent=new Intent(this,HistoryYhqActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyCoupon();
    }
}
