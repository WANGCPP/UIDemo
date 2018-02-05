package com.uidemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.widget.OverScroller;

/**
 * Created by WANGCPP on 2018/2/5.
 * 利用Scroll类实现滑动效果的自定义View
 */
public class MyScrollView extends android.support.v7.widget.AppCompatTextView {

    private final String TAG = MyScrollView.class.getSimpleName();

    private OverScroller mScroller = null;

    private float lastX;
    private float lastY;

    private float startX;
    private float startY;

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context, new BounceInterpolator()); // BounceInterpolator()添加果冻效果
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent Down");
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent Move");
                float disX = event.getRawX() - lastX;
                float disY = event.getRawY() - lastY;

                offsetLeftAndRight((int) disX);
                offsetTopAndBottom((int) disY);
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent Up");
                mScroller.startScroll((int) getX(), (int) getY(), -(int) (getX() - startX),
                        -(int) (getY() - startY));
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            setX(mScroller.getCurrX());
            setY(mScroller.getCurrY());
            invalidate();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startX = getX();
        startY = getY();
    }

    public void spingBack() {

        if (mScroller.springBack((int) getX(), (int) getY(), 0, (int) getX(), 0,
                (int) getY() - 100)) {
            Log.d("TAG", "getX()=" + getX() + "__getY()=" + getY());
            invalidate();
        }

    }

}
