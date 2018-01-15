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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.ConversationListActivity;
import com.longxiang.woniuke.activity.MainActivity;
import com.longxiang.woniuke.activity.OrderCenterActivity;
import com.longxiang.woniuke.activity.QdCenterActivity;
import com.longxiang.woniuke.activity.SystemMsgActivity;
import com.longxiang.woniuke.adapter.OrderAdapter;
import com.longxiang.woniuke.bean.OrderData;
import com.longxiang.woniuke.bean.QdData;
import com.longxiang.woniuke.bean.ShaixuanData;
import com.longxiang.woniuke.bean.SystemMsgData;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class MessageFragment extends Fragment implements View.OnClickListener {
    private TextView title;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private RelativeLayout rl04;
    public  TextView chatcount;
    private TextView tvDdtitle;
    private TextView tvDdtime;
    private TextView tvDdhongdian;
    private TextView tvXttitle;
    private TextView tvXttime;
    private TextView tvXthongdian;
//    private TextView tvQdtitle;
//    private TextView tvQdtime;
    private TextView tvQdhongdian;
    private List<TextView> hdlist;
    private int chatshu=0;
    private int totalmsg=0;
    private String lasttotal="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_messagefragment,null);
        title = (TextView) view.findViewById(R.id.tv_title_message);
        title.setTextColor(Color.WHITE);
        EventBus.getDefault().register(this);
        setView(view);
        setData();
        setListener();
        getDdOrder();
        getQdOrder();
        getSystemMsg();
        getUnreadNumAll();
        RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new RongIM.OnReceiveUnreadCountChangedListener() {
            @Override
            public void onMessageIncreased(int i) {
//                Log.i("lvzhiweiUnread",i+"");
                chatshu=i;
                if(i>0){
                    chatcount.setVisibility(View.VISIBLE);
                    if(i>99){
                        chatcount.setText("99");
                    }else{
                        chatcount.setText(i+"");
                    }
                }else{
                    chatcount.setVisibility(View.GONE);
                }
                getUnreadNumAll();
            }
        }, Conversation.ConversationType.PRIVATE,Conversation.ConversationType.GROUP);
        if(lasttotal.equals("")||lasttotal.equals("0")){
            MainActivity.tvhd.setVisibility(View.GONE);
        }
        if(chatcount.getVisibility()==View.VISIBLE) {
            if (Integer.parseInt(chatcount.getText().toString()) < 0) {
                chatcount.setVisibility(View.GONE);
            }
        }
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void countEventBus(String post){
        Log.i("lvzhiweieventbus", "countEventBus: "+post+",");
        getDdOrder();
        getQdOrder();
        getSystemMsg();
        getUnreadNumqd();
        getUnreadNumdd();
        getUnreadNumsys();
        getUnreadNumAll();
    }
    private void getUnreadNumsys() {
        RequestParams params =new RequestParams("http://bubblefish.jbserver.cn/api/friend/getUnreadNum");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("state","3");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("sysmsg", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            tvXthongdian.setVisibility(View.VISIBLE);
                            if(Integer.parseInt(obj.getString("data"))>99){
                                tvXthongdian.setText("99");
                            }else{
                                tvXthongdian.setText(obj.getString("data"));
                            }
                            break;
                        case "4000":
                            tvXthongdian.setText("0");
                            tvXthongdian.setVisibility(View.GONE);
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
    private void getUnreadNumdd() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getUnreadNum");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("state","2");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ddmsg", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            tvDdhongdian.setVisibility(View.VISIBLE);
                            if(Integer.parseInt(obj.getString("data"))>99){
                                tvDdhongdian.setText("99");
                            }else{
                                tvDdhongdian.setText(obj.getString("data"));
                            }
                            break;
                        case "4000":
                            tvDdhongdian.setText("0");
                            tvDdhongdian.setVisibility(View.GONE);
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

    private void getUnreadNumqd() {
        RequestParams params =new RequestParams("http://bubblefish.jbserver.cn/api/friend/getUnreadNum");
        params.addBodyParameter("uid",MyApp.uid);
        params.addBodyParameter("state","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("qdxiaoxi", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            tvQdhongdian.setVisibility(View.VISIBLE);
                            if(Integer.parseInt(obj.getString("data"))>99){
                                tvQdhongdian.setText("99");
                            }else{
                                tvQdhongdian.setText(obj.getString("data"));
                            }
                            break;
                        case "4000":
                            tvQdhongdian.setText("0");
                            tvQdhongdian.setVisibility(View.GONE);
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

    private void getUnreadNumAll() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getUnreadNumAll");
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("messagetotal", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            MainActivity.tvhd.setVisibility(View.VISIBLE);
                            totalmsg=Integer.parseInt(obj.getString("data"));
                            Log.i("chatcount", "onSuccess: "+chatshu);
                            lasttotal=totalmsg+chatshu+"";
                            if(Integer.parseInt(lasttotal)>99){
                                MainActivity.tvhd.setText("99");
                            }else{
                                MainActivity.tvhd.setText(lasttotal);
                            }
                            break;
                        case "4000":
                            JSONArray arr =obj.getJSONArray("data");
                            if(chatshu==0&&arr.length()==0){
                                MainActivity.tvhd.setText("0");
                                MainActivity.tvhd.setVisibility(View.GONE);
                            }
                            if(chatshu>0&&arr.length()==0){
                                MainActivity.tvhd.setVisibility(View.VISIBLE);
                                if(chatshu>99){
                                    MainActivity.tvhd.setText("99");
                                }else {
                                    MainActivity.tvhd.setText(chatshu + "");
                                }
                            }
                            else{
                                lasttotal="0";
                                MainActivity.tvhd.setVisibility(View.VISIBLE);
                                MainActivity.tvhd.setText(Integer.parseInt(lasttotal)-chatshu+"");
                                if(MainActivity.tvhd.getText().toString().equals("0")) {
                                    MainActivity.tvhd.setVisibility(View.GONE);
                                }
                            }

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

    private void setData() {
        hdlist=new ArrayList<>();
        hdlist.add(tvXthongdian);
        hdlist.add(tvDdhongdian);
        hdlist.add(tvQdhongdian);
        for(int i=0;i<hdlist.size();i++){
            if(hdlist.get(i).getVisibility()==View.VISIBLE){
                MainActivity.tvhd.setVisibility(View.VISIBLE);
            }else{
                MainActivity.tvhd.setVisibility(View.GONE);
            }
        }
    }

    private void getSystemMsg() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getSystemMessage");
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SystemMsgData data=new Gson().fromJson(result,SystemMsgData.class);
                if(data.getData().size()!=0) {
                    SystemMsgData.DataBean datas = data.getData().get(0);
                    tvXttitle.setText(datas.getContent());
                    tvXttime.setText(datas.getAdd_time());
//                    xtread = datas.getRead();
//                    if (Integer.parseInt(xtread) == 0) {
//                        tvXthongdian.setVisibility(View.VISIBLE);
//                    } else {
//                        tvXthongdian.setVisibility(View.GONE);
//                    }
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

    private void getQdOrder() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getRobOrder");
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("qdmessage", "onSuccess: "+result);
                QdData data=new Gson().fromJson(result,QdData.class);
                if(data.getData().size()!=0){
//                    QdData.DataBean datas = data.getData().get(data.getData().size() - 1);
//                    tvQdtime.setText(datas.getOrder().getRealtime());
//                    tvQdtitle.setText(datas.get);
//                    qdread=datas.getRead();
//                    if (Integer.parseInt(qdread) == 0) {
//                        tvQdhongdian.setVisibility(View.VISIBLE);
//                    } else {
//                        tvQdhongdian.setVisibility(View.GONE);
//                    }
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
        rl01.setOnClickListener(this);
        rl02.setOnClickListener(this);
        rl03.setOnClickListener(this);
        rl04.setOnClickListener(this);
    }

    private void setView(View view) {
        rl01= (RelativeLayout) view.findViewById(R.id.message_rl01);
        rl02= (RelativeLayout) view.findViewById(R.id.message_rl02);
        rl03= (RelativeLayout) view.findViewById(R.id.message_rl03);
        rl04= (RelativeLayout) view.findViewById(R.id.message_rl04);
        tvDdtitle= (TextView) view.findViewById(R.id.message_ding_detail);
        tvDdtime= (TextView) view.findViewById(R.id.message_ding_time);
        tvDdhongdian= (TextView) view.findViewById(R.id.message_ding_hongdian);
        tvXttitle= (TextView) view.findViewById(R.id.message_system_content);
        tvXttime= (TextView) view.findViewById(R.id.message_system_time);
        tvXthongdian= (TextView) view.findViewById(R.id.message_system_hongdian);
//        tvQdtitle= (TextView) view.findViewById(R.id.message_qiang_content);
//        tvQdtime= (TextView) view.findViewById(R.id.message_qiang_time);
        tvQdhongdian= (TextView) view.findViewById(R.id.message_qiang_hongdian);
        chatcount= (TextView) view.findViewById(R.id.message_chat_count);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_rl01:
                if(RongIM.getInstance()!=null) {
                    RongIM.getInstance().startConversationList(getActivity());
                }
                break;
            case R.id.message_rl02:
                startActivity(new Intent(getActivity(), SystemMsgActivity.class));
                break;
            case R.id.message_rl03:
                startActivity(new Intent(getActivity(), QdCenterActivity.class));
                break;
            case R.id.message_rl04:
                startActivity(new Intent(getActivity(),OrderCenterActivity.class));
                break;
        }
    }
    private void getDdOrder() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/order/getGeneralOrder");
        params.addBodyParameter("uid", MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("orderdata", "onSuccess: "+result);
                OrderData data=new Gson().fromJson(result,OrderData.class);
                if(data.getData().size()!=0) {
                    tvDdtitle.setText(data.getData().get(data.getData().size() - 1).getContent());
                    tvDdtime.setText(data.getData().get(data.getData().size() - 1).getAdd_time());
//                    ddread = data.getData().get(data.getData().size() - 1).getRead();
//                    if (Integer.parseInt(ddread) == 0) {
//                        tvDdhongdian.setVisibility(View.VISIBLE);
//                    } else {
//                        tvDdhongdian.setVisibility(View.GONE);
//                    }
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
    public void onResume() {
        super.onResume();
        getDdOrder();
        getQdOrder();
        getSystemMsg();
        getUnreadNumsys();
        getUnreadNumdd();
        getUnreadNumqd();
        getUnreadNumAll();
        if(chatcount.getVisibility()==View.VISIBLE) {
            if (Integer.parseInt(chatcount.getText().toString()) < 0) {
                chatcount.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
