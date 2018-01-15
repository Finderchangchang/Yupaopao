package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longxiang.woniuke.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.CSCustomServiceInfo;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack;
    private RelativeLayout rl01;
    private RelativeLayout rl02;
    private RelativeLayout rl03;
    private TextView tvVersion;
    private TextView title;
    private TextView kefumobile;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Intent intent=getIntent();
        if(intent.getStringExtra("name")!=null){
            name=intent.getStringExtra("name");
        }
        setView();
        getMobile();
        setListener();
        try {
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String version = packInfo.versionName;
            tvVersion.setText(version);
            Log.i("lvzhiwei321", "onCreate: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getMobile() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/getmobile");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    String mobile = obj.getString("data");
                    kefumobile.setText(mobile);
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

    private void setView() {
        ivBack = (ImageView) findViewById(R.id.help_iv_back);
        rl01 = (RelativeLayout) findViewById(R.id.help_rl01);
        rl02 = (RelativeLayout) findViewById(R.id.help_rl02);
        rl03 = (RelativeLayout) findViewById(R.id.help_rl03);
        tvVersion = (TextView) findViewById(R.id.help_tv_version);
        title = (TextView) findViewById(R.id.help_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        kefumobile= (TextView) findViewById(R.id.help_tv_mobile);
        kefumobile.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        kefumobile.setTextColor(Color.parseColor("#1EBAF3"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        rl01.setOnClickListener(this);
        rl02.setOnClickListener(this);
        rl03.setOnClickListener(this);
        kefumobile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.help_iv_back:
                finish();
                break;
            case R.id.help_rl01:
                intent = new Intent(this, UserHelpActivity.class);
                startActivity(intent);
                break;
            case R.id.help_rl02:
                //首先需要构造使用客服者的用户信息
                CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
                CSCustomServiceInfo csInfo = csBuilder.nickName(name).build();

/**
 * 启动客户服聊天界面。
 *
 * @param context           应用上下文。
 * @param customerServiceId 要与之聊天的客服 Id。
 * @param title             聊天的标题，如果传入空值，则默认显示与之聊天的客服名称。
 * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
 */
                RongIM.getInstance().startCustomerServiceChat(this, "KEFU147730068326284", "在线客服", csInfo);
                break;
            case R.id.help_rl03:
                intent = new Intent(this, ProFeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.help_tv_mobile:
                String number = kefumobile.getText().toString();
                //用intent启动拨打电话
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                startActivity(intent);
                break;
        }
    }
}
