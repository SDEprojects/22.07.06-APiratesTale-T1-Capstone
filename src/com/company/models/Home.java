package com.company.models;

import com.apps.util.Prompter;
import com.company.client.GameMain;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.*;

public class Home {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private FileGetter file = new FileGetter();
    ;
    Prompter prompter = new Prompter(new Scanner(System.in));



    //Methods
    public void buildHome() {

//        banner(); replace banner with a splash screen intro "title page"
        // maybe swap with "splash screen"
//        gameInfo(); replace with an informational button
        //startGame();
    }

    public void startGame(String menuSelection) {
        while (true) {
            if (menuSelection.toLowerCase().equals("start")) {
                Player player = new Player();
                //player.newPlayer();
                Game newGame = new Game(player);
                //newGame.playGame();
                break;
            }
            if (menuSelection.toLowerCase().equals("exit")) {
                //maybe build a confirmation or do we just click to close?
                System.out.println("\nThanks For Playing... Good Bye!");
                System.exit(0);
            }
            else {
                System.out.println("Invalid Command");
            }
        }
    }
}
