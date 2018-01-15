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

public class SchoolActivity extends AppCompatActivity {
    private ImageView ivBack;
    private EditText etSchool;
    private TextView tvSave;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        ivBack= (ImageView) findViewById(R.id.school_iv_back);
        title= (TextView) findViewById(R.id.school_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        etSchool= (EditText) findViewById(R.id.school_etSchool);
        tvSave= (TextView) findViewById(R.id.school_tvSave);
        tvSave.setTextColor(Color.parseColor("#ffffff"));
        Intent intent=getIntent();
        etSchool.setText(intent.getStringExtra("school"));
        etSchool.setSelection(etSchool.getText().length());
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(SchoolActivity.this);
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
                        intent.putExtra("school",etSchool.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();
                dialog.show();
            }
        });
    }
}
