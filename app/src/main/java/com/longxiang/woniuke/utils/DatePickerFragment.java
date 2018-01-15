package com.longxiang.woniuke.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.longxiang.woniuke.activity.RegistMsgActivity;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/3.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    public static String birthday;
    public static String star;
    public static String age;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year1 = c.get(Calendar.YEAR);
        int month2 = c.get(Calendar.MONTH);
        int day3 = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthday=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                Log.i("lvzhiweiBir", "onDateSet: "+birthday);
                RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getStarcharOrAge");
                params.addBodyParameter("birthday",birthday);
                Log.i("lvzhiweiBir", "onClick: "+birthday);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("lvzhiweiBir", "onSuccess: "+result);
                        try{
                            JSONObject json=new JSONObject(result);
                            JSONObject obj=json.getJSONObject("data");
                            star =obj.getString("starchar");
                            age=obj.getString("age");
                            RegistMsgActivity.tvAge.setText(birthday+star);
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
        },year1,month2,day3);
//        return new DatePickerDialog(getActivity(),this, year, month, day);
//        dialog.setButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        try {
            Date date=new Date();
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth=calendar.get(Calendar.MONTH);
            int mDay=calendar.get(Calendar.DAY_OF_MONTH);
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse((mYear-12)+"-"+(mMonth+1)+"-"+(mDay));
            dialog.getDatePicker().setMaxDate(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("OnDateSet", "select year:"+year+";month:"+monthOfYear+";day:"+dayOfMonth);
    }
//    @Override
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        Log.d("OnDateSet", "select year:"+year+";month:"+monthOfYear+";day:"+dayOfMonth);
//        SecondFragment.time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
//    }
}
