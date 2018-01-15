/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.longxiang.woniuke.customwheelview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longxiang.woniuke.myview.RoundImageViewByXfermode;


/**
 * 滚轮Item布局，包含图片和文本
 *
 * @author venshine
 */
public class WheelItem extends FrameLayout {

    private ImageView mImage;
    private TextView mText;

    public WheelItem(Context context) {
        super(context);
        init();
    }

    public WheelItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WheelItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        LinearLayout layout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, WheelUtils.dip2px(getContext(),
                WheelConstants
                        .WHEEL_ITEM_HEIGHT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(WheelConstants.WHEEL_ITEM_PADDING, WheelConstants.WHEEL_ITEM_PADDING, WheelConstants
                .WHEEL_ITEM_PADDING, WheelConstants.WHEEL_ITEM_PADDING);
        layout.setGravity(Gravity.CENTER);
        addView(layout, layoutParams);

        // 图片
        mImage = new RoundImageViewByXfermode(getContext());
        mImage.setTag(WheelConstants.WHEEL_ITEM_IMAGE_TAG);
        mImage.setVisibility(View.GONE);
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);
        LayoutParams imageParams = new LayoutParams(55, 55);
//        imageParams.rightMargin = WheelConstants.WHEEL_ITEM_MARGIN;
        layout.addView(mImage, imageParams);

        // 文本
        mText = new TextView(getContext());
        mText.setTag(WheelConstants.WHEEL_ITEM_TEXT_TAG);
        mText.setEllipsize(TextUtils.TruncateAt.END);
        mText.setSingleLine();
        mText.setIncludeFontPadding(false);
        mText.setGravity(Gravity.CENTER);
        mText.setTextColor(Color.BLACK);
        LayoutParams textParams = new LayoutParams(220, LayoutParams.WRAP_CONTENT);
        layout.addView(mText, textParams);
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setText(CharSequence text) {
        mText.setText(text);
    }

    /**
     * 设置图片资源
     *
     * @param bitmap
     */
    public void setImageBitmap(Bitmap bitmap) {
        mImage.setVisibility(View.VISIBLE);
        mImage.setImageBitmap(bitmap);
    }


}
