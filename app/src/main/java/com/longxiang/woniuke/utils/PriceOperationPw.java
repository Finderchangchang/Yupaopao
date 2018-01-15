package com.longxiang.woniuke.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.JdSettingActivity;
import com.longxiang.woniuke.adapter.JdsettingAdapter;
import com.longxiang.woniuke.bean.GodPriceData;
import com.longxiang.woniuke.bean.JdsettingData;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class PriceOperationPw extends PopupWindow implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private final View mMenuView;
    private Button btn_confirm;
    private RadioGroup rgmoney;
    private RadioGroup rgzhekou;
    private RadioButton rbprice01;
    private RadioButton rbprice02;
    private RadioButton rbprice03;
    private RadioButton rbzhekou01;
    private RadioButton rbzhekou02;
    private RadioButton rbzhekou03;
    private JdsettingData data;
    private JdsettingAdapter adapter;
    private  Context context;
    private String bgid;
    private String djid;
    private int position;
    private TextView tvmoney;
    private TextView tvzhekou;
    private List<RadioButton> pricelist=new ArrayList<>();
    private List<RadioButton> zhekoulist=new ArrayList<>();
    private double price;
    private double clickrebate;
    private String rebateflag="";
    public PriceOperationPw(Activity context, JdsettingAdapter adapter, String bgid, String djid, int position, JdsettingData data) {
        super(context);
        this.data=data;
        this.adapter=adapter;
        this.context=context;
        this.bgid=bgid;
        this.djid=djid;
        this.position=position;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.price_operation, null);
        tvmoney= (TextView) mMenuView.findViewById(R.id.price_operation_money);
        tvmoney.setTextColor(Color.parseColor("#1EBAF3"));
        tvzhekou= (TextView) mMenuView.findViewById(R.id.price_operation_zhekou);
        tvzhekou.setTextColor(Color.parseColor("#1EBAF3"));
        rbprice01= (RadioButton) mMenuView.findViewById(R.id.price_operation_jiage01);
        rbprice02= (RadioButton) mMenuView.findViewById(R.id.price_operation_jiage02);
        rbprice03= (RadioButton) mMenuView.findViewById(R.id.price_operation_jiage03);
        rbzhekou01= (RadioButton) mMenuView.findViewById(R.id.price_operation_wuzhekou);
        rbzhekou02= (RadioButton) mMenuView.findViewById(R.id.price_operation_wuzhe);
        rbzhekou03= (RadioButton) mMenuView.findViewById(R.id.price_operation_bazhe);
        pricelist.add(rbprice01);
        pricelist.add(rbprice02);
        pricelist.add(rbprice03);
        zhekoulist.add(rbzhekou01);
        zhekoulist.add(rbzhekou02);
        zhekoulist.add(rbzhekou03);
        rgmoney= (RadioGroup) mMenuView.findViewById(R.id.price_operation_rgprice);
        rgzhekou= (RadioGroup) mMenuView.findViewById(R.id.price_operation_rgzhekou);
        btn_confirm = (Button) mMenuView.findViewById(R.id.price_operation_confirm);
        btn_confirm.setTextColor(Color.parseColor("#ffffff"));
        btn_confirm.setOnClickListener(this);
        rgmoney.setOnCheckedChangeListener(this);
        rgzhekou.setOnCheckedChangeListener(this);
        //设置按钮监听
//        btn_jubao.setOnClickListener(itemsOnClick);
//        btn_lahei.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.dialog_enter);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xa0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.price_operation_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        getPriceAndRebate();
    }

    private void getPriceAndRebate() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getPriceAndRebate");
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("djid",djid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("priceoperation", "onSuccess: "+result);
                GodPriceData data=new Gson().fromJson(result,GodPriceData.class);
                String basicprice=data.getData().getPriceandrebate().getBasicprice();
                String rebate=data.getData().getPriceandrebate().getRebate();
                for (int i=0;i<data.getData().getPricerange().size();i++){
                    pricelist.get(i).setVisibility(View.VISIBLE);
                    pricelist.get(i).setText(data.getData().getPricerange().get(i));
                    if(basicprice.equals(data.getData().getPricerange().get(i))){
                        pricelist.get(i).setChecked(true);
                    }
                }
                price=Double.valueOf(basicprice);
                if(rebate.equals("1")){
                    rbzhekou01.setChecked(true);
                    tvmoney.setText(price+"元");
                    rebateflag="1";
                    clickrebate=1.0;
                    tvzhekou.setText("(已选无折扣)");
                }
                if(rebate.equals("2")){
                    rbzhekou02.setChecked(true);
                    clickrebate=0.8;
                    tvmoney.setText(price*clickrebate+"元");
                    rebateflag="2";
                    tvzhekou.setText("(已选8折)");
                }
                if(rebate.equals("3")){
                    rbzhekou03.setChecked(true);
                    clickrebate=0.5;
                    tvmoney.setText(price*clickrebate+"元");
                    rebateflag="3";
                    tvzhekou.setText("(已选5折)");
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.price_operation_confirm:
                setPriceAndRebate();
                break;
        }
    }

    private void setPriceAndRebate() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/setPriceAndRebate");
        params.addBodyParameter("bgid",bgid);
        params.addBodyParameter("basicprice",price+"");
        params.addBodyParameter("rebate",rebateflag);
        params.addBodyParameter("price",price*clickrebate+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("setPriceAndRebate", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                   switch (obj.getString("retcode")){
                       case "2000":
                           data.getData().get(position).setPrice(Double.valueOf(price)*clickrebate+"");
                           adapter.notifyDataSetChanged();
                           Toast.makeText(context.getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                           dismiss();
                           break;
                       case "4001":
                           Toast.makeText(context.getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group==rgmoney){
            switch (checkedId){
                case R.id.price_operation_jiage01:
                    price=Double.valueOf(rbprice01.getText().toString());
                    break;
                case R.id.price_operation_jiage02:
                    price=Double.valueOf(rbprice02.getText().toString());
                    break;
                case R.id.price_operation_jiage03:
                    price=Double.valueOf(rbprice03.getText().toString());
                    break;
            }
            tvmoney.setText(price*clickrebate+"元");
            Log.i("oncheckchanged", "onCheckedChanged: "+price*clickrebate);
        }
        if(group==rgzhekou){
            switch (checkedId) {
                case R.id.price_operation_wuzhekou:
                    clickrebate=1.0;
                    rebateflag="1";
                    tvzhekou.setText("(已选无折扣)");
                    break;
                case R.id.price_operation_wuzhe:
                    clickrebate=0.8;
                    rebateflag="2";
                    tvzhekou.setText("(已选8折)");
                    break;
                case R.id.price_operation_bazhe:
                    clickrebate=0.5;
                    rebateflag="3";
                    tvzhekou.setText("(已选5折)");
                    break;
            }
            tvmoney.setText(price*clickrebate+"元");
        }
    }
}
