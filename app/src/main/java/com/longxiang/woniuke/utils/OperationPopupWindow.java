package com.longxiang.woniuke.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.longxiang.woniuke.R;

/**
 * Created by Administrator on 2016/8/23.
 */
public class OperationPopupWindow extends PopupWindow  {
    private final View mMenuView;
    private final Button btn_quguan;
    private Button btn_jubao, btn_lahei, btn_cancel;
    public OperationPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.friend_operation, null);
        btn_jubao = (Button) mMenuView.findViewById(R.id.friend_operation_jubao);
        btn_lahei = (Button) mMenuView.findViewById(R.id.friend_operation_lahei);
        btn_cancel = (Button) mMenuView.findViewById(R.id.friend_operation_cancel);
        btn_quguan=(Button) mMenuView.findViewById(R.id.friend_operation_quguan);
        btn_cancel.setTextColor(Color.parseColor("#ffffff"));
        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        btn_jubao.setOnClickListener(itemsOnClick);
        btn_lahei.setOnClickListener(itemsOnClick);
        btn_quguan.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.dialog_enter);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xa0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.friend_operation_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
