package com.company.view;

import com.company.client.GameMain;

import javax.swing.*;

public class SceneChanger {
    GameMain gm;

    public SceneChanger(GameMain gm){
        this.gm = gm;
    }

    public void showScreen(int screen) {
        for (JPanel panel:gm.ui.bgPanel
             ) {panel.setVisible(false);
       }
        gm.ui.bgPanel[screen].setVisible(true);
    }

}