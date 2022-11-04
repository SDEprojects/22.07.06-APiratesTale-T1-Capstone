package com.company.view;

import com.company.client.GameMain;
import com.company.models.Game;
import com.company.models.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener {

    private GameMain gm;
    Home home = new Home();



    public ActionHandler(GameMain gm){
        this.gm = gm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String yourChoice = e.getActionCommand();
        String[] inputSplit = yourChoice.trim().toLowerCase().split(" ");

        if (inputSplit.length>2){
            inputSplit[1] = inputSplit[1] +" "+inputSplit[2];
        }


        switch (inputSplit[0]) {
            case "look":
                gm.getUi().messageText.setText("You look at "+ inputSplit[1]);
                break;
            case "grab":
                gm.getUi().messageText.setText("You grab "+ inputSplit[1]);
                gm.getPlayer().grabItem(inputSplit[1]);
                break;
            case "eat":
                gm.getUi().messageText.setText("You try to eat "+ inputSplit[1]);
                gm.getPlayer().useItem(inputSplit[1]);
                break;
            case "fight":
                gm.getUi().messageText.setText("You try to fight "+ inputSplit[1]);
                break;
            case "talk":
                gm.getUi().messageText.setText("You talk to "+ inputSplit[1]);
                break;
            case "trade":
                gm.getUi().messageText.setText("You try and trade with "+ inputSplit[1]);
                break;
            case "move":
                int direction = Integer.parseInt(inputSplit[1]);
                gm.sc.showScreen(direction);
                gm.getUi().messageText.setText("you went to area "+ inputSplit[1]);
                break;
            case "start":
                gm.sc.showScreen(1);
                gm.getUi().createMessageViewer();
                break;
            default:
                break;
        }

    }
}