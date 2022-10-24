package com.company.models;

import com.apps.util.Prompter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Game {
    Prompter prompter = new Prompter(new Scanner(System.in));
    private final Player player;
    Home home = new Home();

    public Game(Player player) {
        this.player = player;
    }

    public void playGame(){
        System.out.printf("\nWelcome to Mango Island, %s.", player.name);
        System.out.println();
        System.out.println("You awaken on the beach in your modest shack on Mango Island after a long nap. You look out the window and notice a sad traveler approaching you. You step outside to greet him.");
        System.out.println();
        System.out.println("You can use the following commands to play the game: ");
        System.out.println("TIP: Enter TALK [name] to speak to others.\n");
        actions();
        System.out.println();
    }

    public void actions(){
        while (true) {
            player.status();
            String userInput = prompter.prompt("\nCMD:  GO [direction] |  TALK [name]  |  GRAB [item]  |  LOOK [item]  |  USE [item]  | QUIT \n").toLowerCase();
            String[] inputSplit = userInput.trim().toLowerCase().split(" ");
            if(inputSplit[0].equals("look")) {
                player.look(inputSplit[1]);
            }
            else if (inputSplit[0].equals("go")) {
                if (inputSplit.length == 2) {
                    player.go(inputSplit[1]);
                }
                else if (inputSplit.length == 3) {
                    String island = inputSplit[1] + " " + inputSplit[2];
                    player.go(island);
                }

            }
            else if (inputSplit[0].equals("grab")){
                player.grabItem(inputSplit[1]);

            }
            else if(inputSplit[0].equals("quit")) {
                Home newHome = new Home();
                newHome.buildHome();
                break;
            }
            else {
                System.out.println("Invalid Command Entered");
            }
        }
    }

    public void loadGame() {

    }

    public void saveGame() {

    }

}