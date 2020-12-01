package com.sd.myimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ChangTextView extends androidx.appcompat.widget.AppCompatTextView {
    private int originColor = Color.BLACK;
    private int changeColor = Color.RED;
    private Paint originPaint;
    private Paint changePaint;
    private float mCurrentProgress = 0.0f;
    private Direction mDirection = Direction.LEFT_TO_RIGHT;
    public enum Direction{
        LEFT_TO_RIGHT,RIGHT_TO_LEFT
    }
    public ChangTextView(Context context) {
        this(context, null);
    }

    public ChangTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChangTextView);
        originColor = array.getColor(R.styleable.ChangTextView_originColor, originColor);
        changeColor = array.getColor(R.styleable.ChangTextView_changeColor, changeColor);
        originPaint = getPaintByColor(originColor);
        changePaint = getPaintByColor(changeColor);
        array.recycle();
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        // TODO: 2020/11/30 防抖动
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int middle = (int) (mCurrentProgress * getWidth());
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, changePaint, 0, middle);
            drawText(canvas, originPaint, middle, getWidth());
        }else {
            drawText(canvas, changePaint, getWidth()-middle, getWidth());
            drawText(canvas, originPaint, 0, getWidth()-middle);
        }
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        // TODO: 2020/11/30 绘制不变色
        Rect rect = new Rect(start,0,end,getHeight());
        canvas.clipRect(rect);
        // TODO: 2020/11/30 绘制全部文字
        String text = getText().toString();
        Rect bounds = new Rect();
        originPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        int y = getHeight() / 2 + ((paint.getFontMetricsInt().bottom - paint.getFontMetricsInt().top) / 2 - paint.getFontMetricsInt().bottom);
        canvas.drawText(text, x, y, paint);
        canvas.restore();
    }

    public void setmCurrentProgress(float mCurrentProgress){
        this.mCurrentProgress = mCurrentProgress;
        invalidate();
    }

    public void setmDirection(Direction mDirection){
        this.mDirection = mDirection;
    }
}
