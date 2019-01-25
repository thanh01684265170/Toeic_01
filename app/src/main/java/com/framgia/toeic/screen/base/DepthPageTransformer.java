package com.framgia.toeic.screen.base;

import android.support.v4.view.ViewPager;
import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIDDLE_SCALE = 0.75f;
    private static final float MIN_SCALE = 0f;
    private static final float MAX_SCALE = 1f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1) {
            view.setAlpha(MIN_SCALE);
            return;
        }
        if (position <= 0) {
            view.setAlpha(MAX_SCALE);
            view.setTranslationX(MIN_SCALE);
            view.setScaleX(MAX_SCALE);
            view.setScaleY(MAX_SCALE);
            return;
        }
        if (position <= 1) {
            view.setAlpha(1 - position);
            view.setTranslationX(pageWidth * -position);
            float scaleFactor = MIDDLE_SCALE + (1 - MIDDLE_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            return;
        }
        view.setAlpha(MIN_SCALE);
    }
}
