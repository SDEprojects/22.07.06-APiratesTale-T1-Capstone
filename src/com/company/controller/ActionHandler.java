package com.company.controller;

import com.company.client.GameMain;
import com.company.models.Item;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener {

    private GameMain gm;

    public ActionHandler(GameMain gm){
        this.gm = gm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String yourChoice = e.getActionCommand();
        String[] inputSplit = yourChoice.trim().split(" ");

        if (inputSplit.length>2){
            inputSplit[1] = inputSplit[1] +" "+inputSplit[2];
        }


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
                //gm.getUi().messageText.setText("You try to drop "+ inputSplit[1]);
                break;
            case "fight":
                gm.getUi().getMessageText().setText("You try to fight "+ inputSplit[1]);
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
                gm.getUi().getMessageText().setText("you went to area "+ inputSplit[1]);
                gm.getSc().screenPicker(direction);
                gm.getUi().getNpcName().setText("");

                break;
            case "inventory":
                gm.getUi().getPlayerBag().setVisible(true);
                break;
            case "equipment":
                gm.getUi().getPlayerEquipment().setVisible(true);
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
                gm.getUi().getGambleGame().setVisible(true);
                gm.getGamble().buildGamble(); //once built will move to actionhandler
                break;
            default:
                break;
        }
        if (gm.getPlayer().getHp()<=0){
            gm.getUi().gameStateWindow("img/gameover.png", "img/end.png", "What do you want to do?", "You got wasted!");
        }
        if (gm.getPlayer().getHp() > 0){
            gm.getUi().getArea().setText("Current location: " + gm.getPlayer().getCurrentRoom());

            Timer timer = new Timer(1000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    gm.getUi().getHp().setText("HP: " + gm.getPlayer().getHp());
                }
            });
            timer.start();

            try {
                String item = gm.getPlayer().getEquipedItem();
                Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
                gm.getUi().getCurrentWeapon().setText("Weapon: " + gm.getPlayer().getEquipedItem() + " (DP: +" + itemInstance.getStrength() + ")" );

            } catch (Exception ignored) {
            }
        }

    }
}