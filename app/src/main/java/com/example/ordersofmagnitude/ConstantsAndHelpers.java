package com.example.ordersofmagnitude;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.widget.TextView;

public class ConstantsAndHelpers {

    public static float DISC_SMALL = 50f;
    public static float DISC_MEDIUM = 150f;
    public static float DISC_LARGE = 300f;

    public static int TRANSITION_DURATION_MEDIUM = 1000;
    public static int TRANSITION_DURATION_SHORT = 250;

    public static void changeTextWithAlpha(final TextView textView, final String newText){
        ValueAnimator animation = ValueAnimator.ofFloat(1f, 0f);
        animation.setDuration((long) ConstantsAndHelpers.TRANSITION_DURATION_SHORT);

        animation.setRepeatMode(ValueAnimator.REVERSE);
        animation.setRepeatCount(1);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                textView.setAlpha((float) updatedAnimation.getAnimatedValue());
            }


        });
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                textView.setText(newText);
            }
        });

        animation.start();
    }
}
