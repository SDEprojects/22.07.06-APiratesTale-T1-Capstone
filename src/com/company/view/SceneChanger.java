package com.company.view;

import com.company.client.GameMain;
import com.company.models.Location;

import javax.swing.*;

public class SceneChanger {
    GameMain gm;

    public SceneChanger(GameMain gm){
        this.gm = gm;
    }

    public void screenPicker(String direction){

            Location currentLocation = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(gm.getPlayer().getCurrentRoom())).findFirst().orElse(null);
            String nextRoom = currentLocation.getDirections().get(direction);


        try {
            if (!nextRoom.isEmpty()){
                gm.getPlayer().setCurrentRoom(nextRoom);
                int roomSelect = gm.getUi().findPanelIndex(nextRoom);
                showScreen(roomSelect);
            }
        } catch (Exception e) {
            gm.getUi().getMessageText().setText("Location not available to move to!");
        }
        try {
            if (currentLocation.getDirections().get("lock").equals(nextRoom)){
                if (!gm.getPlayer().getInventory().contains(currentLocation.getDirections().get("key"))){
                    gm.getUi().getMessageText().setText("Can't go that way, "+ currentLocation.getDirections().get("keyError"));
                    int roomSelect = gm.getUi().findPanelIndex(currentLocation.getName());
                    gm.getPlayer().setCurrentRoom(currentLocation.getName());
                    showScreen(roomSelect);
                }
            }
        } catch (Exception ignored) {
        }

    }

    public void showScreen(int screen) {
        for (JPanel panel:gm.getUi().getBgPanel()
             ) {panel.setVisible(false);
       }
        gm.getUi().getBgPanel().get(screen).setVisible(true);
    }

}