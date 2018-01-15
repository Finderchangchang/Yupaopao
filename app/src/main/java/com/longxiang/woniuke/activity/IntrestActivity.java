package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.IntrestAdapter;
import com.longxiang.woniuke.bean.IntrestData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class IntrestActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    public static EditText editText;
    private GridView gridView;
    private TextView tvSave;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrest);
        setView();
        setListener();
        getIntrest();
    }

    private void getIntrest() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getinterest");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                IntrestData data=new Gson().fromJson(result,IntrestData.class);
                gridView.setAdapter(new IntrestAdapter(IntrestActivity.this,data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.intrest_iv_back);
        editText= (EditText) findViewById(R.id.intrest_etIntrest);
        gridView= (GridView) findViewById(R.id.intrest_gridview);
        tvSave= (TextView) findViewById(R.id.intrest_tvSave);
        title= (TextView) findViewById(R.id.intrest_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvSave.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.intrest_iv_back:
                finish();
                break;
            case R.id.intrest_tvSave:
                AlertDialog.Builder dialog=new AlertDialog.Builder(IntrestActivity.this);
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
                        intent.putExtra("intrest",editText.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();
                dialog.show();
                break;
        }
    }
}
