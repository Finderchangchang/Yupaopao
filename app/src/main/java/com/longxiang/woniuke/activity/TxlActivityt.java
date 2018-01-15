package com.longxiang.woniuke.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.adapter.FriendAdapter;
import com.longxiang.woniuke.adapter.FriendlistAdapter;
import com.longxiang.woniuke.bean.AddFriendData;
import com.longxiang.woniuke.myview.QuickIndexBar;
import com.longxiang.woniuke.utils.Friend;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TxlActivityt extends AppCompatActivity implements View.OnClickListener, QuickIndexBar.onIndexSelectListener, AdapterView.OnItemClickListener {
    private QuickIndexBar quickbar;
    private ListView quicklv;
    private TextView pinyintv;
    private ImageView ivBack;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private ImageView ivadd;
    private TextView friendcount;
    private TextView groupdcount;
    private TextView title;
    List<Friend> friends=new ArrayList<>();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    quicklv.setAdapter(new FriendlistAdapter(friends,TxlActivityt.this));
                    break;
                case 2:
                    pinyintv.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private PopupWindow mPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txl_activityt);
        setView();
        setListener();
//        friends.clear();
        getFriend();
        getNewfriend();
    }

    private void getFriend() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/friendList");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("friend", "onSuccess: "+result);
                try {
                    friends.clear();
                    JSONObject o=new JSONObject(result);
                    Log.e("friend", "onSuccess: "+o.getJSONArray("data").length() );
                    JSONArray array = o.getJSONArray("data");
                    for (int i = 0; i<array.length(); i++){
                        JSONObject obj=array.getJSONObject(i);
                        Log.i("friend", "onSuccess: "+i);
                        friends.add(new Friend(obj.getString("nickname"),obj.getString("uid"),obj.getString("age"),obj.getString("head_pic"),obj.getString("sex"),obj.getString("sign"),obj.getDouble("distance"),obj.getString("time")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                FriendData data=new Gson().fromJson(result,FriendData.class);
//                Log.i("friend", "onSuccess: "+"1");
//                for (int i = 0; i<data.getData().size(); i++){
//                    Log.i("friend", "onSuccess: "+i);
//                    friends.add(new Friend(data.getData().get(i).getNickname(),data.getData().get(i).getUid(),data.getData().get(i).getAge(),data.getData().get(i).getHead_pic(),data.getData().get(i).getSex(),data.getData().get(i).getSign(),data.getData().get(i).getDistance(),data.getData().get(i).getTime()));
//                }
                Log.i("friend", "onSuccess: "+friends.size());
                Collections.sort(friends);
                handler.sendEmptyMessage(1);
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
        ivBack= (ImageView) findViewById(R.id.txl_iv_back);
        rl01= (RelativeLayout) findViewById(R.id.txl_rl01);
//        rl02= (RelativeLayout) findViewById(R.id.txl_rl02);
        friendcount= (TextView) findViewById(R.id.txl_iv_count01);
        friendcount.setTextColor(Color.parseColor("#ff1111"));
//        friendcount= (TextView) findViewById(R.id.txl_iv_count02);
        quickbar= (QuickIndexBar) findViewById(R.id.txl_quickbar);
        quicklv= (ListView) findViewById(R.id.txl_lv_quick);
        pinyintv= (TextView) findViewById(R.id.txl_tv_pinyin);
        ivadd= (ImageView) findViewById(R.id.txl_iv_add);
        title= (TextView) findViewById(R.id.txl_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        rl01.setOnClickListener(this);
//        rl02.setOnClickListener(this);
        quicklv.setTextFilterEnabled(true);
        quicklv.setOnItemClickListener(this);
        quickbar.setOnIndexSelectListener(this);
        ivadd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.txl_iv_back:
                finish();
                break;
            case R.id.txl_rl01:
                intent=new Intent(this,NewfriendActivity.class);
                startActivity(intent);
                break;
//            case R.id.txl_rl02:
//                break;
            case R.id.txl_iv_add:
                showpopwindow();
                break;
            case R.id.txl_add_item_rl01:
                intent=new Intent(this,AddFriendActivity.class);
                startActivity(intent);
                mPopWindow.dismiss();
                break;
//            case R.id.txl_add_item_rl02:
//                break;
//            case R.id.txl_add_item_rl03:
//                break;
        }
    }

    private void showpopwindow() {
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        View contentView = LayoutInflater.from(this).inflate(R.layout.txl_add_item, null);
        RelativeLayout rl01= (RelativeLayout) contentView.findViewById(R.id.txl_add_item_rl01);
//        RelativeLayout rl02= (RelativeLayout) contentView.findViewById(R.id.txl_add_item_rl02);
//        RelativeLayout rl03= (RelativeLayout) contentView.findViewById(R.id.txl_add_item_rl03);
        rl01.setOnClickListener(this);
//        rl02.setOnClickListener(this);
//        rl03.setOnClickListener(this);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(width*9/2/10);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        mPopWindow.showAsDropDown(ivadd);
        mPopWindow.setOnDismissListener(new poponDismissListener());
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fid=friends.get(position).getUid();
        Intent intent=new Intent(this,FriendDataActivity.class);
        intent.putExtra("fid",fid);
        startActivity(intent);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }
    @Override
    public void onIndexSelect(String word, int index) {
        Log.i("435345", "onIndexSelect: "+"221");
        for (int i = 0; i < friends.size(); i++) {
            Friend friend = friends.get(i);
            if (friend.getPinYin().toUpperCase().startsWith(word)) {
                pinyintv.setVisibility(View.VISIBLE);
                quicklv.setSelection(i);
                pinyintv.setText(word);
                handler.removeMessages(2);
                handler.sendEmptyMessageDelayed(2, 1000);
//                textView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setVisibility(View.GONE);
//                    }
//                }, 3000);
                Log.i("435345", "onIndexSelect: "+"221");
                return;//找到第一个后就退出,防止最终是最后一个滚到顶部
            }
        }
    }
    private void getNewfriend() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/newfriendList");
        params.addBodyParameter("uid", MyApp.uid);
        params.addBodyParameter("lat",MyApp.lat_经度);
        params.addBodyParameter("lng",MyApp.lng_纬度);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("newfriend", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "4002":
                            friendcount.setText("0");
                            break;
                        case "2000":
                            AddFriendData data=new Gson().fromJson(result,AddFriendData.class);
                            friendcount.setText(data.getData().size()+"");
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
    protected void onResume() {
        super.onResume();
        friends.clear();
        getFriend();
        getNewfriend();
    }
}
