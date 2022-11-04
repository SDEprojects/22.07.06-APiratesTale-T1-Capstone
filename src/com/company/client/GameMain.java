package com.company.client;

import com.company.models.Game;
import com.company.models.Player;
import com.company.view.ActionHandler;
import com.company.view.SceneChanger;
import com.company.view.UI;

public class GameMain {


    int resolution = 1;
    public ActionHandler aHandler = new ActionHandler(this);
    private final UI ui = new UI(this, resolution);
    public SceneChanger sc = new SceneChanger(this);
    private final Player player = new Player(this);
    private final Game game = new Game(this);


    public static void main(String[] args) {
        new GameMain();
    }

    public GameMain(){
        getGame().buildWorld();
        getUi().generate();
        sc.showScreen(0);
    }

    public UI getUi() {
        return ui;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }
}