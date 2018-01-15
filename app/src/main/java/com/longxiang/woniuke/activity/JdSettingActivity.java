package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.Interfaces.CallBacksss;
import com.longxiang.woniuke.Interfaces.PriceCallBack;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.JdsettingAdapter;
import com.longxiang.woniuke.adapter.QdsettingAdapter;
import com.longxiang.woniuke.bean.JdsettingData;
import com.longxiang.woniuke.bean.QdSettingData;
import com.longxiang.woniuke.myview.MyListView;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.utils.PriceOperationPw;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class JdSettingActivity extends AppCompatActivity implements CallBacksss,PriceCallBack{
private ImageView ivBack;
    private MyListView listView;
    private TextView tvtitle;
    private JdsettingAdapter adapter;
    private MyListView qdlistview;
    private LinearLayout qdlayout;
    private QdsettingAdapter qdadapter;
//    private Switch qdswitch;
    private QdSettingData data;
    private JdsettingData jddata;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    getRobSwitch();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private PriceOperationPw menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jd_setting);
        setView();
        setListener();
        getSkillon();

    }

    private void getRobSwitch() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getRobSwitch");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getRobSwitch", "onSuccess: "+result);
                data=new Gson().fromJson(result,QdSettingData.class);
                qdadapter =new QdsettingAdapter(JdSettingActivity.this,data.getData(),jddata.getData());
                qdlistview.setAdapter(qdadapter);
//                for (int i=0;i<data.getData().size();i++){
//                    String robswitch = data.getData().get(i).getRobswitch();
//                    if(robswitch.equals("1")){
//                        qdswitch.setChecked(true);
//                        qdlistview.setVisibility(View.VISIBLE);
//                    }else{
////                        qdlistview.setVisibility(View.GONE);
//                    }
//                }
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

    private void getSkillon() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getSwitch");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("skillon", "onSuccess: "+result);
                jddata=new Gson().fromJson(result,JdsettingData.class);
                adapter =new JdsettingAdapter(JdSettingActivity.this,jddata.getData());
                listView.setAdapter(adapter);

                handler.sendEmptyMessage(1);

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
        ivBack= (ImageView) findViewById(R.id.jd_setting_iv_back);
        listView= (MyListView) findViewById(R.id.jd_setting_listview);
        tvtitle= (TextView) findViewById(R.id.jd_setting_tvtitle);
        tvtitle.setTextColor(Color.parseColor("#ffffff"));
        qdlistview= (MyListView) findViewById(R.id.jd_setting_qd_listview);
        qdlayout= (LinearLayout) findViewById(R.id.qd_setting_qdll);
//        qdswitch= (Switch) findViewById(R.id.qd_setting_switch);

    }

    private void setListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        qdswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(!isChecked){
//                    for (int i=0;i<jddata.getData().size();i++){
//                        if(data.getData().get(i).getRobswitch().equals("1")) {
//                            data.getData().get(i).setRobswitch("2");
//                            qdlistview.setAdapter(new QdsettingAdapter(JdSettingActivity.this,data.getData(),jddata.getData()));
////                            qdadapter.notifyDataSetChanged();
//                        }
////                        }else{
////                            data.getData().get(i).setRobswitch("1");
////                            qdadapter.notifyDataSetChanged();
////                        }
////                        if(qdadapter!=null) {
////                            qdadapter.notifyDataSetChanged();
////                        }
//                    }
//                    qdlistview.setVisibility(View.GONE);
//                }else{
////                    for (int i=0;i<data.getData().size();i++){
////                        if(jddata.getData().get(i).getSwitchX().equals("1")) {
////                            data.getData().get(i).setRobswitch("1");
////                        }else{
////                            data.getData().get(i).setRobswitch("2");
////                        }
////                        if(qdadapter!=null) {
////                            qdadapter.notifyDataSetChanged();
////                        }
////                    }
//                    qdlistview.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    @Override
    public void Call() {
        adapter.notifyDataSetChanged();
        for (int i=0;i<jddata.getData().size();i++){
            if(jddata.getData().get(i).getSwitchX().equals("1")){
                data.getData().get(i).setRobswitch("1");
                qdadapter.notifyDataSetChanged();
            }else{
                data.getData().get(i).setRobswitch("2");
                qdadapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void setPrice(String bgid, String djid,int position) {
        menuWindow = new PriceOperationPw(JdSettingActivity.this,adapter,bgid,djid,position,jddata);
        if(menuWindow.isShowing()){
            menuWindow.dismiss();
        }else {
            //显示窗口
            menuWindow.showAtLocation(JdSettingActivity.this.findViewById(R.id.jd_setting_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }
}

