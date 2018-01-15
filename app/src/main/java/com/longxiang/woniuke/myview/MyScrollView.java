package com.longxiang.woniuke.myview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;


/**
 * Created by Administrator on 2016/3/10 0010.
 */
public class MyScrollView extends ScrollView {

    private View hoverView;
    private ViewGroup srcView;
    private ViewGroup tergetView;
    private int srcHeight;
    private boolean isHover;
    private int saveHeight;
    private OnHoverListener onHoverListener;
    private View title,icon;

    public void setHoverView(final View hoverView, final ViewGroup srcView, final ViewGroup tergetView, final View title,View icon) {
        this.hoverView = hoverView;
        this.srcView = srcView;
        this.tergetView = tergetView;
        this.title=title;
        this.icon=icon;
        hoverView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                srcHeight = srcView.getTop()-title.getMeasuredHeight();
                srcView.getLayoutParams().height = hoverView.getMeasuredHeight();
                tergetView.getLayoutParams().height = hoverView.getMeasuredHeight();
                hoverView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    public void setOnHoverListener(OnHoverListener onHoverListener) {
        this.onHoverListener = onHoverListener;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
//                getHover(getScrollY()+title.getMeasuredHeight());
                getHover(getScrollY());
                break;
            case MotionEvent.ACTION_UP:
//                saveHeight=getScrollY()+title.getMeasuredHeight();
                saveHeight=getScrollY();
                handler.sendEmptyMessageDelayed(1,20);
                break;
        }
        return super.onTouchEvent(ev);
    }


    private void getHover(int nowHeight) {
        if (nowHeight > srcHeight && !isHover) {
            if (hoverView.getParent() != null) {
                ((ViewGroup) hoverView.getParent()).removeView(hoverView);
            }
            tergetView.setVisibility(VISIBLE);
            tergetView.addView(hoverView);
            isHover = true;
        } else if (nowHeight <= srcHeight && isHover) {
            if (hoverView.getParent() != null) {
                ((ViewGroup) hoverView.getParent()).removeView(hoverView);
            }
            tergetView.setVisibility(GONE);
            srcView.addView(hoverView);
            isHover = false;
        }

        if (onHoverListener!=null){
            if (isHover){
                onHoverListener.isHovered();
            }
            onHoverListener.isScrolling(nowHeight,srcHeight,title.getMeasuredHeight(),icon.getMeasuredHeight());
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            int height=getScrollY()+title.getMeasuredHeight();
            int height=getScrollY();
            switch (msg.what){
                case 1:
                    if (saveHeight!=height){
                        getHover(height);
                        saveHeight=height;
                        handler.sendEmptyMessageDelayed(1,20);
                    }else {
                        getHover(height);
                    }
                    break;
            }
        }
    };


    public interface OnHoverListener{
        void isHovered();
        void isScrolling(int tergetH, int srcH, int titleH,int iconH);
    }

}
