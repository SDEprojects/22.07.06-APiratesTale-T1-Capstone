package com.company.models;

import java.util.*;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.company.client.GameMain;
import com.company.view.Home;

public class Player {
    public String name = "wilson";
    public int hp = 10;
    public int dp = 5;
    // make inventory/location items use list of item
    public List<String> inventory = new ArrayList<>();
    private ArrayList<String> locationItems;
    // make locationNPCs use list of characters
    private ArrayList<String> locationNPC;
    private JsonTools tools = new JsonTools();
    private String currentRoom = "Beach Shack";
    private Map<String, String> directions;
    private boolean playGame;
    GameMain gm;

    Prompter prompter = new Prompter(new Scanner(System.in));
    ArrayList<Map<String, Object>> locationData = tools.readJson("location.json");
    ArrayList<Map<String, Object>> characterData = tools.readJson("character.json");
    //read these 'ArrayList<Map<String, Object>>' into classes ^
    private FileGetter file = new FileGetter();

    public Player(GameMain gm) {
        this.gm = gm;
    }

    // create new player
//    public void newPlayer() {
//        //refactor to UI
//        System.out.println("\nNew Game Created");
//        setPlayerName(prompter.prompt("\nAhoy, What is your name adventurer? "));
//        System.out.println();
//    }

    // status menu bar
    public void status() {
        //status will be the main ui loader for each screen
        //change equipped inventory to not be hard coded
        //example dp * swordStrength
        if (inventory.contains("sword")) {
            setDp(5);
        }
        // if equipped item:
        // replace with setDp(getDp*itemStat)
        for (Map<String, Object> entry : locationData) {
            if (entry.get("name").equals(currentRoom)) {
                directions = (Map<String, String>) entry.get("directions");
                locationItems = (ArrayList<String>) entry.get("items");
                locationNPC = (ArrayList<String>) entry.get("NPC");

                System.out.printf("Location: %s \n", entry.get("name"));
//                Console.pause(1000);
                System.out.printf("\nDescription: %s ", entry.get("description"));
//                Console.pause(1000);
                System.out.println("\nDirections: ");
                directions.forEach((k, v) -> {
                    if (v.length() > 0) {
                        System.out.printf("%s: %s\n", k, v);
                    }
                });
                System.out.println();
//                Console.pause(1000);
                if (!locationNPC.isEmpty()) {
                    System.out.printf("\nCharacters present: \n");
                    locationNPC.forEach(e -> System.out.println(e));
                    System.out.println("\n");
                }
//                Console.pause(1000);
                if (!locationItems.isEmpty()) {
                    System.out.printf("Items you see: \n");
                    locationItems.forEach(e -> System.out.println(e));
                    System.out.println("\n");
                }
//                Console.pause(1000);
                System.out.printf("HP: %s     Damage Points: %s      Inventory: %s ", hp, dp, inventory);
            }

        }
    }

    public void grabItem(String item) { //grab needs overhauled this is a lot of hard coding
        Locations locationStuff = gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Items itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        if (locationStuff.getItems().contains(item)){
            if (itemInstance.getKeyReq().equals("none")) {
                getInventory().add(item);
                locationStuff.getItems().remove(item);
                gm.getUi().deleteObject(item);
                gm.getUi().messageText.setText("you picked up "+ item);
            } else if (inventory.contains(itemInstance.getKeyReq())) {
                getInventory().add(item);
                locationStuff.getItems().remove(item);
                gm.getUi().deleteObject(item);
                gm.getUi().messageText.setText("you picked up "+ item);
            } else {
                gm.getUi().messageText.setText(itemInstance.getKeyError());
            }
        }

    }

//        if (!item.equals("parrot") && !item.equals("treasure chest") && locationItems.contains(item)) {
//            //remove from the location
//            locationItems.remove(item);
//            //add to inventory
//            inventory.add(item);
//            this.locationItems = locationItems;
//
//        }
//        if (inventory.contains("cracker") && locationItems.contains("parrot") && item.equals("parrot")) {
//            inventory.remove("cracker");
//            inventory.add(item);
//            locationItems.remove(item);
//            System.out.println("You were able to grab the parrot by feeding it a cracker.");
//        } else if (!inventory.contains("cracker") && locationItems.contains("parrot") && item.equals("parrot")) {
//            System.out.println("You were not able to grab the Parrot.\n");
//        } else if (inventory.contains("treasure key") && locationItems.contains("treasure chest") && item.equals(
//                "treasure chest")) {
//            inventory.remove("treasure key");
//            inventory.add(item);
//            locationItems.remove(item);
//            winGame();
//        } else if (!inventory.contains("treasure key") && locationItems.contains("treasure chest") && item.equals("treasure chest")) {
//            System.out.println("You were not able to grab the Treasure Chest.\n");
//        }

