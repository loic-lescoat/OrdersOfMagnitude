package com.example.ordersofmagnitude;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class IntroActivity extends AppCompatActivity {

    Disc disc0; // do not use findViewById here
    Disc disc1;
    TextView textView0;
    TextView textView1;
    int step = 0; // step of intro
    ValueAnimator disc0Animation;
    ValueAnimator disc1Animation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        disc0 = findViewById(R.id.disc0);
        disc1 = findViewById(R.id.disc1);

        textView0 = findViewById(R.id.textView0);

        disc0.resize(Constants.DISC_MEDIUM, Constants.TRANSITION_DURATION_MEDIUM);

    }

    public void continueIntro(View view) {
        switch (step){
            case 0: // end of step 0, on to step 1
//                textView0.setText(R.string.step1msg);
                changeTextWithAlpha(textView0, R.string.step1msg);
                disc0Animation = disc0.loopBetweenSizes(disc0.getRadius(), Constants.DISC_LARGE, Constants.TRANSITION_DURATION_MEDIUM);
                break;
            case 1: // end of step 1
                disc0Animation.cancel();
                disc0.resize(Constants.DISC_LARGE, Constants.TRANSITION_DURATION_MEDIUM);
                disc1Animation = disc1.loopBetweenSizes(Constants.DISC_SMALL, Constants.DISC_MEDIUM, Constants.TRANSITION_DURATION_MEDIUM);
                changeTextWithAlpha(textView0, R.string.step2msg);
                break;
            default:
                ActionsList a = new ActionsList();
                Intent i = new Intent();
                i.setClass(this, MainActivity.class);
                startActivity(i);
        }
        step++;

    }

    public void changeTextWithAlpha(final TextView textView, final int newTextId){
        ValueAnimator animation = ValueAnimator.ofFloat(1f, 0f);
        animation.setDuration((long) Constants.TRANSITION_DURATION_SHORT);

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
                textView.setText(getResources().getString(newTextId));
            }
        });

        animation.start();
    }

//    public void toggleSize(View view) {
//        Disc viewDisc = (Disc) view;
//        float rad = viewDisc.getRadius();
//        viewDisc.resize(rad, 100f + 300f - rad, 1000);
//    }


}
