package com.company.models;

import com.company.client.GameMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Gamble {

    GameMain gm;
    int bet = 0;
    String playerPick;
    int racefinish = 100;
    JLabel rat1 = new JLabel();
    JLabel rat2 = new JLabel();
    JLabel rat3 = new JLabel();
    int rat1Moves;
    int rat2Moves;
    int rat3Moves;
    JPanel gambleGame = new JPanel();
    private JLabel[] rats = new JLabel[]{rat1, rat2, rat3};
    int ratsFinished;


    public Gamble(GameMain gm) {
        this.gm = gm;

    }

    public void buildGamble() {
        gm.getUi().getMessageText().setText("Welcome to the Rat Race, place your bets in increments of 5 gold, " +
                "\npick your rat and start the race to see how you fair");

        JLabel finishLine = new JLabel();
        finishLine.setBounds(700,40,20,150);
        finishLine.setBackground(Color.BLACK);
        finishLine.setOpaque(true);
        gm.getUi().getGambleGame().add(finishLine);

        JButton addBet = new JButton("+ Bet");
        addBet.setForeground(Color.black);
        addBet.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        addBet.setBounds(40,200,100,50);
        addBet.setBackground(Color.GRAY);

        addBet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBet(getBet()+5);
                gm.getUi().getMessageText().setText("Increasing bets: " + getBet());
            }
        });

        JButton subBet = new JButton("- Bet");
        subBet.setForeground(Color.black);
        subBet.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        subBet.setBounds(160,200,100,50);
        subBet.setBackground(Color.GRAY);

        subBet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getBet()>5){
                    setBet(getBet()-5);
                    gm.getUi().getMessageText().setText("Decreasing bets: " + getBet());
                }
            }
        });

        JButton bigRat = new JButton("Big Rat");
        bigRat.setForeground(Color.black);
        bigRat.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        bigRat.setBounds(280,200,150,50);
        bigRat.setBackground(Color.GRAY);

        bigRat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerPick = "Big Rat";
                gm.getUi().getMessageText().setText("Player picks: " + playerPick);

            }
        });

        JButton slimeRat = new JButton("Slime Rat");
        slimeRat.setForeground(Color.black);
        slimeRat.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        slimeRat.setBounds(450,200,150,50);
        slimeRat.setBackground(Color.GRAY);

        slimeRat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerPick = "Slime Rat";
                gm.getUi().getMessageText().setText("Player picks: " + playerPick);
            }
        });

        JButton whiteRat = new JButton("White Rat");
        whiteRat.setForeground(Color.black);
        whiteRat.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        whiteRat.setBounds(620,200,150,50);
        whiteRat.setBackground(Color.GRAY);

        whiteRat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerPick = "White Rat";
                gm.getUi().getMessageText().setText("Player picks: " + playerPick);
            }
        });

        JButton race = new JButton("Race");
        race.setForeground(Color.black);
        race.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        race.setBounds(790,200,100,50);
        race.setBackground(Color.GRAY);

        race.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ratRace();
            }
        });


        gm.getUi().getGambleGame().add(addBet);
        gm.getUi().getGambleGame().add(subBet);
        gm.getUi().getGambleGame().add(bigRat);
        gm.getUi().getGambleGame().add(slimeRat);
        gm.getUi().getGambleGame().add(whiteRat);
        gm.getUi().getGambleGame().add(race);

        rat1.setBounds(40,20,66,65);
        ImageIcon objectIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/bigrat.png")));
        rat1.setIcon(objectIcon);
        rat1.setName("0");

        rat2.setBounds(40,70,66,65);
        ImageIcon objectIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/sickrat.png")));
        rat2.setIcon(objectIcon2);
        rat2.setName("0");

        rat3.setBounds(40,120,66,65);
        ImageIcon objectIcon3 = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/whiterat.png")));
        rat3.setIcon(objectIcon3);
        rat3.setName("0");
        gm.getUi().getGambleGame().add(rat1);
        gm.getUi().getGambleGame().add(rat2);
        gm.getUi().getGambleGame().add(rat3);

    }

    public void ratRace(){
        if (gm.getPlayer().getGold()>=getBet()){
            int currentBet = getBet();
            gm.getUi().getMessageText().setText("Let's race! Player betting "+ currentBet+ "on the rat called "+ playerPick);
            gm.getPlayer().setGold(gm.getPlayer().getGold()-currentBet);
            String thisRacePick= playerPick;
            System.out.println("Players gold balance: "+ gm.getPlayer().getGold());
            int rat1Moves=0;
            int rat2Moves=0;
            int rat3Moves=0;
            rat1.setBounds(106,20,66,65);
            rat2.setBounds(106,60,66,65);
            rat3.setBounds(106,100,66,65);
            rat1.setName("0");
            rat2.setName("0");
            rat3.setName("0");
            ratsFinished=0;

            for (JLabel rat:rats
            ) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        moveRat(rat);
                    }

                });
                thread.start();

            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (ratsFinished<3) {
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("done");
                    System.out.println("'Big Rat' made it in "+rat1.getName()+" seconds...");
                    System.out.println("'Slime Rat' made it in "+rat2.getName()+" seconds...");
                    System.out.println("'White Rat' made it in "+rat3.getName()+" seconds...");
                    ArrayList<String> ratList = new ArrayList<>();
                    ratList.add(rat1.getName());
                    ratList.add(rat2.getName());
                    ratList.add(rat3.getName());
                    int minNum = ratList.stream()
                            .mapToInt(Integer::parseInt)
                            .min()
                            .orElse(0);
                    switch (thisRacePick){
                        case "Big Rat":
                            if (rat1.getName().equals(""+minNum)){
                                System.out.println("Big Rat is a winner!");
                                gm.getPlayer().setGold(gm.getPlayer().getGold()+(currentBet*3));
                                System.out.println("Players gold balance: "+ gm.getPlayer().getGold());
                                gm.getUi().getMessageText().setText("Big Rat is a winner! \nPlayer won : "+ (currentBet*3) + "!\nPlayers gold balance: "+ gm.getPlayer().getGold());
                            }else {
                                gm.getUi().getMessageText().setText("Loser! Better luck next time...");
                            }
                            break;
                        case "Slime Rat":
                            if (rat2.getName().equals(""+minNum)){
                                System.out.println("Slime Rat is a winner!");
                                gm.getPlayer().setGold(gm.getPlayer().getGold()+(currentBet*3));
                                System.out.println("Players gold balance: "+ gm.getPlayer().getGold());
                                gm.getUi().getMessageText().setText("Slime Rat is a winner! \nPlayer won : "+ (currentBet*3) + "Gold!\nPlayers gold balance: "+ gm.getPlayer().getGold());
                            }else {
                                gm.getUi().getMessageText().setText("Loser! Better luck next time...");
                            }
                            break;
                        case "White Rat":
                            if (rat3.getName().equals(""+minNum)){
                                System.out.println("White rat is a winner!");
                                gm.getPlayer().setGold(gm.getPlayer().getGold()+(currentBet*3));
                                System.out.println("Players gold balance: "+ gm.getPlayer().getGold());
                                gm.getUi().getMessageText().setText("White Rat is a winner! \nPlayer won : "+ (currentBet*3) + "!\nPlayers gold balance: "+ gm.getPlayer().getGold());
                            }else {
                                gm.getUi().getMessageText().setText("Loser! Better luck next time...");
                            }
                            break;
                        default:
                            gm.getUi().getMessageText().setText("Not a winner");
                    }
                }
            });
            thread.start();
        }
        else {
            gm.getUi().getMessageText().setText("You need more money, your poor!");
        }
    }

    public void moveRat(JLabel rat){

        int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            int i=0;
            int round=0;
            public void actionPerformed(ActionEvent evt) {
                if (i > 700) {//we did the task 10 times
                    ((Timer) evt.getSource()).stop();
                    ratsFinished = ratsFinished+1;
                }
                Random r = new Random();
                int randInt = r.nextInt(100-10) + 10;
                int speed = randInt;
                i = (rat.getLocation().x + speed);
                round = round + 1;
                //gm.getUi().getGambleGame().repaint((rat1.getLocation().x + 20), rat1.getLocation().y,96,128);
                rat.setLocation((rat.getLocation().x + speed), rat.getLocation().y);
                rat.setName((""+round));
                System.out.println(SwingUtilities.isEventDispatchThread()+"i is now: "+ i);

            }
        };
        new Timer(delay, taskPerformer).start();

    }


    public GameMain getGm() {
        return gm;
    }

    public void setGm(GameMain gm) {
        this.gm = gm;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}