package com.company.view;

import com.company.client.GameMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class UI {

    JFrame window;
    GameMain gm;
    double resolutionChanger;
    int windowHeight = 720;
    int windowWidth = 1280;
    public JTextArea messageText;
    public JPanel bgPanel[] = new JPanel[3];
    public JLabel bgLabel[] = new JLabel[10];


    public UI(GameMain gm, double resolutionChanger) {

        this.gm = gm;
        setResolutionChanger(resolutionChanger);
        setWindowHeight((int) (getWindowHeight()*getResolutionChanger()));
        setWindowWidth((int) (getWindowWidth()*getResolutionChanger()));
        createMainField();
        //createMessageViewer();
        generateSplashScene();
        generateScene();
        window.setVisible(true);
    }

    public void createMainField(){
        window = new JFrame();
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
//        int x = 50;
//        int y = 410;


    }

    public void createMessageViewer(){
        messageText = new JTextArea("Welcome to the Jungle!");
        messageText.setBounds((int) (.05*windowWidth), (int) (.68*windowHeight), (int) (.9*windowWidth), (int) (.25*windowHeight));
        messageText.setBackground(Color.BLUE);
        messageText.setForeground(Color.WHITE);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Book Antiqua", Font.PLAIN, 26));
        window.add(messageText);
    }

    public void createBackground(int bgNum, String bgFileName){

        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds((int) (.05*windowWidth),(int) (.08*windowHeight),(int) (.9*windowWidth),(int) (.58*windowHeight));
        bgPanel[bgNum].setBackground(Color.BLUE);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);

        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0,0,(int) (.9*windowWidth),(int) (.58*windowHeight));

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgFileName)));
        bgLabel[bgNum].setIcon(bgIcon);


    }

    public void createSplashScreen(int bgNum, String bgFileName){

        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds(0, 0, 1280, 720);
        bgPanel[bgNum].setBackground(Color.BLUE);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);

        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0,0,1280, 720);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgFileName)));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public void createObject(int bgNum, int objX, int objY, int objWidth, int ObjHeight, String objFile, String choice1, String choice2, String choice3, String target){

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

        menuItem[2] = new JMenuItem(choice3);
        menuItem[2].addActionListener(gm.aHandler);
        menuItem[2].setActionCommand(choice3 +" "+ target);
        menuItem[2].setName(target);
        popupMenu.add(menuItem[2]);


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

        bgPanel[bgNum].add(objectLabel);
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

        bgPanel[bgNum].add(arrowButton);
    }

    public void createStartButton(int bgNum, int x, int y, int width, int height, String command, String target){
        //ImageIcon startIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(arrowFileName)));
        JButton startButton = new JButton("Press Start");
        startButton.setForeground(Color.black);
        //startButton.setFont(oldRetro);
        startButton.setOpaque(true);
        startButton.setBounds(x,y,width,height);
        startButton.setBackground(null);
        //startButton.setContentAreaFilled(false);
        //startButton.setFocusPainted(false);
        //startButton.setBorderPainted(false);
        //startButton.setIcon(startIcon);
        startButton.addActionListener(gm.aHandler);
        startButton.setActionCommand(command + " " + target);
        bgPanel[bgNum].add(startButton);

    }




    public void generateScene(){


        createBackground(1, "img/jungle.png");
        createObject(1, 970, 140, 150, 225, "img/parrot.png", "eat", "grab", "talk", "parrot");
        createObject(1, 70, 80, 264, 400, "img/uncle.png", "talk", "fight", "trade", "uncle");
        createArrowButton(1,0,150,50,50,"img/left.png", "move", "2");
        bgPanel[1].add(bgLabel[1]);

        createBackground(2, "img/village.png");
        //createObject(1, 970, 140, 150, 225, "parrot.png", "eat", "grab", "talk", "parrot");
        //createObject(1, 70, 80, 264, 400, "uncle.png", "talk", "fight", "trade", "uncle");
        createArrowButton(2,0,150,50,50,"img/left.png", "move", "1");
        bgPanel[2].add(bgLabel[2]);
    }

    public void generateSplashScene(){
        createSplashScreen(0, "img/splashscreen.png");
        createStartButton(0, 550, 550, 150, 50, "start", "1");
        bgPanel[0].add(bgLabel[0]);
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
}