package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private ImageView ivHelp;
    private TextView tvMoney;
    private Button btnCz;
    private Button btnTx;
    private String money;
    private TextView title;
    private TextView yuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setView();
        setListener();
        getAccount();
    }

    private void getBasicTime() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/index/getBasicTime");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    String date = obj.getString("data");
                    if(date.equals("2")||date.equals("6")){
                        btnTx.setClickable(true);
                        Intent intent=new Intent(AccountActivity.this,TixianActivity.class);
                        startActivity(intent);
                    }else{
                        btnTx.setClickable(false);
                        btnTx.setText("周二、周六可提现");
                        Toast.makeText(getApplicationContext(),"当前日期不可提现",Toast.LENGTH_SHORT).show();
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
        ivBack= (ImageView) findViewById(R.id.account_iv_back);
//        ivHelp= (ImageView) findViewById(R.id.account_iv_dian);
        tvMoney= (TextView) findViewById(R.id.account_tv_money);
        tvMoney.setTextColor(Color.parseColor("#F8A01A"));
        btnCz= (Button) findViewById(R.id.account_btn_chongzhi);
        btnTx= (Button) findViewById(R.id.account_btn_tixian);
        title= (TextView) findViewById(R.id.account_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btnCz.setTextColor(Color.parseColor("#ffffff"));
        yuan= (TextView) findViewById(R.id.account_tv_yuan);
        yuan.setTextColor(Color.parseColor("#F8A01A"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
//        ivHelp.setOnClickListener(this);
        btnCz.setOnClickListener(this);
        btnTx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_iv_back:
                finish();
                break;
//            case R.id.account_iv_dian:
//                break;
            case R.id.account_btn_chongzhi:
                Intent intent1=new Intent(this,ChongzhiActivity.class);
                intent1.putExtra("money",money);
                startActivity(intent1);
                break;
            case R.id.account_btn_tixian:
                getBasicTime();
                break;
        }
    }
    private void getAccount() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmywallet");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei231",result);
                try {
                    JSONObject json =new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    money=obj.getString("wallet");
                    tvMoney.setText(money);
                }catch (Exception e){
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

    @Override
    protected void onRestart() {
        super.onRestart();
        getAccount();
    }
}