    public void useItem(String item) { //replace hard coding
        Locations locationStuff = gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Items itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        if (locationStuff.getItems().contains(item)) {
            setHp(getHp() +  itemInstance.getValue());
            locationStuff.getItems().remove(item);
            gm.getUi().deleteObject(item);
            gm.getUi().messageText.setText("You ate " + item + " and replenished your health by " + itemInstance.getValue());
        }
    }


//
//        String file = "item.json";
//        ArrayList<Map<String, Object>> itemData = tools.readJson(file);
//        if (locationItems.contains(item) || ) {
//            for (Map<String, Object> entry : itemData) {
//                if (inventory.contains(item) && entry.get("name").toString().toLowerCase().equals(item)) {
//                    System.out.println(entry.get("description") + "\n");
//                    if (item.equals("mango")) {
//                        hp += 5;
//                        inventory.remove(item);
//                    } else if (item.equals("banana")) {
//                        hp += 10;
//                        inventory.remove(item);
//                    } else if (item.equals("sword")) {
//                        System.out.println("In order to wield the sword, please enter 'ATTACK' [name]");
//                    } else {
//                        System.out.println("You can't use that item in this manner. Don't be a fool.");
//                    }
//                }
//            }
//        } else {
//            System.out.println("You can not use this item");
//        }
//    }

    public void dropItem(String item) {
        if (!item.equals("parrot") && inventory.contains(item)) {
            //add to location
            locationItems.add(item);
            //remove from inventory
            inventory.remove(item);
        } else if (item.equals("parrot") && inventory.contains(item)) {
            System.out.println("I should return the parrot to the pirate captain!");
        }
    }


    public void talk(String name) {
        Locations locationStuff = gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Characters NPCInstance = gm.getGame().getCharacters().stream().filter(npc -> npc.getName().equals(name)).findFirst().orElse(null);
        if (locationStuff.getNPC().contains(name)) {
            switch (NPCInstance.getType()) {
                case "quest":
                    //need to replace with a quest method and button response
                    gm.getUi().messageText.setText(NPCInstance.getName()+": " + NPCInstance.getQuote().get("quest"));
                    break;
                case "enemy":
                    gm.getUi().messageText.setText(NPCInstance.getName()+": " + NPCInstance.getQuote().get("initial"));
                    attack(name);
                    break;
                default:
                    gm.getUi().messageText.setText(NPCInstance.getName()+": " + NPCInstance.getQuote().get("initial"));
                    break;
            }
        }
    }
//        if (locationNPC.contains(name)) {
//            for (Map<String, Object> entry : characterData) {
//                // refactor to isFriendly
//                if (entry.get("name").equals(name) && !entry.get("name").equals("skeleton beast") && !entry.get("name").equals("skull king") && !entry.get("name").equals("skeleton soldier") && !entry.get("name").equals("skeleton captain")) {
//                    while (true) {
//                        System.out.println("Speaking to: " + entry.get("name"));
//                        Map<String, String> dialogue = (Map<String, String>) entry.get("quote");
//                        System.out.println(dialogue.get("initial"));
//                        if (dialogue.containsKey("quest")) {
//                            handleQuest(entry, dialogue);
//                            break;
//                        } else if (entry.containsKey("items")) {
//                            ArrayList<String> itemsArray = (ArrayList<String>) entry.get("items");
//                            for (String item : itemsArray) {
//                                inventory.add(item);
//                                System.out.println(item + " was added to inventory.\n");
//                            }
//                            entry.remove("items");
//                        }
//                        break;
//                    }
//                }
//
//            }
//        } else {
//            System.out.println("Invalid Name");
//        }
//    }

