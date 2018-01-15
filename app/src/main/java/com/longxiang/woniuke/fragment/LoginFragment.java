package com.longxiang.woniuke.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.activity.ForgetPswActivity;
import com.longxiang.woniuke.activity.MainActivity;
import com.longxiang.woniuke.activity.RegistMsgActivity;
import com.longxiang.woniuke.activity.RegisterActivity;
import com.longxiang.woniuke.receiver.MyReceiver;
import com.longxiang.woniuke.utils.IsInstallQQUtil;
import com.longxiang.woniuke.utils.MyApp;
import com.longxiang.woniuke.wxapi.WXEntryActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/5/17 0017.
 */
public class LoginFragment extends Fragment {
    private View sub;
    private TextView title;
    private TextView phoneregister;
    private TextView wxregister;
    private TextView qqregister;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnConfirm;
    private TextView tvForget;
    private SharedPreferences.Editor pEditor;
    private IWXAPI mApi;
    private SharedPreferences.Editor editor;
    public static final String APP_ID="1105677455";
    private Tencent mTencent;
    private String openidString;
    private String expiresString;
    private String tokenString;
    private boolean isinstallqq;
    private String nicknameString;
    private String headurlString;
    private String genderString;
    private int sex;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_loginfragment,null);
        init(view);
        isinstallqq=IsInstallQQUtil.isQQClientAvailable(getActivity());
        mTencent = Tencent.createInstance(APP_ID, getActivity().getApplicationContext());
        mApi = WXAPIFactory.createWXAPI(getActivity(), WXEntryActivity.APP_ID, false);
        mApi.registerApp(WXEntryActivity.APP_ID);
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor=prefs.edit();
        SharedPreferences prefs2 = getActivity().getSharedPreferences("deviceId",getActivity().MODE_PRIVATE);
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
        return view;
    }

    private void init(View view) {
        sub = (View) view.findViewById(R.id.sub_title_login);
        title = (TextView) sub.findViewById(R.id.tv_title_name);
        etUsername= (EditText) view.findViewById(R.id.et_username);
        etPassword= (EditText) view.findViewById(R.id.et_userpassword);
        btnConfirm= (Button) view.findViewById(R.id.login_btnConfirm);
        tvForget= (TextView) view.findViewById(R.id.login_forgetpsw);
        title.setText("登录");
        title.setTextColor(Color.WHITE);
        phoneregister = (TextView) view.findViewById(R.id.tv_phoneregister);
        phoneregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        wxregister= (TextView) view.findViewById(R.id.tv_wxregister);
        wxregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"微信登录中,请稍后...",Toast.LENGTH_SHORT).show();
                if (mApi != null && mApi.isWXAppInstalled()) {
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    mApi.sendReq(req);
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "您未安装微信", Toast.LENGTH_SHORT).show();
            }

        });
        qqregister= (TextView) view.findViewById(R.id.tv_qqregister);
        qqregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isinstallqq==true) {
                    Toast.makeText(getActivity().getApplicationContext(), "QQ登录中,请稍后...", Toast.LENGTH_SHORT).show();
                    login();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"您未安装QQ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ForgetPswActivity.class);
                startActivity(intent);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/login_pw");
                params.addBodyParameter("mobile",etUsername.getText().toString());
                params.addBodyParameter("password",etPassword.getText().toString());
                params.addBodyParameter("device_token",MyApp.deviceId);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("lvzhiwei123", "onSuccess: "+result);
                        try {
                            JSONObject obj=new JSONObject(result);
                            switch (obj.getString("retcode")){
                                case "4000":
                                case "4001":
                                case "4002":
                                case "4003":
                                    Toast.makeText(getActivity().getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                                    break;
                                case "2000":
                                    Toast.makeText(getActivity().getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                                    JSONObject obj1=obj.getJSONObject("data");
                                    MyApp.uid=obj1.getString("uid");
                                    MyApp.token=obj1.getString("r_token");
                                    editor.putString("uid",MyApp.uid);
                                    editor.putString("r_token",MyApp.token);
                                    editor.commit();
                                    Intent intent=new Intent(getActivity(),MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    break;
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
        });
    }

    private class BaseUiListener implements IUiListener {

        protected void doComplete(JSONObject values) {
            Log.i("qqlogindocomplete", "doComplete: "+values);
        }

        @Override
        public void onComplete(Object o) {
            doComplete((JSONObject) o);
            Log.i("qqlogindata", "onComplete: "+o.toString());
            try {
                openidString = ((JSONObject) o).getString("openid");
                expiresString = ((JSONObject) o).getString(Constants.PARAM_EXPIRES_IN);
                tokenString = ((JSONObject) o).getString(Constants.PARAM_ACCESS_TOKEN);
                mTencent.setOpenId(openidString);
                mTencent.setAccessToken(tokenString, expiresString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getActivity().getApplicationContext(), qqToken);
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    Log.i("handlerzoumei", "onComplete: "+"55555");
                    Message msg = mHandler.obtainMessage();
                    msg.obj = o;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                }

                @Override
                public void onError(UiError uiError) {

                }

                @Override
                public void onCancel() {

                }
            });

        }

        @Override
        public void onError(UiError e) {
            Log.i("QQerror", "onError:"+"code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {

        }
    }
    public void login()
    {
        mTencent = Tencent.createInstance(APP_ID, getActivity().getApplicationContext());
        if (!mTencent.isSessionValid())
        {
            Log.i("mtencentlistener", "login: "+"zoulema");
            mTencent.login(getActivity(), "all", new BaseUiListener());
            Log.i("mtencentlistener", "login: "+"zoulema22");
        }else {
            // 注销登录
            mTencent.logout(getActivity().getApplicationContext());
        }
    }
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
//                if (response.has("nickname")) {
                    try {
                        nicknameString=response.getString("nickname");
                        headurlString=response.getString("figureurl_qq_2");
                        genderString=response.getString("gender");
                        if(genderString.equals("男")){
                            sex=0;
                        }else{
                            sex=1;
                        }
                        Log.i("loginloginQQ", "handleMessage: "+openidString+","+nicknameString+","+headurlString);
                        registOrlogin(openidString,nicknameString,sex,headurlString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//            }
        }

    };
    private void registOrlogin(final String openid, final String nickname,final int sex, final String headimgurl) {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/regOtherState");
        params.addBodyParameter("openid",openid);
        params.addBodyParameter("device_token", MyApp.deviceId);
        params.addBodyParameter("type","2");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("wxregist", "onSuccess: "+result);
                Intent intent;
                try {
                    JSONObject obj=new JSONObject(result);
                    switch (obj.getString("retcode")){
                        case "2000":
                            Toast.makeText(getActivity().getApplicationContext(),"正在跳转...",Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity().getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            JSONObject obj1=obj.getJSONObject("data");
                            MyApp.uid=obj1.getString("uid");
                            MyApp.token=obj1.getString("r_token");
                            editor.putString("uid",MyApp.uid);
                            editor.putString("r_token",MyApp.token);
                            editor.commit();
                            MainActivity.instance.finish();
                            intent =new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case "4333":
                            Toast.makeText(getActivity().getApplicationContext(),"正在跳转...",Toast.LENGTH_SHORT).show();
                            intent=new Intent(getActivity(), RegisterActivity.class);
                            intent.putExtra("type","2");
                            intent.putExtra("openid",openid);
                            intent.putExtra("nickname",nickname);
                            intent.putExtra("headimgurl",headimgurl);
                            intent.putExtra("sex",sex);
                            intent.putExtra("device_token",MyApp.deviceId);
                            startActivity(intent);
                            getActivity().finish();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("321123321", "onActivityResult: "+"3123");
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
        Log.i("321123321", "onActivityResult: "+"3123");
    }
}
