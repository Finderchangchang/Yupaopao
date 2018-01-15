package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class JiFenActivity extends AppCompatActivity {
private ImageView ivBack;
    private TextView tvJf;
    private TextView title;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_fen);
        ivBack= (ImageView) findViewById(R.id.jifen_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvJf= (TextView) findViewById(R.id.jifen_tv_much);
        title= (TextView) findViewById(R.id.jifen_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvJf.setTextColor(Color.parseColor("#F8A01A"));
        tv= (TextView) findViewById(R.id.jifen_tv);
        tv.setTextColor(Color.parseColor("#000000"));
        getAccount();
    }
    private void getAccount() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmywallet");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei231",result);
                try {
                    JSONObject json =new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    tvJf.setText(obj.getString("points"));
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
