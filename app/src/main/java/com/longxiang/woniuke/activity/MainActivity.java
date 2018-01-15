package com.longxiang.woniuke.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longxiang.woniuke.R;
import com.longxiang.woniuke.bean.PersonalData;
import com.longxiang.woniuke.fragment.ExploreFragment;
import com.longxiang.woniuke.fragment.HomeFragment;
import com.longxiang.woniuke.fragment.LoginFragment;
import com.longxiang.woniuke.fragment.MessageFragment;
import com.longxiang.woniuke.fragment.OverbookingFragment;
import com.longxiang.woniuke.fragment.UserFragment;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.utils.NetUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener, RongIM.UserInfoProvider {

    private HomeFragment homeFragment;
    private ExploreFragment exploreFragment;
    private Fragment messageFragment;
    private Fragment userFragment;
    private Fragment loginFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioGroup rg;
    public static TextView tvhd;
    public static ImageView ivsechd;
    public static MainActivity instance;
    Timer timer=new Timer();
    private boolean isQuit;
    private Fragment overbookingFragment;
    private String headpic;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取访问相册权限
        getPermission();
        instance=this;
        init();
        inputTime();
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        String token=prefs.getString("r_token",null);
        SharedPreferences.Editor editor=prefs.edit();
        if(token==null){
            editor.putString("r_token",MyApp.token);
        }else {
            MyApp.token=token;
            connect(MyApp.token);
        }
//        Log.i("lvzhiweiAAADDD", "onCreate: "+MyApp.token);
//        connect(MyApp.token);
        RongIM.getInstance().setUserInfoProvider(this,true);
//        RongIM.getInstance().setMessageAttachedUserInfo(true);
        boolean networkState = NetUtils.detect(this);
        if(networkState==false){
            isopennet();
        }
    }

    private void getPermission() {
        if((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))

        {
            ActivityCompat.requestPermissions
                    (MainActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },0);
        }
    }

    private void isopennet() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("蜗牛壳").setMessage("当前网络不可用,是否开启网络").setNegativeButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        }).setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.instance.finish();
                System.exit(0);
                Process.killProcess(Process.myPid());
            }
        }).create();
        dialog.show();
    }

    private void inputTime() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/setlogintime");
        params.addBodyParameter("uid",MyApp.uid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.i("lvzhiwei777", "onSuccess: "+result);
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
    private void init() {
        tvhd= (TextView) findViewById(R.id.main_tvtotalcount);
        ivsechd= (ImageView) findViewById(R.id.main_ivsecondhong);
        rg= (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
//        rb1 = (RadioButton) findViewById(R.id.rb1);
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        String userUid=prefs.getString("uid",null);
        RegistMsgActivity.editor = prefs.edit();
        judgeUserStatus(userUid);
        if (userUid==null||userUid.equals("")) {
            RegistMsgActivity.editor.putString("uid", MyApp.uid);
            homeFragment=new HomeFragment();
            exploreFragment=new ExploreFragment();
            messageFragment=new LoginFragment();
            userFragment=new LoginFragment();
            loginFragment=new LoginFragment();
            overbookingFragment=new LoginFragment();
        }else{
            MyApp.uid = userUid;
            homeFragment=new HomeFragment();
            exploreFragment=new ExploreFragment();
            messageFragment=new MessageFragment();
            userFragment=new UserFragment();
            loginFragment=new UserFragment();
            overbookingFragment=new OverbookingFragment();
        }

        manager=getFragmentManager();
        transaction=manager.beginTransaction();

        transaction.add(R.id.ll, homeFragment);
        transaction.add(R.id.ll,exploreFragment);
        transaction.add(R.id.ll,overbookingFragment);
        transaction.add(R.id.ll,messageFragment);
        transaction.add(R.id.ll,userFragment);
        transaction.add(R.id.ll,loginFragment,"loginfragment");

        transaction.hide(exploreFragment);
        transaction.hide(messageFragment);
        transaction.hide(overbookingFragment);
        transaction.hide(userFragment);
        transaction.hide(loginFragment);
        transaction.commit();

    }

    private void judgeUserStatus(String userUid) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/judgeUserStatus");
        params.addBodyParameter("uid",userUid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("judgeUserStatus", "onSuccess: "+result);
                try {
                    JSONObject obj=new JSONObject(result);
                    if(obj.getString("retcode").equals("4001")){
                        Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_LONG).show();
                        RegistMsgActivity.editor.putString("uid", null);
                        RegistMsgActivity.editor.commit();
                        finish();
                        Intent intent =new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
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
        transaction=manager.beginTransaction();
        switch (checkedId){
            case R.id.rb1:
                transaction.show(homeFragment);
                transaction.hide(exploreFragment);
                transaction.hide(messageFragment);
                transaction.hide(overbookingFragment);
                transaction.hide(userFragment);
                transaction.hide(loginFragment);
                break;
            case R.id.rb2:
                transaction.show(exploreFragment);
                transaction.hide(homeFragment);
                transaction.hide(messageFragment);
                transaction.hide(overbookingFragment);
                transaction.hide(userFragment);
                transaction.hide(loginFragment);
                break;
            case R.id.rb3:
//                Intent intent=new Intent(this,OverbookingActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade, R.anim.hold);
                transaction.show(overbookingFragment);
                transaction.hide(homeFragment);
                transaction.hide(exploreFragment);
                transaction.hide(messageFragment);
                transaction.hide(userFragment);
                transaction.hide(loginFragment);

                break;
            case R.id.rb4:
                transaction.show(messageFragment);
                transaction.hide(homeFragment);
                transaction.hide(exploreFragment);
                transaction.hide(overbookingFragment);
                transaction.hide(userFragment);
                transaction.hide(loginFragment);
                break;
            case R.id.rb5:
                transaction.show(loginFragment);
                transaction.hide(homeFragment);
                transaction.hide(exploreFragment);
                transaction.hide(messageFragment);
                transaction.hide(overbookingFragment);
                transaction.hide(userFragment);
//                transaction.show(userFragment);
//                transaction.hide(homeFragment);
//                transaction.hide(exploreFragment);
//                transaction.hide(messageFragment);
//                transaction.hide(overbookingFragment);
//                transaction.hide(loginFragment);
                break;

        }
        transaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (isQuit==false){
                isQuit=true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                TimerTask task=null;
                task=new TimerTask() {
                    @Override
                    public void run() {
                        isQuit=false;
                    }
                };
                timer.schedule(task,2000);
            }else {
                MainActivity.instance.finish();
                System.exit(0);
                Process.killProcess(Process.myPid());
            }
        }
        return false;
    }



    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(MyApp.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("lvzhiweiAAADDD", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("lvzhiweiAAADDD", "--onSuccess" + userid);
                    MyApp.RCuserid=userid;
//                    getUserInfo();
//                Intent intent=new Intent(GoodsDetailActivity.this,ConversationActivity.class);
//                intent.putExtra("chatName",chatName);
//                startActivity(intent);
//                    enterFragment();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("lvzhiweiAAADDD", "--onError" + errorCode.getValue());
                }
            });
        }
    }
