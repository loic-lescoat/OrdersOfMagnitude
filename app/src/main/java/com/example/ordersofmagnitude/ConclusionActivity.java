package com.example.ordersofmagnitude;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConclusionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusion);
    }

    public void startOver(View view) {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }
}
