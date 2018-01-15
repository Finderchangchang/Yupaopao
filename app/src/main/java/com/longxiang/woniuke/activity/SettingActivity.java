package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.DataCleanManager;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private RelativeLayout rl04;
    private RelativeLayout rl05;
    private RelativeLayout rl06;
    private RelativeLayout rl07;
    private RelativeLayout rl08;
    private TextView tvBang;
    private TextView title;
    private TextView tvhuancun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        try {
            setView();
            setListener();
            getIdstate();
            tvhuancun.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getIdstate() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getidstate");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("settingactivity", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            tvBang.setVisibility(View.VISIBLE);
                            tvBang.setText(obj.getString("msg"));
                            rl01.setClickable(false);
                            tvBang.setTextColor(Color.parseColor("#1EBAF3"));
                            break;
                        case "2001":
                            tvBang.setVisibility(View.VISIBLE);
                            tvBang.setText(obj.getString("msg"));
                            rl01.setClickable(false);
                            tvBang.setTextColor(Color.parseColor("#ff1111"));
                            break;
                        case "2002":
                            tvBang.setVisibility(View.VISIBLE);
                            tvBang.setText(obj.getString("msg"));
                            rl01.setClickable(true);
                            tvBang.setTextColor(Color.parseColor("#ff1111"));
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

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.setting_iv_back);
        rl01= (RelativeLayout) findViewById(R.id.setting_rl02);
        rl02= (RelativeLayout) findViewById(R.id.setting_rl03);
        rl03= (RelativeLayout) findViewById(R.id.setting_rl04);
        rl04= (RelativeLayout) findViewById(R.id.setting_rl05);
        rl05= (RelativeLayout) findViewById(R.id.setting_rl06);
        rl06= (RelativeLayout) findViewById(R.id.setting_rl07);
        rl07= (RelativeLayout) findViewById(R.id.setting_rl08);
        rl08= (RelativeLayout) findViewById(R.id.setting_rl01);
        tvBang= (TextView) findViewById(R.id.setting_tvBangding);
        title= (TextView) findViewById(R.id.setting_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvhuancun= (TextView) findViewById(R.id.setting_tv_huancun);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        rl01.setOnClickListener(this);
        rl02.setOnClickListener(this);
        rl03.setOnClickListener(this);
        rl04.setOnClickListener(this);
        rl05.setOnClickListener(this);
        rl06.setOnClickListener(this);
        rl07.setOnClickListener(this);
        rl08.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.setting_iv_back:
                finish();
                break;
            case R.id.setting_rl02:
                intent=new Intent(this,AuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_rl03:
                intent=new Intent(this,ModifyPswActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_rl04:
                intent=new Intent(this,BankActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_rl05:
                intent=new Intent(this,InvisibleActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_rl06:
                intent=new Intent(this,MsgAlertActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_rl07:
                DataCleanManager.clearAllCache(this);
                Toast.makeText(getApplicationContext(),"清理成功",Toast.LENGTH_SHORT).show();
                tvhuancun.setText("0.00K");
                break;
            case R.id.setting_rl08:
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("蜗牛壳")
                        .setMessage("确认退出吗？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        RegistMsgActivity.editor.putString("uid",null);
                        RegistMsgActivity.editor.putString("r_token",null);
                        RegistMsgActivity.editor.commit();
                        Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }).create().show();
                break;
            case R.id.setting_rl01:
                intent=new Intent(this,BlacklistActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        getIdstate();
        super.onResume();
    }
}
