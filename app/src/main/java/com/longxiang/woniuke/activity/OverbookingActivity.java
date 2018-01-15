package com.longxiang.woniuke.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.myview.ArrayWheelAdapter;
import com.longxiang.woniuke.myview.DatePicker;
import com.longxiang.woniuke.myview.OnWheelChangedListener;
import com.longxiang.woniuke.myview.TimePicker;
import com.longxiang.woniuke.myview.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverbookingActivity extends AppCompatActivity implements OnWheelChangedListener {

    /**
     * 省的WheelView控件
     */
    private WheelView wvname;
    /**
     * 市的WheelView控件
     */
    private WheelView wvcontent;
    /**
     * 所有省
     */
    private String[] mProvinceDatas={"游戏","休闲娱乐","占星"};
    private String[] mGrides={"钻石","白金","黄金"};
    private String[] mTimes={"1小时","2小时","3小时","4小时","5小时","6小时","7小时","8小时"};
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;

    private AlertDialog dialog;
    private LinearLayout layout;
    private View sub;
    private Animation formShow,formDismiss;

    private Calendar calendar;
    private DatePicker dp_test;
    private TimePicker tp_test;
    private TextView tv_ok,tv_cancel;	//确定、取消button
    private String selectDate,selectTime;
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour,currentMinute,currentDay,selectHour,selectMinute,selectDay;
    //整体布局

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            layout.setVisibility(View.VISIBLE);
        }
    };
    private View sub_title;
    private TextView title;
    private ImageView back;
    private RelativeLayout layout1;
    private TextView tv_type;
    private RelativeLayout layout2;
    private TextView tv_gride;
    private RelativeLayout layout3;
    private TextView tv_time;

    private PopupWindow pw;
    private LinearLayout Rl_all;
    private WheelView wv_gride;
    private PopupWindow pw_gride,pw_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overbooking);
        init();
    }

    public void init(){
        Rl_all = (LinearLayout) findViewById(R.id.Rl_all1);
        calendar = Calendar.getInstance();
        layout = (LinearLayout) findViewById(R.id.ll_overbooking1);
        sub = (View) findViewById(R.id.sub_overbooking1);
        sub_title = (View) findViewById(R.id.sub_title_overbooking1);
        title = (TextView) sub_title.findViewById(R.id.tv_title_name);
        title.setText("下单");
        title.setTextColor(Color.WHITE);
        back = (ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layout1 = (RelativeLayout) findViewById(R.id.layout_type_overbooking1);
        tv_type = (TextView) findViewById(R.id.tv_type_overbooking1);
//        layout1.setOnClickListener(this);
//        layout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                View view = View.inflate(OverbookingActivity.this, R.layout.item_dialog, null);
////                pw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
////                //设置这2个使得点击pop以外区域可以去除pop
////                pw.setOutsideTouchable(true);
////                pw.setBackgroundDrawable(new BitmapDrawable());
////
////                //出现在布局底端
////                pw.showAtLocation(Rl_all, 0, 0,  Gravity.END);
//
//
//
//
//
//
//
////                layout.setVisibility(View.VISIBLE);
////                layout.startAnimation(formShow);
////                handler.sendEmptyMessageDelayed(0, 2000);
//            }
//        });


        layout2 = (RelativeLayout) findViewById(R.id.layout_gride_overbooking1);
//        layout2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                layout.setVisibility(View.VISIBLE);
//                layout.startAnimation(formShow);
//                handler.sendEmptyMessageDelayed(0, 2000);
//            }
//        });
        tv_gride = (TextView) findViewById(R.id.tv_gride_overbooking1);

        layout3 = (RelativeLayout) findViewById(R.id.layout_time_overbooking1);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = View.inflate(OverbookingActivity.this, R.layout.dialog_select_time, null);
                selectDate = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月"
                        + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                        + DatePicker.getDayOfWeekCN(calendar.get(Calendar.DAY_OF_WEEK));
                //选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
                selectDay = currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                selectMinute = currentMinute = calendar.get(Calendar.MINUTE);
                selectHour = currentHour = calendar.get(Calendar.HOUR_OF_DAY);

                selectTime = currentHour + "点" + ((currentMinute < 10)?("0"+currentMinute):currentMinute) + "分";
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
                pw.showAtLocation(Rl_all, 0, 0,  Gravity.END);

                //点击确定
                tv_ok.setOnClickListener(new View.OnClickListener() {
                    private WheelView wv_time;

                    @Override
                    public void onClick(View arg0) {
                        if(selectDay == currentDay ){	//在当前日期情况下可能出现选中过去时间的情况
                            if(selectHour < currentHour){
                                Toast.makeText(getApplicationContext(), "不能选择过去的时间\n        请重新选择", Toast.LENGTH_SHORT).show();
                            }else if( (selectHour == currentHour) && (selectMinute < currentMinute) ){
                                Toast.makeText(getApplicationContext(), "不能选择过去的时间\n        请重新选择", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), selectDate+selectTime, Toast.LENGTH_SHORT).show();
                                pw.dismiss();
                                View view1=View.inflate(OverbookingActivity.this,R.layout.item_dialoggride,null);
                                wv_time = (WheelView) view1.findViewById(R.id.wv_choose_gride);
                                wv_time.setAdapter(new ArrayWheelAdapter<String>(mTimes, mTimes.length));
                                wv_time.setVisibleItems(3);
                                wv_time.addChangingListener(new OnWheelChangedListener() {
                                    @Override
                                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                                        Log.e("12345", "onChanged: "+mTimes[wv_time.getCurrentItem()] );
                                    }
                                });
                                pw_time = new PopupWindow(view1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                                pw_time.setOutsideTouchable(true);
                                pw_time.setBackgroundDrawable(new BitmapDrawable());

                                //出现在布局底端
                                pw_time.showAtLocation(Rl_all, 0, 0, Gravity.END);
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
//                        layout.startAnimation(formDismiss);
//                        layout.setVisibility(View.GONE);
//                        if (mCurrentProviceName != null && mCurrentCityName != null) {
//                            tv_type.setText(mCurrentCityName);
//                            tv_type.setTextColor(Color.parseColor("#1EBAF3"));
//                        }
//                        Toast.makeText(OverbookingActivity.this, mCurrentProviceName + mCurrentCityName, Toast.LENGTH_SHORT).show();
                                        Log.e("12345", "onClick: 选择时间");
                                        pw_time.dismiss();
                                    }
                                });

                            }
                        }else{
                            Toast.makeText(getApplicationContext(), selectDate+selectTime, Toast.LENGTH_SHORT).show();
                            pw.dismiss();
                        }

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
        });
        tv_time = (TextView) findViewById(R.id.tv_time_overbooking1);

        formShow = AnimationUtils.loadAnimation(this, R.anim.choutixia);
        formDismiss = AnimationUtils.loadAnimation(this, R.anim.downdismiss);
        wvname = (WheelView) sub.findViewById(R.id.wv_choose1);
        wvcontent = (WheelView) sub.findViewById(R.id.wv_choose2);

        getCity();
        wvname.setAdapter(new ArrayWheelAdapter<String>(mProvinceDatas, mProvinceDatas.length));
        // 添加change事件
        wvname.addChangingListener(this);
        // 添加change事件
        wvcontent.addChangingListener(this);

        wvname.setVisibleItems(3);
        wvcontent.setVisibleItems(3);
        updateCities();
        TextView delete= (TextView) sub.findViewById(R.id.tv_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.startAnimation(formDismiss);
                layout.setVisibility(View.GONE);
//                dialog.dismiss();
            }
        });
        TextView enter= (TextView) sub.findViewById(R.id.tv_enter);
        enter.setTextColor(Color.parseColor("#1EBAF3"));
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.startAnimation(formDismiss);
                layout.setVisibility(View.GONE);
                if (mCurrentProviceName!=null&&mCurrentCityName!=null){
                    tv_type.setText(mCurrentCityName);
                    tv_type.setTextColor(Color.parseColor("#1EBAF3"));
                }
                Toast.makeText(OverbookingActivity.this, mCurrentProviceName + mCurrentCityName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //listeners
    DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            selectDay = day;
            selectDate = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
        }
    };
    TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
            selectTime = hour + "点" + ((minute < 10)?("0"+minute):minute) + "分";
            selectHour = hour;
            selectMinute = minute;
        }
    };


    public void onClick(View view){
        switch (view.getId()){
            case R.id.layout_type_overbooking1:
                View view1=View.inflate(this, R.layout.item_dialog, null);

                wvname = (WheelView) view1.findViewById(R.id.wv_choose1);
                wvcontent = (WheelView) view1.findViewById(R.id.wv_choose2);

                getCity();
                wvname.setAdapter(new ArrayWheelAdapter<String>(mProvinceDatas, mProvinceDatas.length));
                // 添加change事件
                wvname.addChangingListener(this);
                // 添加change事件
                wvcontent.addChangingListener(this);

                wvname.setVisibleItems(3);
                wvcontent.setVisibleItems(3);
                updateCities();
                pw = new PopupWindow(view1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//				//设置这2个使得点击pop以外区域可以去除pop
                pw.setOutsideTouchable(true);
                pw.setBackgroundDrawable(new BitmapDrawable());

                //出现在布局底端
                pw.showAtLocation(Rl_all, 0, 0, Gravity.END);
                TextView delete= (TextView) view1.findViewById(R.id.tv_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                layout.startAnimation(formDismiss);
//                layout.setVisibility(View.GONE);
                        pw.dismiss();
                    }
                });
                TextView enter= (TextView) view1.findViewById(R.id.tv_enter);
                enter.setTextColor(Color.parseColor("#1EBAF3"));
                enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.startAnimation(formDismiss);
                        layout.setVisibility(View.GONE);
                        if (mCurrentProviceName != null && mCurrentCityName != null) {
                            tv_type.setText(mCurrentCityName);
                            tv_type.setTextColor(Color.parseColor("#1EBAF3"));
                        }
                        Toast.makeText(OverbookingActivity.this, mCurrentProviceName + mCurrentCityName, Toast.LENGTH_SHORT).show();
                        pw.dismiss();
                    }
                });
                break;
            case R.id.layout_gride_overbooking1:
                View view2=View.inflate(this,R.layout.item_dialoggride,null);
                wv_gride = (WheelView) view2.findViewById(R.id.wv_choose_gride);
                wv_gride.setAdapter(new ArrayWheelAdapter<String>(mGrides, mGrides.length));
                wv_gride.setVisibleItems(3);
                wv_gride.addChangingListener(new OnWheelChangedListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        int count = wv_gride.getCurrentItem();
                        Log.e("12345", "onChanged: " + mGrides[count]);

                    }
                });

                pw_gride = new PopupWindow(view2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                pw_gride.setOutsideTouchable(true);
                pw_gride.setBackgroundDrawable(new BitmapDrawable());

                //出现在布局底端
                pw_gride.showAtLocation(Rl_all, 0, 0, Gravity.END);
                TextView delete_gride= (TextView) view2.findViewById(R.id.tv_delete);
                delete_gride.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                layout.startAnimation(formDismiss);
//                layout.setVisibility(View.GONE);
                        pw_gride.dismiss();
                    }
                });
                TextView enter_gride= (TextView) view2.findViewById(R.id.tv_enter);
                enter_gride.setTextColor(Color.parseColor("#1EBAF3"));
                enter_gride.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        layout.startAnimation(formDismiss);
