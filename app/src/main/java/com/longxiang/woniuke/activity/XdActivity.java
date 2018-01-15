package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.DataBean;
import com.longxiang.woniuke.customwheelview.SimpleWheelAdapter;
import com.longxiang.woniuke.customwheelview.WheelData;
import com.longxiang.woniuke.customwheelview.WheelView;
import com.longxiang.woniuke.myview.ArrayWheelAdapter;
import com.longxiang.woniuke.myview.DatePicker;
import com.longxiang.woniuke.myview.OnWheelChangedListener;
import com.longxiang.woniuke.myview.TimePicker;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class XdActivity extends AppCompatActivity implements View.OnClickListener, OnWheelChangedListener {
private ImageView ivBack;
    private Button btnConfirm;
    private TextView title;
    private ImageView icon;
    private TextView tvname;
    private TextView tvpinlei;
    private TextView tvtime;
    private TextView tvYhq;
    private TextView feiyong;
    private TextView heji;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private WheelView wheelview;
    private RelativeLayout rlall;
    private PopupWindow pw;
    private List<DataBean.DataEntity> datas=new ArrayList<>();
    private Calendar calendar;
    private PopupWindow pw_gride,pw_time;
    private DatePicker dp_test;
    private TimePicker tp_test;
    private TextView tv_ok,tv_cancel;	//确定、取消button
    private String selectDate,selectTime;
    private String[] mTimes={"1","2","3","4","5","6","7","8"};
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour,currentMinute,currentDay,selectHour,selectMinute,selectDay;
    private String unit;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String result= (String) msg.obj;
                    DataBean bean=new Gson().fromJson(result,DataBean.class);
                    datas=bean.getData();
                    if(datas.size()!=0) {
                        wheelview.setWheelData(createDatas2(datas));
                    }else{
                        Toast.makeText(getApplicationContext(),"该大神没有开启服务",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    getgodskill();
                    break;
            }

        }
    };
    private String mCurrentpl;
    private String fid;
    private String pic;
    private String name;
    private String count;
    private String bgid;
    private String jnid;
    private String price;
    private String yhqmoney="";
    private double zongjia;
    private String ucid;
    private String djid;
    private TextView tvCount;
    private String jnname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xd);
        Intent intent=getIntent();
        fid=intent.getStringExtra("fid");
        name=intent.getStringExtra("name");
        pic=intent.getStringExtra("pic");
        setView();
        setListener();
        if(intent.getStringExtra("jnid")!=null) {
            jnid = intent.getStringExtra("jnid");
            jnname = intent.getStringExtra("jnname");
            unit = intent.getStringExtra("unit");
            bgid=intent.getStringExtra("bgid");
            djid=intent.getStringExtra("djid");
            tvpinlei.setText(jnname);
            getgodskill();
        }
    }

    private void setView() {
        tvCount= (TextView) findViewById(R.id.xd_tvcount);
        title= (TextView) findViewById(R.id.xd_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        ivBack= (ImageView) findViewById(R.id.xd_iv_back);
        btnConfirm= (Button) findViewById(R.id.xd_btn_xiadan);
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        icon= (ImageView) findViewById(R.id.xd_ivPic);
        tvname= (TextView) findViewById(R.id.xd_tvname);
        tvpinlei= (TextView) findViewById(R.id.xd_tvpinlei);
        tvtime= (TextView) findViewById(R.id.xd_tvtime);
        feiyong= (TextView) findViewById(R.id.xd_feiyong);
        heji= (TextView) findViewById(R.id.xd_heji);
        rl01= (RelativeLayout) findViewById(R.id.xd_rl01);
        rl02= (RelativeLayout) findViewById(R.id.xd_rl02);
        rl03= (RelativeLayout) findViewById(R.id.xd_rl03);
        rlall= (RelativeLayout) findViewById(R.id.xd_Rl_all);
        tvYhq= (TextView) findViewById(R.id.xd_yhq);
        calendar = Calendar.getInstance();
        x.image().bind(icon,pic);
        tvname.setText(name);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        rl01.setOnClickListener(this);
        rl02.setOnClickListener(this);
        rl03.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xd_iv_back:
                finish();
                break;
            case R.id.xd_rl01:
                selectPinlei();
                break;
            case R.id.xd_rl02:
                if(tvpinlei.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"请先选择品类",Toast.LENGTH_SHORT).show();
                }else {
                    selectTime();
                }
                break;
            case R.id.xd_rl03:
                if(tvtime.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"请先选择时间",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(this, XdYhqActivity.class);
                    intent.putExtra("type", jnid);
                    startActivityForResult(intent, 480);
                }
                break;
            case R.id.xd_btn_xiadan:
                if(zongjia!=0&&Integer.parseInt(count)!=0) {
                    confirmXD();
                }else{
                    Toast.makeText(getApplicationContext(),"下单错误，请重新下单",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void confirmXD() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/setOrderdirectional");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("jnid",jnid);
        params.addBodyParameter("djid",djid);
        params.addBodyParameter("times",count);
        params.addBodyParameter("amount",zongjia+"");
        Log.i("amount", "confirmXD: "+zongjia);
        params.addBodyParameter("realtime",selectDate+" "+selectTime);
        params.addBodyParameter("bguid",fid);
        if(!tvYhq.getText().toString().equals("")){
            params.addBodyParameter("ucid",ucid);
            params.addBodyParameter("money",yhqmoney);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("654789", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case "4000":
                        case "4002":
                        case "4003":
                        case "4004":
                        case "4005":
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

    private void selectPinlei() {
        tvpinlei.setText("");
        tvtime.setText("");
        feiyong.setText("");
        tvYhq.setText("");
        heji.setText("");
        tvCount.setText("");
        View view=View.inflate(this, R.layout.item_dialogwheelview, null);
        wheelview= (WheelView) view.findViewById(R.id.simple_wheelview);
        wheelview.setWheelSize(3);
        wheelview.setSkin(WheelView.Skin.None);
        wheelview.setWheelClickable(true);
        pw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//				//设置这2个使得点击pop以外区域可以去除pop
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        //出现在布局底端
        pw.showAtLocation(rlall, 0, 0, Gravity.END);
        getdata2();
        getdata();
        wheelview.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<WheelData>() {
            @Override
            public void onItemSelected(int position, WheelData data) {
                mCurrentpl=data.getName();
                bgid=data.getBgid();
                jnid=data.getJnid();
                djid=data.getDjid();
                if(datas.size()!=0) {
                    unit = datas.get(position).getUnit();
                }
                Log.i("43213", "onItemSelected: "+bgid);
            }
        });
//                wheelview.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
//                    @Override
//                    public void onItemClick(int position, Object o) {
//                        Log.i("234567", "onItemClick: "+position);
//                    }
//                });
        TextView delete= (TextView) view.findViewById(R.id.item_dialogwheelview_tv_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                layout.startAnimation(formDismiss);
//                layout.setVisibility(View.GONE);
                pw.dismiss();
            }
        });
        TextView enter= (TextView) view.findViewById(R.id.item_dialogwheelview_tv_enter);
        enter.setTextColor(Color.parseColor("#1EBAF3"));
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvpinlei != null) {
                    tvpinlei.setText(mCurrentpl);
                }
//                        Toast.makeText(XdActivity.this, mCurrentpl, Toast.LENGTH_SHORT).show();
                pw.dismiss();
                handler.sendEmptyMessage(2);
            }
        });
    }

    private void getgodskill() {
        RequestParams params =new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getGodInfoByBgid");
        params.addBodyParameter("uid", fid);
        params.addBodyParameter("jnid",jnid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("43213", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    JSONObject obj1=obj.getJSONObject("data");
                    price=obj1.getString("price");
                    feiyong.setText(Double.valueOf(price)+"元");
                    heji.setText(Double.valueOf(price)+"元");
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

    private void selectTime() {
         View view = View.inflate(this, R.layout.dialog_select_time, null);
        selectDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH);
//                + DatePicker.getDayOfWeekCN(calendar.get(Calendar.DAY_OF_WEEK));
        //选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
        selectDay = currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        if((calendar.get(Calendar.MINUTE)/10)==5){
            selectHour=(calendar.get(Calendar.HOUR_OF_DAY)+1);
            selectMinute=0;
        }else {
            selectHour = calendar.get(Calendar.HOUR_OF_DAY);
            selectMinute = Integer.valueOf((1 + (calendar.get(Calendar.MINUTE) / 10)) + "0");
        }
//        selectMinute =0;
        currentMinute = calendar.get(Calendar.MINUTE);
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        selectTime = selectHour + ":" + ((selectMinute < 10)?("0"+selectMinute):selectMinute);
        dp_test = (DatePicker)view.findViewById(R.id.dp_test);
        tp_test = (TimePicker)view.findViewById(R.id.tp_test);
        tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        //设置滑动改变监听器
        dp_test.setOnChangeListener(dp_onchanghelistener);
        tp_test.setOnChangeListener(tp_onchanghelistener);
        pw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//				//设置这2个使得点击pop以外区域可以去除pop
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());

        //出现在布局底端
        pw.showAtLocation(rlall, 0, 0,  Gravity.END);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            private com.longxiang.woniuke.myview.WheelView wv_time;

            @Override
            public void onClick(View arg0) {
                if(selectDay == currentDay ){	//在当前日期情况下可能出现选中过去时间的情况
                    if(selectHour < currentHour){
                        Toast.makeText(getApplicationContext(), "不能选择过去的时间,请重新选择...", Toast.LENGTH_SHORT).show();
                    }else if( (selectHour == currentHour) && (selectMinute < currentMinute) ){
                        Toast.makeText(getApplicationContext(), "不能选择过去的时间,请重新选择...", Toast.LENGTH_SHORT).show();
                    }else{
//                        Toast.makeText(XdActivity.this, selectDate+selectTime, Toast.LENGTH_SHORT).show();
                        getDateAndTime();

                    }
                }else{
                    getDateAndTime();
                }

            }

            private void getDateAndTime() {
                pw.dismiss();
                View view1=View.inflate(XdActivity.this, R.layout.item_dialoggride,null);
                wv_time = (com.longxiang.woniuke.myview.WheelView) view1.findViewById(R.id.wv_choose_gride);
                wv_time.setAdapter(new ArrayWheelAdapter<String>(mTimes, mTimes.length));
                wv_time.setVisibleItems(3);
                count=1+"";
                wv_time.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(com.longxiang.woniuke.myview.WheelView wheel, int oldValue, int newValue) {
                        count=mTimes[wv_time.getCurrentItem()];
                    }

                });
                pw_time = new PopupWindow(view1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                pw_time.setOutsideTouchable(true);
                pw_time.setBackgroundDrawable(new BitmapDrawable());

                //出现在布局底端
                pw_time.showAtLocation(rlall, 0, 0, Gravity.END);
                TextView delete_gride= (TextView) view1.findViewById(R.id.tv_delete);
                delete_gride.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw_time.dismiss();
                    }
                });
                TextView enter_gride= (TextView) view1.findViewById(R.id.tv_enter);
                enter_gride.setTextColor(Color.parseColor("#1EBAF3"));
                enter_gride.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvtime.setText(selectDate+" "+selectTime+"分");
                        tvCount.setText(count+unit);
                        zongjia=Double.valueOf(price)*Integer.parseInt(count);
                        heji.setText(zongjia+"元");
                        pw_time.dismiss();
                    }
                });
            }
        });
        //点击取消
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pw.dismiss();
            }
        });

    }

    private void getdata() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getSkillon");
        params.addBodyParameter("uid",fid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("12345", "onSuccess: " + result);
                Message msg=handler.obtainMessage();
                msg.obj=result;
                msg.what=1;
                handler.sendMessage(msg);
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
    private ArrayList<WheelData> createDatas2(List<DataBean.DataEntity> dataEntities) {
        ArrayList<WheelData> list = new ArrayList<WheelData>();
        WheelData item;
        if(dataEntities!=null) {
            for (int i = 0; i < dataEntities.size(); i++) {
                item = new WheelData();
                item.setId(dataEntities.get(i).getUid());
                item.setBgid(dataEntities.get(i).getBgid());
                item.setIcon(dataEntities.get(i).getTypepic());
                item.setName(dataEntities.get(i).getSkill());
                item.setJnid(dataEntities.get(i).getJnid());
                item.setDjid(dataEntities.get(i).getDjid());

                list.add(item);
            }
        }
        return list;
    }
    private void getdata2() {
        wheelview.setWheelAdapter(new SimpleWheelAdapter(this));
        wheelview.setWheelSize(3);
        wheelview.setSkin(WheelView.Skin.None);
        wheelview.setWheelData(createDatas());
        wheelview.setWheelClickable(true);
        wheelview.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onItemClick(int position, Object o) {
            }
        });
        wheelview.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<WheelData>() {
//            @Override
//            public void onItemSelected(int position, DataBean.DataEntity dataEntity) {
//                WheelUtils.log("selected:" + position);
//            }

            @Override
            public void onItemSelected(int position, WheelData data) {
            }
        });
    }
    private ArrayList<WheelData> createDatas() {
        ArrayList<WheelData> list = new ArrayList<WheelData>();
        WheelData item;
        for (int i = 0; i < 20; i++) {
            item = new WheelData();
            item.setIcon("");
            item.setName("");
            list.add(item);
        }
        return list;
    }
    DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            selectDay = day;
            selectDate = year + "-" + month + "-" + day; //+ DatePicker.getDayOfWeekCN(day_of_week)
        }
    };
    TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
            selectMinute = minute;
            selectHour = hour;
            selectTime = hour + ":" + ((selectMinute < 10)?("0"+selectMinute):selectMinute);
        }
    };

    @Override
    public void onChanged(com.longxiang.woniuke.myview.WheelView wheel, int oldValue, int newValue) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 480) {
                yhqmoney=data.getStringExtra("money");
                ucid=data.getStringExtra("ucid");
                tvYhq.setText("-"+yhqmoney+"元");
                if(Double.valueOf(yhqmoney)>=zongjia){
                    heji.setText("0元");
//                    Toast.makeText(XdActivity.this,"合计金额小于优惠券的金额,不可使用",Toast.LENGTH_SHORT).show();
                }else {
                    heji.setText(zongjia - Double.valueOf(yhqmoney) + "元");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
