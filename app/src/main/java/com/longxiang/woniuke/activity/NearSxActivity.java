package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.longxiang.woniuke.R;

public class NearSxActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
private ImageView ivBack;
    private RadioGroup rgsex;
    private RadioGroup rgtime;
    private Button btnConfirm;
    private String sex="";
    private String time="259200";
    private TextView tvTitle;
    private TextView tvGender;
    private TextView tvAppear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_sx);
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.nearsx_iv_back);
        rgsex= (RadioGroup) findViewById(R.id.nearsx_rg01);
        rgtime= (RadioGroup) findViewById(R.id.nearsx_rg02);
        btnConfirm= (Button) findViewById(R.id.nearsx_btn_confirm);
        tvTitle= (TextView) findViewById(R.id.nearsx_title);
        tvGender= (TextView) findViewById(R.id.nearsx_gender);
        tvAppear= (TextView) findViewById(R.id.nearsx_appeartime);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        tvGender.setTextColor(Color.parseColor("#000000"));
        tvAppear.setTextColor(Color.parseColor("#000000"));
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        rgsex.setOnCheckedChangeListener(this);
        rgtime.setOnCheckedChangeListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nearsx_iv_back:
                isSaveAlert();
                break;
            case R.id.nearsx_btn_confirm:
                Intent intent=new Intent();
                intent.putExtra("sex",sex);
                Log.i("nearsxactivity", "onClick: "+sex);
                intent.putExtra("time",time);
                Log.i("nearsxactivity", "onClick: "+time);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    private void isSaveAlert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("蜗牛壳").setMessage("是否保存?").setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sex="";
                time="";
                Intent intent=new Intent();
                intent.putExtra("sex",sex);
                intent.putExtra("time",time);
                setResult(RESULT_OK,intent);
                finish();
            }
        }).setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.putExtra("sex",sex);
                intent.putExtra("time",time);
                setResult(RESULT_OK,intent);
                finish();
            }
        }).create();
        builder.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group==rgsex) {
            switch (checkedId) {
                case R.id.nearsx_rb01:
                    sex="";
                    break;
                case R.id.nearsx_rb02:
                    sex="男";
                    break;
                case R.id.nearsx_rb03:
                    sex="女";
                    break;
            }
        }
        if(group==rgtime){
            switch (checkedId){
                case R.id.nearsx_rb04:
                    time="259200";
                    break;
                case R.id.nearsx_rb05:
                    time="86400";
                    break;
                case R.id.nearsx_rb06:
                    time="900";
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
           isSaveAlert();
        }
        return false;
    }
}
