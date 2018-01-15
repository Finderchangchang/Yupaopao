package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import de.greenrobot.event.EventBus;

public class ReasonActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private EditText et;
    private Button confirm;
    private String oid;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);
        Intent intent=getIntent();
        oid=intent.getStringExtra("oid");
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.reason_iv_back);
        et= (EditText) findViewById(R.id.reason_et);
        confirm= (Button) findViewById(R.id.reason_confirm);
        confirm.setTextColor(Color.parseColor("#ffffff"));
        title= (TextView) findViewById(R.id.reason_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reason_iv_back:
                finish();
                break;
            case R.id.reason_confirm:
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("蜗牛壳")
                        .setMessage("确认提交吗？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmReason();
                    }
                }).create().show();
                break;
        }
    }

    private void confirmReason() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/refuseGeneralOrder");
        params.addBodyParameter("oid",oid);
        params.addBodyParameter("refuseReason",et.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("reasonactivity", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            String json="refresh";
                            EventBus.getDefault().post(json);
                            break;
                        case "4000":
                        case "4003":
                        case "4004":
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
