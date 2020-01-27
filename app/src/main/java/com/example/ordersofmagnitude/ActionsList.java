package com.example.ordersofmagnitude;

import java.util.Iterator;
import java.util.Vector;

public class ActionsList {

    private Vector<Action> actionsVector = new Vector<>();

    // see Nov version for constructor extracting from csv

    public Iterator<Action> getActionsIterator(){
        return actionsVector.iterator();
    }

    public Action getAction(int index){
        return actionsVector.get(index);
    }
}
