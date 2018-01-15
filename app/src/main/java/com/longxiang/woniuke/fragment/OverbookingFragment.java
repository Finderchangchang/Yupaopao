package com.longxiang.woniuke.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.L;
import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.WaitGodActivity;
import com.longxiang.woniuke.activity.XdYhqActivity;
import com.longxiang.woniuke.bean.DataBean;
import com.longxiang.woniuke.bean.SqlvData;
import com.longxiang.woniuke.bean.XdxmData;
import com.longxiang.woniuke.bean.XmData;
import com.longxiang.woniuke.customwheelview.SimpleWheelAdapter;
import com.longxiang.woniuke.customwheelview.WheelData;
import com.longxiang.woniuke.myview.ArrayWheelAdapter;
import com.longxiang.woniuke.myview.DatePicker;
import com.longxiang.woniuke.myview.OnWheelChangedListener;
import com.longxiang.woniuke.myview.TimePicker;
import com.longxiang.woniuke.myview.WheelView;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/4.
 */
public class OverbookingFragment extends Fragment implements OnWheelChangedListener, View.OnClickListener {

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
    private String[] mGrides;
    private String[] mGrideId;
    private String[] mTimes={"1","2","3","4","5","6","7","8"};
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
    private View sub_title;
    private TextView title;
    private ImageView back;
    private RelativeLayout layout1;
    private TextView tv_type;
    private RelativeLayout layout2;
    private TextView tv_gride;
    private TextView tv_yhq;
    private RelativeLayout layout3;
    private RelativeLayout layout4;
    private PopupWindow pw;
    private LinearLayout Rl_all;
    private WheelView wv_gride;
    private PopupWindow pw_gride,pw_time;
    private View view;
    private String pid;
    private com.longxiang.woniuke.customwheelview.WheelView wheelview;
    private String mCurrentpl;
    private List<XmData.DataBean> datas;
    private TextView tv_time;
    private String count;
    private RadioGroup radioGroup;
    private String sex="0";
    private Button btnFabu;
    private String levelid;
    private String level;
    private String unit;
    private TextView tvCount;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            layout.setVisibility(View.VISIBLE);
            switch (msg.what){
                case 1:
                    String result= (String) msg.obj;
                    XmData bean=new Gson().fromJson(result,XmData.class);
                    if(bean!=null||bean.getData().size()!=0) {
                        datas = bean.getData();
                        wheelview.setWheelData(createDatas2(datas));
                    }
                    wheelview.setOnWheelItemSelectedListener(new com.longxiang.woniuke.customwheelview.WheelView.OnWheelItemSelectedListener<WheelData>() {
                        @Override
                        public void onItemSelected(int position, WheelData data) {
                            mCurrentpl=data.getName();
                            pid=data.getId();
                            if(datas.size()!=0||datas!=null) {
                                unit = datas.get(position).getUnit();
                            }
                            Log.i("43213", "onItemSelected: "+pid);
                        }
                    });
                    break;
                case 2:
                    getLevel();
                    break;
            }
        }
    };
    private String yhqmoney;
    private String ucid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.item_overbooking,null);
        init();
        setListener();
        return view;
    }

    private void setListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb1_overbooking:
                        sex="0";
                        break;
                    case R.id.rb2_overbooking:
                        sex="男";
                        break;
                    case R.id.rb3_overbooking:
                        sex="女";
                        break;
                }
            }
        });
        btnFabu.setOnClickListener(this);
        layout4.setOnClickListener(this);
    }

    public void init(){
        tvCount= (TextView) view.findViewById(R.id.tv_count_overbooking);
        radioGroup= (RadioGroup) view.findViewById(R.id.rg_overbooking);
        btnFabu= (Button) view.findViewById(R.id.overbooking_btnfabu);
        Rl_all = (LinearLayout) view.findViewById(R.id.Rl_all);
        calendar = Calendar.getInstance();
        layout = (LinearLayout)view.findViewById(R.id.ll_overbooking);
        sub = (View) view.findViewById(R.id.sub_overbooking);
        layout1 = (RelativeLayout) view.findViewById(R.id.layout_type_overbooking);
        layout1.setOnClickListener(this);
        tv_type = (TextView)view. findViewById(R.id.tv_type_overbooking);
        tv_gride = (TextView)view. findViewById(R.id.tv_gride_overbooking);
        tv_time= (TextView) view.findViewById(R.id.tv_time_overbooking);
        tv_yhq= (TextView) view.findViewById(R.id.tv_yhq_overbooking);
        layout2 = (RelativeLayout)view. findViewById(R.id.layout_gride_overbooking);
        layout2.setOnClickListener(this);
        layout3 = (RelativeLayout) view.findViewById(R.id.layout_time_overbooking);
        layout4 = (RelativeLayout) view.findViewById(R.id.layout_yhq_overbooking);
            layout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("".equals(tv_type.getText().toString())) {
                        Toast.makeText(getActivity().getApplicationContext(), "请先选择品类", Toast.LENGTH_SHORT).show();
                    } else {

                        View view = View.inflate(getActivity(), R.layout.dialog_select_time, null);
                        selectDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-"
                                + calendar.get(Calendar.DAY_OF_MONTH);

                        //选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
                        selectDay = currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                        if((calendar.get(Calendar.MINUTE)/10)==5){
                            selectHour=(calendar.get(Calendar.HOUR_OF_DAY)+1);
                            selectMinute=0;
                        }else {
                            selectHour = calendar.get(Calendar.HOUR_OF_DAY);
                            selectMinute = Integer.valueOf((1 + (calendar.get(Calendar.MINUTE) / 10)) + "0");
                        }
//                        selectMinute=0;
                        currentMinute = calendar.get(Calendar.MINUTE);
                        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                        selectTime = selectHour + ":" + ((selectMinute<10)?("0"+selectMinute):selectMinute);
                        dp_test = (DatePicker) view.findViewById(R.id.dp_test);
                        tp_test = (TimePicker) view.findViewById(R.id.tp_test);

                        tv_ok = (TextView) view.findViewById(R.id.tv_ok);
                        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                        //设置滑动改变监听器
                        dp_test.setOnChangeListener(dp_onchanghelistener);
                        tp_test.setOnChangeListener(tp_onchanghelistener);
                        pw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//				        //设置这2个使得点击pop以外区域可以去除pop
                        pw.setOutsideTouchable(true);
                        pw.setBackgroundDrawable(new BitmapDrawable());

                        //出现在布局底端
                        pw.showAtLocation(Rl_all, 0, 0, Gravity.END);

                        //点击确定
                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            private WheelView wv_time;

                            @Override
                            public void onClick(View arg0) {
                                if (selectDay == currentDay) {    //在当前日期情况下可能出现选中过去时间的情况
                                    if (selectHour < currentHour) {
                                        Toast.makeText(getActivity(), "不能选择过去的时间,请重新选择...", Toast.LENGTH_SHORT).show();
                                    } else if ((selectHour == currentHour) && (selectMinute < currentMinute)) {
                                        Toast.makeText(getActivity(), "不能选择过去的时间,请重新选择...", Toast.LENGTH_SHORT).show();
                                    } else {
//                                Toast.makeText(getActivity(), selectDate+selectTime, Toast.LENGTH_SHORT).show();
                                        getDateAndTime();
                                    }
                                } else {
                                    getDateAndTime();
                                }
                            }

                            private void getDateAndTime() {
                                pw.dismiss();
                                View view1 = View.inflate(getActivity(), R.layout.item_dialoggride, null);
                                wv_time = (WheelView) view1.findViewById(R.id.wv_choose_gride);
                                wv_time.setAdapter(new ArrayWheelAdapter<String>(mTimes, mTimes.length));
                                wv_time.setVisibleItems(3);
                                count = 1 + "";
                                wv_time.addChangingListener(new OnWheelChangedListener() {
                                    @Override
                                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                                        Log.e("12345", "onChanged: "+mTimes[wv_time.getCurrentItem()] );
                                        count = mTimes[wv_time.getCurrentItem()];
                                    }
                                });
                                pw_time = new PopupWindow(view1, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                                pw_time.setOutsideTouchable(true);
                                pw_time.setBackgroundDrawable(new BitmapDrawable());

                                //出现在布局底端
                                pw_time.showAtLocation(Rl_all, 0, 0, Gravity.END);
                                TextView delete_gride = (TextView) view1.findViewById(R.id.tv_delete);
                                delete_gride.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        pw_time.dismiss();
                                    }
                                });
                                TextView enter_gride = (TextView) view1.findViewById(R.id.tv_enter);
                                enter_gride.setTextColor(Color.parseColor("#1EBAF3"));
                                enter_gride.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tv_time.setText(selectDate + " " + selectTime+"分");
                                        tvCount.setText(count + unit);
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
                }
            });
    }

    //listeners
    DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
        @Override
        public void onChange(int year, int month, int day, int day_of_week) {
            selectDay = day;
            selectDate = year + "-" + month + "-" + day ;
        }
    };
    TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
        @Override
        public void onChange(int hour, int minute) {
            selectMinute = minute;
            selectHour = hour;
            selectTime = hour + ":" +((selectMinute<10)?("0"+selectMinute):selectMinute);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_type_overbooking:
                selectPinlei();
                break;
            case R.id.layout_gride_overbooking:
                if("".equals(tv_type.getText().toString())){
                    Toast.makeText(getActivity().getApplicationContext(),"请先选择品类",Toast.LENGTH_SHORT).show();
                }else {
                    selectLevel();
                }
                break;
            case R.id.layout_yhq_overbooking:
                if("".equals(tv_type.getText().toString())){
                    Toast.makeText(getActivity().getApplicationContext(),"请先选择品类",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), XdYhqActivity.class);
                    intent.putExtra("type",pid);
                    startActivityForResult(intent, 480);
                }
                break;
            case R.id.overbooking_btnfabu:
                // TODO: 2016/8/8
                if(tv_type.getText().toString().equals("")||tv_gride.getText().toString().equals("")||tv_time.getText().toString().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(),"请填写完成信息,亲~",Toast.LENGTH_SHORT).show();
                }else {
                    sendQdMsg();
                }
                break;
        }
    }

    private void sendQdMsg() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/setOrderRob");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("jnid",pid);
