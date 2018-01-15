package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AmdsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView money;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private TextView title;
    private TextView tv01;
    private TextView tv02;
    private TextView tv03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amds);
        setView();
        setListener();
        getorderamount();
    }

    private void getorderamount() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getorderamount");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ordermoney", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    JSONObject obj1=obj.getJSONObject("data");
                    String amount=obj1.getString("amount") ;
                    money.setText("¥" + Double.valueOf(amount));
                    if(obj.getString("retcode").equals("4001")){
                        money.setText("¥0.0");
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
        ivBack= (ImageView) findViewById(R.id.am_ds_iv_back);
        money= (TextView) findViewById(R.id.am_ds_money);
        money.setTextColor(Color.parseColor("#000000"));
        rl01= (RelativeLayout) findViewById(R.id.am_ds_rl01);
        rl02= (RelativeLayout) findViewById(R.id.am_ds_rl02);
        rl03= (RelativeLayout) findViewById(R.id.am_ds_rl03);
        title= (TextView) findViewById(R.id.am_ds_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tv01= (TextView) findViewById(R.id.am_ds_tv01);
        tv02= (TextView) findViewById(R.id.am_ds_tv02);
        tv03= (TextView) findViewById(R.id.am_ds_tv03);
        tv01.setTextColor(Color.parseColor("#000000"));
        tv02.setTextColor(Color.parseColor("#000000"));
        tv03.setTextColor(Color.parseColor("#000000"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        rl01.setOnClickListener(this);
        rl02.setOnClickListener(this);
        rl03.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.am_ds_iv_back:
                finish();
                break;
            case  R.id.am_ds_rl01:
                intent=new Intent(this,SqxmActivity.class);
                intent.putExtra("flag","1");
                startActivity(intent);
                break;
            case  R.id.am_ds_rl02:
                intent=new Intent(this,OrderRecordActivity.class);
                startActivity(intent);
                break;
            case  R.id.am_ds_rl03:
                intent=new Intent(this,JdSettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
