package com.company.models;

import com.company.client.GameMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Gamble {

    GameMain gm;
    int bet = 0;
    int racefinish = 100;
    JLabel rat1 = new JLabel();
    JLabel rat2 = new JLabel();
    JLabel rat3 = new JLabel();
    JPanel gambleGame = new JPanel();

    public Gamble(GameMain gm) {
        this.gm = gm;

    }


    public void buildGamble() {
        gambleGame = gm.getUi().eventPanel(100, 100, 1000, 300, "gambleGame");

        JButton rat1bet = new JButton("Rat 1");
        rat1bet.setForeground(Color.black);
        rat1bet.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        rat1bet.setBounds(350,200,150,50);
        rat1bet.setBackground(Color.GRAY);

        rat1bet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bet();
            }
        });

        JButton race = new JButton("Race");
        race.setForeground(Color.black);
        race.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        race.setBounds(500,200,150,50);
        race.setBackground(Color.GRAY);

        race.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveRat(3);
            }
        });



        gm.getUi().getGambleGame().add(rat1bet);
        gm.getUi().getGambleGame().add(race);

        JLabel rat1 = new JLabel();
        rat1.setBounds(20,20,96,128);
        ImageIcon objectIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/rat.png")));
        rat1.setIcon(objectIcon);
        rat1.setName("Rat 1");
        gm.getUi().getGambleGame().add(rat1);

    }

    public void moveRat(int speed){
        int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            int count=0;
            public void actionPerformed(ActionEvent evt) {
                if(count==10) {//we did the task 10 times
                    ((Timer)evt.getSource()).stop();
                }

                rat1.setLocation((rat1.getLocationOnScreen().x+10), gm.getGamble().rat1.getLocationOnScreen().y);
                System.out.println(SwingUtilities.isEventDispatchThread());
                count++;
            }
        };
        new Timer(delay, taskPerformer).start();
    }

    public void bet(){
        setBet(getBet()+1);
        System.out.println("Increasing bets: " + getBet());
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