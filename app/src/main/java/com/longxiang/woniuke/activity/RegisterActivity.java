package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private View sub;
    private TextView title;
    private ImageView back;
    private Button bt;
    private CheckBox cb;
    private TextView xieyi;
    private EditText etPhone;
    private EditText etVercode;
    private TextView tvVercode;
    private EditText etPsw;
    private String phone;
    private String vercode;
    private String password;
    public static RegisterActivity instance;
    int time=60;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (time<=0){
                        tvVercode.setText("发送验证码");
                        tvVercode.setEnabled(true);
                        time=60;
                    }else{
                        tvVercode.setText(time--+"秒");
                        tvVercode.setEnabled(false);
                        handler.sendEmptyMessageDelayed(0,1000);
                    }
                    break;
            }

        }
    };
    private String openid;
    private String nickname;
    private String headimgurl;
    private int sex;
    private LinearLayout pswlayout;
    private String device_token;
    private SharedPreferences.Editor editor;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        instance=this;
        init();
        Intent intent=getIntent();
        if(intent.getStringExtra("type")!=null){
            type=intent.getStringExtra("type");
        }
        if(intent.getStringExtra("openid")!=null) {
            openid=intent.getStringExtra("openid");
            pswlayout.setVisibility(View.INVISIBLE);
            title.setText("绑定手机");
            bt.setText("进入蜗牛壳");
        }else{
            pswlayout.setVisibility(View.VISIBLE);
            title.setText("注册");
            bt.setText("下一步");
        }
        if(intent.getStringExtra("nickname")!=null){
            nickname=intent.getStringExtra("nickname");
        }
        if(intent.getStringExtra("headimgurl")!=null){
            headimgurl=intent.getStringExtra("headimgurl");
        }
        if(intent.getStringExtra("device_token")!=null){
            device_token=intent.getStringExtra("device_token");
        }
        sex=intent.getIntExtra("sex",-1);
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
        setListener();
    }
    public void getData(){
        phone=etPhone.getText().toString();
        vercode=etVercode.getText().toString();
        password=etPsw.getText().toString();
    }
    private void setListener() {
        tvVercode.setOnClickListener(this);
        xieyi.setOnClickListener(this);
    }

    private void init() {
        sub = (View) findViewById(R.id.sub_title_register);
        title = (TextView) sub.findViewById(R.id.tv_title_name);
        title.setText("注册");
        title.setTextColor(Color.WHITE);
        back = (ImageView) findViewById(R.id.iv_back);
        pswlayout= (LinearLayout) findViewById(R.id.regist_password_layout);
        etPhone= (EditText) findViewById(R.id.et_phonenum);
        etVercode= (EditText) findViewById(R.id.et_yzword);
        etPsw= (EditText) findViewById(R.id.et_password);
        tvVercode= (TextView) findViewById(R.id.seedyzword);
        tvVercode.setTextColor(Color.parseColor("#ffffff"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xieyi = (TextView) findViewById(R.id.tv_xieyi_register);
        xieyi.setTextColor(getResources().getColor(R.color.colorWordxieyi));
        bt = (Button) findViewById(R.id.bt_register);
        bt.setTextColor(Color.WHITE);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                if(openid==null) {
                    Intent intent = new Intent(RegisterActivity.this, RegistMsgActivity.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("vercode", vercode);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }else{
                    bindingMobilelogin();
                }
            }
        });
        cb = (CheckBox) findViewById(R.id.cb_register);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    bt.setClickable(true);
                }else {
                    bt.setClickable(false);
                }
            }
        });

    }

    private void bindingMobilelogin() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/bindingMobile");
        params.addBodyParameter("mobile",phone);
        params.addBodyParameter("device_token",device_token);
        params.addBodyParameter("code",vercode);
        params.addBodyParameter("openid",openid);
        params.addBodyParameter("type",type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("bingdingmobilelogin", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            JSONObject obj1=obj.getJSONObject("data");
                            MyApp.uid=obj1.getString("uid");
                            MyApp.token=obj1.getString("r_token");
                            editor.putString("uid",MyApp.uid);
                            editor.putString("r_token",MyApp.token);
                            editor.commit();
                            MainActivity.instance.finish();
                            Intent intent =new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "4333":
                            intent = new Intent(RegisterActivity.this, RegistMsgActivity.class);
                            intent.putExtra("type",type);
                            intent.putExtra("phone", phone);
                            intent.putExtra("vercode", vercode);
                            intent.putExtra("nickname",nickname);
                            intent.putExtra("headimgurl",headimgurl);
                            intent.putExtra("sex",sex);
                            intent.putExtra("openid",openid);
                            intent.putExtra("device_token",device_token);
                            startActivity(intent);
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

    @Override
    public void onClick(View v) {
        getData();
        switch (v.getId()){
            case R.id.seedyzword:
                if(openid==null) {
                    sendVercode();
                }else{
                    bindsendVercode();
                }
                break;
            case R.id.tv_xieyi_register:
                Intent intent=new Intent(this,AgreementActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void bindsendVercode() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/api/sendVerCode_forget");
        params.addBodyParameter("mobile",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("registervercode", "onSuccess: "+result+"1111111111");
                try{
                    JSONObject obj =new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4006":
                        case "4007":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
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

    private void sendVercode() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/api/sendVerCode");
        params.addBodyParameter("mobile",phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("registervercode", "onSuccess: "+result+"222222222");
                try{
                    JSONObject obj =new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4006":
                        case "4007":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
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
