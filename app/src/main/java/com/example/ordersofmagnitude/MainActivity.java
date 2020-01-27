package com.example.ordersofmagnitude;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStreamReader isr = new InputStreamReader(getResources().openRawResource(R.raw.actions));

        ActionsList actionsList = ActionsList.getSampleActions(isr);
        int a = 5;
    }
}
