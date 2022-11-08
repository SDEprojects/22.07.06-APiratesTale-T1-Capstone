package com.company.models;

import java.util.List;
import java.util.Map;

public class Characters {
    private String name;
    private Map<String,String> quote;
    private List<String> items;
    private int hp;
    private boolean isFriendly;
    private int dp;
    private List<String> questReq;
    private List<String> reward;
    private String img;
    private int xaxis;
    private int yaxis;
    private int width;
    private int height;
    private String type;

    public Characters(String name, Map<String, String> quote, List<String> items, int hp, boolean isFriendly, int dp, List<String> questReq, List<String> reward, String img, int xaxis, int yaxis, int width, int height, String type) {
        this.name = name;
        this.quote = quote;
        this.items = items;
        this.hp = hp;
        this.isFriendly = isFriendly;
        this.dp = dp;
        this.questReq = questReq;
        this.reward = reward;
        this.img = img;
        this.xaxis = xaxis;
        this.yaxis = yaxis;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getQuote() {
        return quote;
    }

    public void setQuote(Map<String, String> quote) {
        this.quote = quote;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public List<String> getQuestReq() {
        return questReq;
    }

    public void setQuestReq(List<String> questReq) {
        this.questReq = questReq;
    }

    public List<String> getReward() {
        return reward;
    }

    public void setReward(List<String> reward) {
        this.reward = reward;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}