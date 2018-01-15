package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.SelectGodAdapter;
import com.longxiang.woniuke.bean.SelcetGodData;
import com.longxiang.woniuke.myview.RushBuyCountDownTimerView;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.utils.TimerCountdownView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class SelectGodActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private ImageView iv;
    private String oid;
    private boolean isRefresh=false;
    private boolean isFirst=true;
    private int godnum;
    private int beforegodnum=0;
    private TimerCountdownView timeview;
    private TextView tvTimer;
    private boolean isDestory=true;
//    private RushBuyCountDownTimerView tvTime;
    List<SelcetGodData.DataBean> godlist=new ArrayList<>();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    getGodAlreadyRobOrder();
                    break;
                case 2:
                    getGodAlreadyRobOrder02();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private SelectGodAdapter adapter;
    private String ucmoney;
    private String bgid;
    private String bguid;
    private double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_god);
        Intent intent=getIntent();
        oid=intent.getStringExtra("oid");
        ucmoney=intent.getStringExtra("ucmoney");
        if(ucmoney==null){
            ucmoney="0";
        }
        Log.i("selectgod", "onCreate: "+oid);
        setView();
        setListener();
//        tvTime.setTime(0,10,0);
//        tvTime.start();
        getRobNum();
        if(!isRefresh) {
            handler.postDelayed(runnable, 5000);
        }
    }

    private void getRobNum() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getRobNum");
        params.addBodyParameter("oid",oid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("selectgod", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Log.i("selectgod", "onSuccess: "+"2000");
                            godnum=obj.getInt("data");
                            Log.i("selectgod", "onSuccess: "+godnum+","+beforegodnum);
                            if(godnum>beforegodnum){
                                Log.i("selectgod", "onSuccess: "+"大于");
                                if(isFirst) {
                                    Log.i("selectgod", "onSuccess: "+"1ci");
                                    handler.sendEmptyMessage(1);
                                }else{
                                    handler.sendEmptyMessage(2);
                                }
                            }
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
    private void getGodAlreadyRobOrder() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getGodAlreadyRobOrder");
        params.addBodyParameter("oid",oid);
        params.addBodyParameter("befornum",beforegodnum+"");
        params.addBodyParameter("robnum",godnum+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("selectgod", "onSuccess: "+result);
                SelcetGodData data=new Gson().fromJson(result,SelcetGodData.class);
                if(data.getRetcode()==2000) {
                    godlist.addAll(data.getData());
                    adapter = new SelectGodAdapter(SelectGodActivity.this, godlist);
                    listView.setAdapter(adapter);
                    isFirst = false;
                    beforegodnum = godnum;
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
    private void getGodAlreadyRobOrder02() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getGodAlreadyRobOrder");
        params.addBodyParameter("oid",oid);
        params.addBodyParameter("befornum",beforegodnum+"");
        params.addBodyParameter("robnum",godnum+"");
        Log.i("getGodAlreadyRobOrder2", "getGodAlreadyRobOrder2: "+godnum+","+beforegodnum);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiweiOrder", "onSuccess: "+result);
                SelcetGodData data=new Gson().fromJson(result,SelcetGodData.class);
                if(data.getRetcode()==2000) {
                    Log.i("selectgod", "onSuccess: "+"notify");
                    godlist.addAll(data.getData());
                    adapter.notifyDataSetChanged();
                    beforegodnum = godnum;
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
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try{
                handler.postDelayed(this, 5000);
                getRobNum();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    private void setView() {
        listView= (ListView) findViewById(R.id.select_god_listview);
        iv= (ImageView) findViewById(R.id.select_god_iv);
        timeview= (TimerCountdownView) findViewById(R.id.select_god_timeview);
//        tvTime= (RushBuyCountDownTimerView) findViewById(R.id.select_god_timerview);
        timeview.setMaxTime(10);
        timeview.updateView();
        timeview.addCountdownTimerListener(litener);
        tvTimer= (TextView) findViewById(R.id.select_god_timer);
    }
    TimerCountdownView.CountdownTimerListener litener = new TimerCountdownView.CountdownTimerListener() {

        @Override
        public void onCountDown(String time) {
            tvTimer.setText(time);
        }

        @Override
        public void onTimeArrive(boolean isArrive) {
            if(isArrive){
                Toast.makeText(getApplicationContext(),"没有大神抢单或者没有选择大神,订单取消",Toast.LENGTH_SHORT).show();
                removeOrder();
            }
        }
    };

    private void setListener() {
        iv.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        cancelOrder();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bgid=godlist.get(position).getBgid();
        bguid=godlist.get(position).getUid();
        amount=godlist.get(position).getAmount();
        alertDialog(bguid);

    }

    private void chooseGod() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/chooseGod");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("oid",oid);
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("bguid",bguid);
        params.addBodyParameter("amount",amount+"");
        params.addBodyParameter("ucmoney",ucmoney);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            isDestory=false;
                            Toast.makeText(SelectGodActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(SelectGodActivity.this,OrderCenterActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "4000":
                        case "4001":
                        case "4003":
                        case "4004":
                        case "3838":
                            Toast.makeText(SelectGodActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
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

    private void cancelOrder() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(SelectGodActivity.this);
        builder.setTitle("蜗牛壳").setMessage("是否取消订单").setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeOrder();
            }
        }).create();
        builder.show();
    }

    public void removeOrder() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/removeOrder");
//        Log.i("lvzhiweiucmoney", "removeOrder: "+oid);
        params.addBodyParameter("oid",oid);
//        Log.i("lvzhiweiucmoney", "removeOrder: "+MyApp.uid);
        params.addBodyParameter("uid", MyApp.uid);
//        Log.i("lvzhiweiucmoney", "removeOrder: "+ucmoney);
        params.addBodyParameter("ucmoney",ucmoney);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("removeorderoff", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(SelectGodActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            isRefresh=true;
                            handler.removeCallbacks(runnable);
                            finish();
                            break;
                        case "4000":
                        case "4001":
                        case "4002":
                        case "4005":
                            Toast.makeText(SelectGodActivity.this,obj.getString("msg"),Toast.LENGTH_SHORT).show();
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
    private void alertDialog(final String bguid) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(SelectGodActivity.this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.select_god_dialog);
        alertDialog.setCanceledOnTouchOutside(true);
        RelativeLayout rlck= (RelativeLayout) window.findViewById(R.id.select_god_ckds);
        RelativeLayout rlxz= (RelativeLayout) window.findViewById(R.id.select_god_xzds);
        rlck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectGodActivity.this,FriendDataActivity.class);
                intent.putExtra("fid",bguid);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        rlxz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(SelectGodActivity.this);
                builder.setTitle("蜗牛壳").setMessage("是否选择大神").setPositiveButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chooseGod();
                    }
                }).create();
                builder.show();
                alertDialog.dismiss();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        isRefresh=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRefresh=true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isRefresh=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRefresh=true;
        if(isDestory==true) {
            removeOrder();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            cancelOrder();
        }
        return super.onKeyDown(keyCode, event);
    }
}
