package com.longxiang.woniuke.activity;

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
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ModifyPswActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private EditText etOldpsw;
    private EditText etNewpsw;
    private EditText etReNewpsw;
    private TextView tvForget;
    private Button btnConfirm;
    private String oldpsw;
    private String newpsw;
    private String renewpas;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.modifypsw_iv_back);
        etOldpsw= (EditText) findViewById(R.id.modifypsw_oldpsw);
        etNewpsw= (EditText) findViewById(R.id.modifypsw_newpsw);
        etReNewpsw= (EditText) findViewById(R.id.modifypsw_newrepsw);
        tvForget= (TextView) findViewById(R.id.modifypsw_tvForgetpsw);
        btnConfirm= (Button) findViewById(R.id.modifypsw_btn_confirm);
        title= (TextView) findViewById(R.id.modifypsw_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));

    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modifypsw_iv_back:
                finish();
                break;
            case R.id.modifypsw_tvForgetpsw:
                Intent intent=new Intent(this,ForgetPswActivity.class);
                startActivity(intent);
                break;
            case R.id.modifypsw_btn_confirm:
                modifyPsw();
                break;
        }
    }
    public void getData(){
        oldpsw=etOldpsw.getText().toString();
        newpsw=etNewpsw.getText().toString();
        renewpas=etReNewpsw.getText().toString();
    }
    private void modifyPsw() {
        getData();
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/pwdEdit");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("password",oldpsw);
        params.addBodyParameter("npwd1",newpsw);
        params.addBodyParameter("npwd2",renewpas);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei666", "onSuccess: "+result);
                try{
                    JSONObject json =new JSONObject(result);
                    switch (json.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                            Toast.makeText(getApplicationContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(getApplicationContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
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
}
