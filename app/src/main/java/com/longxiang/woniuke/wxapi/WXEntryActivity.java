package com.longxiang.woniuke.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.MainActivity;
import com.longxiang.woniuke.activity.RegistMsgActivity;
import com.longxiang.woniuke.activity.RegisterActivity;
import com.longxiang.woniuke.receiver.MyReceiver;
import com.longxiang.woniuke.utils.MyApp;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private final String TAG = "lvzhiweiweixin";
    public static final String APP_ID = "wx71945f5996975f3d";
    public static final String APP_SECRET = "70c53abe65b48ae603ba204953da83e4";

    private IWXAPI mApi;
    private SharedPreferences.Editor pEditor;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        mApi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        mApi.handleIntent(getIntent(), this);
//        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
//        String deviceID=prefs.getString("deviceId",null);
//        pEditor = prefs.edit();
//        if(deviceID==null) {
//            pEditor.putString("deviceId", MyReceiver.regId);
//            MyApp.deviceId=MyReceiver.regId;
//            pEditor.commit();
////                    Log.i("lvzhiweiasd",MyApp.deviceId);
//        }else{
//            MyApp.deviceId=deviceID;
////                    Log.i("lvzhiweiasd",MyApp.deviceId);
//        }
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
        SharedPreferences prefs2 = getSharedPreferences("deviceId",MODE_PRIVATE);
        String deviceID=prefs2.getString("deviceId",null);
        pEditor = prefs2.edit();
        if(deviceID==null) {
            pEditor.putString("deviceId", MyReceiver.regId);
            MyApp.deviceId=MyReceiver.regId;
            pEditor.commit();
//                    Log.i("lvzhiweiasd",MyApp.deviceId);
        }else{
            MyApp.deviceId=deviceID;
//                    Log.i("lvzhiweiasd",MyApp.deviceId);
        }
    }


    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(final String code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                        + APP_ID
                        + "&secret="
                        + APP_SECRET
                        + "&code="
                        + code
                        + "&grant_type=authorization_code";

                    RequestParams params=new RequestParams(path);
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject jsonObject=new JSONObject(result);
                                if (null != jsonObject) {
                                    String openid = jsonObject.getString("openid").toString().trim();
                                    String access_token = jsonObject.getString("access_token").toString().trim();
                                    Log.i(TAG, "onSuccess: "+openid+","+access_token);
                                    getUserMesg(access_token, openid);
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
//                    JSONObject jsonObject = JsonUtils.initSSLWithHttpClinet(path);// 请求https连接并得到json结果
//                    if (null != jsonObject) {
//                        String openid = jsonObject.getString("openid").toString().trim();
//                        String access_token = jsonObject.getString("access_token").toString().trim();
//                        getUserMesg(access_token, openid);
//                    }

            }
        }).start();

    }
    /**
     * 获取微信的个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        RequestParams params=new RequestParams(path);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (null != jsonObject) {
                        String nickname = jsonObject.getString("nickname");
                        int sex = Integer.parseInt(jsonObject.get("sex").toString());
                        String headimgurl = jsonObject.getString("headimgurl");
                        registOrlogin(openid,nickname,sex,headimgurl);
                        Log.e(TAG, "getUserMesg 拿到了用户Wx基本信息.. nickname:" + nickname+","+sex+","+headimgurl);

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
//        try {
//            JSONObject jsonObject = JsonUtils.initSSLWithHttpClinet(path);// 请求https连接并得到json结果
//            if (null != jsonObject) {
//                String nickname = jsonObject.getString("nickname");
//                int sex = Integer.parseInt(jsonObject.get("sex").toString());
//                String headimgurl = jsonObject.getString("headimgurl");
//
//                Log.e(TAG, "getUserMesg 拿到了用户Wx基本信息.. nickname:" + nickname);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return;
    }

    private void registOrlogin(final String openid, final String nickname,final int sex, final String headimgurl) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/regOtherState");
        params.addBodyParameter("openid",openid);
        params.addBodyParameter("device_token", MyApp.deviceId);
        params.addBodyParameter("type","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("wxregist", "onSuccess: "+result);
                Intent intent;
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getApplicationContext(),"正在跳转...",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            JSONObject obj1=obj.getJSONObject("data");
                            MyApp.uid=obj1.getString("uid");
                            MyApp.token=obj1.getString("r_token");
                            editor.putString("uid",MyApp.uid);
                            editor.putString("r_token",MyApp.token);
                            editor.commit();
                            MainActivity.instance.finish();
                            intent =new Intent(WXEntryActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "4333":
                            Toast.makeText(getApplicationContext(),"正在跳转...",Toast.LENGTH_SHORT).show();
                            intent=new Intent(WXEntryActivity.this, RegisterActivity.class);
                            intent.putExtra("type","1");
                            intent.putExtra("openid",openid);
                            intent.putExtra("nickname",nickname);
                            intent.putExtra("headimgurl",headimgurl);
                            intent.putExtra("sex",sex);
                            intent.putExtra("device_token",MyApp.deviceId);
                            startActivity(intent);
                            finish();
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        mApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp instanceof SendAuth.Resp){
            SendAuth.Resp newResp = (SendAuth.Resp) baseResp;
            //获取微信传回的code
            String code = newResp.code;
        }

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //发送成功
                SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                break;
            default:
                //发送返回
                break;
        }
        finish();
    }
}