    public void go(String directionInput) throws NullPointerException {
        ArrayList<String> bossKeys = new ArrayList<String>(Arrays.asList("left boss key", "right boss key"));
        // refactor to "doors method"
        if (directions.containsKey(directionInput)) {
            String location = directions.get(directionInput);
            if (!location.equals("Boat") && !location.equals("Treasure Room")) {
                currentRoom = location;
            } else if (inventory.contains("Boat Pass") && location.equals("Boat")) {
                currentRoom = location;
            } else if (!inventory.contains("Boat Pass") && location.equals("Boat")) {
                System.out.println("Get a Boat Pass from a Pirate Captain\n");
//            } else if (inventory.containsAll(bossKeys) && location.equals("Skull King Throne Room")) {
//                currentRoom = location;
//            } else if (!inventory.containsAll(bossKeys) && location.equals("Skull King Throne Room")) {
//                System.out.println("Find the boss door keys, the local enemies may be carrying them.");
            } else if (inventory.contains("treasure room key") && location.equals("Treasure Room")) {
                currentRoom = location;
            } else if (!inventory.contains("treasure room key") && location.equals("Treasure Room")) {
                System.out.println("You must defeat the Skull King to get the key.");
            }
        } else {
            System.out.println("Invalid Direction");
        }
    }

    public void look(String item) {
        Locations locationStuff = gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Items itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        if (locationStuff.getItems().contains(item)) {
            gm.getUi().messageText.setText("You look at "+item+", "+itemInstance.getDescription());
        }
    }
//        String file = "item.json";
//        ArrayList<Map<String, Object>> itemData = tools.readJson(file);
//        if (locationItems.contains(item) || inventory.contains(item)) {
//            for (Map<String, Object> entry : itemData) {
//                if (entry.get("name").toString().toLowerCase().equals(item)) {
//                    System.out.println(entry.get("description") + "\n");
//                }
//            }
//        } else {
//            System.out.println("You cannot look at items that are not in front of you.");
//        }
//    }