//    private void getUserInfo() {
//        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmineinfo");
//        params.addBodyParameter("uid", MyApp.uid);
//        Log.i("lvzhiwei222", "getUserInfo: "+MyApp.uid);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("lvzhiwei222", "onSuccess: "+result);
//                try {
//                    JSONObject json=new JSONObject(result);
//                    JSONObject obj=json.getJSONObject("data");
//                    headpic=obj.getString("head_pic");
//                    name=obj.getString("nickname");
//                    handler.sendEmptyMessage(1);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
//    /**
//     * 加载 会话列表 ConversationListFragment
//     */
//    private void enterFragment() {
//
//        ConversationListFragment fragment = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationlist);
//
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversationlist")
//                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
//                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
//                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
//                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
//                .build();
//
//        fragment.setUri(uri);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment=manager.findFragmentByTag("loginfragment");
        fragment.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public UserInfo getUserInfo(String s) {
        Log.i("maingetuserinfobyid", "getUserInfo: "+s);
        return getUserInfoById(s);
    }

    private UserInfo getUserInfoById(final String userId) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/friend/getInfoByMobile");
        params.addBodyParameter("mobile",userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                PersonalData data=new Gson().fromJson(result,PersonalData.class);
                String nickname = data.getData().getNickname();
                String headpic=data.getData().getHead_pic();
                parseJson(userId,nickname,headpic);
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
        return null;
    }

    private UserInfo parseJson(String id,String nickname,String headpic) {
        /**
         * 刷新用户缓存数据。
         *
         * @param userInfo 需要更新的用户缓存数据。
         */
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(id, nickname, Uri.parse(headpic)));
        return new UserInfo(id,nickname,Uri.parse(headpic));
    }
//    private void removeOrderOff() {
//        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/index.php/Api/order/removeOrderOff");
//        params.addBodyParameter("uid",MyApp.uid);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("removeorderoff", "onSuccess: "+result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
//        connect(MyApp.token);
    }

    @Override
    protected void onPause() {
        super.onPause();
        judgeUserStatus(MyApp.uid);
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApp.mLocationClient.stop();
//        removeOrderOff();
    }

}

