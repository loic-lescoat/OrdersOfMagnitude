package com.example.ordersofmagnitude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name;
    TextView description;
    Disc disc;
    FloatingActionButton fab;
    float scaleFactor;
    float oldScaleFactor;
    float maxSize = 400f;
    float targetSizeForFirstDisc = 50f;
    boolean isFirstDisc;
    ActionsList actionsList;
    Action newAction;

    int[] COLORS;

    Vector<Disc> visibleDiscs = new Vector<>();
    int numberOfAction = -1;


    Handler handler = new Handler();

    Iterator<Action> actionsIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        disc = findViewById(R.id.disc0);
        fab = findViewById(R.id.fab);

        COLORS = new int[] {getColor(R.color.color0), getColor(R.color.color1),
                getColor(R.color.color2), getColor(R.color.color3),
                getColor(R.color.color4), getColor(R.color.color5),
                getColor(R.color.color6)}; // TODO get colors from resource file instead of hardcode


        InputStreamReader isr = new InputStreamReader(getResources().openRawResource(R.raw.actions));

        actionsList = ActionsList.getSampleActions(isr);
        actionsIterator = actionsList.getActionsIterator();

        if (actionsIterator.hasNext()){
            // first action
            scaleFactor = targetSizeForFirstDisc / actionsList.getAction(0).getCo2Equivalent();
            oldScaleFactor = scaleFactor;
            displayNextAction();
        } else{
            name.setText("no Actions...");
        }

    }

    public void fabClick(View v){
        if (actionsIterator.hasNext()){
            displayNextAction();
        } else{
            Intent i = new Intent();
            i.setClass(this, ConclusionActivity.class);
            startActivity(i);
        }
    }

    private void displayNextAction() {
        numberOfAction++;
        newAction = actionsIterator.next();

        if (scaleFactor * newAction.getCo2Equivalent() > maxSize){
            oldScaleFactor = scaleFactor;
            scaleFactor = maxSize / newAction.getCo2Equivalent();
        }

        Drawable newImage = getDrawable(getResources().getIdentifier("@drawable/" + newAction.getImage(), null, getPackageName()));

        setNextImage(newImage);
        name.setText(newAction.getName());
        description.setText(newAction.getDescription());


        // TODO: add Discs programatically; code below raises error right after drawing them
//        Disc newDisc = new Disc(this, 200f, Color.BLUE);
//        ConstraintLayout myLayout = (ConstraintLayout) findViewById(R.id.myLayout);
//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) disc.getLayoutParams();
//        newDisc.setLayoutParams(layoutParams);
//        newDisc.setId(View.generateViewId());
//        myLayout.addView(newDisc);
        Disc discToAdd = findViewById(getResources().getIdentifier("disc" + Integer.toString(numberOfAction),
                "id", getPackageName()));
        discToAdd.setZ((-1f) * numberOfAction);
        discToAdd.setColor(COLORS[numberOfAction % COLORS.length]);
        visibleDiscs.add(discToAdd);

        float radiusOfBiggestDiscOnscreen;
        if (numberOfAction == 0){
            radiusOfBiggestDiscOnscreen = 0f;
        } else{
            radiusOfBiggestDiscOnscreen = ((Disc) findViewById(getResources().getIdentifier("disc" + Integer.toString(numberOfAction - 1),
                    "id", getPackageName()))).getRadius();
        }

        discToAdd.setRadius(radiusOfBiggestDiscOnscreen);
        
        discToAdd.resize(oldScaleFactor*newAction.getCo2Equivalent(), Constants.TRANSITION_DURATION_MEDIUM);

        handler.postDelayed(runnable, Constants.TRANSITION_DURATION_MEDIUM); // wait 1s; resize to scalefactor * co2eq
    }

    private void setNextImage(Drawable newImage) {
        Drawable oldImage = imageView.getDrawable();
        Drawable[] drawables = new Drawable[]{oldImage, newImage};
        TransitionDrawable transitionDrawable = new TransitionDrawable(drawables);
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(Constants.TRANSITION_DURATION_SHORT);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO: resize all discs
            Iterator<Disc> onscreenDiscsIterator = visibleDiscs.iterator();
            while (onscreenDiscsIterator.hasNext()){
                Disc d = onscreenDiscsIterator.next();
                d.resizeByFactor(scaleFactor / oldScaleFactor, Constants.TRANSITION_DURATION_MEDIUM);
            }

        }
    };

    public Disc getLastDiscOnscreen(){
        Iterator<Disc> visibleDiscsIterator = visibleDiscs.iterator();
        Disc d = visibleDiscsIterator.next(); // assumes at least one disc visible
        while (visibleDiscsIterator.hasNext()){
            d = visibleDiscsIterator.next();
        }
        return d;
    }


}
