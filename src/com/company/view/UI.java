package com.company.view;

import com.company.client.GameMain;
import com.company.models.Game;
import com.company.models.Items;
import com.company.models.Locations;
import com.company.models.Characters;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class UI {

    JFrame window;
    GameMain gm;
    double resolutionChanger;
    int windowHeight = 720;
    int windowWidth = 1280;
    public JTextArea messageText;
    private Font oldRetro;
    ArrayList<JPanel> bgPanel= new ArrayList<>();
    ArrayList<JLabel> bgLabel= new ArrayList<>();
//    JPanel bgPanel[];
//    JLabel bgLabel[];


    public UI(GameMain gm, double resolutionChanger) {

        this.gm = gm;
        fontCreate();
        setResolutionChanger(resolutionChanger);
        setWindowHeight((int) (getWindowHeight()*getResolutionChanger()));
        setWindowWidth((int) (getWindowWidth()*getResolutionChanger()));
        createMainField();
        System.out.println("Generating main splash");
        generateSplashScene();
        window.setVisible(true);
    }

    private void fontCreate(){
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(("resources/Press_Start_2P/PressStart2P-Regular.ttf")));
            Font retro = Font.createFont(Font.TRUETYPE_FONT, is);
            setOldRetro(retro.deriveFont(Font.PLAIN, 20));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }

    public void createMainField(){
        window = new JFrame("A Pirates Tale");
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new JLabel(new ImageIcon("resources/img/gamegackground.jpg")));
        window.setLayout(null);
        window.setResizable(false);
        window.setLocationRelativeTo(null);

    }

    public void createMessageViewer(){
        messageText = new JTextArea("Welcome to the Jungle!");
        messageText.setBounds((int) (.05*windowWidth), (int) (.68*windowHeight), (int) (.9*windowWidth), (int) (.25*windowHeight));
        //messageText.setBackground(Color.BLUE);
        messageText.setForeground(Color.black);
        messageText.setOpaque(false);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(getOldRetro());
        window.add(messageText);
    }

    public void createBackground(int bgNum, String bgFileName){

        JPanel panel = new JPanel();
        panel.setName(String.valueOf(bgNum));
        panel.setBounds((int) (.05*windowWidth),(int) (.08*windowHeight),(int) (.9*windowWidth),(int) (.58*windowHeight));
        panel.setBackground(Color.BLUE);
        panel.setLayout(null);
        bgPanel.add(panel);
        window.add(panel);


        JLabel label = new JLabel();
        label.setName(String.valueOf(bgNum));
        label.setBounds(0,0,(int) (.9*windowWidth),(int) (.58*windowHeight));
        bgLabel.add(label);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgFileName)));
        label.setIcon(bgIcon);
    }

    public void createSplashScreen(String bgFileName){

        JPanel splash = new JPanel();
        splash.setBounds(0, 0, 1280, 720);
        splash.setBackground(Color.BLUE);
        splash.setLayout(null);
        bgPanel.add(splash);
        window.add(splash);

        JLabel label = new JLabel();
        label.setBounds(0,0,1280, 720);
        bgLabel.add(label);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgFileName)));
        label.setIcon(bgIcon);
    }

    public void createObject(int bgNum, int objX, int objY, int objWidth, int ObjHeight, String objFile, String type, String target){
        String choice1 = null;
        String choice2 = null;
        String choice3 = null;
        int choices = 0;

        switch (type){
            case "quest":
              choice1 = "look";
              choice2 = "grab";
              choices = 2;
                break;
            case "weapon":
            case "armor":
                choice1 = "look";
                choice2 = "grab";
                choice3 = "equip";
                choices = 3;
                break;
            case "food":
                choice1 = "look";
                choice2 = "grab";
                choice3 = "eat";
                choices = 3;
                break;
            case "notFriendly":
                choice1 = "look";
                choice2 = "fight";
                choices = 2;
                break;
            default:
                choice1 = "talk";
                choice2 = "look";
                choice3 = "fight";
                choices = 3;
                break;
        }

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem[] = new JMenuItem[4];
        menuItem[0] = new JMenuItem(choice1);
        menuItem[0].addActionListener(gm.aHandler);
        menuItem[0].setActionCommand(choice1 +" "+ target);
        popupMenu.add(menuItem[0]);

        menuItem[1] = new JMenuItem(choice2);
        menuItem[1].addActionListener(gm.aHandler);
        menuItem[1].setActionCommand(choice2 +" "+ target);
        menuItem[1].setName(target);
        popupMenu.add(menuItem[1]);


        if (choices == 3){
            menuItem[2] = new JMenuItem(choice3);
            menuItem[2].addActionListener(gm.aHandler);
            menuItem[2].setActionCommand(choice3 +" "+ target);
            menuItem[2].setName(target);
            popupMenu.add(menuItem[2]);
        }

        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objX,objY,objWidth,ObjHeight);
        ImageIcon objectIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(objFile)));
        objectLabel.setIcon(objectIcon);

        objectLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(objectLabel, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        bgPanel.get(bgNum).add(objectLabel);
       ;
    }


    public void createArrowButton(int bgNum, int x, int y, int width, int height, String arrowFileName, String command, String target){
        ImageIcon arrowIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(arrowFileName)));
        //Image image = arrowIcon.getImage().getScaledInstance(width*1,height*1, Image.SCALE_DEFAULT);
        //example of resizing
        JButton arrowButton = new JButton();
        arrowButton.setBounds(x,y,width,height);
        arrowButton.setBackground(null);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setFocusPainted(false);
        arrowButton.setBorderPainted(false);
        arrowButton.setIcon(arrowIcon);
        arrowButton.addActionListener(gm.aHandler);
        arrowButton.setActionCommand(command + " " + target);
        bgPanel.get(bgNum).add(arrowButton);
    }

    public void createStartButton(int x, int y, int width, int height, String command, String target){
        JButton startButton = new JButton("Press Start");
        startButton.setForeground(Color.black);
        //startButton.setFont(oldRetro);
        startButton.setOpaque(true);
        startButton.setBounds(x,y,width,height);
        startButton.setBackground(null);
        startButton.addActionListener(gm.aHandler);
        startButton.setActionCommand(command + " " + target);
        bgPanel.get(0).add(startButton);

    }

    public void generate() {
        int i = 1;
        for (Locations location:gm.getGame().getLocations()
        ) {
            generateScenes(i,location.getImg(),location.getItems(),location.getNPC(),location.getDirections());
            System.out.println("loading... scene "+ i);
            i++;
        }

    }

    public void generateScenes(int sceneNum, String img, List<String> items, List<String> npcs, Map<String, String> directions) {
        gm.getUi().createBackground(sceneNum, img);

        for (String item:items
             ) {
            System.out.println(item);
            Items itemBuild = gm.getGame().getItems().stream().filter(itemSeek -> itemSeek.getName().equals(item)).findFirst().orElse(null);
            System.out.println("Item name: " + itemBuild.getName() + ". Description: " +itemBuild.getDescription());
            gm.getUi().createObject(sceneNum, itemBuild.getXaxis(), itemBuild.getYaxis(), itemBuild.getWidth(), itemBuild.getHeight(),
                    itemBuild.getImg(), itemBuild.getType(), itemBuild.getName());
        }

        for (String npc:npcs
        ) {
            System.out.println(npc);
            Characters characterBuild = gm.getGame().getCharacters().stream().filter(characterSeek -> characterSeek.getName().equals(npc)).findFirst().orElse(null);
            System.out.println("Character name: " + characterBuild.getName() + ". Description: " +characterBuild.getQuote());
            String type = "notFriendly";
            if (characterBuild.isFriendly()){
                type = "Friendly";
            }
            gm.getUi().createObject(sceneNum, characterBuild.getXaxis(), characterBuild.getYaxis(), characterBuild.getWidth(), characterBuild.getHeight(),
                    characterBuild.getImg(), type, characterBuild.getName());
            //gm.getUi().createObject(sceneNum, 970, 140, 150, 225, "img/parrot.png", "eat", "grab", "talk", "parrot");
        }

        for (Map.Entry<String, String> entry: directions.entrySet()
        ) {
            System.out.println(entry.getKey());
            //gm.getUi().createObject(sceneNum, 970, 140, 150, 225, "img/parrot.png", "eat", "grab", "talk", "parrot");
        }


        if (sceneNum<15) {
            createArrowButton(sceneNum, 0, 150, 50, 50, "img/left.png", "move", String.valueOf(sceneNum+1));
        }
        else {
            createArrowButton(sceneNum, 0, 150, 50, 50, "img/left.png", "move", String.valueOf(1));
        }
        gm.getUi().bgPanel.get(sceneNum).add(bgLabel.get(sceneNum));


//        for (String object : objects
//        ) {
//            createObject(sceneNum, 970, 140, 150, 225, "img/" + object + ".png", "eat", "grab", "talk", "parrot");
//        }
//        for (String npc : npcs) {
//            createObject(1, 70, 80, 264, 400, "img/" + npc + ".png", "talk", "fight", "trade", "uncle");
//        }
//        for (String direction : directions) {
//            createArrowButton(sceneNum, 0, 150, 50, 50, "img/" + direction + ".png", "move", String.valueOf(sceneNum+1));
//        }
    }

