package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class UserHelpActivity extends AppCompatActivity {
    private TextView title;
    private String htmlcode;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help);
        ImageView ivBack= (ImageView) findViewById(R.id.userhelp_iv_back);
        title= (TextView) findViewById(R.id.userhelp_tv_title_name);
        webView= (WebView) findViewById(R.id.userhelp_webview);
        WebSettings settings =webView.getSettings();
        settings.setJavaScriptEnabled(true);
        title.setTextColor(Color.parseColor("#ffffff"));
        gethtml();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void gethtml() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/gethtml");
        params.addBodyParameter("type","3");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj=new JSONObject(result);
                    htmlcode=obj.getString("data");
                    webView.loadDataWithBaseURL(null, htmlcode, "text/html", "utf-8", null);

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
    protected void onDestroy() {
        super.onDestroy();
        webView.loadDataWithBaseURL(null, "","text/html", "utf-8",null);
    }
}
