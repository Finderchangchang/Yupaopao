package com.longxiang.woniuke.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by mac on 16/9/23.
 */
public class DashangDialog extends AlertDialog implements View.OnClickListener, TextView.OnEditorActionListener {
    private final EditText et;
    private final AlertDialog alertDialog;
    private Context context;
    private String dmid;
    private int currentindex;
    private List<Button> buttons=new ArrayList<>();
    private String money;
    private InputMethodManager imm;

    public DashangDialog(Context context,String dmid) {
        super(context);
        this.context=context;
        this.dmid=dmid;
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dashang_dialog_item);
        alertDialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button btn01= (Button) window.findViewById(R.id.dashang_bt01);
        Button btn02= (Button) window.findViewById(R.id.dashang_bt02);
        Button btn03= (Button) window.findViewById(R.id.dashang_bt03);
        Button btn04= (Button) window.findViewById(R.id.dashang_bt04);
        Button btn05= (Button) window.findViewById(R.id.dashang_bt05);
        Button btn06= (Button) window.findViewById(R.id.dashang_bt06);
        et= (EditText) window.findViewById(R.id.dashang_et);
        et.setFocusable(false);
        et.setOnEditorActionListener(this);
        et.setOnClickListener(this);
        Button confirm= (Button) window.findViewById(R.id.dashang_btnconfirm);
        buttons.add(btn01);
        buttons.add(btn02);
        buttons.add(btn03);
        buttons.add(btn04);
        buttons.add(btn05);
        buttons.add(btn06);
        btn01.setSelected(true);
        money="1";
        for (int i=0;i<buttons.size();i++){
            buttons.get(i).setOnClickListener(this);
        }
        confirm.setOnClickListener(this);
        imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dashang_bt01:
                currentindex=0;
                changeMoney();
                break;
            case R.id.dashang_bt02:
                currentindex=1;
                changeMoney();
                break;
            case R.id.dashang_bt03:
                currentindex=2;
                changeMoney();
                break;
            case R.id.dashang_bt04:
                currentindex=3;
                changeMoney();
                break;
            case R.id.dashang_bt05:
                currentindex=4;
                changeMoney();
                break;
            case R.id.dashang_bt06:
                currentindex=5;
                changeMoney();
                break;
            case R.id.dashang_et:
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                et.setFocusable(true);
                et.setFocusableInTouchMode(true);
                et.requestFocus();
                et.findFocus();
                for (int i=0;i<buttons.size();i++){
                    buttons.get(i).setSelected(false);
                }
                money="";
                break;
            case R.id.dashang_btnconfirm:
                if(!et.getText().toString().equals("")){
                    money=et.getText().toString();
                }
                Log.i("dashangdialogmoney", "onClick: "+money);
                if(money.equals("")){
                    Toast.makeText(context,"打赏点吧...",Toast.LENGTH_SHORT).show();
                }else{
                    alertdialog();
                }
                break;
        }
    }

    private void alertdialog() {
        android.support.v7.app.AlertDialog.Builder dialog=new android.support.v7.app.AlertDialog.Builder(context);
        dialog.setMessage("是否打赏?")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takeReward();
                dialog.dismiss();
                alertDialog.dismiss();
            }
        }).create();
        dialog.show();
    }

    private void takeReward() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/takeReward");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("dmid",dmid);
        params.addBodyParameter("amount",money);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("takereward", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(context.getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            break;
                        case "4000":
                        case "4002":
                        case "4003":
                        case "4005":
                        case "4006":
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

    public void changeMoney(){
        et.setFocusable(false);
        et.setText("");
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        for (int i=0;i<buttons.size();i++){
            buttons.get(i).setSelected(false);
        }
        buttons.get(currentindex).setSelected(true);
        money=buttons.get(currentindex).getText().toString().substring(0,buttons.get(currentindex).getText().toString().length()-1);
        Log.i("dashangdialog", "changeMoney: "+money);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        /*判断是否是“Search”键*/
        if(actionId == EditorInfo.IME_ACTION_DONE){
                          /*隐藏软键盘*/
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(
                        v.getApplicationWindowToken(), 0);
            }
            return true;
        }
        return false;
    }
}
