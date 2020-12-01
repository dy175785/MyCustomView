package com.sd.myimageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class CircularImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Paint mPaint;
    private PorterDuffXfermode duffXfermode;

    public CircularImageView(Context context) {
        super(context);
        init();
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        duffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint.setXfermode(duffXfermode);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        int width = drawable.getIntrinsicWidth();
        //获得图片的高度
        int height = drawable.getIntrinsicHeight();
        //短的二分之一作为半径
        int radius = height > width ? width / 2 : height / 2;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);
    }
}
