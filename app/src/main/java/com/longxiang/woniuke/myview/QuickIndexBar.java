package com.longxiang.woniuke.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class QuickIndexBar extends View {
    private Paint paint;//画笔
    private int cellWidth, cellHeight;//每个字所在的格子的宽高度
    //用于显示的26个英文字母
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"
            , "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private onIndexSelectListener onIndexSelectListener;//点击事件

    public QuickIndexBar.onIndexSelectListener getOnIndexSelectListener() {
        return onIndexSelectListener;
    }

    public void setOnIndexSelectListener(QuickIndexBar.onIndexSelectListener onIndexSelectListener) {
        this.onIndexSelectListener = onIndexSelectListener;
    }

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化参数
     *////bv,
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//打开抗锯齿
        paint.setColor(Color.parseColor("#1EBAF3"));//设置颜色
        paint.setTextSize(20);//字体大小
//        paint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体为粗体

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cellWidth == 0) cellWidth = getMeasuredWidth();//字体所在格子的宽度
        if (cellHeight == 0) cellHeight = getMeasuredHeight() / words.length;//字体所在格子的高度
        for (int i = 0; i < words.length; i++) {//遍历所有字母挨个绘制
            String word = words[i];//获取到当前应该绘制的字母
            float x = 0;//字体x 轴的起点
            float y = 0;//字体Y 的起点
            float textWidth = paint.measureText(word);//字的宽度
            x = cellWidth / 2 - textWidth / 2;//x的起点
            Rect rect = new Rect();//用于存放字体大小数据的类
            paint.getTextBounds(word, 0, 1, rect);//获取对应字的大小参数
            y = i * cellHeight + rect.height() / 2 + cellHeight / 2;//计算每个字的y 的点
//            if (i == index) {//代表当前点击的和绘制的是同一个字母
//                 paint.setColor(Color.parseColor("#666666"));
//            }
            paint.setColor(Color.parseColor(i == index ? "#0000ff" : "#1EBAF3"));//如果当前点击的和绘制的是同一个字 就设置灰色,否则为白色
            canvas.drawText(word, x, y, paint);
        }
    }

    private int index = -1;//默认点击的索引为-1,因为第一个就是0

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下去的时候计算点的位置
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();//获取当前的Y 点
                int currentIndex = (int) (y / cellHeight);//计算当前所在的格子是第几个
                if (currentIndex==26) currentIndex = 25;//因为存在一点点偏差在最底部,会导致出现26这个下标,如果是26 我们就认为是Z 字母 设置为25
                if (index != currentIndex) {//点击的字母发生变化后才赋值
                    index = currentIndex;
                  //  if (index == 26) index = 25;//因为存在一点点偏差在最底部,会导致出现26这个下标,如果是26 我们就认为是Z 字母 设置为25
                    //在此设置监听
                    if (onIndexSelectListener != null) {
                        onIndexSelectListener.onIndexSelect(words[index], index);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                index = -1;//抬起的时候恢复默认
                break;

        }
        invalidate();//重绘界面,会让 ondraw 重新执行,必须在主线程调用
        //   postInvalidate();//功能同上,但是可以在子线程调用
        return true;
    }

    public interface onIndexSelectListener {
        /**
         * 字母点击事件的处理,因为不知道需要什么数据出去,就干脆一起传出去
         *
         * @param word  当前点击的是哪个字母
         * @param index //当前点击的下标
         */
        void onIndexSelect(String word, int index);
    }
}
