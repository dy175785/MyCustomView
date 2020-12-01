package com.sd.myimageview.practice;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

public class CustomProgress extends View {
    private int mOutColor = Color.BLACK;
    private int mInnerColor = Color.BLUE;
    private int mTextColor = Color.BLACK;
    private float mBorderWidth = 30;
    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private int mOverallProgress = 0;
    private int mCurrentProgress = 0;

    public CustomProgress(Context context) {
        this(context, null);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // TODO: 2020/12/1 外弧度
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOutColor);
        mOutPaint.setStyle(Paint.Style.STROKE);
        // TODO: 2020/12/1 内弧度
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        // TODO: 2020/12/1 文字画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mBorderWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: 2020/12/1 外弧度
        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, getWidth() - mBorderWidth / 2, getHeight() - mBorderWidth / 2);
        canvas.drawArc(rectF, 0, 360, false, mOutPaint);
        // TODO: 2020/12/1 绘制Text
        String text = mCurrentProgress + "%";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int y = getHeight()/2 + (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        canvas.drawText(text, x,y,mTextPaint);
        // TODO: 2020/12/1 内弧度
        if (mOverallProgress == 0 )return;
        float sweepAngle = (float) mCurrentProgress / mOverallProgress;
        canvas.drawArc(rectF, 0, sweepAngle * 360, false, mInnerPaint);
    }

    private void setProgress(int mCurrentProgress){
        this.mCurrentProgress = mCurrentProgress;
        invalidate();
    }

    public void start(int mCurrentProgress,int mOverallProgress){
        this.mOverallProgress = mOverallProgress;
        ValueAnimator animator = ObjectAnimator.ofFloat(0,mCurrentProgress);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float an = (float) animation.getAnimatedValue();
                setProgress((int) an);
            }
        });
        animator.start();
    }

}
