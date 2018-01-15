package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;

public class JdDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private ImageView icon;
    private TextView name;
    private TextView gender;
    private TextView level;
    private Button chat;
    private TextView typename;
    private TextView time;
    private TextView money;
    private TextView times;
    private TextView amount;
    private TextView state;
    private Button refuse;
    private Button confirm;
    private Button wancheng;
    private String head_pic;
    private String mname;
    private String mgender;
    private String mage;
    private int mlevel;
    private String mmobile;
    private String mtype;
    private String mtime;
    private String mtimes;
    private String mprice;
    private String mstate;
    private RelativeLayout rlpf;
    private LinearLayout llbuttons;
    private String oid;
    private String bguid;
    private String bgid;
    public static JdDetailActivity instance;
    private String unit;
    private TextView title;
    private LinearLayout llfrienddata;
    private String uid;
    private String mlgbuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jd_detail);
        instance=this;
        Intent intent=getIntent();
        head_pic=intent.getStringExtra("head_pic");
        mname=intent.getStringExtra("name");
        mgender=intent.getStringExtra("gender");
        mage=intent.getStringExtra("age");
        mlevel=intent.getIntExtra("level",0);
        mmobile=intent.getStringExtra("mobile");
        mtype=intent.getStringExtra("type");
        mtime=intent.getStringExtra("time");
        mtimes=intent.getStringExtra("times");
        mprice=intent.getStringExtra("price");
        mstate=intent.getStringExtra("state");
        oid=intent.getStringExtra("oid");
        bguid=intent.getStringExtra("bguid");
        bgid=intent.getStringExtra("bgid");
        unit=intent.getStringExtra("unit");
        uid=intent.getStringExtra("uid");
        mlgbuid=intent.getStringExtra("mlgbuid");
        setView();
        setData();
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.jd_detail_iv_back);
        icon= (ImageView) findViewById(R.id.jd_detail_iv_icon);
        name= (TextView) findViewById(R.id.jd_detail_tvname);
        name.setTextColor(Color.parseColor("#000000"));
        gender= (TextView) findViewById(R.id.jd_detail_tvStarAndAge);
        level= (TextView) findViewById(R.id.jd_detail_tv_pingfen);
        chat= (Button) findViewById(R.id.jd_detail_chat);
        typename= (TextView) findViewById(R.id.jd_detail_tvtype);
        typename.setTextColor(Color.parseColor("#000000"));
        time= (TextView) findViewById(R.id.jd_detail_tvtime);
        time.setTextColor(Color.parseColor("#000000"));
        money= (TextView) findViewById(R.id.jd_detail_tvmoney);
        money.setTextColor(Color.parseColor("#000000"));
        times= (TextView) findViewById(R.id.jd_detail_tvcount);
        times.setTextColor(Color.parseColor("#000000"));
        amount= (TextView) findViewById(R.id.jd_detail_tvheji);
        state= (TextView) findViewById(R.id.jd_detail_tvState);
        refuse= (Button) findViewById(R.id.jd_detail_btn_jujie);
        confirm= (Button) findViewById(R.id.jd_detail_btn_confirm);
        rlpf= (RelativeLayout) findViewById(R.id.jd_detail_rl_pingfen);
        llbuttons= (LinearLayout) findViewById(R.id.jd_detail_ll_buttons);
        wancheng= (Button) findViewById(R.id.jd_detail_btn_wancheng);
        title= (TextView) findViewById(R.id.jd_detail_title_name);
        wancheng.setTextColor(Color.parseColor("#ffffff"));
        refuse.setTextColor(Color.parseColor("#000000"));
        title.setTextColor(Color.parseColor("#ffffff"));
        confirm.setTextColor(Color.parseColor("#ffffff"));
        gender.setTextColor(Color.parseColor("#ffffff"));
        amount.setTextColor(Color.parseColor("#1EBAF3"));
        llfrienddata= (LinearLayout) findViewById(R.id.jd_detail_ll_frienddata);
    }
    private void setData() {
        x.image().bind(icon,head_pic);
        name.setText(mname);
        if(mgender.equals("男")){
            gender.setSelected(true);
            gender.setText(" "+mage+" ");
            gender.setBackgroundResource(R.drawable.conner_man_bg);
        }else{
            gender.setSelected(false);
            gender.setText(" "+mage+" ");
            gender.setBackgroundResource(R.drawable.conner_women_bg);
        }
        if(mlevel==0){
            rlpf.setVisibility(View.GONE);
        }else{
            rlpf.setVisibility(View.VISIBLE);
            level.setText(mlevel+"");
        }
        typename.setText(mtype);
        time.setText(mtime+"  "+mtimes+unit);
        money.setText(Double.valueOf(mprice)/Integer.valueOf(mtimes)+"元");
        times.setText("x"+mtimes);
        amount.setText(Double.valueOf(mprice)+"元");
        if (mstate.equals("1")){
            state.setText("已接单");
            llbuttons.setVisibility(View.GONE);
            if(!MyApp.uid.equals(bguid)) {
                wancheng.setVisibility(View.VISIBLE);
                wancheng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/8/5
                        Intent intent=new Intent(JdDetailActivity.this,PingjiaActivity.class);
                        intent.putExtra("oid",oid);
                        intent.putExtra("bguid",bguid);
                        intent.putExtra("head_pic",head_pic);
                        intent.putExtra("name",mname);
                        startActivity(intent);
                    }
                });
            }
        }
        if(mstate.equals("3")){
            if(mlgbuid.equals(MyApp.uid)){
                llbuttons.setVisibility(View.GONE);
                state.setText("待服务");
            }else {
                state.setText("待确定");
            }
        }
        if(mstate.equals("4")){
            state.setText("已完成");
        }
        if(mstate.equals("5")){
            state.setText("已取消");
            llbuttons.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        chat.setOnClickListener(this);
        refuse.setOnClickListener(this);
        confirm.setOnClickListener(this);
        llfrienddata.setOnClickListener(this);
        icon.setOnClickListener(this);
        // TODO: 16/10/14
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jd_detail_iv_back:
                finish();
                break;
            case R.id.jd_detail_chat:
                RongIM.getInstance().startPrivateChat(this,mmobile,mname);
                break;
            case R.id.jd_detail_btn_jujie:
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("蜗牛壳")
                        .setMessage("确认拒接吗？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(JdDetailActivity.this,ReasonActivity.class);
                        intent.putExtra("oid",oid);
                        startActivity(intent);
                        finish();
                        String json="refresh";
                        EventBus.getDefault().post(json);
                    }
                }).create().show();
                break;
            case R.id.jd_detail_btn_confirm:
                final AlertDialog.Builder builder2=new AlertDialog.Builder(this);
                builder2.setTitle("蜗牛壳")
                        .setMessage("确认接单吗？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmJd();
                    }
                }).create().show();
                break;
            case R.id.jd_detail_ll_frienddata:
                Intent intent=new Intent(this,FriendDataActivity.class);
                intent.putExtra("fid",uid);
                startActivity(intent);
                break;
            case R.id.jd_detail_iv_icon:
                Intent intent2=new Intent(this,ZoomInPhotoActivity.class);
                intent2.putExtra("imgurl",head_pic);
                startActivity(intent2);
                break;
        }
    }


    private void confirmJd() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/receiveGeneralOrder");
        params.addBodyParameter("oid",oid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    Log.i("jddetail", "onSuccess: "+result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            String json="refresh";
                            EventBus.getDefault().post(json);
                            break;
                        case "4000":
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
