package com.sd.myimageview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class RootActivity extends AppCompatActivity {
    private List<ChangTextView> textViewList = new ArrayList<>();
    private String[] strings = {"视频","直播","视频","视频","视频","视频","视频"};
    private LinearLayout llRoot;
    private ViewPager vpRoot;
    private ChangTextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        initView();
    }

    private void initView() {
        llRoot = findViewById(R.id.ll_root);
        vpRoot = findViewById(R.id.view_pager);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        for (int i = 0; i < strings.length; i++) {
            textView = new ChangTextView(this);
            textView.setText(strings[i]);
            textView.setLayoutParams(params);
            textViewList.add(textView);
            llRoot.addView(textView);
        }
        vpRoot.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(strings[position]);
            }

            @Override
            public int getCount() {
                return strings.length;
            }
        });

        vpRoot.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                textViewList.get(position)
                ChangTextView left = textViewList.get(position);
                left.setmDirection(ChangTextView.Direction.RIGHT_TO_LEFT);
                left.setmCurrentProgress(1-positionOffset);

                if (position == strings.length-1)return;
                ChangTextView right = textViewList.get(position+1);
                right.setmDirection(ChangTextView.Direction.LEFT_TO_RIGHT);
                right.setmCurrentProgress(positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
