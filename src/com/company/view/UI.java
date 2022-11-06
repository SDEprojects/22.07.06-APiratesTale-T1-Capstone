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
    private JPanel directionPanel;
    private JButton northButton, southButton, eastButton, westButton;
    private JButton invButton, equipButton, settingButton, helpButton;
    private JPanel invPanel, settingPanel;
    private JButton invBag, equipWeapon, settingIcon, helpIcon;
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
        setDirectionPanel(new JPanel());
        getDirectionPanel().setBounds((int)(.76*windowWidth), (int) (.68*windowHeight), (int) (.18*windowWidth), (int) (.25*windowHeight));
        //getDirectionPanel().setBackground(Color.BLUE);
        getDirectionPanel().setLayout(null);

        setNorthButton(new JButton("N"));
        setSouthButton(new JButton("S"));
        setEastButton(new JButton("E"));
        setWestButton(new JButton("W"));

        getNorthButton().setBounds(100,30,30,30);
        getSouthButton().setBounds(100,120,30,30);
        getEastButton().setBounds(150,75,30,30);
        getWestButton().setBounds(50,75,30,30);
        getNorthButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getWestButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getEastButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getSouthButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getNorthButton().addActionListener(gm.aHandler);
        getNorthButton().setActionCommand("move north");
        getSouthButton().addActionListener(gm.aHandler);
        getSouthButton().setActionCommand("move south");
        getWestButton().addActionListener(gm.aHandler);
        getWestButton().setActionCommand("move west");
        getEastButton().addActionListener(gm.aHandler);
        getEastButton().setActionCommand("move east");
        getDirectionPanel().add(getSouthButton());
        getDirectionPanel().add(getEastButton());
        getDirectionPanel().add(getNorthButton());
        getDirectionPanel().add(getWestButton());
        getDirectionPanel().setOpaque(false);

        setInvPanel(new JPanel());
        getInvPanel().setBounds((int) (.05*windowWidth), (int) (.68*windowHeight), (int) (.07*windowWidth), (int) (.25*windowHeight));
        //getInvPanel().setBackground(Color.BLUE);
        getInvPanel().setLayout(null);
        getInvPanel().setOpaque(false);
        setInvBag(new JButton());
        getInvBag().setBounds(10,0,72,72);
        getInvBag().setOpaque(false);
        getInvBag().setContentAreaFilled(false);
        getInvBag().setBorderPainted(false);
        getInvPanel().add(getInvBag());
        ImageIcon bagIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/bag.png")));
        getInvBag().setIcon(bagIcon);
        setEquipWeapon(new JButton());
        getEquipWeapon().setBounds(10,80,72,72);
        getEquipWeapon().setOpaque(false);
        getEquipWeapon().setContentAreaFilled(false);
        getEquipWeapon().setBorderPainted(false);
        getInvPanel().add(getEquipWeapon());
        ImageIcon eqIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/equipped.png")));
        getEquipWeapon().setIcon(eqIcon);

        setSettingPanel(new JPanel());
        getSettingPanel().setBounds((int)(.76*windowWidth), (int) (0*windowHeight), (int) (.18*windowWidth), (int) (.08*windowHeight));
        //getSettingPanel().setBackground(Color.BLUE);
        getSettingPanel().setLayout(null);
        getSettingPanel().setOpaque(false);
        setSettingIcon(new JButton());
        getSettingIcon().setBounds(100,0,50,50);
        getSettingIcon().setOpaque(false);
        getSettingIcon().setContentAreaFilled(false);
        getSettingIcon().setBorderPainted(false);
        getSettingPanel().add(getSettingIcon());
        ImageIcon sIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/gear.png")));
        getSettingIcon().setIcon(sIcon);
        setHelpIcon(new JButton());
        getHelpIcon().setBounds(160,0,50,50);
        getHelpIcon().setOpaque(false);
        getHelpIcon().setContentAreaFilled(false);
        getHelpIcon().setBorderPainted(false);
        getSettingPanel().add(getHelpIcon());
        ImageIcon helpIcon = new ImageIcon(getClass().getClassLoader().getResource("img/help.png"));
        getHelpIcon().setIcon(helpIcon);


        messageText = new JTextArea();
        messageText.setBounds((int) (.13*windowWidth), (int) (.68*windowHeight), (int) (.62*windowWidth), (int) (.25*windowHeight));
       // messageText.setBackground(Color.BLUE);
        messageText.setForeground(Color.black);
        messageText.setOpaque(false);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(getOldRetro());
        window.add(getSettingPanel());
        window.add(getInvPanel());
        window.add(getDirectionPanel());
        window.add(messageText);
    }

    public void createBackground(int bgNum, String bgFileName, String target){

        JPanel panel = new JPanel();
        setDirectionPanel(new JPanel());
        panel.setName(target);
        panel.setBounds((int) (.05*windowWidth),(int) (.08*windowHeight),(int) (.9*windowWidth),(int) (.58*windowHeight));
        panel.setLayout(null);
        panel.setOpaque(false);
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
        splash.setName("Title");
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
        startButton.setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        startButton.setOpaque(false);
        startButton.setBounds(x,y,width,height);
        startButton.setBackground(Color.GRAY);
        startButton.addActionListener(gm.aHandler);
        startButton.setActionCommand(command + " " + target);
        bgPanel.get(0).add(startButton);

    }

    public void generate() {
        int i = 1;
        for (Locations location:gm.getGame().getLocations()
        ) {
            generateScenes(i,location.getImg(),location.getItems(),location.getNPC(),location.getDirections(), location.getName());
            System.out.println("loading... scene "+ i);
            i++;
        }

    }

    public void generateScenes(int sceneNum, String img, List<String> items, List<String> npcs, Map<String, String> directions, String location) {
        gm.getUi().createBackground(sceneNum, img, location);

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


//        if (sceneNum<15) {
//            createArrowButton(sceneNum, 0, 150, 50, 50, "img/left.png", "move", String.valueOf(sceneNum+1));
//        }
//        else {
//            createArrowButton(sceneNum, 0, 150, 50, 50, "img/left.png", "move", String.valueOf(1));
//        }
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
        createStartButton(550, 550, 200, 50, "start", "1");
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

    public JPanel getDirectionPanel() {
        return directionPanel;
    }

    public void setDirectionPanel(JPanel directionPanel) {
        this.directionPanel = directionPanel;
    }

    public JButton getNorthButton() {
        return northButton;
    }

    public void setNorthButton(JButton northButton) {
        this.northButton = northButton;
    }

    public JButton getSouthButton() {
        return southButton;
    }

    public void setSouthButton(JButton southButton) {
        this.southButton = southButton;
    }

    public JButton getEastButton() {
        return eastButton;
    }

    public void setEastButton(JButton eastButton) {
        this.eastButton = eastButton;
    }

    public JButton getWestButton() {
        return westButton;
    }

    public void setWestButton(JButton westButton) {
        this.westButton = westButton;
    }

    public JPanel getInvPanel() {
        return invPanel;
    }

    public void setInvPanel(JPanel invPanel) {
        this.invPanel = invPanel;
    }


    public JPanel getSettingPanel() {
        return settingPanel;
    }

    public void setSettingPanel(JPanel settingPanel) {
        this.settingPanel = settingPanel;
    }


    public JButton getInvButton() {
        return invButton;
    }

    public void setInvButton(JButton invButton) {
        this.invButton = invButton;
    }

    public JButton getEquipButton() {
        return equipButton;
    }

    public void setEquipButton(JButton equipButton) {
        this.equipButton = equipButton;
    }

    public JButton getSettingButton() {
        return settingButton;
    }

    public void setSettingButton(JButton settingButton) {
        this.settingButton = settingButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }

    public void setHelpButton(JButton helpButton) {
        this.helpButton = helpButton;
    }

    public JButton getInvBag() {
        return invBag;
    }

    public void setInvBag(JButton invBag) {
        this.invBag = invBag;
    }

    public JButton getEquipWeapon() {
        return equipWeapon;
    }

    public void setEquipWeapon(JButton equipWeapon) {
        this.equipWeapon = equipWeapon;
    }

    public JButton getSettingIcon() {
        return settingIcon;
    }

    public void setSettingIcon(JButton settingIcon) {
        this.settingIcon = settingIcon;
    }

    public JButton getHelpIcon() {
        return helpIcon;
    }

    public void setHelpIcon(JButton helpIcon) {
        this.helpIcon = helpIcon;
    }
}