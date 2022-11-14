package com.company.models;

import java.util.*;

import com.apps.util.Console;
import com.company.client.GameMain;

import javax.swing.*;

public class Player {
    private String name = "wilson";
    private int hp = 10;
    private int dp = 1;
    // make inventory/location items use list of item
    private List<String> inventory = new ArrayList<>();
    private ArrayList<String> locationItems;
    // make locationNPCs use list of characters
    private ArrayList<String> locationNPC;
    private JsonTools tools = new JsonTools();
    private String currentRoom = "Beach Shack";
    private Map<String, String> directions;
    private boolean playGame;
    private GameMain gm;
    private String equipedItem;
    private int gold = 50;

    private ArrayList<Map<String, Object>> locationData = tools.readJson("location.json");
    private ArrayList<Map<String, Object>> characterData = tools.readJson("character.json");
    //read these 'ArrayList<Map<String, Object>>' into classes ^
    private FileGetter file = new FileGetter();

    public Player(GameMain gm) {
        this.gm = gm;
    }


    public void grabItem(String item) { //grab needs overhauled this is a lot of hard coding
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        if (locationStuff.getItems().contains(item)) {
            if (itemInstance.getKeyReq().equals("none")) {
                getInventory().add(item);
                gm.getUi().getInventory().addElement(item);
                locationStuff.getItems().remove(item);
                gm.getUi().deleteObject(item);
                gm.getUi().getMessageText().setText("you picked up " + item);
            } else if (inventory.contains(itemInstance.getKeyReq())) {
                getInventory().add(item);
                gm.getUi().getInventory().addElement(item);
                locationStuff.getItems().remove(item);
                gm.getUi().deleteObject(item);
                gm.getUi().getMessageText().setText("you picked up " + item);
                if (inventory.contains("Parrot")){
                    gm.getMusic().playMusic("music/polly.wav");
                }
            } else {
                gm.getUi().getMessageText().setText(itemInstance.getKeyError());
            }
        }
    }

    public void sail(String island){

        gm.getShop().shiftEconomy();

        switch (island){
            case "Mango Island":
                gm.getSc().boatScreenPicker("Mango Docks");
                break;
            case "Monkey Island":
                gm.getSc().boatScreenPicker("Monkey Docks");
                break;
            case "Skull Island":
                gm.getSc().boatScreenPicker("Skull Docks");
                break;
            default:
                break;


        }

    }

