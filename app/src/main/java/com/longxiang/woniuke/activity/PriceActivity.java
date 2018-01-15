package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;

public class PriceActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private EditText editText;
    private TextView tvsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        setView();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.price_iv_back);
        editText= (EditText) findViewById(R.id.price_etGexing);
        tvsave= (TextView) findViewById(R.id.price_tvSave);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvsave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.price_iv_back:
                finish();
                break;
            case R.id.price_tvSave:
                AlertDialog.Builder dialog=new AlertDialog.Builder(PriceActivity.this);
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
                        intent.putExtra("price",editText.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();
                dialog.show();
                break;
        }
    }
}
