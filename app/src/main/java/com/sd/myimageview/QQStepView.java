package com.sd.myimageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class QQStepView extends View {
    private int mStepOuterColor = Color.BLUE;
    private int mStepInnerColor = Color.YELLOW;
    //代表20px
    private int mStepBorderWidth = 30;
    private int mStepTextSize = 16;
    private int mStepTextColor = Color.BLACK;
    //外圆弧画笔
    private Paint mStepOuterPaint;
    //内圆弧画笔
    private Paint mStepInnerPaint;
    //文字画笔
    private Paint mStepTextPaint;
    //总进度
    private int mStepMax = 0;
    //当前进度
    private int mStepCurrent = 0;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 获取自定义属性
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mStepInnerColor = array.getColor(R.styleable.QQStepView_step_innerColor, mStepInnerColor);
        mStepOuterColor = array.getColor(R.styleable.QQStepView_step_outerColor, mStepOuterColor);
        mStepBorderWidth = (int) array.getDimension(R.styleable.QQStepView_step_borderWidth, mStepBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_step_TextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_step_TextColor, mStepTextColor);
        array.recycle();
        // TODO: 2020/11/30 初始化外弧度画笔
        mStepOuterPaint = new Paint();
        mStepOuterPaint.setAntiAlias(true);
        mStepOuterPaint.setStrokeWidth(mStepBorderWidth);
        mStepOuterPaint.setColor(mStepOuterColor);
        mStepOuterPaint.setStyle(Paint.Style.STROKE);
        mStepOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        // TODO: 2020/11/30 初始化内弧度画笔
        mStepInnerPaint = new Paint();
        mStepInnerPaint.setAntiAlias(true);
        mStepInnerPaint.setStrokeWidth(mStepBorderWidth);
        mStepInnerPaint.setColor(mStepInnerColor);
        mStepInnerPaint.setStyle(Paint.Style.STROKE);
        mStepInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        // TODO: 2020/11/30 文字画笔
        mStepTextPaint = new Paint();
        mStepTextPaint.setColor(mStepTextColor);
        mStepTextPaint.setAntiAlias(true);
        mStepTextPaint.setTextSize(mStepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(height, width));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(mStepBorderWidth / 2, mStepBorderWidth / 2, getWidth() - mStepBorderWidth / 2, getWidth() - mStepBorderWidth / 2);
        // TODO: 2020/11/30 内弧度
        canvas.drawArc(rectF, 135, 270, false, mStepOuterPaint);
        if (mStepMax == 0) return;
        // TODO: 2020/11/30 得出当前的步数占总步数的百分比，然后乘以270
        float sweepAngle = (float) mStepCurrent / mStepMax;
        // TODO: 2020/11/30 外弧度
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mStepInnerPaint);
        // TODO: 2020/11/30 绘制文字
        String stepText = mStepCurrent + "";
        Rect bounds = new Rect();
        mStepTextPaint.getTextBounds(stepText, 0, stepText.length(), bounds);
        // TODO: 2020/11/30 文字横坐标为控件宽度的一半减去文字宽度的一半
        int dx = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mStepTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        // TODO: 2020/11/30 文字纵坐标为文字基线加上文字高度的一半
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mStepTextPaint);
    }

    public synchronized void setStepMax(int max){
        this.mStepMax = max;
    }

    public synchronized void setStepCurrent(int current){
        this.mStepCurrent = current;
        //不断绘制
        invalidate();
    }
}
