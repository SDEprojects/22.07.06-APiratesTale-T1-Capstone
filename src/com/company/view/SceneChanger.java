package com.company.view;

import com.company.client.GameMain;

import javax.swing.*;

public class SceneChanger {
    GameMain gm;

    public SceneChanger(GameMain gm){
        this.gm = gm;
    }

    public void showScreen(int screen) {
        for (JPanel panel:gm.getUi().getBgPanel()
             ) {panel.setVisible(false);
       }
        gm.getUi().getBgPanel().get(screen).setVisible(true);
    }

}