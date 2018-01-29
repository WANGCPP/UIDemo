package com.uidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.uidemo.R;

import java.util.concurrent.Executor;

/**
 * Created by WANGCPP on 2018/1/29.
 * 贝塞尔曲线
 */
public class BezierView extends View implements View.OnTouchListener {

    private final String TAG = BezierView.class.getSimpleName();


    /**
     * 画笔对象
     */
    private Paint mPaint = null;

    /**
     * 路径对象
     */
    private Path mPath = null;

    /**
     * 控制点X轴坐标
     */
    private float mXCoordinate = 300;

    /**
     * 控制点Y轴坐标
     */
    private float mYCoordinate = 600;

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BezierView);

        typedArray.recycle();
        mPaint = new Paint();
        mPath = new Path();
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.parseColor("#FF0000"));

        //TODO: 贝塞尔曲线
        mPath.moveTo(200, 600);
        mPath.quadTo(mXCoordinate, mYCoordinate, 400, 600);

        canvas.drawPath(mPath, mPaint);
        if (!mPath.isEmpty()) {
            mPath.reset(); //清空本次路径保存的内容，防止路径残留到下一次绘制
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_BUTTON_PRESS:
                break;
            case MotionEvent.ACTION_DOWN:
                mXCoordinate = event.getX();
                mYCoordinate = event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mXCoordinate = event.getX();
                mYCoordinate = event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                float offsetX = 300 - mXCoordinate;
                float offsetY = 600 - mYCoordinate;
                rebound(offsetX, offsetY);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 曲线回弹
     *
     * @param offsetYCoordinate Y轴偏移距离
     * @param offsetXCoordinate X轴偏移距离
     */
    private synchronized void moveBack(final float offsetXCoordinate, final float offsetYCoordinate) {
        float stepYCoordinate;
        float stepXCoordinate;
        stepYCoordinate = offsetYCoordinate / 2000;
        stepXCoordinate = offsetXCoordinate / 2000;

        while (true) {
            Log.d(TAG, "x == " + mXCoordinate + " y == " + mYCoordinate);
            if (Math.abs(300 - mXCoordinate) < 0.01 || Math.abs(600 - mYCoordinate) < 0.01) {
                break;
            }
            mYCoordinate += stepYCoordinate;
            mXCoordinate += stepXCoordinate;
            Log.d(TAG, "PID == " + Thread.currentThread().getId());
            postInvalidate();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void rebound(final float offsetXCoordinate, final float offsetYCoordinate) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                moveBack(offsetXCoordinate, offsetYCoordinate);
            }
        }).start();
    }
}