    public void attack(String name) {
        Locations locationStuff = gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Characters NPCInstance = gm.getGame().getCharacters().stream().filter(npc -> npc.getName().equals(name)).findFirst().orElse(null);
        if (locationStuff.getNPC().contains(name)) {
            boolean isFighting = true;
            while (isFighting) {
                //added switchcase to make fights feel like an event
                gm.getUi().messageText.setText("fight, run, bag");
                String battleAction = prompter.prompt("fight, run, bag");
                switch (battleAction) {
                    case "fight":
                        System.out.println(NPCInstance.getName() + "'s current hp is : " + NPCInstance.getHp());
                        System.out.println("You are attacking: " + NPCInstance.getName());
                        if (NPCInstance.getHp() > 0) {
                            NPCInstance.setHp(NPCInstance.getHp()- gm.getPlayer().dp);
                            System.out.println(NPCInstance.getName() + "'s hp after attack is : " + NPCInstance.getHp());
                            //second if condition to only hit back if NPC still living
                            if (NPCInstance.getHp() > 0) {
                                int damage = NPCInstance.getDp();
                                gm.getPlayer().setHp(gm.getPlayer().getHp() - damage);
                                gm.getUi().messageText.setText(NPCInstance.getName() + "'s current hp is : " + NPCInstance.getHp()+"\n"+"You are attacking: " + NPCInstance.getName()+"\n"+NPCInstance.getName() + "'s hp after attack is : " + NPCInstance.getHp()+"\n"+NPCInstance.getName() + " was able to attack you back. Your HP is now " + gm.getPlayer().getHp());
                                System.out.println(NPCInstance.getName() + " was able to attack you back. Your HP is now " + gm.getPlayer().getHp());
                                }
                        }
                        if (gm.getPlayer().getHp() <= 0) {
                            //gameOver();
                            System.out.println("Game over...");
                            gm.getUi().messageText.setText("Game over...");
                            isFighting = false;
                            break;
                        }
                        if (NPCInstance.getHp() <= 0 && !NPCInstance.getItems().isEmpty()) {
                            System.out.println("Wasted " + NPCInstance.getName() + "!");
                            gm.getUi().messageText.setText("Wasted " + NPCInstance.getName() + "!");
                            gm.getUi().deleteObject(NPCInstance.getName());
//                            ArrayList<String> itemsArray = (ArrayList<String>) entry.get("items");
//                            locationNPC.remove(name);
//                            for (String item : itemsArray) {
//                                inventory.add(item);
//                                System.out.println(entry.get("name") + "'s " + item + " has been added to your inventory");
                            isFighting = false;
                            break;
                        }
                        if (NPCInstance.getHp() <= 0) {
                            gm.getUi().messageText.setText("Wasted " + NPCInstance.getName() + "!");
                            gm.getUi().deleteObject(NPCInstance.getName());
                            isFighting = false;
                            break;
                        }
                        break;
                    case "run":
                        System.out.println("running");
                        gm.getUi().messageText.setText("You ran away from the fight with " + NPCInstance.getName() + "!");
                        isFighting = false;
                        break;
                    case "bag":
                        System.out.println("get items");
                        //add a way to use during fight
                        gm.getUi().messageText.setText("You check you bag!\n...Found nothing to use");
                        break;
                    default:
                        System.out.println("nothing happened");
                        break;
                }
            }

        } else {
            System.out.println("Invalid Target");
        }
    }


//                        for (Map<String, Object> entry : characterData) {
//                            if (entry.get("name").equals(name)) {
//                                while (true) {
//                                    System.out.println(entry.get("name") + "'s current hp is : " + entry.get("hp"));
//                                    System.out.println("You are attacking: " + entry.get("name"));
//                                    Double points = (Double) entry.get("hp");
//                                    if (points > 0) {
//                                        points -= dp;
//                                        entry.put("hp", points);
//                                        System.out.println(entry.get("name") + "'s hp after attack is : " + points);
//                                        //second if condition to kill npc characters at or under 0 health
//                                        if (points > 0) {
//                                            Double damage = (Double) entry.get("dp");
//                                            hp -= damage;
//                                            System.out.println(entry.get("name") + " was able to attack you back. Your HP is now " + hp);
//                                        }
//                                    }
//
//                                    if (hp <= 0) {
//                                        gameOver();
//                                        isFighting = false;
//
//                                    }
//                                    if (points <= 0 && entry.containsKey("items")) {
//                                        System.out.println(this.name + " has wasted " + entry.get("name") + "!");
//                                        ArrayList<String> itemsArray = (ArrayList<String>) entry.get("items");
//                                        locationNPC.remove(name);
//                                        for (String item : itemsArray) {
//                                            inventory.add(item);
//                                            System.out.println(entry.get("name") + "'s " + item + " has been added to your inventory");
//
//                                        }isFighting = false;
//                                    }
//
//                                    break;
//                                }
//                            }
//                        }
//                        break;
//                    case "run":
//                        System.out.println("running");
//                        isFighting = false;
//                        break;
//                    case "bag":
//                        System.out.println("get items");
//                        break;
//                    default:
//                        System.out.println("nothing happened");
//                }
//            }
//
//        } else {
//            System.out.println("Invalid Target");
//        }

//        if (locationNPC.contains(name)) {
//            boolean isFighting = true;
//            while (isFighting) {
//                //added switchcase to make fights feel like an event
//                String battleAction = prompter.prompt("fight, run, bag");
//                switch (battleAction) {
//                    case "fight":
//                        for (Map<String, Object> entry : characterData) {
//                            if (entry.get("name").equals(name)) {
//                                while (true) {
//                                    System.out.println(entry.get("name") + "'s current hp is : " + entry.get("hp"));
//                                    System.out.println("You are attacking: " + entry.get("name"));
//                                    Double points = (Double) entry.get("hp");
//                                    if (points > 0) {
//                                        points -= dp;
//                                        entry.put("hp", points);
//                                        System.out.println(entry.get("name") + "'s hp after attack is : " + points);
//                                        //second if condition to kill npc characters at or under 0 health
//                                        if (points > 0) {
//                                            Double damage = (Double) entry.get("dp");
//                                            hp -= damage;
//                                            System.out.println(entry.get("name") + " was able to attack you back. Your HP is now " + hp);
//                                        }
//                                    }
//
//                                    if (hp <= 0) {
//                                        gameOver();
//                                        isFighting = false;
//
//                                    }
//                                    if (points <= 0 && entry.containsKey("items")) {
//                                        System.out.println(this.name + " has wasted " + entry.get("name") + "!");
//                                        ArrayList<String> itemsArray = (ArrayList<String>) entry.get("items");
//                                        locationNPC.remove(name);
//                                        for (String item : itemsArray) {
//                                            inventory.add(item);
//                                            System.out.println(entry.get("name") + "'s " + item + " has been added to your inventory");
//
//                                        }isFighting = false;
//                                    }
//
//                                    break;
//                                }
//                            }
//                        }
//                        break;
//                    case "run":
//                        System.out.println("running");
//                        isFighting = false;
//                        break;
//                    case "bag":
//                        System.out.println("get items");
//                        break;
//                    default:
//                        System.out.println("nothing happened");
//                }
//            }
//
//        } else {
//            System.out.println("Invalid Target");
//        }
        //
//    }

