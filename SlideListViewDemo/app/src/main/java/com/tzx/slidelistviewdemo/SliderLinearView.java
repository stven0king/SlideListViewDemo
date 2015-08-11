package com.tzx.slidelistviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by 58 on 2015/8/4.
 */
public class SliderLinearView extends LinearLayout {

    private static String TAG = "SliderLinearView";
    private static final int TAN = 2;
    private Context mContext;
    private Scroller mScroller;
    private LinearLayout mViewContent;
    private int mHolderWidth = 60;
    private float mLastX = 0;
    private float mLastY = 0;
    public SliderLinearView(Context context) {
        super(context);
        initView();
    }

    public SliderLinearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SliderLinearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        setOrientation(LinearLayout.HORIZONTAL);
        mContext = getContext();
        mScroller = new Scroller(mContext);
        View.inflate(mContext, R.layout.slide_view_merge, this);
        mViewContent = (LinearLayout) findViewById(R.id.view_content);
        mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,mHolderWidth,getResources()
                .getDisplayMetrics()
        ));
    }
    public void setContentView(View view){
        mViewContent.addView(view);
    }
    public void shrink(){
        int offset = getScrollX();
        if (offset == 0){
            return;
        }
        scrollTo(0 ,0);
    }
    public void reset(){
        int offset = getScrollX();
        if (offset == 0){
            return;
        }
        smoothScrollTo(0, 0);
        Log.d(TAG,"reset");
    }
    public void adjust(boolean left){
        int offset = getScrollX();
        if (offset == 0) {
            return;
        }
        if (offset < 20) {
            this.smoothScrollTo(0, 0);
        } else if (offset < mHolderWidth - 20) {
            if (left) {
                this.smoothScrollTo(mHolderWidth, 0);
            } else {
                this.smoothScrollTo(0, 0);
            }
        } else {
            this.smoothScrollTo(mHolderWidth, 0);
        }
    }
    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }

    @Override
    public void computeScroll(){
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                float deltaX = x - mLastX;
                float deltaY = y = mLastY;
                mLastX = x;
                mLastY = y;
                if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
                    break;
                }
                if (deltaX != 0) {
                    float newScrollX = getScrollX() - deltaX;
                    if (newScrollX < 0){
                        newScrollX = 0;
                    } else if (newScrollX > mHolderWidth) {
                        newScrollX = mHolderWidth;
                    }
                    this.scrollTo((int) newScrollX, 0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
