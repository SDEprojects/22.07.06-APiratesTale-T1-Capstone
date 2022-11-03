package com.company.client;

import com.company.models.Home;
import com.company.view.ActionHandler;
import com.company.view.SceneChanger;
import com.company.view.UI;

public class GameMain {


    int resolution = 1;
    public ActionHandler aHandler = new ActionHandler(this);
    public UI ui = new UI(this, resolution);
    public SceneChanger sc = new SceneChanger(this);


    public static void main(String[] args) {

        new GameMain();
    }

    public GameMain(){
        sc.showScreen(0);
    }

}