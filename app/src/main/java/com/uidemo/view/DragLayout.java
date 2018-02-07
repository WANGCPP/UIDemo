package com.uidemo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.uidemo.R;

/**
 * Created by WANGCPP on 2018/2/5.
 * ViewDragHelper 实践
 */
public class DragLayout extends ConstraintLayout {

    private final String TAG = DragLayout.class.getSimpleName();

    /**
     * 上下文
     */
    private Context mContext = null;

    private ViewDragHelper viewDragHelper = null;

    /**
     * 标记正在拖拽的view
     */
    private View dragView = null;

    /**
     * 标记跟踪的view
     */
    private View trackView = null;

    /**
     * 起始位置
     */
    private int startLocation = 0;

    /**
     * 目标位置
     */
    private int targetLocation = 0;


    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;

        viewDragHelper = ViewDragHelper.create(this, mCallback);
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL); // 添加边缘检测
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return dragView.getId() == child.getId();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            //两个if主要是让view在ViewGroup中
            if (left < (getPaddingLeft() - trackView.getWidth())) {
                return (getPaddingLeft() - trackView.getWidth());
            }

            if (left > getWidth() - child.getMeasuredWidth()) {
                return getWidth() - child.getMeasuredWidth();
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            //两个if主要是让view在ViewGroup中
            if (top < getPaddingTop()) {
                return getPaddingTop();
            }

            if (top > getHeight() - child.getMeasuredHeight()) {
                return getHeight() - child.getMeasuredHeight();
            }
            return top;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            trackView.offsetLeftAndRight(dx);
            trackView.offsetTopAndBottom(dy);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (releasedChild.getLeft() < getPaddingLeft() - trackView.getWidth() / 3) {
                viewDragHelper.settleCapturedViewAt(targetLocation, 0);
            } else {
                viewDragHelper.settleCapturedViewAt(startLocation, 0);
            }
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        /**
         * 如果view是clickable的，必须要重写下面这个方法
         * @param child 子View
         * @return 移动范围（像素） 作用不是很清楚
         */
        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return 1;
//            Log.d(TAG,"horizontal range == "+(getMeasuredWidth() - child.getMeasuredWidth()));
//            return getMeasuredWidth() - child.getMeasuredWidth();
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * ViewGroup 事件拦截器
     *
     * @param ev 触摸事件
     * @return true:触摸事件被ViewGroup拦截，不在向子View分发  false:ViewGroup不拦截事件，向子View分发
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);
        if (result) {
            Log.d(TAG, "onInterceptTouchEvent");
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 获取子View
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = findViewById(R.id.view1_draglayout);
        trackView = findViewById(R.id.view2_draglayout);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        startLocation = 0;
        targetLocation = -trackView.getWidth();
    }

    public View getDragView() {
        return dragView;
    }

    public void setDragView(View dragView) {
        this.dragView = dragView;
    }

    public View getTrackView() {
        return trackView;
    }

    public void setTrackView(View trackView) {
        this.trackView = trackView;
    }
}
