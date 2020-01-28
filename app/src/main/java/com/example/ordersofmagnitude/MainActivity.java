package com.example.ordersofmagnitude;

import androidx.appcompat.app.AppCompatActivity;

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
    int[] COLORS_LIST = new int[] {Color.MAGENTA, Color.RED,Color.GREEN, Color.CYAN, Color.BLUE};

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

        newAction = actionsIterator.next();

        if (scaleFactor * newAction.getCo2Equivalent() > maxSize){
            oldScaleFactor = scaleFactor;
            scaleFactor = maxSize / newAction.getCo2Equivalent();
        }

        Drawable newImage = getDrawable(getResources().getIdentifier("@drawable/" + newAction.getImage(), null, getPackageName()));

        imageView.setImageDrawable(newImage);
        name.setText(newAction.getName());
        description.setText(newAction.getDescription());

        disc.resize(oldScaleFactor*newAction.getCo2Equivalent(), 1000);
        // wait 1s; resize to scalef * ...
        handler.postDelayed(runnable, 1200);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            disc.resize(scaleFactor * newAction.getCo2Equivalent(), 1000);
        }
    };


}
