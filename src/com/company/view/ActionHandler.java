package com.company.view;

import com.company.client.GameMain;
import com.company.models.Home;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener {

    GameMain gm;
    Home home = new Home();


    public ActionHandler(GameMain gm){
        this.gm = gm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String yourChoice = e.getActionCommand();
        String[] inputSplit = yourChoice.trim().toLowerCase().split(" ");

        switch (inputSplit[0]) {
            case "look":
                gm.ui.messageText.setText("You look at "+ inputSplit[1]);
                break;
            case "grab":
                gm.ui.messageText.setText("You grab "+ inputSplit[1]);
                break;
            case "eat":
                gm.ui.messageText.setText("You try to eat "+ inputSplit[1]);
                break;
            case "fight":
                gm.ui.messageText.setText("You try to fight "+ inputSplit[1]);
                break;
            case "talk":
                gm.ui.messageText.setText("You talk to "+ inputSplit[1]);
                break;
            case "trade":
                gm.ui.messageText.setText("You try and trade with "+ inputSplit[1]);
                break;
            case "move":
                int direction = Integer.parseInt(inputSplit[1]);
                gm.sc.showScreen(direction);
                break;
            case "start":
                int nextScreen = Integer.parseInt(inputSplit[1]);
                gm.sc.showScreen(nextScreen);
                gm.ui.createMessageViewer();
                home.startGame("start");
                break;
            default:
                break;
        }

    }
}