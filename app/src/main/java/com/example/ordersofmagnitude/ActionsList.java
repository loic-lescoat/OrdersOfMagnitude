package com.example.ordersofmagnitude;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

public class ActionsList implements Serializable {

    private Vector<Action> actionsVector = new Vector<>();

    // see Nov version for constructor extracting from csv

    public Iterator<Action> getActionsIterator(){
        return actionsVector.iterator();
    }

    public Action getAction(int index){
        return actionsVector.get(index);
    }

    public static ActionsList getSampleActions(InputStreamReader isr){
        ActionsList sampleActions = new ActionsList();
        try{
            BufferedReader reader = new BufferedReader(isr);
            String ligne = reader.readLine(); // skip first line
            while ((ligne = reader.readLine()) != null) {
                String [] data = ligne.split(";");
                int a = Integer.parseInt(data[5]);
                sampleActions.actionsVector.add(new Action(data[0], data[1], Float.parseFloat(data[2]), data[3], data[4]));
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
            Log.e("ee", e.getMessage());
        }
        return sampleActions;
    }
}
