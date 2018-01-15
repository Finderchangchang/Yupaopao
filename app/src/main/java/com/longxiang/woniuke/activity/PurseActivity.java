package com.longxiang.woniuke.activity;

import android.content.Intent;
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

public class PurseActivity extends AppCompatActivity {

    private TextView tv_yue_name;
    private View sub;
    private TextView title;
    private ImageView back;
    private TextView tv_yue_num;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private TextView tv_jifeng_name,tv_youhuijuan_name;
    private TextView tv_jifen_num,tv_youhuijuan_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purse);
        init();
        getAccount();
    }

    private void init() {
        sub = (View) findViewById(R.id.sub_title_purse);
        title = (TextView) sub.findViewById(R.id.tv_title_name);
        title.setText("我的钱包");
        title.setTextColor(Color.WHITE);
        back = (ImageView) sub.findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_yue_name = (TextView) findViewById(R.id.tv_yue_name);
        tv_yue_name.setTextColor(Color.BLACK);
        tv_yue_num = (TextView) findViewById(R.id.tv_yue_num);
        tv_yue_num.setTextColor(getResources().getColor(R.color.colorWordlight));
        rl01 = (RelativeLayout) findViewById(R.id.purse_rl01);
        rl01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurseActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        tv_jifeng_name = (TextView) findViewById(R.id.tv_jifen_name);
        tv_jifeng_name.setTextColor(Color.BLACK);
        tv_jifen_num = (TextView) findViewById(R.id.tv_jifen_num);
        tv_jifen_num.setTextColor(getResources().getColor(R.color.colorWordlight));
        rl02 = (RelativeLayout) findViewById(R.id.purse_rl02);
        rl02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurseActivity.this,JiFenActivity.class);
                startActivity(intent);
            }
        });
        tv_youhuijuan_name = (TextView) findViewById(R.id.tv_youhuijuan_name);
        tv_youhuijuan_name.setTextColor(Color.BLACK);
        tv_youhuijuan_num = (TextView) findViewById(R.id.tv_youhuijuan_num);
        tv_youhuijuan_num.setTextColor(getResources().getColor(R.color.colorWordlight));
        rl03 = (RelativeLayout) findViewById(R.id.purse_rl03);
        rl03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(PurseActivity.this,YhqActivity.class);
                startActivity(intent);
            }
        });
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
                    tv_yue_num.setText(obj.getString("wallet")+"元");
                    tv_jifen_num.setText(obj.getString("points")+"分");
                    tv_youhuijuan_num.setText(obj.getString("coupon")+"张优惠券");
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