    // EquipItem
    public void equipItem(String item) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        locationStuff.getItems().add(item);
        try {
            gm.getUi().getInventory().addElement(equipedItem);
        } catch (Exception ignored) {
        }
        gm.getUi().getInventory().removeElement(item);
        gm.getPlayer().setEquipedItem(item);
        gm.getUi().getMessageText().setText("You are equipped with " + item + "!");
    }

    public void useItem(String item) { //replace hard coding
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        if (itemInstance.getType().equals("food")){
            if (locationStuff.getItems().contains(item)) {
                setHp(getHp() + itemInstance.getValue());
                locationStuff.getItems().remove(item);
                gm.getUi().deleteObject(item);
                gm.getUi().getMessageText().setText("You ate " + item + " and replenished your health by " + itemInstance.getValue());
            }
            else if (inventory.contains(item)){
                setHp(getHp() + itemInstance.getValue());
                inventory.remove(item);
                gm.getUi().getInventory().removeElement(item);
                gm.getUi().getMessageText().setText("You ate " + item + " and replenished your health by " + itemInstance.getValue());
            }
        } else{
            gm.getUi().getMessageText().setText("That is not something you want to eat");
        }

    }

    public void dropItem(String item) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        //Items itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        locationStuff.getItems().add(item);
        //gm.getUi().deleteObject(item); remake the item?
        gm.getUi().getInventory().removeElement(item);
        gm.getUi().getMessageText().setText("You dropped " + item + "!");
        gm.getUi().addObject(item);
    }

    public void talk(String name) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Character NPCInstance = gm.getGame().getCharacters().stream().filter(npc -> npc.getName().equals(name)).findFirst().orElse(null);
        if (locationStuff.getNPC().contains(name)) {
            try {
                gm.getMusic().playMusic(NPCInstance.getQuote().get("sfx"));
            } catch (Exception ignored) {
            }
            switch (NPCInstance.getType()) {
                case "quest":
                    handleQuest(name);
                    break;
                case "enemy":
                    gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("initial"));
                    attack(name);
                    break;
                default:
                    gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("initial"));
                    break;
            }
        }
    }

    public void look(String item) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        if (locationStuff.getItems().contains(item)) {
            gm.getUi().getMessageText().setText("You look at " + item + ", " + itemInstance.getDescription());
        }
    }

    public int luck(){
        return (int)(Math.random() + 1) * 4;
    }

    public void attack(String name) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Character NPCInstance = gm.getGame().getCharacters().stream().filter(npc -> npc.getName().equals(name)).findFirst().orElse(null);
        Item eqpItem = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(gm.getPlayer().getEquipedItem())).findFirst().orElse(null);
        if (locationStuff.getNPC().contains(name)) {
            boolean isFighting = true;
            String[] options = {"fight", "run"};
            int goldAquired;
            int playerDamageModifier = 0;
            try {
                if (!gm.getPlayer().getEquipedItem().isEmpty()){
                    playerDamageModifier += eqpItem.getStrength();
                }
            } catch (Exception ignore) {
            }
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/fight.png")));
            int playerDamage = gm.getPlayer().dp + playerDamageModifier;
            int damage = NPCInstance.getDp();
            while (isFighting) {
                Double chance = Math.random();
                goldAquired = (NPCInstance.getDp()) * luck();
                String selected = (String) JOptionPane.showInputDialog(null, "What do you want to do?", "You decided to fight", JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
                switch (selected) {
                    case "fight":
                        gm.getUi().getMessageText().setText(NPCInstance.getName() + "'s current hp is : " + NPCInstance.getHp());
                        gm.getUi().getMessageText().append("You are attacking: " + NPCInstance.getName());
                        if (NPCInstance.getHp() > 0) {

                            if (chance > 0.2){
                                NPCInstance.setHp(NPCInstance.getHp() - playerDamage);
                                gm.getPlayer().setHp(gm.getPlayer().getHp() - damage);
                                gm.getUi().getMessageText().setText("You dealt: " + playerDamage + "\n");
                                gm.getUi().getMessageText().append(NPCInstance.getName() + " dealt " + damage + "\n");
                                gm.getUi().getMessageText().append(NPCInstance.getName() + "'s hp after attack is : " + NPCInstance.getHp());

                            }
                            if(chance < 0.2){
                                System.out.println(luck());
                                int luckDamage = ((int)(Math.random() + 1) * 4) + playerDamage;
                                NPCInstance.setHp(NPCInstance.getHp() - luckDamage);
                                gm.getPlayer().setHp(gm.getPlayer().getHp() - (damage + ((int)(Math.random() + 1) * 4)));
                                gm.getUi().getMessageText().setText("You crit and dealt: " + luckDamage + "\n");
                                gm.getUi().getMessageText().append(NPCInstance.getName() + "'s hp after attack is : " + NPCInstance.getHp());
                            }
                        }
                        if (gm.getPlayer().getHp() <= 0) {
                            gm.getUi().getMessageText().setText("Game over...");
                            gm.getUi().gameStateWindow("img/gameover.png", "img/end.png", "What do you want to do?", "You got wasted!");
                            isFighting = false;
                            break;
                        }
                        if (NPCInstance.getHp() <= 0 && !NPCInstance.getItems().isEmpty()) {
                            gm.getUi().getMessageText().setText("Wasted " + NPCInstance.getName() + "!");
                            gm.getUi().deleteObject(NPCInstance.getName());

                            for (String item : NPCInstance.getItems()) {
                                locationStuff.getItems().add(item);
                                gm.getUi().addObject(item);
                            }
                            gm.getUi().getMessageText().setText(NPCInstance.getName() + " dropped " + NPCInstance.getItems() + "!\n");
                            gm.getUi().getMessageText().append("Gold dropped: " + goldAquired + "\n");
                            setGold(goldAquired + gold);
                            isFighting = false;
                            break;
                        }
                        if (NPCInstance.getHp() <= 0) {
                            gm.getUi().getMessageText().append("Gold dropped: " + goldAquired + "\n");
                            gm.getUi().getMessageText().setText("Wasted " + NPCInstance.getName() + "!");
                            gm.getUi().deleteObject(NPCInstance.getName());
                            setGold(goldAquired + gold);
                            isFighting = false;
                            break;
                        }
                        break;
                    case "run":
                        System.out.println("running");
                        gm.getUi().getMessageText().setText("You ran away from the fight with " + NPCInstance.getName() + "!");
                        isFighting = false;
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

    // dialogue to accept quest from NPC
    private void handleQuest(String name) {
        Character NPCInstance = gm.getGame().getCharacters().stream().filter(npc -> npc.getName().equals(name)).findFirst().orElse(null);
        if (NPCInstance.getItems().isEmpty()) {  //if quest npc has no items do normal talk
            gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("initial"));
        } else {
            if (NPCInstance.getQuestReq().isEmpty()) {  //if NPC has items w/ no requirement just give items
                for (String item :
                        NPCInstance.getReward()) {
                    gm.getUi().getInventory().addElement(item);
                    inventory.add(item);
                    NPCInstance.getItems().remove(item);
                    System.out.println("received item");
                }
                gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("reward"));
            } else {  //else figure out if the player has the required items
                int i = 0;
                for (String questReq : NPCInstance.getQuestReq()
                ) {
                    if (inventory.contains(questReq)) {
                        i = i + 1;
                    }
                }
                if (NPCInstance.getQuestReq().size() == i) {  //if player has all items required

                    for (String questReq : NPCInstance.getQuestReq()) {//for each required item remove them
                        inventory.remove(questReq);
                        gm.getUi().getInventory().removeElement(questReq);
                    }
                    for (String item :  //for each reward give player an item and delete the item from inventory
                            NPCInstance.getReward()) {
                        gm.getUi().getInventory().addElement(item);
                        inventory.add(item);
                        NPCInstance.getItems().remove(item);
                        System.out.println("received quest reward item");
                    }
                    gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("reward"));
                    System.out.println("npc items: " + NPCInstance.getItems());

                } else {  //if player doesnt haver required quest items do this
                    //quest text
                    //gives item that helps quest...
                    gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("quest"));
                    try {
                        gm.getUi().getInventory().addElement(NPCInstance.getQuote().get("gives"));
                        inventory.add(NPCInstance.getQuote().get("gives"));
                        System.out.println("received item");
                    } catch (Exception ignored) {

                    }

                }
            }
        }
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

    public String getEquipedItem() {
        return equipedItem;
    }

    public void setEquipedItem(String equipedItem) {
        this.equipedItem = equipedItem;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
