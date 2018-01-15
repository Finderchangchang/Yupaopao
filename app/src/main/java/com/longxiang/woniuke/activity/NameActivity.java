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

public class NameActivity extends AppCompatActivity {
private ImageView ivBack;
    private EditText etName;
    private TextView tvSave;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        title= (TextView) findViewById(R.id.name_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        ivBack= (ImageView) findViewById(R.id.name_iv_back);
        etName= (EditText) findViewById(R.id.name_etName);
        tvSave= (TextView) findViewById(R.id.name_tvSave);
        tvSave.setTextColor(Color.parseColor("#ffffff"));
        Intent intent=getIntent();
        etName.setText(intent.getStringExtra("name"));
        etName.setSelection(etName.getText().length());
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(NameActivity.this);
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
                        intent.putExtra("name",etName.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();
                dialog.show();
            }
        });
    }
}
