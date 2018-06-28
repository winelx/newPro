package com.example.administrator.newsdf.pzgc.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.administrator.newsdf.R;

/**
 * Created by Administrator on 2018/6/25 0025.
 */

public class FloatMeunAnims {
    public void doclickt(final View view, final View view2, final View view3) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", 0f, -180f);
        ObjectAnimator translation2 = ObjectAnimator.ofFloat(view2, "translationY", -180f, -350f);
        ObjectAnimator translation3 = ObjectAnimator.ofFloat(view2, "alpha", 0, 1);
        ObjectAnimator translation4 = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        translation.setDuration(300);
        translation2.setDuration(400);
        translation3.setDuration(400);
        translation4.setDuration(300);
        // 可以设置重复次数
        translation.setRepeatCount(0);
        translation2.setRepeatCount(0);
        //  可以设置插值器,使之按照一定的规律运动
        animatorSet.setInterpolator(new DecelerateInterpolator());
        // 开始方法
        animatorSet.play(translation2).with(translation3).after(translation).after(translation4);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            //动画开始
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setBackgroundResource(R.mipmap.diass_meun);
            }
        });
        animatorSet.start();
    }

    public void doclicktclose(final View view, final View view2, final View view3) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", -180f, 0f);
        ObjectAnimator translation2 = ObjectAnimator.ofFloat(view2, "translationY", -350f, -0f);
        ObjectAnimator translation3 = ObjectAnimator.ofFloat(view2, "alpha", 1, 0);
        ObjectAnimator translation4 = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        //执行时间
        translation3.setDuration(300);
        translation4.setDuration(400);
        translation.setDuration(300);
        translation2.setDuration(400);
        // 可以设置重复次数
        translation.setRepeatCount(0);
        translation2.setRepeatCount(0);
        // 开始方法
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.play(translation).with(translation2).with(translation3).with(translation4);
        //为动画设置监听
        animatorSet.addListener(new AnimatorListenerAdapter() {
            //动画开始
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view3.setBackgroundResource(R.mipmap.floatbutton_meun);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }
}
