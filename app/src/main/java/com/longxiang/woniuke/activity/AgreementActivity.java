package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AgreementActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView ivBack;
    private TextView title;
    private String htmlcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ivBack= (ImageView) findViewById(R.id.argreement_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title= (TextView) findViewById(R.id.argreement_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
        webView= (WebView) findViewById(R.id.argreement_webview);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        gethtml();
    }
    private void gethtml() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/gethtml");
        params.addBodyParameter("type","1");
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
