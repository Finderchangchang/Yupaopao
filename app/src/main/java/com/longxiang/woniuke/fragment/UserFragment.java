package com.longxiang.woniuke.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longxiang.woniuke.activity.AccountActivity;
import com.longxiang.woniuke.activity.AmdsActivity;
import com.longxiang.woniuke.activity.EditPersonMsgActivity;
import com.longxiang.woniuke.activity.HelpActivity;
import com.longxiang.woniuke.activity.JiFenActivity;
import com.longxiang.woniuke.activity.MemberCenterActivity;
import com.longxiang.woniuke.activity.MyDtActivity;
import com.longxiang.woniuke.activity.OrderCenterActivity;
import com.longxiang.woniuke.activity.PurseActivity;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.SettingActivity;
import com.longxiang.woniuke.activity.SqdsActivity;
import com.longxiang.woniuke.activity.YhqActivity;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private View sub;
    private TextView title;
    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvAge;
    private RelativeLayout rl_incontext;
    private RelativeLayout rlEdit;
    private LinearLayout llZh;
    private LinearLayout llJf;
    private LinearLayout llYhq;
    private LinearLayout llHy;
    private LinearLayout llDt;
    private LinearLayout llDd;
    private LinearLayout llSq;
    private LinearLayout llSz;
    private LinearLayout llBz;
    private TextView tvZh;
    private TextView tvJf;
    private TextView tvYhq;
    private TextView tvShenhe;
    private TextView tvSq;
    private String name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_userfragment,null);
        init(view);
        setListener();
        getUserInfo();
        getAccount();
        getGodstate();
        return view;
    }

    private void getGodstate() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getgodstate");
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ewqewq", "onSuccess: "+result);
                try {
                    JSONObject json =new JSONObject(result);
                    JSONArray array=json.getJSONArray("data");
                    if(json.getString("retcode").equals("4005")){
                        tvSq.setText("申请大神");
                        llSq.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getActivity(), SqdsActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                    for (int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        String state=obj.getString("state");
                        if(state.equals("2")){
                            tvSq.setText("我是大神");
                            llSq.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(), AmdsActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
//                        if(state.equals("1")){
//                            tvSq.setText("申请大神");
//                            llSq.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent=new Intent(getActivity(), SqdsActivity.class);
//                                    startActivity(intent);
//                                }
//                            });
//                        }
                    }

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

    private void getAccount() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmywallet");
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei231",result);
                try {
                    JSONObject json =new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    tvZh.setText(obj.getString("wallet")+"元");
                    tvJf.setText(obj.getString("points")+"分");
                    tvYhq.setText(obj.getString("coupon")+"张");
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

    private void getUserInfo() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmineinfo");
        params.addBodyParameter("uid", MyApp.uid);
        Log.i("lvzhiwei222", "getUserInfo: "+MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("lvzhiwei222", "onSuccess: "+result);
                try {
                    JSONObject json=new JSONObject(result);
                    JSONObject obj=json.getJSONObject("data");
                    x.image().bind(ivIcon,obj.getString("head_pic"));
                    name=obj.getString("nickname");
                    tvName.setText(name);
                    if(obj.getString("sex").equals("男")){
                        tvAge.setSelected(true);
                        tvAge.setText(" "+obj.getString("age")+" ");
                        tvAge.setBackgroundResource(R.drawable.conner_man_bg);
//                        tvAge.setBackgroundColor(Color.parseColor("#31C0F4"));
                    }else if(obj.getString("sex").equals("女")){
                        tvAge.setSelected(false);
                        tvAge.setText(" "+obj.getString("age")+" ");
                        tvAge.setBackgroundResource(R.drawable.conner_women_bg);
//                        tvAge.setBackgroundColor(Color.parseColor("#FC88AF"));
                    }
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

    private void setListener() {
        rlEdit.setOnClickListener(this);
        rl_incontext.setOnClickListener(this);
        llZh.setOnClickListener(this);
        llJf.setOnClickListener(this);
        llYhq.setOnClickListener(this);
//        llHy.setOnClickListener(this);
        llDt.setOnClickListener(this);
        llDd.setOnClickListener(this);
//        llSq.setOnClickListener(this);
        llSz.setOnClickListener(this);
        llBz.setOnClickListener(this);
    }

    private void init(View view) {
        sub = (View) view.findViewById(R.id.sub_title_user);
        title = (TextView) sub.findViewById(R.id.tv_title_name);
        title.setText("我的");
        title.setTextColor(Color.WHITE);
        rlEdit= (RelativeLayout) view.findViewById(R.id.userfragment_rlEdit);
        rl_incontext = (RelativeLayout) view.findViewById(R.id.rl_incontext);
        llZh= (LinearLayout) view.findViewById(R.id.userfragment_ll_zh);
        llJf= (LinearLayout) view.findViewById(R.id.userfragment_ll_jf);
        llYhq= (LinearLayout) view.findViewById(R.id.userfragment_ll_yhq);
//        llHy= (LinearLayout) view.findViewById(R.id.userfragment_ll_huiyuan);
        llDt= (LinearLayout) view.findViewById(R.id.userfragment_ll_wodedongtai);
        llDd= (LinearLayout) view.findViewById(R.id.userfragment_ll_dingdan);
        llSq= (LinearLayout) view.findViewById(R.id.userfragment_ll_shenqing);
        llSz= (LinearLayout) view.findViewById(R.id.userfragment_ll_setting);
        llBz= (LinearLayout) view.findViewById(R.id.userfragment_ll_help);
        ivIcon= (ImageView) view.findViewById(R.id.userfragment_ivPic);
        tvName= (TextView) view.findViewById(R.id.userfragment_tvNickname);
        tvAge= (TextView) view.findViewById(R.id.userfragment_tvAge);
        tvZh= (TextView) view.findViewById(R.id.tv_userzh);
        tvJf= (TextView) view.findViewById(R.id.tv_userjf);
        tvYhq= (TextView) view.findViewById(R.id.tv_useryhq);
        tvShenhe= (TextView) view.findViewById(R.id.userfragment_tvshenhe);
        tvSq= (TextView) view.findViewById(R.id.userfragment_tvSq);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.userfragment_rlEdit:
                intent=new Intent(getActivity(), EditPersonMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_incontext:
                intent=new Intent(getActivity(), PurseActivity.class);
                startActivity(intent);
                break;
            case R.id.userfragment_ll_zh:
                intent=new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.userfragment_ll_jf:
                intent=new Intent(getActivity(), JiFenActivity.class);
                startActivity(intent);
                break;
            case R.id.userfragment_ll_yhq:
                intent=new Intent(getActivity(), YhqActivity.class);
                startActivity(intent);
                break;
//            case R.id.userfragment_ll_huiyuan:
//                intent=new Intent(getActivity(), MemberCenterActivity.class);
//                startActivity(intent);
//                break;
            case R.id.userfragment_ll_wodedongtai:
                intent=new Intent(getActivity(), MyDtActivity.class);
                startActivity(intent);
                break;
            case R.id.userfragment_ll_dingdan:
                intent=new Intent(getActivity(), OrderCenterActivity.class);
                startActivity(intent);
                break;
//            case R.id.userfragment_ll_shenqing:
//                intent=new Intent(getActivity(), SqdsActivity.class);
//                startActivity(intent);
//                break;
            case R.id.userfragment_ll_setting:
                intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.userfragment_ll_help:
                intent=new Intent(getActivity(), HelpActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        getUserInfo();
        getAccount();
        getGodstate();
        super.onResume();
    }
}
