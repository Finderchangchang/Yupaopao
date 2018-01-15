package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.YhqAdapter;
import com.longxiang.woniuke.bean.YhqData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class DhYhqActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private ImageView ivBack;
    private YhqData data;
    private String cid;
    private String money;
    private String days;
    private String price;
    private String type;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dh_yhq);
        setView();
        setListener();
        getAllCoupon();
    }

    private void setView() {
        listView= (ListView) findViewById(R.id.dhyhq_listview);
        ivBack= (ImageView) findViewById(R.id.dhyhq_iv_back);
        title= (TextView) findViewById(R.id.dhyhq_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }
    private void getAllCoupon() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getAllCoupon");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                data=new Gson().fromJson(result,YhqData.class);
                listView.setAdapter(new YhqAdapter(DhYhqActivity.this,data.getData()));
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
    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("蜗牛壳")
                .setMessage("确认兑换吗？")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cid=data.getData().get(position).getCid();
                money=data.getData().get(position).getMoney();
                days=data.getData().get(position).getDays();
                price=data.getData().get(position).getPrice();
                type=data.getData().get(position).getType();
                confirmDh();
            }
        }).create().show();
    }

    private void confirmDh() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/exchangeCoupon");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("cid",cid);
        params.addBodyParameter("money",money);
        params.addBodyParameter("days",days);
        params.addBodyParameter("price",price);
        params.addBodyParameter("type",type);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("dhyhq", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(DhYhqActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4003":
                        case "4004":
                            Toast.makeText(DhYhqActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
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
}
