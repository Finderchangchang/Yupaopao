package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;

public class SqdsIntroActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private TextView tvTijiao;
    private EditText etIntro;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqds_intro);
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.sqdsintro_iv_back);
        tvTijiao= (TextView) findViewById(R.id.sqdsintro_tijiao);
        etIntro= (EditText) findViewById(R.id.sqdsintro_etIntro);
        title= (TextView) findViewById(R.id.sqdsintro_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvTijiao.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvTijiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sqdsintro_iv_back:
                finish();
                break;
            case R.id.sqdsintro_tijiao:
                AlertDialog.Builder dialog=new AlertDialog.Builder(SqdsIntroActivity.this);
                dialog.setMessage("是否保存?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.putExtra("lvintro",etIntro.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();
                dialog.show();
                break;
        }
    }
}