//                        layout.setVisibility(View.GONE);
//                        if (mCurrentProviceName != null && mCurrentCityName != null) {
//                            tv_type.setText(mCurrentCityName);
//                            tv_type.setTextColor(Color.parseColor("#1EBAF3"));
//                        }
//                        Toast.makeText(OverbookingActivity.this, mCurrentProviceName + mCurrentCityName, Toast.LENGTH_SHORT).show();
                        pw_gride.dismiss();
                    }
                });
                break;
        }

//        layout.setVisibility(View.VISIBLE);
//        layout.startAnimation(formShow);
//        handler.sendEmptyMessageDelayed(0, 2000);


//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        View view1=View.inflate(this, R.layout.item_dialog, null);
//        builder.setView(view1);
//        wvname = (WheelView) view1.findViewById(R.id.wv_choose1);
//        wvcontent = (WheelView) view1.findViewById(R.id.wv_choose2);
//        TextView delete= (TextView) view1.findViewById(R.id.tv_delete);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        TextView enter= (TextView) view1.findViewById(R.id.tv_enter);
//        enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(OverbookingActivity.this, mCurrentProviceName + mCurrentCityName, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        getCity();
//
//        wvname.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
//        // 添加change事件
//        wvname.addChangingListener(this);
//        // 添加change事件
//        wvcontent.addChangingListener(this);
//
//        wvname.setVisibleItems(3);
//        wvcontent.setVisibleItems(3);
//        updateCities();
//
//        dialog=builder.create();
//        dialog.show();


    }

    public void getCity(){
        List<String[]> citylist=new ArrayList<>();
        citylist.add(new String[]{"线上LOL", "线上DOTA", "线上DOTA2", "穿越火线CF"});
        citylist.add(new String[]{"声优", "歌手"});
        citylist.add(new String[]{"占卜", "星座"});
        for (int i = 0; i <mProvinceDatas.length ; i++) {
            mCitisDatasMap.put(mProvinceDatas[i],citylist.get(i));
        }
    }

//    public void getUpdata(){
//        MainActivity.instance.finish();
//        startActivity(new Intent(OverbookingActivity.this,MainActivity.class));
//        finish();
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode==KeyEvent.KEYCODE_BACK){
//            MainActivity.rb1.setChecked(true);
//            finish();
////            getUpdata();
//        }
//        return false;
//    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = wvname.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null)
        {
            cities = new String[] { "" };
        }
        wvcontent.setAdapter(new ArrayWheelAdapter<String>(cities, cities.length));
        wvcontent.setCurrentItem(0);
        int count = wvcontent.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[count];
    }
    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue)
    {
        if (wheel == wvname)
        {
            updateCities();
        }
    }

}
