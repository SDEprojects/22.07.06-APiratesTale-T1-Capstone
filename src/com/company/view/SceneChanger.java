package com.company.view;

import com.company.client.GameMain;
import com.company.models.Locations;

import javax.swing.*;
import java.util.List;

public class SceneChanger {
    GameMain gm;

    public SceneChanger(GameMain gm){
        this.gm = gm;
    }

    public void screenPicker(String direction){

            Locations currentLocation = gm.getGame().getLocations().stream().filter(locationFind -> locationFind.getName().equals(gm.getPlayer().getCurrentRoom())).findFirst().orElse(null);
            String nextRoom = currentLocation.getDirections().get(direction);


        try {
            if (!nextRoom.isEmpty()){
                gm.getPlayer().setCurrentRoom(nextRoom);
                int roomSelect = gm.getUi().findPanelIndex(nextRoom);
                showScreen(roomSelect);
            }
        } catch (Exception e) {
            gm.getUi().messageText.setText("Location not available to move to!");
        }
        try {
            if (currentLocation.getDirections().get("lock").equals(nextRoom)){
                if (!gm.getPlayer().inventory.contains(currentLocation.getDirections().get("key"))){
                    gm.getUi().messageText.setText("Can't go that way, "+ currentLocation.getDirections().get("keyError"));
                    int roomSelect = gm.getUi().findPanelIndex(currentLocation.getName());
                    gm.getPlayer().setCurrentRoom(currentLocation.getName());
                    showScreen(roomSelect);
                }
            }
        } catch (Exception ignored) {

        }

        //gm.getUi().getBgPanel().stream().filter(name -> name.getName().equals(nextRoom)).findFirst().orElse(null);


    }

//    public static int findIndex(List<JPanel> panel, String name)
//    {
//        // find length of array
//        int len = panel.size();
//        int i = 0;
//
//        // traverse in the array
//        while (i < len) {
//
//            // if the i-th element is t
//            // then return the index
//            if (panel.get(i).getName().equals(name)) {
//                return i;
//            }
//            else {
//                i = i + 1;
//            }
//        }
//        return -1;
//    }

    public void showScreen(int screen) {
        for (JPanel panel:gm.getUi().getBgPanel()
             ) {panel.setVisible(false);
       }
        gm.getUi().getBgPanel().get(screen).setVisible(true);
    }

}