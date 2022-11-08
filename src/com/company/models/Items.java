package com.company.models;

public class Items {
    //Fields
    private String name;
    private String description;
    private int value;
    private int cost;
    private int strength;
    private String type;
    private String img;
    private int xaxis;
    private int yaxis;
    private int width;
    private int height;
    private String keyReq;
    private String keyError;

    //Constructor

    public Items(String name, String description, int value, int cost, int strength, String type, String img, int xaxis, int yaxis, int width, int height, String keyReq, String keyError) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.cost = cost;
        this.strength = strength;
        this.type = type;
        this.img = img;
        this.xaxis = xaxis;
        this.yaxis = yaxis;
        this.width = width;
        this.height = height;
        this.keyReq = keyReq;
        this.keyError = keyError;
    }

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getXaxis() {
        return xaxis;
    }

    public void setXaxis(int xaxis) {
        this.xaxis = xaxis;
    }

    public int getYaxis() {
        return yaxis;
    }

    public void setYaxis(int yaxis) {
        this.yaxis = yaxis;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getKeyReq() {
        return keyReq;
    }

    public void setKeyReq(String keyReq) {
        this.keyReq = keyReq;
    }

    public String getKeyError() {
        return keyError;
    }

    public void setKeyError(String keyError) {
        this.keyError = keyError;
    }
}