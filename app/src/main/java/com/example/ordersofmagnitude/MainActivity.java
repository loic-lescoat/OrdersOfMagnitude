package com.example.ordersofmagnitude;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
    int[] COLORS_LIST = new int[] {Color.MAGENTA, Color.RED,Color.GREEN, Color.CYAN, Color.BLUE}; // TODO get colors from resource file
    Vector<Disc> onscreenDiscs= new Vector<>();
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

        imageView.setImageDrawable(newImage);
        name.setText(newAction.getName());
        description.setText(newAction.getDescription());


        // TODO: add Disc, resize all
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
        discToAdd.setColor(COLORS_LIST[numberOfAction % COLORS_LIST.length]);
        onscreenDiscs.add(discToAdd);

        discToAdd.resize(oldScaleFactor*newAction.getCo2Equivalent(), 1000);

        handler.postDelayed(runnable, 1200); // wait 1s; resize to scalefactor * co2eq
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO: resize all discs
            Iterator<Disc> onscreenDiscsIterator = onscreenDiscs.iterator();
            while (onscreenDiscsIterator.hasNext()){
                Disc d = onscreenDiscsIterator.next();
                d.resizeByFactor(scaleFactor / oldScaleFactor, 1000);
            }

        }
    };


}
