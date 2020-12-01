package com.sd.myimageview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.sd.myimageview.practice.CustomProgress;

public class MainActivity extends AppCompatActivity {
    private QQStepView qqStepView;
    private MyStepView myStepView;
    private ChangTextView tvChange;
    private CustomProgress cp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        qqStepView = findViewById(R.id.step_view);
        qqStepView.setStepMax(4000);
        //属性动画
        // TODO: 2020/11/30 这个是表示零到三千慢慢的累加
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 3000);
        // TODO: 2020/11/30 一秒内加载完
        valueAnimator.setDuration(1000);
        // TODO: 2020/11/30 前面速度比较快，后面比较慢
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                qqStepView.setStepCurrent((int) animatedValue);
            }
        });
        valueAnimator.start();

        myStepView = findViewById(R.id.step_my);
        myStepView.setStepMax(5000);
        ValueAnimator animator = ObjectAnimator.ofFloat(0,4500);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                myStepView.setStepCurrent((int) value);
            }
        });
        animator.start();
        tvChange = findViewById(R.id.tv_change);
        cp = findViewById(R.id.cp);
        cp.start(60,100);
    }

    public void left_to_right(View view){
        tvChange.setmDirection(ChangTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator1 = ObjectAnimator.ofFloat(0,1);
        valueAnimator1.setDuration(3000);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvChange.setmCurrentProgress((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator1.start();
    }
    public void right_to_left(View view){
        tvChange.setmDirection(ChangTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator1 = ObjectAnimator.ofFloat(0,1);
        valueAnimator1.setDuration(3000);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvChange.setmCurrentProgress((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator1.start();
    }
}