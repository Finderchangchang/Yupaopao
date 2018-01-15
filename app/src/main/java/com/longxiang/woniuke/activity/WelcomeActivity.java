package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import de.greenrobot.event.EventBus;

public class WelcomeActivity extends AppCompatActivity {
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
                finish();
            }
        },3000);
    }
}
