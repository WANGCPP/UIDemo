package com.uidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.uidemo.R;

/**
 * Created by WANGCPP on 2018/1/25.
 * 环形进度条
 */
public class RoundProgressBar extends View {

    private static final String TAG = RoundProgressBar.class.getSimpleName();

    /**
     * 控件总宽度
     */
    private int widthSize;

    /**
     * 控件总高度
     */
    private int heightSize;


    /**
     * 画笔对象的引用
     */
    private Paint mPaint;

    /**
     * 圆环底色
     */
    private int roundColor;

    /**
     * 圆环进度颜色
     */
    private int roundProgressColor;

    /**
     * 中间百分比进度文字颜色
     */
    private int textColor;

    /**
     * 中间百分比进度文字大小
     */
    private float textSize;

    /**
     * 进度最大值
     */
    private int max;

    /**
     * 是否设定文字
     */
    private boolean textIsDisplayable;

    /**
     * 圆环宽度
     */
    private float arcWidth;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    /**
     * 空心
     */
    public static final int STROKE = 0;

    /**
     * 实心
     */
    public static final int FILL = 1;


    /**
     * 需要描画的进度
     */
    private int mProgress;

    /**
     * 扫描速度
     */
    private int scanSpeed;

    /**
     * 矩形边界区域
     */
    private RectF mOval = new RectF();

    /**
     * 控制线程运行
     */
    private boolean stopRunning = false;

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        roundColor = typedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.BLACK);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.DKGRAY);
        textSize = typedArray.getDimension(R.styleable.RoundProgressBar_textSize, (float) 14.0);
        max = typedArray.getInt(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = typedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = typedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        arcWidth = typedArray.getDimension(R.styleable.RoundProgressBar_arcWidth, (float) 30.0);
        scanSpeed = typedArray.getInt(R.styleable.RoundProgressBar_scanSpeed, 10);
        typedArray.recycle();

        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw()");
        super.onDraw(canvas);
        int centre = getWidth() / 2; //获取圆心的x坐标

        //=======画进度百分比文字========
        mPaint.setStrokeWidth(0);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        float textWidth = mPaint.measureText(mProgress + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        if (textIsDisplayable && mProgress != 0 && style == STROKE) {
            canvas.drawText(mProgress + "%", centre - textWidth / 2, centre + textSize / 2 - 10, mPaint); //画出进度百分比
        }


        mOval.set(arcWidth / 2, arcWidth / 2, getWidth() - arcWidth / 2,
                getHeight() - arcWidth / 2); //用于定义的圆弧的形状和大小的界限

        //=======画底衬圆环========
        mPaint.setStrokeWidth(arcWidth); //设置补充圆环的宽度
        mPaint.setColor(roundColor);  //设置进度的颜色
        mPaint.setAntiAlias(true);  //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mOval, -90, 360, false, mPaint);  //根据进度画圆弧

        //=======画进度圆环========
        mPaint.setStrokeWidth(arcWidth); //设置圆环的宽度
        mPaint.setColor(roundProgressColor);  //设置进度的颜色
        mPaint.setAntiAlias(true);  //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mOval, -90, 360 * mProgress / max, false, mPaint);  //根据进度画圆弧
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        Log.d(TAG, "宽的模式:" + widthMode);
        Log.d(TAG, "高的模式:" + heightMode);
        Log.d(TAG, "宽的尺寸:" + widthSize);
        Log.d(TAG, "高的尺寸:" + heightSize);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow()");
        stopRunning = true;
        super.onDetachedFromWindow();
    }

    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max 进度最大值
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return 进度
     */
    public synchronized int getProgress() {
        return mProgress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress 进度
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.mProgress = progress;
            postInvalidate();
        }
    }

    /**
     * 开始圆环扫描
     *
     * @param start 起始角度
     * @param end   终止角度
     */
    public synchronized void startScan(final int start, final int end) {

        if (start >= end) {
            return;
        }

        mProgress = start;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.d(TAG, "startScan");
                    if (mProgress == end || stopRunning) {
                        break;
                    }
                    mProgress++;
                    postInvalidate();

                    try {
                        Thread.sleep((long) (1000 * (float) (1.0 / scanSpeed)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public float getArcWidth() {
        return this.arcWidth;
    }

    public void setArcWidth(float arcWidth) {
        this.arcWidth = arcWidth;
    }

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getScanSpeed() {
        return scanSpeed;
    }

    public void setScanSpeed(int scanSpeed) {
        this.scanSpeed = scanSpeed;
    }

}
