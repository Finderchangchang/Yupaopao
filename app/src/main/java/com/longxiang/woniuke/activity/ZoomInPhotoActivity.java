package com.longxiang.woniuke.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.longxiang.woniuke.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;


public class ZoomInPhotoActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView iv;
    private RelativeLayout layout;
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(500), DensityUtil.dip2px(500))
            .setRadius(DensityUtil.dip2px(0))
//            .setCrop(true)
            // 加载中或错误图片的ScaleType
//            .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            //设置加载过程中的图片
            .setLoadingDrawableId(R.mipmap.woniu)
            //设置加载失败后的图片
            .setFailureDrawableId(R.mipmap.woniu)
            //设置使用缓存
            .setUseMemCache(true)
            //设置支持gif
            .setIgnoreGif(false)
            //设置显示圆形图片
            .setCircular(false)
            .setSquare(true)
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_in_photo);
        iv= (ImageView) findViewById(R.id.zoom_in_imagview);
        Intent intent=getIntent();
        x.image().bind(iv,intent.getStringExtra("imgurl"),imageOptions);
        layout= (RelativeLayout) findViewById(R.id.zoom_in_photo_layout);
        iv.setOnClickListener(this);
        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
