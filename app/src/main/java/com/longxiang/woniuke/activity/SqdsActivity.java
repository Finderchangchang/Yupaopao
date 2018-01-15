package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class SqdsActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private WebView webView;
    private Button btnConfirm;
    public static SqdsActivity instance;
    private TextView tvTitle;
    private String htmlcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqds);
        instance=this;
        setView();
        setListener();
        gethtml();
    }

    private void setView() {
        ivBack= (ImageView) findViewById(R.id.sqds_iv_back);
        tvTitle= (TextView) findViewById(R.id.sqds_tv_title_name);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        webView= (WebView) findViewById(R.id.sqds_webview);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        btnConfirm= (Button) findViewById(R.id.sqds_btn_confirm);
        btnConfirm.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sqds_iv_back:
                finish();
                break;
            case R.id.sqds_btn_confirm:
                Intent intent=new Intent(this,SqxmActivity.class);
                intent.putExtra("flag","2");
                startActivity(intent);
                break;
        }
    }
    private void gethtml() {
        RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/gethtml");
        params.addBodyParameter("type","2");
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
