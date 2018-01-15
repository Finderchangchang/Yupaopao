package com.longxiang.woniuke.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.longxiang.woniuke.R;

public class YhqHelpActivity extends AppCompatActivity {
private ImageView ivBack;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yhq_help);
        ivBack= (ImageView) findViewById(R.id.yhqhelp_iv_back);
        webView= (WebView) findViewById(R.id.yhqhelp_webview);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){});
    }
}
