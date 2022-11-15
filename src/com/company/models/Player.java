package com.company.models;

import java.util.*;

import com.apps.util.Console;
import com.company.client.GameMain;

import javax.swing.*;

public class Player {
    private String name = "wilson";
    private int hp = 20;
    private int dp = 2;
    private List<String> inventory = new ArrayList<>();
    private ArrayList<String> locationItems;
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
    private FileGetter file = new FileGetter();

    public Player(GameMain gm) {
        this.gm = gm;
    }


    public void grabItem(String item) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        //grab checks inventory for the item
        if (locationStuff.getItems().contains(item)) {
            //checks for a key/lock on inventory (example: parrot)
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
                //if you dont have the requirement to pick up it will tell you what you need
                gm.getUi().getMessageText().setText(itemInstance.getKeyError());
            }
        }
    }

    public void sail(String island){
        //sail shifts economy simulating ships coming and going to port and trading supplies
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

    //cheat code activates
    public void extra(String top){
        if (top.equals("top")){
            setHp(100);
            setGold(1000);
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

    public void useItem(String item) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
        //use became eat... if it isn't food don't eat it
        if (itemInstance.getType().equals("food")){
            if (locationStuff.getItems().contains(item)) {
                setHp(getHp() + itemInstance.getValue());
                locationStuff.getItems().remove(item);
                gm.getUi().deleteObject(item);
                inventory.remove(item);
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
        locationStuff.getItems().add(item);
        gm.getUi().getInventory().removeElement(item);
        getInventory().remove(item);
        gm.getUi().getMessageText().setText("You dropped " + item + "!");
        gm.getUi().addObject(item);
    }

    public void talk(String name) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Character NPCInstance = gm.getGame().getCharacters().stream().filter(npc -> npc.getName().equals(name)).findFirst().orElse(null);
        if (locationStuff.getNPC().contains(name)) {
            //when talkign first try to play sfx
            try {
                gm.getMusic().playMusic(NPCInstance.getQuote().get("sfx"));
            } catch (Exception ignored) {
            }
            //switch on different character types...
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
        //you look at something, it sends description to message text on ui
        if (locationStuff.getItems().contains(item)) {
            gm.getUi().getMessageText().setText("You look at " + item + ", " + itemInstance.getDescription());
        }
    }

    //luck is used in combat
    public int luck(){
        return (int)(Math.random() + 1) * 4;
    }

    public void attack(String name) {
        Location locationStuff = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(currentRoom)).findFirst().orElse(null);
        Character NPCInstance = gm.getGame().getCharacters().stream().filter(npc -> npc.getName().equals(name)).findFirst().orElse(null);
        Item eqpItem = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(gm.getPlayer().getEquipedItem())).findFirst().orElse(null);
        //if the NPC is in the area fight with them
        if (locationStuff.getNPC().contains(name)) {
            boolean isFighting = true;
            String[] options = {"fight", "run"};
            int goldAquired;
            int playerDamageModifier = 0;
            //if player is equipped modify stats
            try {
                if (!gm.getPlayer().getEquipedItem().isEmpty()){
                    playerDamageModifier += eqpItem.getStrength();
                }
            } catch (Exception ignore) {
            }
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/fight.png")));
            int playerDamage = gm.getPlayer().dp + playerDamageModifier;
            int damage = NPCInstance.getDp();
            //if player is enemy get the enemies quote
            if (NPCInstance.getType().equals("enemy")){
                gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("initial"));
            }
            while (isFighting) {
                Double chance = Math.random();
                goldAquired = (NPCInstance.getDp()) * luck();
                String selected = (String) JOptionPane.showInputDialog(null, "What do you want to do?", "You decided to fight", JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
                if (selected == null || selected.isEmpty()){
                    break;
                }
                switch (selected) {
                    case "fight":
                        gm.getUi().getMessageText().setText(NPCInstance.getName() + "'s current hp is : " + NPCInstance.getHp());
                        gm.getUi().getMessageText().append("You are attacking: " + NPCInstance.getName());
                        if (NPCInstance.getHp() > 0) {
                            //player gives first attack will hit normal or a chance to hit critical
                            if (chance > 0.2){
                                NPCInstance.setHp(NPCInstance.getHp() - playerDamage);
                                gm.getUi().getMessageText().setText("You dealt: " + playerDamage + "\n");
                                gm.getUi().getMessageText().append(NPCInstance.getName() + "'s hp after attack is : " + NPCInstance.getHp()+"\n");
                            }
                            else {
                                int luckDamage = ((int)(Math.random() + 1) * 4) + playerDamage;
                                NPCInstance.setHp(NPCInstance.getHp() - luckDamage);
                                gm.getUi().getMessageText().setText("You dealt critical damage: " + luckDamage + "\n");
                                gm.getUi().getMessageText().append(NPCInstance.getName() + "'s hp after attack is : " + NPCInstance.getHp()+"\n");
                            }
                            //new if starts NPC combat... if they are alive they can hit normal or have a chance to critically wound you
                            if (NPCInstance.getHp() > 0){
                                Double npcChance = Math.random();
                                if (npcChance > 0.2){
                                    gm.getPlayer().setHp(gm.getPlayer().getHp() - damage);
                                    gm.getUi().getMessageText().append(NPCInstance.getName() + " dealt " + damage + "\n");
                                }
                                else {
                                    int npcDamage = ((int)(Math.random() + 1) * 4) + damage;
                                    //or this...
                                    gm.getPlayer().setHp(gm.getPlayer().getHp() - npcDamage);
                                    gm.getUi().getMessageText().append("You have been critically hit: " + npcDamage + "\n");
                                }
                            }
                        }
                        //if you die you lose the game... this is currently the only lose condition in the game
                        if (gm.getPlayer().getHp() <= 0) {
                            gm.getUi().getMessageText().setText("Game over...");
                            gm.getUi().gameStateWindow("img/gameover.png", "img/end.png", "What do you want to do?", "You got wasted!");
                            isFighting = false;
                            break;
                        }
                        //if you beat npc they are 'wasted' and drop items they are carrying plus gold or if no items, they just give gold
                        if (NPCInstance.getHp() <= 0 && !NPCInstance.getItems().isEmpty()) {
                            gm.getUi().getMessageText().append("Wasted " + NPCInstance.getName() + "!" + "\n");
                            gm.getUi().deleteObject(NPCInstance.getName());

                            for (String item : NPCInstance.getItems()) {
                                locationStuff.getItems().add(item);
                                gm.getUi().addObject(item);
                            }
                            gm.getUi().getMessageText().append(NPCInstance.getName() + " dropped " + NPCInstance.getItems() + "!\n");
                            gm.getUi().getMessageText().append("Gold dropped: " + goldAquired + "\n");
                            setGold(goldAquired + gold);
                            isFighting = false;
                            break;
                        }
                        if (NPCInstance.getHp() <= 0) {
                            gm.getUi().getMessageText().append("Wasted " + NPCInstance.getName() + "!" + "\n");
                            gm.getUi().getMessageText().append("Gold dropped: " + goldAquired);
                            gm.getUi().deleteObject(NPCInstance.getName());
                            setGold(goldAquired + gold);
                            isFighting = false;
                            break;
                        }
                        break;
                    case "run":
                        gm.getUi().getMessageText().setText("You ran away from the fight with " + NPCInstance.getName() + "!");
                        isFighting = false;
                        break;
                    default:
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
        //if quest npc has no items do normal talk
        if (NPCInstance.getItems().isEmpty()) {
            gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("initial"));
        } else {
            //if NPC has items w/ no requirement just give items
            if (NPCInstance.getQuestReq().isEmpty()) {
                for (String item :
                        NPCInstance.getReward()) {
                    gm.getUi().getInventory().addElement(item);
                    inventory.add(item);
                    NPCInstance.getItems().remove(item);
                }
                gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("reward"));
                //else figure out if the player has the required items
            } else {
                int i = 0;
                for (String questReq : NPCInstance.getQuestReq()
                ) {
                    if (inventory.contains(questReq)) {
                        i = i + 1;
                    }
                }
                //if player has all items required
                if (NPCInstance.getQuestReq().size() == i) {

                    //for each required item remove them
                    for (String questReq : NPCInstance.getQuestReq()) {
                        inventory.remove(questReq);
                        gm.getUi().getInventory().removeElement(questReq);
                    }

                    //for each reward give player an item and delete the item from inventory
                    for (String item :
                            NPCInstance.getReward()) {
                        gm.getUi().getInventory().addElement(item);
                        inventory.add(item);
                        NPCInstance.getItems().remove(item);
                    }
                    gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("reward"));

                    //if player doesn't have required quest items do this
                } else {
                    //quest text
                    gm.getUi().getMessageText().setText(NPCInstance.getQuote().get("quest"));
                    //gives item that helps quest...
                    try {
                        gm.getUi().getInventory().addElement(NPCInstance.getQuote().get("gives"));
                        inventory.add(NPCInstance.getQuote().get("gives"));
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
