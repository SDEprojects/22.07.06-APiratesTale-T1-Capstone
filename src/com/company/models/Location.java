package com.company.models;

import java.util.List;
import java.util.Map;

public class Location {

    private String name;
    private String description;
    private List<String> NPC;
    private List<String> items;
    private Map<String, String> directions;
    private String img;
    private String island;
    private String grid;

    public Location(String name, String description, List<String> NPC, List<String> items, Map<String, String> directions, String img, String island, String grid) {
        this.name = name;
        this.description = description;
        this.NPC = NPC;
        this.items = items;
        this.directions = directions;
        this.img = img;
        this.island = island;
        this.grid = grid;
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

    public List<String> getNPC() {
        return NPC;
    }

    public void setNPC(List<String> NPC) {
        this.NPC = NPC;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public void setDirections(Map<String, String> directions) {
        this.directions = directions;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIsland() {
        return island;
    }

    public void setIsland(String island) {
        this.island = island;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }
}