    // dialogue to accept quest from NPC
    private void handleQuest(Map<String, Object> entry, Map<String, String> dialogue) {

        List<String> req = (List<String>) entry.get("questReq");
        if (inventory.containsAll(req)) {
            System.out.println(dialogue.get("reward"));
            if (entry.containsKey("reward")) {
                ArrayList<String> rewardsArray = (ArrayList<String>) entry.get("reward");
                for (String reward : rewardsArray) {
                    inventory.add(reward);
                    System.out.println(reward + " was added to inventory.\n");
                }
                entry.remove("reward");
                inventory.removeAll(req);
            }
        } else {
            System.out.println(dialogue.get("quest"));
            if (dialogue.containsKey("yes")) {
                String userInput = prompter.prompt("");
                if (userInput.equals("yes")) {
                    System.out.println(dialogue.get("yes"));
                    if (entry.containsKey("items")) {
                        ArrayList<String> itemsArray = (ArrayList<String>) entry.get("items");
                        for (String item : itemsArray) {
                            inventory.add(item);
                            System.out.println(item + " was added to inventory.\n");
                        }
                        entry.remove("items");
                    }
                }
                if (userInput.equals("no")) {
                    System.out.println(dialogue.get("no"));
                }
            }
        }
    }

    public void gameOver() {
        Console.pause(2000);
        System.out.println("\nYou've been wasted. The game is over.");
        Console.pause(2000);
        Console.clear();
        Home newGame = new Home();
        newGame.buildHome();
    }

    public void winGame() {
        System.out.println("Congratulations, " + getPlayerName() + ", you have plundered the long-lost treasure of" +
                "Skull Island. You are a true pirate!");
        Console.pause(2000);
        Console.clear();
        Home newGame = new Home();
        newGame.buildHome();
    }

    private void setPlayerName(String name) {
        this.name = name;
    }

    public String getPlayerName() {
        return name;
    }


    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public boolean isPlayGame() {
        return playGame;
    }

    public void setPlayGame(boolean playGame) {
        this.playGame = playGame;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }
}