//notes from old
////        createBackground(1, "img/jungle");
////        createObject(1, 970, 140, 150, 225, "img/parrot.png", "eat", "grab", "talk", "parrot");
////        createObject(1, 70, 80, 264, 400, "img/uncle.png", "talk", "fight", "trade", "uncle");
////        createArrowButton(1,0,150,50,50,"img/left.png", "move", "2");
////        bgPanel[1].add(bgLabel[1]);
//
//        createBackground(2, "img/village.png");
//        //createObject(1, 970, 140, 150, 225, "parrot.png", "eat", "grab", "talk", "parrot");
//        //createObject(1, 70, 80, 264, 400, "uncle.png", "talk", "fight", "trade", "uncle");
//        createArrowButton(2,0,150,50,50,"img/left.png", "move", "1");
//        bgPanel[2].add(bgLabel[2]);

    public void generateSplashScene(){
        createSplashScreen("img/splashscreen.png");
        createStartButton(550, 550, 150, 50, "start", "1");
        bgPanel.get(0).add(bgLabel.get(0));
    }

    public double getResolutionChanger() {
        return resolutionChanger;
    }

    public void setResolutionChanger(double resolutionChanger) {
        this.resolutionChanger = resolutionChanger;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public ArrayList<JPanel> getBgPanel() {
        return bgPanel;
    }

    public void setBgPanel(ArrayList<JPanel> bgPanel) {
        this.bgPanel = bgPanel;
    }

    public ArrayList<JLabel> getBgLabel() {
        return bgLabel;
    }

    public void setBgLabel(ArrayList<JLabel> bgLabel) {
        this.bgLabel = bgLabel;
    }

    public Font getOldRetro() {
        return oldRetro;
    }

    public void setOldRetro(Font oldRetro) {
        this.oldRetro = oldRetro;
    }
}