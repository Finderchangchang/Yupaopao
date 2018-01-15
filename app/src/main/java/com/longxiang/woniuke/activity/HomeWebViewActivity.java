package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.longxiang.woniuke.R;

public class HomeWebViewActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView ivBack;
    private TextView tvTitle;
    private WebView webView;
    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_web_view);
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        url=intent.getStringExtra("url");
        setView();
        setListener();
    }
    private void setView() {
        ivBack= (ImageView) findViewById(R.id.home_webview_iv_back);
        tvTitle= (TextView) findViewById(R.id.home_webview_tv_title_name);
        tvTitle.setText(title);
        tvTitle.setTextColor(Color.parseColor("#ffffff"));
        webView= (WebView) findViewById(R.id.home_webview);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_webview_iv_back:
                finish();
                break;
        }
    }
}
