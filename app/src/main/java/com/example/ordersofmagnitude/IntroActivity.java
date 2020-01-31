package com.example.ordersofmagnitude;

import androidx.appcompat.app.AppCompatActivity;

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

        disc0.resize(ConstantsAndHelpers.DISC_MEDIUM, ConstantsAndHelpers.TRANSITION_DURATION_MEDIUM);

    }

    public void continueIntro(View view) {
        switch (step){
            case 0: // end of step 0, on to step 1
//                textView0.setText(R.string.step1msg);
                ConstantsAndHelpers.changeTextWithAlpha(textView0, getString(R.string.step1msg));
                disc0Animation = disc0.loopBetweenSizes(disc0.getRadius(), ConstantsAndHelpers.DISC_LARGE, ConstantsAndHelpers.TRANSITION_DURATION_MEDIUM);
                break;
            case 1: // end of step 1
                disc0Animation.cancel();
                disc0.resize(ConstantsAndHelpers.DISC_LARGE, ConstantsAndHelpers.TRANSITION_DURATION_MEDIUM);
                disc1Animation = disc1.loopBetweenSizes(ConstantsAndHelpers.DISC_SMALL, ConstantsAndHelpers.DISC_MEDIUM, ConstantsAndHelpers.TRANSITION_DURATION_MEDIUM);
                ConstantsAndHelpers.changeTextWithAlpha(textView0, getString(R.string.step2msg));
                break;
            default:
                ActionsList a = new ActionsList();
                Intent i = new Intent();
                i.setClass(this, MainActivity.class);
                startActivity(i);
        }
        step++;

    }



//    public void toggleSize(View view) {
//        Disc viewDisc = (Disc) view;
//        float rad = viewDisc.getRadius();
//        viewDisc.resize(rad, 100f + 300f - rad, 1000);
//    }


}
