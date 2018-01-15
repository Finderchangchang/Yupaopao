package com.longxiang.woniuke.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.longxiang.woniuke.activity.SelectGodActivity;

import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class MyApp extends MultiDexApplication {
    public static LocationClient mLocationClient = null;
    public static String city_choose;
    public static String lat_经度;
    public static String lng_纬度;
    public static String uid;
    public static String token;
    public static String deviceId;
    public static String RCuserid;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        mLocationClient = new LocationClient(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }
//        if(MyApp.token!=null){
//            connect(MyApp.token);
//        }
        SDKInitializer.initialize(this);


    }
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
//    private void connect(String token) {
//
//        if (getApplicationInfo().packageName.equals(MyApp.getCurProcessName(getApplicationContext()))) {
//
//            /**
//             * IMKit SDK调用第二步,建立与服务器的连接
//             */
//            RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//                /**
//                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
//                 */
//                @Override
//                public void onTokenIncorrect() {
//
//                    Log.d("lvzhiweiAAADDD", "--onTokenIncorrect");
//                }
//
//                /**
//                 * 连接融云成功
//                 * @param userid 当前 token
//                 */
//                @Override
//                public void onSuccess(String userid) {
//                    Log.d("lvzhiweiAAADDD", "--onSuccess" + userid);
////                Intent intent=new Intent(GoodsDetailActivity.this,ConversationActivity.class);
////                intent.putExtra("chatName",chatName);
////                startActivity(intent);
//                }
//
//                /**
//                 * 连接融云失败
//                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
//                 */
//                @Override
//                public void onError(RongIMClient.ErrorCode errorCode) {
//
//                    Log.d("lvzhiweiAAADDD", "--onError" + errorCode.getValue());
//                }
//            });
//        }
//    }

}
