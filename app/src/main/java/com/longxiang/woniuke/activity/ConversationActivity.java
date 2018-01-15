package com.longxiang.woniuke.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.DataBean;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Locale;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.LocationMessage;

public class ConversationActivity extends FragmentActivity implements RongIM.LocationProvider, RongIM.ConversationBehaviorListener, View.OnClickListener {
private ImageView ivBack;
    private TextView tvTitle;
    private String mTitle;
    private String mTargetId;
    private String mTargetIds;
    private ImageView ivDetaildata;
    private ImageView ivtypeimg;
    private TextView tvtypename;
    private TextView tvlevel;
    private TextView tvprice;
    private Button btnMakeorder;
    private LinearLayout layout;
    private Conversation.ConversationType mConversationType;
    private RongIM.LocationProvider.LocationCallback mLastLocationCallback;
    public static ConversationActivity instance;
    private String uid;
    private String nickname;
    private String head_pic;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 1:
                    getConversationSkillType();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        instance=this;
        Intent intent = getIntent();
        getIntentDate(intent);
        isReconnect(intent);
        RongIM.setLocationProvider(this);
        RongIM.setConversationBehaviorListener(this);
        setView();
        setListener();
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivDetaildata.setOnClickListener(this);
        btnMakeorder.setOnClickListener(this);
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.conversation_iv_back);
        tvTitle= (TextView) findViewById(R.id.conversation_tv_title_name);
        tvTitle.setText(mTitle);
        ivDetaildata= (ImageView) findViewById(R.id.conversation_iv_detailmsg);
        ivtypeimg= (ImageView) findViewById(R.id.conversation_iv_typeimg);
        tvtypename= (TextView) findViewById(R.id.conversation_tv_typename);
        tvlevel= (TextView) findViewById(R.id.conversation_tv_level);
        tvprice= (TextView) findViewById(R.id.conversation_tv_price);
        btnMakeorder= (Button) findViewById(R.id.conversation_btn_makeorder);
        layout= (LinearLayout) findViewById(R.id.conversation_ll_layout);
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {
//        InputProvider.ExtendProvider[] provider = {
//                new ImageInputProvider(RongContext.getInstance()),//图片
//                new CameraInputProvider(RongContext.getInstance()),//相机
//                new LocationInputProvider(RongContext.getInstance()),//地理位置
////                new VoIPInputProvider(RongContext.getInstance()),// 语音通话
//        };
//        RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
        mTitle=intent.getData().getQueryParameter("title");
        String telRegex = "[1][34578]\\d{9}";
        if(mTitle.matches(telRegex)){
            mTitle=mTitle.replace(mTitle.substring(3,7),"****");
        }
        Log.i("998867566", "getIntentDate: "+mTitle);
        mTargetId = intent.getData().getQueryParameter("targetId");
//        mTargetIds = intent.getData().getQueryParameter("targetIds");
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
//        enterFragment(mConversationType, mTargetId);
        getuserifonbymobile();
    }

    private void getuserifonbymobile() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getInfoByMobile");
        params.addBodyParameter("mobile",mTargetId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    JSONObject obj1=obj.getJSONObject("data");
                    uid=obj1.getString("uid");
                    nickname=obj1.getString("nickname");
                    head_pic=obj1.getString("head_pic");
                    handler.sendEmptyMessage(1);
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

    private void getConversationSkillType() {
        RequestParams params =new RequestParams("http://bubblefish.jbserver.cn/api/biggods/getSkillon");
        params.addBodyParameter("uid",uid);
        Log.i("getConversation", "getConversationSkillType: "+uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("getConversation", "onSuccess: "+result);
                DataBean data=new Gson().fromJson(result, DataBean.class);
                if(data.getData().size()!=0) {
                    layout.setVisibility(View.VISIBLE);
                    DataBean.DataEntity datas = data.getData().get(0);
                    x.image().bind(ivtypeimg, datas.getTypepic());
                    tvtypename.setText(datas.getSkill());
                    tvprice.setText("单价:"+datas.getPrice()+"元");
                    tvlevel.setText(datas.getLevel());
                }else{
                    layout.setVisibility(View.GONE);
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


    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
//    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
//
//        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
//
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
//                .appendQueryParameter("targetId", mTargetId).build();
//
//        fragment.setUri(uri);
//    }


    /**
     * 判断消息是否是 push 消息
     */
    private void isReconnect(Intent intent) {
        String token = PreferenceManager.getDefaultSharedPreferences(this).getString("r_token", "");
        Log.i("lvzhiweiooo",token);
        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {
                 reconnect(token);
            } else {
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
                    reconnect(token);
                } else {
//                    enterFragment(mConversationType, mTargetId);
                }
            }
        }
    }

//    /**
//     * 设置 actionbar 事件
//     */
//    private void setActionBar() {
//
//        mTitle = (TextView) findViewById(R.id.txt1);
//        mBack = (RelativeLayout) findViewById(R.id.back);
//
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

//    /**
//     * 设置 actionbar title
//     */
//    private void setActionBarTitle(String targetid) {
//
//        tvTitle.setText(targetid);
//    }

    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {

        if (getApplicationInfo().packageName.equals(MyApp.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {

//                    enterFragment(mConversationType, mTargetId);
                    if (RongIM.getInstance() != null) {
                        //设置自己发出的消息监听器。
                    }
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        Log.i("lvzhiwei321123", "onStartLocation: "+locationCallback);
        setLastLocationCallback(locationCallback);
        Intent intent = new Intent(context, MapLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        String userid = userInfo.getUserId();
        getInfoByMobile(userid);
        return true;
    }

    private void getInfoByMobile(String userid) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getInfoByMobile");
        params.addBodyParameter("mobile",userid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    JSONObject obj1=obj.getJSONObject("data");
                    String ortheruid=obj1.getString("uid");
                    Intent intent=new Intent(ConversationActivity.this,FriendDataActivity.class);
                    intent.putExtra("fid",ortheruid);
                    startActivity(intent);
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
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        // 消息点击事件，判断如果是位置消息就取出Content()跳转到地图activity
        if (message.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(context, MapLocationActivity.class);
            intent.putExtra("location", message.getContent());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        return false;

    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
    public RongIM.LocationProvider.LocationCallback getLastLocationCallback() {
        return mLastLocationCallback;
    }

    public void setLastLocationCallback(RongIM.LocationProvider.LocationCallback lastLocationCallback) {
        this.mLastLocationCallback = lastLocationCallback;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.conversation_iv_back:
                finish();
                break;
            case R.id.conversation_iv_detailmsg:
                getInfoByMobile(mTargetId);
                break;
            case R.id.conversation_btn_makeorder:
                Intent intent=new Intent(ConversationActivity.this,XdActivity.class);
                intent.putExtra("fid",uid);
                intent.putExtra("name",nickname);
                intent.putExtra("pic",head_pic);
                startActivity(intent);
                break;
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        reconnect(MyApp.token);
//    }
}

