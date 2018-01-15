package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.BlacklistAdapter;
import com.longxiang.woniuke.bean.BlacklistData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BlacklistActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
private ImageView ivBack;
    private ListView listView;
    private TextView title;
    private BlacklistData data;
    private String fid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        setView();
        setListener();
        getBlackList();
    }

    private void getBlackList() {
        RequestParams param=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getBlackList");
        param.addBodyParameter("uid", MyApp.uid);
        x.http().post(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                data=new Gson().fromJson(result,BlacklistData.class);
                listView.setAdapter(new BlacklistAdapter(getApplicationContext(),data.getData()));
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
        ivBack= (ImageView) findViewById(R.id.blacklist_iv_back);
        listView= (ListView) findViewById(R.id.blacklist_listview);
        title= (TextView) findViewById(R.id.blacklist_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fid=data.getData().get(position).getUid();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("您确定要将此人移除黑名单吗?").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelBlackState();
                dialog.dismiss();
            }
        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        builder.show();
    }

    private void cancelBlackState() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/cancelBlackState");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("fuid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            getBlackList();
                            break;
                        case "4000":
                        case "4001":
                        case "4003":
                        case "4004":
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
}
