package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;

public class CompletedOrderActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private ImageView icon;
    private TextView tvname;
    private Button btnChat;
    private TextView tvMoney;
    private TextView tvZmoney;
    private TextView tvPtmoney;
    private ImageView ivType;
    private TextView tvType;
    private TextView tvTimeAndCount;
    private RatingBar ratingBar;
    private TextView tvPingjia;
    private String name;
    private String typename;
    private String typeimg;
    private String time;
    private String times;
    private String mobile;
    private String level;
    private String headpic;
    private String oid;
    private LinearLayout lljyf;
    private String amount;
    private String expense;
    private String ucmoney;
    private String comment;
    private TextView title;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if(!bguid.equals(MyApp.uid)){
                        lljyf.setVisibility(View.GONE);
                        tvMoney.setText(Double.valueOf(amount)+"元");
                        tvZmoney.setText(Double.valueOf(Double.valueOf(amount)+Double.valueOf(ucmoney))+"元"+"(优惠金额:"+Double.valueOf(ucmoney)+"元)");
                        ratingBar.setRating(Float.valueOf(level));
                        tvPingjia.setText(comment);
                    }else{
                        lljyf.setVisibility(View.VISIBLE);
                        tvMoney.setText(Double.valueOf(Double.valueOf(amount)+Double.valueOf(ucmoney)-Double.valueOf(expense))+"元");
                        tvZmoney.setText(Double.valueOf(Double.valueOf(amount)+Double.valueOf(ucmoney))+"元");
                        tvPtmoney.setText("-"+Double.valueOf(expense)+"元");
                        ratingBar.setRating(Float.valueOf(level));
                        tvPingjia.setText(comment);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private String bguid;
    private String unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_order);
        Intent intent=getIntent();
        headpic=intent.getStringExtra("head_pic");
        name=intent.getStringExtra("name");
        typename=intent.getStringExtra("typename");
        typeimg=intent.getStringExtra("typeimg");
        time=intent.getStringExtra("time");
        times=intent.getStringExtra("times");
        mobile=intent.getStringExtra("mobile");
        oid=intent.getStringExtra("oid");
        bguid=intent.getStringExtra("bguid");
        unit=intent.getStringExtra("unit");
        setView();
        setData();
        setListener();
        getOrderLevel();
    }

    private void getOrderLevel() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getOrderLevel");
        params.addBodyParameter("oid",oid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getOrderLevel", "onSuccess: "+result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    amount=obj.getString("amount");
                    expense=obj.getString("expense");
                    ucmoney=obj.getString("ucmoney");
                    level= obj.getString("level");
                    comment=obj.getString("comment");
                    handler.sendEmptyMessage(1);
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


    private void setView() {
        ivBack= (ImageView) findViewById(R.id.completed_order_iv_back);
        icon= (ImageView) findViewById(R.id.completed_order_iv_icon);
        tvname= (TextView) findViewById(R.id.completed_order_tvname);
        btnChat= (Button) findViewById(R.id.completed_order_btnchat);
        tvMoney= (TextView) findViewById(R.id.completed_order_tvmoney);
        tvZmoney= (TextView) findViewById(R.id.completed_order_tvAmount);
        tvPtmoney= (TextView) findViewById(R.id.completed_order_tvtax);
        ivType= (ImageView) findViewById(R.id.completed_order_iv_type);
        tvType= (TextView) findViewById(R.id.completed_order_tv_typename);
        tvTimeAndCount= (TextView) findViewById(R.id.completed_order_tvtime);
        ratingBar= (RatingBar) findViewById(R.id.completed_order_ratingbar);
        tvPingjia= (TextView) findViewById(R.id.completed_order_tvEvaluate);
        lljyf= (LinearLayout) findViewById(R.id.completed_order_lljyf);
        title= (TextView) findViewById(R.id.completed_order_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }
    private void setData() {
        x.image().bind(icon,headpic);
        tvname.setText(name);
        x.image().bind(ivType,typeimg);
        tvType.setText(typename);
        tvTimeAndCount.setText(time+"  "+times+unit);
    }
    private void setListener() {
        ivBack.setOnClickListener(this);
        btnChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.completed_order_iv_back:
                finish();
                break;
            case R.id.completed_order_btnchat:
                RongIM.getInstance().startPrivateChat(this,mobile,name);
                break;
        }
    }
}
