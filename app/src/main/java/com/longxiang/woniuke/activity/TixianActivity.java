package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.BankData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class TixianActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private EditText et;
    private Button btn;
    private String bid;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian);
        setView();
        setListener();
        getbankcard();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.tixian_iv_back);
        et= (EditText) findViewById(R.id.tixian_et);
        btn= (Button) findViewById(R.id.tixian_btn);
        title= (TextView) findViewById(R.id.tixian_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btn.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btn.setOnClickListener(this);
    }
    private void getbankcard() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getbankcard");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getbank", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            BankData data=new Gson().fromJson(result,BankData.class);
                            bid=data.getData().get(0).getBid();
                            break;
                        case "4000":
                        case "4002":
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tixian_iv_back:
                finish();
                break;
            case R.id.tixian_btn:
                if(et.getText().toString().equals("")||Integer.parseInt(et.getText().toString())<=0||Integer.parseInt(et.getText().toString())%10!=0){
                    Toast.makeText(TixianActivity.this,"提现金额需要是10的倍数",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TixianActivity.this);
                    dialog.setMessage("确认提现吗?")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tixian();
                        }
                    }).create();
                    dialog.show();
                }
                break;
        }
    }

    private void tixian() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/tixian");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("bid",bid);
        params.addBodyParameter("money",et.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                        case "4044":
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
}
