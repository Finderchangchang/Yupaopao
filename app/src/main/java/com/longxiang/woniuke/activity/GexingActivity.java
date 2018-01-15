package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;

public class GexingActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private EditText etGexing;
    private TextView tvSave;
    private TextView tvCount;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gexing);
        setView();
        Intent intent=getIntent();
        setListener();
        etGexing.setText(intent.getStringExtra("gexing"));
        etGexing.setSelection(etGexing.getText().length());
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.gexing_iv_back);
        etGexing= (EditText) findViewById(R.id.gexing_etGexing);
        tvSave= (TextView) findViewById(R.id.gexing_tvSave);
        tvCount= (TextView) findViewById(R.id.gexing_tvCount);
        title= (TextView) findViewById(R.id.gexing_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvSave.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        etGexing.addTextChangedListener(new EditTitleChangedListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gexing_iv_back:
                finish();
                break;
            case R.id.gexing_tvSave:
                AlertDialog.Builder dialog=new AlertDialog.Builder(GexingActivity.this);
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
                        intent.putExtra("gexing",etGexing.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();
                dialog.show();
                break;
        }
    }

    class EditTitleChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvCount.setText("("+s.length()+"/22)");
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (temp.length()==10) {
                Toast.makeText(GexingActivity.this, "字数已经达到了限制！", Toast.LENGTH_LONG).show();
            }
        }
    };
}
