package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ForgetPswActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private EditText etPhone;
    private EditText etVercode;
    private Button btnVercode;
    private EditText etPassword;
    private Button btnConfirm;
    private String phone;
    private String vercode;
    private String password;
    private TextView title;
    int time=60;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (time<=0){
                        btnVercode.setText("发送验证码");
                        btnVercode.setEnabled(true);
                        time=60;
                    }else{
                        btnVercode.setText(time--+"秒");
                        btnVercode.setEnabled(false);
                        handler.sendEmptyMessageDelayed(0,1000);
                    }
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.forget_back);
        etPhone= (EditText) findViewById(R.id.forget_phone);
        etVercode= (EditText) findViewById(R.id.forget_vercode);
        btnVercode= (Button) findViewById(R.id.forget_btn_vercode);
        btnVercode.setTextColor(Color.parseColor("#ffffff"));
        etPassword= (EditText) findViewById(R.id.forget_password);
        btnConfirm= (Button) findViewById(R.id.forget_btn_confirm);
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        title= (TextView) findViewById(R.id.forget_tv_title);
        title.setTextColor(Color.parseColor("#0BA3EC"));
    }
    public void getData(){
        phone=etPhone.getText().toString();
        vercode=etVercode.getText().toString();
        password=etPassword.getText().toString();
    }
    private void setListener() {
        ivBack.setOnClickListener(this);
        btnVercode.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_back:
                finish();
                break;
            case R.id.forget_btn_confirm:
                changeConfirm();
                break;
            case R.id.forget_btn_vercode:
                getVercode();
                break;
        }
    }

    private void changeConfirm() {
        getData();
        RequestParams params =new RequestParams("http://bubblefish.jbserver.cn/api/users/forgotPassword");
        params.addBodyParameter("mobile",phone);
        params.addBodyParameter("code",vercode);
        params.addBodyParameter("password",password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei321", "onSuccess: "+result);
                try{
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                        case "4005":
                        case "4006":
                        case "4007":
                            Toast.makeText(ForgetPswActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(ForgetPswActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
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

    private void getVercode() {
        getData();
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/api/sendVerCode_forget");
        params.addBodyParameter("mobile",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei456", "onSuccess: "+result);
                try{
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                            Toast.makeText(ForgetPswActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(ForgetPswActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(0);
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
