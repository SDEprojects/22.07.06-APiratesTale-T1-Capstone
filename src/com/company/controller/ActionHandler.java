package com.company.controller;

import com.company.client.GameMain;
import com.company.models.Item;
import com.company.models.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener {

    private GameMain gm;

    public ActionHandler(GameMain gm){
        this.gm = gm;
    }

    //this function is called from the ui and is the "controller" for all functions of the game
    @Override
    public void actionPerformed(ActionEvent e) {

        String yourChoice = e.getActionCommand();

        //Original parser from old game used to pass verb(inputSplit[0]) & noun (inputSplit[1]) into the action switch that follows
        String[] inputSplit = yourChoice.trim().split(" ");

        //if noun is two words add second word
        if (inputSplit.length>2){
            inputSplit[1] = inputSplit[1] +" "+inputSplit[2];
        }

        //switch on verb passed from UI, actions passed on to other classes
        switch (inputSplit[0]) {
            case "look":
                gm.getPlayer().look(inputSplit[1]);
                gm.getUi().getNpcName().setText(inputSplit[1]);
                break;
            case "grab":
                gm.getPlayer().grabItem(inputSplit[1]);
                if (gm.getPlayer().getInventory().contains("Treasure Chest")){
                    gm.getUi().gameStateWindow("img/giphy.gif", "img/trophy.png", "What do you want to do?", "You beat the Game");
                }
                break;
            case "eat":
                gm.getUi().getNpcName().setText(inputSplit[1]);
                gm.getPlayer().useItem(inputSplit[1]);
                break;
            case "drop":
                gm.getPlayer().dropItem(inputSplit[1]);
                break;
            case "fight":
                gm.getUi().getNpcName().setText(inputSplit[1]);
                gm.getPlayer().attack(inputSplit[1]);
                break;
            case "talk":
                gm.getPlayer().talk(inputSplit[1]);
                gm.getUi().getNpcName().setText(inputSplit[1]);
                break;
            case "trade":
                gm.getUi().getMessageText().setText("You try and trade with "+ inputSplit[1]);
                break;
            case "move":
                String direction = inputSplit[1];
                gm.getSc().screenPicker(direction);
                break;
            case "inventory":
                gm.getUi().getPlayerBag().setVisible(true);
                break;
            case "map":
                Location currentLocation = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(gm.getPlayer().getCurrentRoom())).findFirst().orElse(null);
                gm.getUi().createMap(currentLocation.getIsland(), currentLocation.getGrid());
                gm.getUi().getPlayerMap().setVisible(true);
                break;
            case "setting":
                gm.getUi().getSettings().setVisible(true);
                break;
            case "help":
                gm.getUi().getHelp().setVisible(true);
                gm.getUi().getHelpText().setText(gm.getUi().textHelp());
                break;
            case "close":
                gm.getUi().eventPanelClose(inputSplit[1]);
                break;
            case "inspect":
                Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(inputSplit[1])).findFirst().orElse(null);
                gm.getUi().getMessageText().setText("item : "+ inputSplit[1] + " | Description: "+ itemInstance.getDescription() + " | Gold Value: "+itemInstance.getCost());
                break;
            case "start":
                gm.getSc().showScreen(1);
                gm.getUi().createMessageViewer();
                break;
            case "equip":
                gm.getPlayer().equipItem(inputSplit[1]);
                break;
            case "gamble":
                gm.getPlayer().talk(inputSplit[1]);
                gm.getUi().getNpcName().setText(inputSplit[1]);
                gm.getUi().getGambleGame().setVisible(true);
                gm.getGamble().buildGamble();
                break;
            case "shop":
                gm.getPlayer().talk(inputSplit[1]);
                gm.getUi().getNpcName().setText(inputSplit[1]);
                gm.getUi().getStorePanel().setVisible(true);
                gm.getShop().buildShop(); //once built will move to actionhandler
                break;
            case "sail":
                gm.getPlayer().sail(inputSplit[1]);
                break;
            default:
                break;
        }

        //if player is still alive continue playing the game
        if (gm.getPlayer().getHp() > 0){
            gm.getUi().getArea().setText("Current location: " + gm.getPlayer().getCurrentRoom());

            Timer timer = new Timer(1000, new ActionListener() {

                //timer used to continue and update the HP & Gold as those parameters change
                @Override
                public void actionPerformed(ActionEvent e) {
                    gm.getUi().getHp().setText("HP:" + gm.getPlayer().getHp());
                    gm.getUi().getGold().setText("Gold:"+ gm.getPlayer().getGold());
                }
            });
            timer.start();

            //if player equips item, show item in equipment bar
            try {
                String item = gm.getPlayer().getEquipedItem();
                Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
                gm.getUi().getCurrentWeapon().setText("Weapon:" + gm.getPlayer().getEquipedItem() + " (DP: +" + itemInstance.getStrength() + ")" );

            } catch (Exception ignored) {
            }
        }

    }
}