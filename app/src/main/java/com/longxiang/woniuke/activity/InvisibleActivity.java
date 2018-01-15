package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class InvisibleActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private ImageView online;
    private ImageView offline;
    private RelativeLayout rlonline;
    private RelativeLayout rloffline;
    private TextView title;
    private TextView tvonline;
    private TextView tvoffline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invisible);
        setView();
        setListner();
        getLinestate();
    }

    private void getLinestate() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getlinestate");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("state", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("data").equals("1")){
                        online.setImageResource(R.mipmap.lanquan);
                        offline.setImageResource(R.mipmap.huiquan);
                    }
                    if(obj.getString("data").equals("2")){
                        online.setImageResource(R.mipmap.huiquan);
                        offline.setImageResource(R.mipmap.lanquan);
                    }
                }catch (Exception e){
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

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.invisible_iv_back);
        online= (ImageView) findViewById(R.id.invisible_iv_online);
        offline= (ImageView) findViewById(R.id.invisible_iv_offline);
        rlonline= (RelativeLayout) findViewById(R.id.invisible_rlonline);
        rloffline= (RelativeLayout) findViewById(R.id.invisible_rl_offline);
        title= (TextView) findViewById(R.id.invisible_tv_title_name);
       title.setTextColor(Color.parseColor("#ffffff"));
        tvonline= (TextView) findViewById(R.id.invisible_online);
        tvoffline= (TextView) findViewById(R.id.invisible_offonline);
        tvonline.setTextColor(Color.parseColor("#000000"));
        tvoffline.setTextColor(Color.parseColor("#000000"));
    }

    private void setListner() {
        ivBack.setOnClickListener(this);
        rlonline.setOnClickListener(this);
        rloffline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invisible_rlonline:
                setLinestate01();
                break;
            case R.id.invisible_rl_offline:
                setLinestate02();
                break;
            case R.id.invisible_iv_back:
                finish();
                break;
        }
    }

    private void setLinestate02() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/setlinestate");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("state","2");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("state", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("retcode").equals("2000")) {
                        online.setImageResource(R.mipmap.huiquan);
                        offline.setImageResource(R.mipmap.lanquan);
                        Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
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

    private void setLinestate01() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/setlinestate");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("state","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("state", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("retcode").equals("2000")) {
                        online.setImageResource(R.mipmap.lanquan);
                        offline.setImageResource(R.mipmap.huiquan);
                        Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
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
