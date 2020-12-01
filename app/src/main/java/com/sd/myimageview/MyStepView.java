package com.sd.myimageview;

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

public class MyStepView extends View {
    private int mStepOuterColor = Color.RED;
    private int mStepInnererColor = Color.YELLOW;
    private int mStepBorderWidth = 30;
    private int mStepTextSize = 20;
    private int mStepTextColor = Color.BLACK;
    //画笔
    private Paint mOutPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;
    //步数
    private int StepMax = 0;
    private int StepCurrent = 0;

    public MyStepView(Context context) {
        this(context, null);
    }

    public MyStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyStepView);
        mStepBorderWidth = (int) array.getDimension(R.styleable.MyStepView_my_step_borderWidth, mStepBorderWidth);
        mStepInnererColor = array.getColor(R.styleable.MyStepView_my_step_innerColor, mStepInnererColor);
        mStepOuterColor = array.getColor(R.styleable.MyStepView_my_outerColor, mStepOuterColor);
        mStepTextColor = array.getColor(R.styleable.MyStepView_my_step_TextColor, mStepTextColor);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.MyStepView_my_step_TextSize, mStepTextSize);
        array.recycle();
        // TODO: 2020/11/30 初始化外圆弧画笔
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mStepBorderWidth);
        mOutPaint.setColor(mStepOuterColor);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        // TODO: 2020/11/30 初始化内弧度画笔
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mStepBorderWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setColor(mStepInnererColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        // TODO: 2020/11/30 初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mStepTextSize);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setAntiAlias(true);
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
        // TODO: 2020/11/30  绘制外圆弧
        RectF rectF = new RectF(mStepBorderWidth / 2, mStepBorderWidth / 2, getWidth() - mStepBorderWidth / 2, getWidth() - mStepBorderWidth / 2);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);
        // TODO: 2020/11/30  绘制内圆弧
        if (StepMax == 0) return;
        float percentage = (float) StepCurrent / StepMax;
        canvas.drawArc(rectF, 135, percentage * 270, false, mInnerPaint);
        // TODO: 2020/11/30  绘制文字
        String step = StepCurrent + "";
        Rect rect = new Rect();
        mTextPaint.getTextBounds(step, 0, step.length(), rect);
        int x = getWidth() / 2 - rect.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int y = getHeight() / 2 + ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom);
        canvas.drawText(step, x, y, mTextPaint);
    }

    public void setStepMax(int stepMax) {
        this.StepMax = stepMax;
    }

    public void setStepCurrent(int StepCurrent) {
        this.StepCurrent = StepCurrent;
        invalidate();
    }
}
