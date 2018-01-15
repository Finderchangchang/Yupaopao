package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.ShaixuanData;
import com.longxiang.woniuke.bean.SqlvData;
import com.longxiang.woniuke.bean.XmData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GodFlSxActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
private ImageView ivBack;
    private Button btnConfirm;
    private RadioGroup radioGroup;
    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;
    private Button btn05;
    private Button btn06;
    private Button btn07;
    private Button btn08;
    private Button btn09;
    private List<Button> pricelist;
    private int currentIndex;
    private GridView gridView;
    private String sex="";
    private String djid="";
    private String price="";
    private String jnid;
    private Button djbtn01;
    private Button djbtn02;
    private Button djbtn03;
    private Button djbtn04;
    private Button djbtn05;
    private Button djbtn06;
    private Button djbtn07;
    private Button djbtn08;
    private Button djbtn09;
    private List<Button> djbuttons;
    private int levelcurrentindex;
    private SqlvData data;
    private TextView tvTitle;
    private TextView tvGender;
    private TextView tvPrice;
    private TextView tvLevel;
    private ArrayList<String> pricearray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_god_fl_sx);
        Intent intent=getIntent();
        jnid=intent.getStringExtra("jnid");
        pricearray=intent.getStringArrayListExtra("price");
        Log.i("goddflsxactivity", "onCreate: "+pricearray.toString());
        setView();
        setData();
        setListener();
        setPrice();
        getRank();
    }

    private void setPrice() {
        for (int i=0;i<pricearray.size();i++){
            pricelist.get(i+1).setVisibility(View.VISIBLE);
            pricelist.get(i+1).setText(pricearray.get(i));
        }
        pricelist.get(0).setVisibility(View.VISIBLE);
        pricelist.get(0).setSelected(true);
    }

    private void getRank() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getRank");
        params.addBodyParameter("pid",jnid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiweirank", "onSuccess: "+result);
                data=new Gson().fromJson(result,SqlvData.class);
                for (int i=0;i<data.getData().size();i++){
                    Log.i("1245432", "onSuccess: "+"1111");
                    djbuttons.get(i+1).setVisibility(View.VISIBLE);
                    djbuttons.get(i+1).setText(data.getData().get(i).getTitle());
                }
                djbuttons.get(0).setVisibility(View.VISIBLE);
                djbuttons.get(0).setSelected(true);
//                djid=data.getData().get(0).getId();
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
        ivBack= (ImageView) findViewById(R.id.god_fl_iv_back);
        tvTitle= (TextView) findViewById(R.id.god_fl_sx_title);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        tvGender= (TextView) findViewById(R.id.god_fl_sx_tvgender);
        tvGender.setTextColor(Color.parseColor("#000000"));
        tvPrice= (TextView) findViewById(R.id.god_fl_sx_tvprice);
        tvPrice.setTextColor(Color.parseColor("#000000"));
        tvLevel= (TextView) findViewById(R.id.god_fl_sx_tvlevel);
        tvLevel.setTextColor(Color.parseColor("#000000"));
        btnConfirm= (Button) findViewById(R.id.god_fl_btn_confirm);
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        radioGroup= (RadioGroup) findViewById(R.id.god_fl_rg01);
        btn01= (Button) findViewById(R.id.god_fl_btn01);
        btn02= (Button) findViewById(R.id.god_fl_btn02);
        btn03= (Button) findViewById(R.id.god_fl_btn03);
        btn04= (Button) findViewById(R.id.god_fl_btn04);
        btn05= (Button) findViewById(R.id.god_fl_btn05);
        btn06= (Button) findViewById(R.id.god_fl_btn06);
        btn07= (Button) findViewById(R.id.god_fl_btn07);
        btn08= (Button) findViewById(R.id.god_fl_btn08);
        btn09= (Button) findViewById(R.id.god_fl_btn09);
        djbtn01= (Button) findViewById(R.id.god_fl_level_btn01);
        djbtn02= (Button) findViewById(R.id.god_fl_level_btn02);
        djbtn03= (Button) findViewById(R.id.god_fl_level_btn03);
        djbtn04= (Button) findViewById(R.id.god_fl_level_btn04);
        djbtn05= (Button) findViewById(R.id.god_fl_level_btn05);
        djbtn06= (Button) findViewById(R.id.god_fl_level_btn06);
        djbtn07= (Button) findViewById(R.id.god_fl_level_btn07);
        djbtn08= (Button) findViewById(R.id.god_fl_level_btn08);
        djbtn09= (Button) findViewById(R.id.god_fl_level_btn09);
        gridView= (GridView) findViewById(R.id.god_fl_gridview);
        btn01.setSelected(true);
    }

    private void setData() {
        pricelist=new ArrayList<>();
        pricelist.add(btn01);
        pricelist.add(btn02);
        pricelist.add(btn03);
        pricelist.add(btn04);
        pricelist.add(btn05);
        pricelist.add(btn06);
        pricelist.add(btn07);
        pricelist.add(btn08);
        pricelist.add(btn09);
        djbuttons=new ArrayList<>();
        djbuttons.add(djbtn01);
        djbuttons.add(djbtn02);
        djbuttons.add(djbtn03);
        djbuttons.add(djbtn04);
        djbuttons.add(djbtn05);
        djbuttons.add(djbtn06);
        djbuttons.add(djbtn07);
        djbuttons.add(djbtn08);
        djbuttons.add(djbtn09);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        for(int i=0;i<pricelist.size();i++){
            pricelist.get(i).setOnClickListener(this);
        }
        for (int i=0;i<djbuttons.size();i++){
            djbuttons.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.god_fl_iv_back:
                isSaveAlert();
                break;
            case R.id.god_fl_btn_confirm:
                EventBus.getDefault().post(new ShaixuanData(sex,price,djid));
                finish();
                // TODO: 2016/8/13
                break;
            case R.id.god_fl_btn01:
                currentIndex=0;
                changeprice();
                break;
            case R.id.god_fl_btn02:
                currentIndex=1;
                changeprice();
                break;
            case R.id.god_fl_btn03:
                currentIndex=2;
                changeprice();
                break;
            case R.id.god_fl_btn04:
                currentIndex=3;
                changeprice();
                break;
            case R.id.god_fl_btn05:
                currentIndex=4;
                changeprice();
                break;
            case R.id.god_fl_btn06:
                currentIndex=5;
                changeprice();
                break;
            case R.id.god_fl_btn07:
                currentIndex=6;
                changeprice();
                break;
            case R.id.god_fl_btn08:
                currentIndex=7;
                changeprice();
                break;
            case R.id.god_fl_btn09:
                currentIndex=8;
                changeprice();
                break;
            case R.id.god_fl_level_btn01:
                levelcurrentindex=0;
                changelevel();
                break;
            case R.id.god_fl_level_btn02:
                levelcurrentindex=1;
                changelevel();
                break;
            case R.id.god_fl_level_btn03:
                levelcurrentindex=2;
                changelevel();
                break;
            case R.id.god_fl_level_btn04:
                levelcurrentindex=3;
                changelevel();
                break;
            case R.id.god_fl_level_btn05:
                levelcurrentindex=4;
                changelevel();
                break;
            case R.id.god_fl_level_btn06:
                levelcurrentindex=5;
                changelevel();
                break;
            case R.id.god_fl_level_btn07:
                levelcurrentindex=6;
                changelevel();
                break;
            case R.id.god_fl_level_btn08:
                levelcurrentindex=7;
                changelevel();
                break;
            case R.id.god_fl_level_btn09:
                levelcurrentindex=8;
                changelevel();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.god_fl_rb01:
                sex="";
                break;
            case R.id.god_fl_rb02:
                sex="男";
                break;
            case R.id.god_fl_rb03:
                sex="女";
                break;
        }
    }
    private void isSaveAlert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("蜗牛壳").setMessage("是否保存?").setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sex="";
                djid="";
                price="";
                EventBus.getDefault().post(new ShaixuanData(sex,price,djid));
                finish();
            }
        }).setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().post(new ShaixuanData(sex,price,djid));
                finish();
            }
        }).create();
        builder.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            isSaveAlert();
        }
        return false;
    }
    public void changeprice(){
        for (int i=0;i<pricelist.size();i++){
            pricelist.get(i).setSelected(false);
        }
        if(currentIndex==0){
            price="";
            pricelist.get(currentIndex).setSelected(true);
        }else {
            pricelist.get(currentIndex).setSelected(true);
            price = pricelist.get(currentIndex).getText().toString();
        }
        Log.i("lvzhiweiprice", "changeprice: "+price);
    }
    public void changelevel(){
        for (int i=0;i<data.getData().size()+1;i++){
            djbuttons.get(i).setSelected(false);
        }
        if(levelcurrentindex==0){
            djid="";
            djbuttons.get(levelcurrentindex).setSelected(true);
        }else {
            djbuttons.get(levelcurrentindex).setSelected(true);
            djid = data.getData().get(levelcurrentindex-1).getId();
        }
        Log.i("lvzhiweidjid", "changelevel: "+djid);
    }
}
