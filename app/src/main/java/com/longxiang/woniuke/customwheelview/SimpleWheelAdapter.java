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
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.longxiang.woniuke.R;


/**
 * 滚轮图片和文本适配器
 *
 * @author venshine
 */
public class SimpleWheelAdapter extends BaseWheelAdapter<WheelData> {

    private Context mContext;

    public SimpleWheelAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new WheelItem(mContext);
        }
        WheelItem item = (WheelItem) convertView;
        if (mList.get(position).getIcon()!=null) {
            Log.e("12345", "bindView: " + mList.get(position).getIcon());
//            ImageView iv= (ImageView) item.getTag(WheelConstants.WHEEL_ITEM_IMAGE_TAG);
//            x.image().bind((ImageView)item.getChildAt(0),mList.get(position).getIcon());
            item.setImageBitmap(BitmapDownLoad.getBitmap(mList.get(position).getIcon(), mContext));
        }else {
            Bitmap bitmap= BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
            item.setImageBitmap(bitmap);
        }
        item.setText(mList.get(position).getName());
        return convertView;
    }

}
