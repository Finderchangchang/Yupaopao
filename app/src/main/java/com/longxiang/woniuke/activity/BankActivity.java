package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.BankData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BankActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private EditText etBackcard;
    private EditText etName;
    private TextView tvBackname;
    private Button btnConirm;
    private LinearLayout llbuttons;
    private Button btnEdit;
    private Button btnDelete;
    private String bid;
    private TextView title;
    private ImageView ivka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        setView();
        setListener();
        getbankcard();
    }

    private void setView() {
        ivka= (ImageView) findViewById(R.id.bank_ivka);
        ivBack= (ImageView) findViewById(R.id.bank_iv_back);
        etBackcard= (EditText) findViewById(R.id.bank_et_bankcard);
        etBackcard.setTextColor(Color.parseColor("#000000"));
        etName= (EditText) findViewById(R.id.bank_et_name);
        etName.setTextColor(Color.parseColor("#000000"));
        tvBackname= (TextView) findViewById(R.id.bank_bankname);
        tvBackname.setTextColor(Color.parseColor("#ff1111"));
        btnConirm= (Button) findViewById(R.id.bank_btn);
        llbuttons= (LinearLayout) findViewById(R.id.bank_buttons);
        btnEdit= (Button) findViewById(R.id.bank_btn_eidt);
        btnDelete= (Button) findViewById(R.id.bank_btn_delete);
        btnConirm.setTextColor(Color.parseColor("#ffffff"));
        btnEdit.setTextColor(Color.parseColor("#ffffff"));
        btnDelete.setTextColor(Color.parseColor("#ffffff"));
        title= (TextView) findViewById(R.id.bank_title);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        etBackcard.addTextChangedListener(new EditTitleChangedListener());
        ivBack.setOnClickListener(this);
        btnConirm.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog;
        switch (v.getId()){
            case R.id.bank_iv_back:
                finish();
                break;
            case R.id.bank_btn:
                dialog=new AlertDialog.Builder(BankActivity.this);
                dialog.setMessage("是否绑定?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmbind();
                    }
                }).create();
                dialog.show();
                break;
            case R.id.bank_btn_eidt:
                dialog=new AlertDialog.Builder(BankActivity.this);
                dialog.setMessage("是否修改?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editBankcard();
                    }
                }).create();
                dialog.show();
                break;
            case R.id.bank_btn_delete:
                dialog=new AlertDialog.Builder(BankActivity.this);
                dialog.setMessage("是否删除?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBankcard();
                    }
                }).create();
                dialog.show();
                break;
        }
    }

    private void deleteBankcard() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/deletebankcard");
        params.addBodyParameter("bid",bid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4000":
                        case "4006":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
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

    private void editBankcard() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/addbankcard");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("bankcard",etBackcard.getText().toString());
        params.addBodyParameter("bankname",tvBackname.getText().toString());
        params.addBodyParameter("realname",etName.getText().toString());
        params.addBodyParameter("bid",bid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4000":
                        case "4001":
                        case "4003":
                        case "4005":
                        case "4006":
                        case "4088":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
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

    private void getbankcard() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getbankcard");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getbank", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            BankData data=new Gson().fromJson(result,BankData.class);
                            if(data.getData().size()==0){
                                btnConirm.setVisibility(View.VISIBLE);
                                llbuttons.setVisibility(View.GONE);
                            }else{
                                btnConirm.setVisibility(View.GONE);
                                llbuttons.setVisibility(View.VISIBLE);
                                etBackcard.setText(data.getData().get(0).getBankcard());
                                etName.setText(data.getData().get(0).getRealname());
                                tvBackname.setText(data.getData().get(0).getBankname());
                                bid=data.getData().get(0).getBid();
                                ivka.setVisibility(View.VISIBLE);
                            }
                            break;
                        case "4000":
                        case "4002":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
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
    private void confirmbind() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/addbankcard");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("bankcard",etBackcard.getText().toString());
        params.addBodyParameter("bankname",tvBackname.getText().toString());
        params.addBodyParameter("realname",etName.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("bangding", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4001":
                        case "4003":
                        case "4005":
                        case "4006":
                        case "4088":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                    }
                } catch (JSONException e) {
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
    private void getBanknameByBanknum() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getBanknameByBanknum");
        params.addBodyParameter("bankcard",etBackcard.getText().toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("banknu", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    String bankname = obj.getString("data");
                    tvBackname.setText(bankname);
                    ivka.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
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
    class EditTitleChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(temp.length()<19){
                tvBackname.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(temp.length()==19){
                getBanknameByBanknum();
            }
        }
    };
}
