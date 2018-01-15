package com.longxiang.woniuke.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.longxiang.woniuke.R;

import cn.jpush.android.api.JPushInterface;

public class MsgAlertActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
private ImageView ivBack;
    private Switch swPush;
    private Switch swVoice;
    private Switch swShock;
    private Vibrator vibrator;
    private boolean isPush=true;
    private boolean isVoice=true;
    private boolean isShock=true;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_alert);
        setView();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
        isPush=prefs.getBoolean("push",true);
        isVoice=prefs.getBoolean("voice",true);
        isShock=prefs.getBoolean("shock",true);
        if(isPush){
            swPush.setChecked(true);
        }else{
            swPush.setChecked(false);
        }
        if(isVoice){
            swVoice.setChecked(true);
        }else{
            swVoice.setChecked(false);
        }
        if(isShock){
            swShock.setChecked(true);
        }else{
            swShock.setChecked(false);
        }
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.msgalert_iv_back);
        swPush= (Switch) findViewById(R.id.msgalert_switchPush);
        swVoice= (Switch) findViewById(R.id.msgalert_switchVoice);
        swShock= (Switch) findViewById(R.id.msgalert_switchShock);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        title= (TextView) findViewById(R.id.msgalert_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        swPush.setOnCheckedChangeListener(this);
        swVoice.setOnCheckedChangeListener(this);
        swShock.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView==swPush){
            if(swPush.isChecked()){
                editor.putBoolean("push",true);
                editor.commit();
                JPushInterface.resumePush(getApplicationContext());
            }else {
                editor.putBoolean("push",false);
                editor.commit();
                JPushInterface.stopPush(getApplicationContext());
            }
        }
        if(buttonView==swVoice){
            if(swVoice.isChecked()){
                editor.putBoolean("voice",true);
                editor.commit();
            }else{
                editor.putBoolean("voice",false);
                editor.commit();
            }
        }
        if(buttonView==swShock){
            if(swShock.isChecked()){
                editor.putBoolean("shock",true);
                editor.commit();
                vibrator.vibrate(new long[]{300,500},-1);
            }else{
                editor.putBoolean("shock",false);
                editor.commit();
                vibrator.cancel();
            }
        }
    }
}
