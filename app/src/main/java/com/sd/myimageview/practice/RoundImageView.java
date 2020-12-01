package com.sd.myimageview.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 我们使用path路径可以画出绝大部分需求了
 * 圆形图片
 * 圆角图片
 * 几何不规则图片等
 */
public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Context context;
    private AttributeSet attributeSet;

    private int width;
    private int height;
    private Paint paint;
    private Path path;
    private Path srcPath;
    private Xfermode xfermode;
    private RectF srcRectF;

    public RoundImageView(@NonNull Context context) {
        super(context, null);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (this.context == null){
            this.context = context;
        }
        if (this.attributeSet == null){
            this.attributeSet = attrs;
        }
        initView();
    }

    private void initView(){
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        srcRectF = new RectF();
        paint = new Paint();
        path = new Path();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 利用bitmapdrawable来画
         * 解释一下下面对代码，如果你是第一次看可能会绕不过来。
         * getDrawable方法是获取src的一个目标资源
         *  @InspectableProperty(name = "src")
         *     public Drawable getDrawable() {
         *         if (mDrawable == mRecycleableBitmapDrawable) {
         *             // Consider our cached version dirty since app code now has a reference to it
         *             mRecycleableBitmapDrawable = null;
         *         }
         *         return mDrawable;
         *     }
         *  那么为什么canvas里面需要传入一个bitmap对象，首先bitmap是一个承载像素的对象，我们最终看到的也就是像素其实
         *  就是一个bitmap。而画布是什么，我们可以把bitmap理解为一张纸，canvas是放在纸上作画的一张布，我们用画笔在画布上画，
         *  最终都会落到bitmap上去，所以bitmap也是我们最终需要的。
         *
         *  官方解释了Drawable
         *  A Drawable is a general abstraction for “something that can be drawn.”
         *  Drawable就是能够被画到画布上的对象。
         *  Drawable和bitmap是什么关系呢，Drawable有一个子类bitmapDrawable，bitmap也是一种Drawable，所以也就有了
         *  bitmapDrawable，颜色也是一种Drawable，因为我们也有ColorDrawable等等。
         *  drawable.setBounds(0, 0, getWidth(), getHeight());
         *  drawable.draw(bitmapCanvas);
         *  上吗这俩行代码，第一行drawable的范围限制，第二行把我们获取到的bitmap对象绘制在画布中（也就对应了drawable是
         *  一种可以被画到画布上的对象），最终也会落到bitmap上。
         *  所以综上我们可以把drawable理解为一种快速作画的工具，比如印章。
         *
         *  Xfermode PorterDuff.Mode.DST_IN
         *  PorterDuff 一种图像合成模式，
         *  我们使用的 DST_IN模式 这种模式就是在俩者相交的地方绘制目标图像，并且绘制的效果会受到源图像对应地方透明体的影响
         */
        Bitmap mMaskBitmap = null;
        Bitmap bitmap = null;
        //得到当前的资源文件
        Drawable drawable = getDrawable();
        if (drawable != null) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bitmapCanvas = new Canvas(bitmap);
            //在canvas中画出一个这么大都范围
            drawable.setBounds(0, 0, getWidth(), getHeight());
            //目标图
            drawable.draw(bitmapCanvas);
            //得到一个目标
            //源图
            mMaskBitmap = getBitmapCircle(getWidth(), getHeight());
            //设置模式
            paint.setXfermode(xfermode);
            //参照目标图开始绘制
            //在俩者相交的地方绘制图像
            bitmapCanvas.drawBitmap(mMaskBitmap, 0.0f, 0.0f, paint);
            paint.setXfermode(null);
            //最后绘制
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        }



    }

    private Bitmap getBitmapCircle(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        //圆形图片实现
        canvas.drawCircle(width / 2, height / 2, Math.min(width / 2, height / 2), paint);
//        RectF rectF = new RectF(0,0,getWidth(),getHeight());
//        //圆角实现
//        canvas.drawRoundRect(rectF,10,10,paint);
        return bitmap;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Bitmap getBitmapHeart(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        Path path = new Path();
        path.addArc(0, 0, getWidth() / 2.0f, getHeight() / 1.8f, -225, 225);
        path.arcTo(getWidth() / 2.0f, 0, getWidth(), getHeight() / 1.8f, -180, 225, false);
        path.lineTo(getWidth() / 2.0f, getHeight());
        canvas.drawPath(path, paint);
        return bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }
}
