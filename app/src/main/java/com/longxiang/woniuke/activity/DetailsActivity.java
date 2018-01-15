package com.longxiang.woniuke.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.myview.MyScrollView;

public class DetailsActivity extends AppCompatActivity {

    private WebView wv;
    private MyScrollView myscrollview;
    private LinearLayout yuanshi;
    private LinearLayout mubiao;
    private View xuanting;
    private TextView touming;
    private View title_deatils;
    private RelativeLayout icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
    }

    private void init() {

        myscrollview = (MyScrollView) findViewById(R.id.myscrollview);
        icon = (RelativeLayout) findViewById(R.id.layout_icon);
        yuanshi = (LinearLayout) findViewById(R.id.layout_yuanshi);
        mubiao = (LinearLayout) findViewById(R.id.layout_mubiao);
        xuanting = (View) findViewById(R.id.layout_xuanting);
        touming = (TextView) findViewById(R.id.touming);
        title_deatils = (View) findViewById(R.id.layout_title_details);
        title_deatils.setAlpha(0);
        myscrollview.setHoverView(xuanting, yuanshi, mubiao,title_deatils,icon);//设置想要悬停的控件为 textview
        myscrollview.setOnHoverListener(new MyScrollView.OnHoverListener() {
            @Override
            public void isHovered() {

            }

            @Override
            public void isScrolling(int tergetH, int srcH, int titleH,int iconH) {
//                if (tergetH>0) {
                    float scale = (float) (tergetH)/srcH<1.0f?(float) (tergetH)/srcH:1.0f;
                    title_deatils.setAlpha(scale);
//                }else{
//                    title_deatils.setAlpha(0);
//                }
            }
        });


        wv = (WebView) findViewById(R.id.wv_details);
        wv.loadUrl("http://101.201.37.121:888/Home/Scenic/index");
        WebSettings settings=wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

//    @Override
//    public void onXuanting() {
//
//    }
//
//    @Override
//    public void onProgess(int currentY, int totalY) {
//        //透明度 0透明  1不透明
//        float alpha = ((float) currentY) / totalY > 1.0f ? 1.0f : ((float) currentY) / totalY;
////        myScrollView.setAlpha(1-alpha);
//        touming.setAlpha(alpha);
//    }
}
