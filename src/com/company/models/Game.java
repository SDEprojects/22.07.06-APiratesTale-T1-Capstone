package com.company.models;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.company.client.GameMain;
import com.company.view.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
    Prompter prompter = new Prompter(new Scanner(System.in));
    //private final Player player;
    ArrayList<Locations> locations = new ArrayList<>();
    ArrayList<Items> items = new ArrayList<>();
    ArrayList<Characters> characters = new ArrayList<>();
//    private Music musicObject = new Music();
    JsonTools tools = new JsonTools();
    GameMain gm;

    public Game(GameMain gm) {
        this.gm = gm;
        //this.player = player;
    }

    //testing this
//    public static void main(String[] args) {
//        //Player player = new Player();
//        Game game = new Game();
//        game.buildWorld();
//        for (Locations location: game.locations
//             ) {
//            System.out.println(location.getName()+" "+location.getNPC() +" "+ location.getItems());
//        }
//
//    }

    public void buildWorld(){
        ArrayList<Map<String, Object>> itemData = tools.readJson("item.json");
        ArrayList<Map<String, Object>> locationData = tools.readJson("location.json");
        ArrayList<Map<String, Object>> characterData = tools.readJson("character.json");

        for (Map<String, Object> entry : itemData){
            Items item = new Items(entry.get("name").toString(), entry.get("description").toString(), Integer.parseInt(entry.get("value").toString()),
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

    public void playGame() {
//        System.out.printf("\nWelcome to Mango Island, %s.", player.name);
//        System.out.println();
//        Console.pause(2000);
//        System.out.println("You awaken on the beach in your modest shack on Mango Island after a long nap.\nYou look out the window and notice a sad traveler approaching you. You step outside to greet him.");
//        System.out.println();
//
//        Console.pause(2000);
//        //get rid of with ui implementation
//        System.out.println("You can use the following commands to play the game: ");
//
//
//        System.out.println("TIP: Enter TALK [name] to speak to others.\n");
 //       actions();
//        System.out.println();
    }

    // menu actions for player
//    public void actions(){
//
//        while (true) {
////            Console.pause(1000);
//
//            player.status();
//            //replace with UI
//            String userInput = prompter.prompt("\nCMD:  GO [direction] |  TALK [name]  |  GRAB [item]  |   DROP [item]" +
//                    "    |     LOOK [item]" +
//
//                    "  |  USE [item]    |   ATTACK [name]   |   MUSIC   |    QUIT \n" +
//                    "------------------------------------------------------------------------------------------------" +
//                    "---------------------------------------------------------\nYOUR MOVE: ").toLowerCase();
////            Console.pause(500);
//            Console.clear();
//
//            //rebuild to switch statement and use event handler
//            String[] inputSplit = userInput.trim().toLowerCase().split(" ");
//            if(inputSplit[0].equals("look")) {
//                if (inputSplit.length == 2) {
//                    player.look(inputSplit[1]);
//                }
//                else if (inputSplit.length == 3) {
//                    String look = inputSplit[1] + " " + inputSplit[2];
//                    player.look(look);
//                }
//                else {
//                    System.out.println("Invalid action");
//                }
//
//            }
//            else if (inputSplit[0].equals("go")) {
//                if (inputSplit.length == 2) {
//                    player.go(inputSplit[1]);
//                }
//                else if (inputSplit.length == 3) {
//                    String island = inputSplit[1] + " " + inputSplit[2];
//                    player.go(island);
//                }
//                else {
//                    System.out.println("Invalid direction");
//                }
//            }
//            else if (inputSplit[0].equals("talk")) {
//                if (inputSplit.length == 2) {
//                    player.talk(inputSplit[1]);
//                }
//                else if (inputSplit.length == 3) {
//                    String npc = inputSplit[1] + " " + inputSplit[2];
//                    player.talk(npc);
//                }
//                else {
//                    System.out.println("Invalid name");
//                }
//            }
//            else if (inputSplit[0].equals("grab")){
//                if (inputSplit.length == 2) {
//                    player.grabItem(inputSplit[1]);
//                }
//                else if (inputSplit.length == 3) {
//                    String item = inputSplit[1] + " " + inputSplit[2];
//                    player.grabItem(item);
//                }
//                else {
//                    System.out.println("Invalid item");
//                }
//            }
//
//            else if(inputSplit[0].equals("use")){
//                if (inputSplit.length == 2) {
//                    player.useItem(inputSplit[1]);
//                }
//                else if (inputSplit.length == 3) {
//                    String use = inputSplit[1] + " " + inputSplit[2];
//                    player.useItem(use);
//                }
//                else {
//                    System.out.println("Invalid use");
//                }
//
//            }
//            else if (inputSplit[0].equals("drop")){
//                if (inputSplit.length == 2) {
//                    player.dropItem(inputSplit[1]);
//                }
//                else if (inputSplit.length == 3) {
//                    String item = inputSplit[1] + " " + inputSplit[2];
//                    player.dropItem(item);
//                }
//                else {
//                    System.out.println("Invalid item");
//                }
//            }
//
//            else if(inputSplit[0].equals("attack")){
//                if (inputSplit.length == 2) {
//                    player.attack(inputSplit[1]);
//                }
//                else if (inputSplit.length == 3) {
//                    String character = inputSplit[1] + " " + inputSplit[2];
//                    player.attack(character);
//                }
//            }
//            else if(inputSplit[0].equals("music")){
//                String musicInput = prompter.prompt("[PLAY] or [STOP] Music: ").toLowerCase();
//                if (musicInput.equals("play")){
//                    musicObject.playMusic("music.wav");
//                }
//                else if (musicInput.equals("stop")){
//                    musicObject.stopMusic("music.wav");
//                }
//                else {
//                    System.out.println("Invalid input");
//                }
//            }
//            else if(inputSplit[0].equals("quit")) {
//                Home newHome = new Home();
//                newHome.buildHome();
//                break;
//            }
//            else {
//                System.out.println("Invalid Command Entered");
//            }
//        }
//    }
//
//    public Player getPlayer() {
//        return player;
//    }

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
//    public void loadGame() {
//    }
//
//    public void saveGame() {
//    }
}
