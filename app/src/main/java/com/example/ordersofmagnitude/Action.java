package com.example.ordersofmagnitude;

import java.io.Serializable;

public class Action implements Serializable {

    private String name;
    private String description;
    private float co2Equivalent;
    private String image;
    private String reference;

    public Action(String name, String description, float co2Equivalent, String image, String reference){
        this.name = name;
        this.description = description;
        this.co2Equivalent = co2Equivalent;
        this.image = image;
        this.reference = reference;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public float getCo2Equivalent(){
        return co2Equivalent;
    }

    public String getImage(){
        return image;
    }

    public String getReference(){
        return reference;
    }
}
