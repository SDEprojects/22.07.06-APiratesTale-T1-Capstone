package com.company.models;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.company.client.GameMain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private ArrayList<Locations> locations = new ArrayList<>();
    private ArrayList<Items> items = new ArrayList<>();
    private ArrayList<Characters> characters = new ArrayList<>();
    private JsonTools tools = new JsonTools();
    private GameMain gm;

    public Game(GameMain gm) {
        this.gm = gm;
    }

    public void buildWorld(){
        ArrayList<Map<String, Object>> itemData = tools.readJson("item.json");
        ArrayList<Map<String, Object>> locationData = tools.readJson("location.json");
        ArrayList<Map<String, Object>> characterData = tools.readJson("character.json");

        for (Map<String, Object> entry : itemData){
            // this is how we map the k/v for character quote
            Map<String, String> dialogue = (Map<String, String>) entry.get("quote");
            Items item = new Items(entry.get("name").toString(), dialogue, entry.get("description").toString(), Integer.parseInt(entry.get("value").toString()),
                    Integer.parseInt(entry.get("cost").toString()), Integer.parseInt(entry.get("strength").toString()), entry.get("type").toString(),
                    entry.get("img").toString(), Integer.parseInt(entry.get("xaxis").toString()), Integer.parseInt(entry.get("yaxis").toString()),
                    Integer.parseInt(entry.get("width").toString()), Integer.parseInt(entry.get("height").toString()), entry.get("keyReq").toString(), entry.get("keyError").toString());
            items.add(item);
        }
        for (Map<String, Object> entry : characterData){
            // this is how we map the k/v for character quote
            Map<String, String> dialogue = (Map<String, String>) entry.get("quote");
            Characters character = new Characters(entry.get("name").toString(), dialogue,(List<String>) entry.get("items"),
                    Integer.parseInt(entry.get("hp").toString()), (boolean) entry.get("isFriendly"), Integer.parseInt(entry.get("dp").toString()),
                    (List<String>) entry.get("questReq"), (List<String>) entry.get("reward"), entry.get("img").toString(), Integer.parseInt(entry.get("xaxis").toString()),
                    Integer.parseInt(entry.get("yaxis").toString()), Integer.parseInt(entry.get("width").toString()), Integer.parseInt(entry.get("height").toString()), entry.get("type").toString());
            characters.add(character);
        }
        for (Map<String, Object> entry : locationData){
            Map<String, String> directions = (Map<String, String>) entry.get("directions");
            Locations location = new Locations(entry.get("name").toString(), entry.get("description").toString(), (List<String>) entry.get("NPC"), (List<String>) entry.get("items"),
                    directions, entry.get("img").toString(), entry.get("island").toString(), entry.get("grid").toString());
            locations.add(location);
        }

    }

    public ArrayList<Locations> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Locations> locations) {
        this.locations = locations;
    }

    public ArrayList<com.company.models.Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<com.company.models.Items> items) {
        items = items;
    }

    public ArrayList<Characters> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Characters> characters) {
        this.characters = characters;
    }
}
