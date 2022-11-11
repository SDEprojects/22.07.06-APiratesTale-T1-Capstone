package com.company.client;

import com.company.models.Gamble;
import com.company.models.Game;
import com.company.models.Music;
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
    private Music music = new Music(this);
    private Gamble gamble = new Gamble(this);



    public static void main(String[] args) {
        new GameMain();
    }

    public GameMain(){
        getGame().buildWorld();
        getUi().generate();
        getMusic().playMusic(getUi().getMusicFile());
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

    public SceneChanger getSc() {
        return sc;
    }

    public void setSc(SceneChanger sc) {
        this.sc = sc;
    }

    public ActionHandler getaHandler() {
        return aHandler;
    }

    public void setaHandler(ActionHandler aHandler) {
        this.aHandler = aHandler;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Gamble getGamble() {
        return gamble;
    }

    public void setGamble(Gamble gamble) {
        this.gamble = gamble;
    }
}