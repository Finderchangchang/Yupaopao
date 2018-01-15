package com.longxiang.woniuke.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthdayActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private TextView tvSave;
    private TextView tvAge;
    private TextView tvStar;
    private DatePicker datePicker;
    private String birthday;
    private String age;
    private String star;
    private int year;
    private int month;
    private int day;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        setView();
        Intent intent=getIntent();
        birthday=intent.getStringExtra("birthday");
        age=intent.getStringExtra("age");
        star=intent.getStringExtra("star");
        tvAge.setText(age+"岁");
        tvStar.setText(star);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            Calendar c=Calendar.getInstance();
            c.setTime(date);
            year=c.get(Calendar.YEAR);
            month=c.get(Calendar.MONTH);
            day=c.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setListener();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.birthday_iv_back);
        tvSave= (TextView) findViewById(R.id.birthday_tvSave);
        tvAge= (TextView) findViewById(R.id.birthday_tvAge);
        tvStar= (TextView) findViewById(R.id.birthday_tvStar);
        datePicker= (DatePicker) findViewById(R.id.birthday_datepicker);
        datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        title= (TextView) findViewById(R.id.birthday_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        tvSave.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        try {
            Date date=new Date();
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth=calendar.get(Calendar.MONTH);
            int mDay=calendar.get(Calendar.DAY_OF_MONTH);
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse((mYear-12)+"-"+(mMonth+1)+"-"+(mDay));
            datePicker.setMaxDate(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datePicker.init(year, month , day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthday=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                Log.i("lvzhiweiBir", "onDateSet: "+birthday);
                RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getStarcharOrAge");
                params.addBodyParameter("birthday",birthday);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("lvzhiweiBir", "onSuccess: "+result);
                        try{
                            JSONObject json=new JSONObject(result);
                            JSONObject obj=json.getJSONObject("data");
                            star =obj.getString("starchar");
                            age=obj.getString("age");
                            tvAge.setText(age);
                            tvStar.setText(star);
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
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.birthday_iv_back:
                finish();
                break;
            case R.id.birthday_tvSave:
                AlertDialog.Builder dialog=new AlertDialog.Builder(BirthdayActivity.this);
                dialog.setMessage("是否保存?")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.putExtra("age",tvAge.getText().toString());
                        intent.putExtra("star",tvStar.getText().toString());
                        intent.putExtra("birthday",birthday);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }).create();
                dialog.show();
                break;
        }
    }
}
