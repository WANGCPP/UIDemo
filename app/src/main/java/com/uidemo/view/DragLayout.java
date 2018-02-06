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
import android.widget.OverScroller;

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
            Log.d(TAG, "tryCaptureView == " + child.getId());
            Log.d(TAG, "child id ==" + dragView.getId());
            return dragView.getId() == child.getId();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            Log.e(TAG, "left:" + left + "++++dx:" + dx);
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
            Log.e(TAG, "top:" + top + "++++dy:" + dy);
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
        public int getViewHorizontalDragRange(@NonNull View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            Log.d(TAG, "onViewReleased trackview width == " + trackView.getWidth() + "releaseChild left == " + releasedChild.getLeft());

            if (releasedChild.getLeft() < getPaddingLeft() - trackView.getWidth() / 3) {
                Log.d(TAG, "little " + releasedChild.getLeft());
                viewDragHelper.settleCapturedViewAt(targetLocation, 0);
            } else {
                Log.d(TAG, "big");
                viewDragHelper.settleCapturedViewAt(startLocation, 0);
            }
            invalidate();
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    //获取拖拽的子View
    @Override
    protected void onFinishInflate() {
        Log.d(TAG, "onFinishInflate()");
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