//        Log.i("sendQdMsg", "sendQdMsg: "+pid);
        params.addBodyParameter("djid",levelid);
//        Log.i("sendQdMsg", "sendQdMsg: "+levelid);
        params.addBodyParameter("times",count);
//        Log.i("sendQdMsg", "sendQdMsg: "+count);
        params.addBodyParameter("realtime",selectDate+" "+selectTime);
//        Log.i("sendQdMsg", "sendQdMsg: "+selectDate+" "+selectTime);
        params.addBodyParameter("sex",sex);
//        Log.i("sendQdMsg", "sendQdMsg: "+sex);
        if(!tv_yhq.getText().toString().equals("")){
            params.addBodyParameter("ucid",ucid);
//            Log.i("sendQdMsg", "sendQdMsg: "+ucid);
            params.addBodyParameter("money",yhqmoney);
//            Log.i("sendQdMsg", "sendQdMsg: "+yhqmoney);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("sendQdMsg", "onSuccess: "+result);
                try {
                    JSONObject json=new JSONObject(result);
                    switch (json.getString("retcode")){
                        case "2000":
                            tv_type.setText("");
                            tv_gride.setText("");
                            tv_time.setText("");
                            tv_yhq.setText("");
                            tvCount.setText("");
                            JSONObject obj=json.getJSONObject("data");
                            String bgnum=obj.getString("bgnum");
                            String oid=obj.getString("oid");
                            Intent intent=new Intent(getActivity(), WaitGodActivity.class);
                            intent.putExtra("bgnum",bgnum);
                            intent.putExtra("oid",oid);
                            intent.putExtra("ucmoney",yhqmoney);
                            startActivity(intent);
                            Toast.makeText(getActivity().getApplicationContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "4000":
                        case "4002":
                        case "4777":
                        case "4003":
                        case "4004":
                        case "4044":
                            Toast.makeText(getActivity().getApplicationContext(),json.getString("msg"),Toast.LENGTH_SHORT).show();
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

    private void selectLevel(){
        View view2 = View.inflate(getActivity(), R.layout.item_dialoggride, null);
        wv_gride = (WheelView) view2.findViewById(R.id.wv_choose_gride);
        if(mGrides.length!=0||mGrides!=null) {
            wv_gride.setAdapter(new ArrayWheelAdapter<String>(mGrides, mGrides.length));
            wv_gride.setVisibleItems(3);
            level = mGrides[0];
            levelid = mGrideId[0];
            tv_gride.setText(level);
            wv_gride.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    int count = wv_gride.getCurrentItem();
                    Log.i("levelid", "onChanged: " + count);
                    level = mGrides[count];
                    levelid = mGrideId[count];
                    tv_gride.setText(level);
                    Log.i("levelid", "onChanged: " + levelid);
                }
            });
        }
        pw_gride = new PopupWindow(view2, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw_gride.setOutsideTouchable(true);
        pw_gride.setBackgroundDrawable(new BitmapDrawable());

        //出现在布局底端
        pw_gride.showAtLocation(Rl_all, 0, 0, Gravity.END);
        TextView delete_gride = (TextView) view2.findViewById(R.id.tv_delete);
        delete_gride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_gride.dismiss();
            }
        });
        TextView enter_gride = (TextView) view2.findViewById(R.id.tv_enter);
        enter_gride.setTextColor(Color.parseColor("#1EBAF3"));
        enter_gride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw_gride.dismiss();
            }
        });
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue)
    {
//        if (wheel == wvname)
//        {
//            updateCities();
//        }
    }
    private void selectPinlei() {
        tv_type.setText("");
        tv_gride.setText("");
        tv_time.setText("");
        tv_yhq.setText("");
        tvCount.setText("");
        View view=View.inflate(getActivity(), R.layout.item_dialogwheelview, null);
        wheelview= (com.longxiang.woniuke.customwheelview.WheelView) view.findViewById(R.id.simple_wheelview);
        wheelview.setWheelSize(3);
        wheelview.setSkin(com.longxiang.woniuke.customwheelview.WheelView.Skin.None);
        wheelview.setWheelClickable(true);
        pw = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//				//设置这2个使得点击pop以外区域可以去除pop
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        //出现在布局底端
        pw.showAtLocation(Rl_all, 0, 0, Gravity.END);
        getdata2();
        getdata();

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
                if (tv_type != null) {
                    tv_type.setText(mCurrentpl);
                }
//                        Toast.makeText(XdActivity.this, mCurrentpl, Toast.LENGTH_SHORT).show();
                pw.dismiss();
                handler.sendEmptyMessage(2);
            }
        });
    }
    private void getdata() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getAlltype");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("overbooking", "onSuccess: " + result);
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
    private ArrayList<WheelData> createDatas2(List<XmData.DataBean> dataEntities) {
        ArrayList<WheelData> list = new ArrayList<WheelData>();
        WheelData item;
        if(dataEntities!=null||dataEntities.size()!=0) {
            for (int i = 0; i < dataEntities.size(); i++) {
                item = new WheelData();
                item.setId(dataEntities.get(i).getId());
                item.setIcon(dataEntities.get(i).getIcon());
                item.setName(dataEntities.get(i).getTitle());
                list.add(item);
            }
        }
        return list;
    }
    private void getdata2() {
        wheelview.setWheelAdapter(new SimpleWheelAdapter(getActivity()));
        wheelview.setWheelSize(3);
        wheelview.setSkin(com.longxiang.woniuke.customwheelview.WheelView.Skin.None);
        wheelview.setWheelData(createDatas());
        wheelview.setWheelClickable(true);
        wheelview.setOnWheelItemClickListener(new com.longxiang.woniuke.customwheelview.WheelView.OnWheelItemClickListener() {
            @Override
            public void onItemClick(int position, Object o) {
            }
        });
        wheelview.setOnWheelItemSelectedListener(new com.longxiang.woniuke.customwheelview.WheelView.OnWheelItemSelectedListener<WheelData>() {
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
        for (int i = 0; i < 1; i++) {
            item = new WheelData();
            item.setIcon("");
            item.setName("");
            list.add(item);
        }
        return list;
    }

    private void getLevel() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getRankNew");
        params.addBodyParameter("pid",pid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiweilevel", "onSuccess: "+result+","+pid);
                SqlvData lvdata=new Gson().fromJson(result,SqlvData.class);
                // TODO: 2016/8/3
                mGrides=new String[lvdata.getData().size()];
                mGrideId=new String[lvdata.getData().size()];
                for (int i=0;i<lvdata.getData().size();i++){
                    mGrides[i]=lvdata.getData().get(i).getTitle();
                    mGrideId[i]=lvdata.getData().get(i).getId();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 480) {
                yhqmoney=data.getStringExtra("money");
                ucid=data.getStringExtra("ucid");
                tv_yhq.setText("-"+yhqmoney+"元");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        tv_type.setText("");
//        tv_gride.setText("");
//        tv_time.setText("");
//    }
}
