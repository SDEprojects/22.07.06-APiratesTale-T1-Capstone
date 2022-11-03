package com.company.models;

public class Items {
    //Fields
    private String name;
    private String description;
    private int value;

    //Constructor
    public Items(String name, String description, int value) { //adding "value"
        this.name = name;
        this.description = description;
        this.value = value;
    }

    //adding getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}