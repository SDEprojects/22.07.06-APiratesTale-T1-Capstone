package com.company.models;

import com.company.client.GameMain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Character> characters = new ArrayList<>();
    private JsonTools tools = new JsonTools();
    private GameMain gm;

    public Game(GameMain gm) {
        this.gm = gm;
    }

    //build worlds takes the json and makes an instance of each entry into corresponding class...then adds to related list
    public void buildWorld(){
        ArrayList<Map<String, Object>> itemData = tools.readJson("item.json");
        ArrayList<Map<String, Object>> locationData = tools.readJson("location.json");
        ArrayList<Map<String, Object>> characterData = tools.readJson("character.json");

        for (Map<String, Object> entry : itemData){
            // this is how we map the k/v for character quote
            Map<String, String> dialogue = (Map<String, String>) entry.get("quote");
            Item item = new Item(entry.get("name").toString(), dialogue, entry.get("description").toString(), Integer.parseInt(entry.get("value").toString()),
                    Integer.parseInt(entry.get("cost").toString()), Integer.parseInt(entry.get("strength").toString()), entry.get("type").toString(),
                    entry.get("img").toString(), Integer.parseInt(entry.get("xaxis").toString()), Integer.parseInt(entry.get("yaxis").toString()),
                    Integer.parseInt(entry.get("width").toString()), Integer.parseInt(entry.get("height").toString()), entry.get("keyReq").toString(), entry.get("keyError").toString());
            items.add(item);
        }
        for (Map<String, Object> entry : characterData){
            // this is how we map the k/v for character quote
            Map<String, String> dialogue = (Map<String, String>) entry.get("quote");
            Character character = new Character(entry.get("name").toString(), dialogue,(List<String>) entry.get("items"),
                    Integer.parseInt(entry.get("hp").toString()), (boolean) entry.get("isFriendly"), Integer.parseInt(entry.get("dp").toString()),
                    (List<String>) entry.get("questReq"), (List<String>) entry.get("reward"), entry.get("img").toString(), Integer.parseInt(entry.get("xaxis").toString()),
                    Integer.parseInt(entry.get("yaxis").toString()), Integer.parseInt(entry.get("width").toString()), Integer.parseInt(entry.get("height").toString()), entry.get("type").toString());
            characters.add(character);
        }
        for (Map<String, Object> entry : locationData){
            Map<String, String> directions = (Map<String, String>) entry.get("directions");
            Location location = new Location(entry.get("name").toString(), entry.get("description").toString(), (List<String>) entry.get("NPC"), (List<String>) entry.get("items"),
                    directions, entry.get("img").toString(), entry.get("island").toString(), entry.get("grid").toString());
            locations.add(location);
        }

    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        items = items;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
}
