package com.tzx.slidelistviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by tanzhenxing on 2015/8/10.
 */
public class SliderListView extends ListView {
    private static final String TAG = "SilderListView";
    //当前滑动的item
    private SliderLinearView mFocusedItemView;
    float mX = 0;
    float mY = 0;
    private int mPosition = -1;
    //是否产生滑动
    boolean isSlider = false;

    public SliderListView(Context context) {
        super(context);
    }

    public SliderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            //当按下屏幕是，将已经划出的item收回去
            case MotionEvent.ACTION_DOWN:
                isSlider = false;
                mX = x;
                mY = y;
                //我把它理解为通过x和y的位置来确定这个listView里面这个item的位置
                int position = pointToPosition((int) x, (int) y);
                if (mPosition != position) {
                    mPosition = position;
                    if (mFocusedItemView != null){
                        mFocusedItemView.reset();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPosition != -1){
                    if (Math.abs(mY - y) < 30 && Math.abs(mX - x) > 20) {
                        //获取当前可见的第一个Item的position并记录
                        int first = this.getFirstVisiblePosition();
                        int index = mPosition - first;
                        Log.d(TAG,"index="+index);
                        mFocusedItemView = (SliderLinearView) getChildAt(index);
                        mFocusedItemView.onTouchEvent(event);
                        isSlider = true;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSlider) {
                    isSlider = false;
                    if (mFocusedItemView != null){
                        mFocusedItemView.adjust(mX - x > 0);
                        return true;
                    }
                }
                break;
        }
        return  super.onTouchEvent(event);
    }
}
