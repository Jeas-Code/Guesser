package com.example.jeas.netapp;

import android.graphics.drawable.Drawable;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.graphics.drawable.TransitionDrawable;

/**
 * Created by Jeas on 2021/5/24.
 */

public class ImageAnimation {

    private AlphaAnimation alphaAnimation;

    public void setAlphaAnimation(AlphaAnimation alphaAnimation) {
        this.alphaAnimation = alphaAnimation;
    }

    public void fadeIn(ImageView view, Integer integer){
        //设置图片效果
        view.setImageResource(integer);
        view.setAlpha(0.0f);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);    //深浅动画持续时间
        alphaAnimation.setFillAfter(true);   //动画结束时保持结束的画面
    }

    public void fadeOut(ImageView view, Integer integer){
        view.setImageResource(integer);
        view.setAlpha(1.0f);
        view.setAnimation(alphaAnimation);
        alphaAnimation.start();
    }


}
