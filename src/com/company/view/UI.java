package com.company.view;

import com.company.client.GameMain;
import com.company.models.*;
import com.company.models.Character;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class UI {

    private JFrame window;
    private GameMain gm;
    private int windowHeight = 720;
    private int windowWidth = 1280;
    private JTextArea messageText, helpText;
    private Font oldRetro;
    private JPanel directionPanel, musicPanel, winPanel;
    private JButton northButton, southButton, eastButton, westButton;
    private JButton invButton, equipButton, settingButton, helpButton;
    private JPanel invPanel, settingPanel, statusPanel, textPanel, hpPanel, currentEquipPanel;
    private JButton invBag, equipWeapon, settingIcon, helpIcon;
    private JTextArea area, hp, currentWeapon, npcName;
    private JToggleButton musicToggle;
    private JComboBox musicStatus, soundFXStatus;
    private JLabel musicLabel, soundFxLabel;
    private JButton volumeUp, volumeDown;
    private ArrayList<JPanel> bgPanel = new ArrayList<>();
    private ArrayList<JLabel> bgLabel = new ArrayList<>();
    private JPanel playerBag = new JPanel();
    private JPanel gambleGame = new JPanel();
    private JPanel playerMap = new JPanel();
    private JPanel help = new JPanel();
    private JPanel settings = new JPanel();
    private JList<String> inventoryList = new JList<>();
    private DefaultListModel inventory = new DefaultListModel();
    private String selectedItem;
    private String musicFile = "pirate-music.wav";

    public UI(GameMain gm) {

        this.gm = gm;
        fontCreate();
        setWindowHeight((int) (getWindowHeight()));
        setWindowWidth((int) (getWindowWidth()));
        createMainField();
        generateSplashScene();
        window.setVisible(true);
        playerBag = eventPanel(300, 300, 400, 180, "playerBag");
        inventoryListBuilder();
        playerBag.setVisible(false);
        playerMap = eventPanel(100, 100, 400, 500, "playerMap");
        playerMap.setVisible(false);
        gambleGame = eventPanel(100, 100, 1000, 300, "gambleGame");
        gambleGame.setVisible(false);
        settings = eventPanel(700, 100, 400, 200, "settings");
        settings.setVisible(false);
        help = eventPanel(500, 100, 600, 400, "help");
        help.setVisible(false);
        settingMenuOption();
        helpOption();
    }

    public void createMap(String island, String gridSpace) {
        String fileName = "";
        switch (island) {
            case "mango":
                fileName = "img/map1.png";
                break;
            case "monkey":
                fileName = "img/map2.png";
                break;
            case "skull":
                fileName = "img/map3.png";
                break;
            default:
                break;
        }
        String yCoordinate = "A";
        int xAxis = 50;
        int yAxis = 0;


        List<String> moveLog = new ArrayList<>();
        moveLog.add(gridSpace);
        for (String s : moveLog
        ) {
            yCoordinate = s.substring(0, 1);
            xAxis = xAxis + (Integer.parseInt(s.substring(1, 2)) * 100);
        }

        switch (yCoordinate) {
            case "a":
                yAxis = 50;
                break;
            case "b":
                yAxis = 150;
                break;
            case "c":
                yAxis = 250;
                break;
            case "d":
                yAxis = 350;
                break;
            case "e":
                yAxis = 450;
                break;
            default:
                break;
        }

        JButton exitButton = new JButton("X");
        exitButton.setForeground(Color.black);
        exitButton.setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        exitButton.setOpaque(false);
        exitButton.setBounds(370, 0, 30, 30);
        exitButton.setBackground(Color.GRAY);
        exitButton.addActionListener(gm.getActionHandler());
        exitButton.setActionCommand("close playerMap");
        playerMap.add(exitButton);

        JLabel playerMarker = new JLabel();
        playerMarker.setBounds(xAxis - 37, yAxis - 37, 75, 75);
        playerMap.add(playerMarker);

        ImageIcon playerIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/mapMarker.png")));
        playerMarker.setIcon(playerIcon);

        JLabel locationMap = new JLabel();
        locationMap.setBounds(0, 0, 400, 500);
        playerMap.add(locationMap);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)));
        locationMap.setIcon(bgIcon);

    }


    private void fontCreate() {
        try {
            FileGetter fileGetter = new FileGetter();
            InputStream is = new BufferedInputStream(fileGetter.fileGetter("Press_Start_2P/PressStart2P-Regular.ttf"));
            Font retro = Font.createFont(Font.TRUETYPE_FONT, is);
            setOldRetro(retro.deriveFont(Font.PLAIN, 15));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }

    public void gameStateWindow(String bgImg, String iconImg, String message, String title) {
        window.setContentPane(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgImg)))));
        String[] options = {"Start", "Exit"};
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(iconImg)));
        String selected = (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        if (selected.equals("Start")) {
            window.dispose();
            gm.getMusic().stopMusic(getMusicFile());
            window = new JFrame();
            new GameMain();
        }
        if (selected.equals("Exit")) {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void createMainField() {
        window = new JFrame("A Pirates Tale");
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/gamegackground.jpg")))));
        window.setLayout(null);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
    }

    public void createMessageViewer() {
        setDirectionPanel(new JPanel());
        getDirectionPanel().setBounds((int) (.76 * windowWidth), (int) (.68 * windowHeight), (int) (.18 * windowWidth), (int) (.25 * windowHeight));
        getDirectionPanel().setBackground(Color.BLUE);
        getDirectionPanel().setLayout(null);

        setNorthButton(new JButton("N"));
        setSouthButton(new JButton("S"));
        setEastButton(new JButton("E"));
        setWestButton(new JButton("W"));

        getNorthButton().setBounds(100, 30, 30, 30);
        getSouthButton().setBounds(100, 120, 30, 30);
        getEastButton().setBounds(150, 75, 30, 30);
        getWestButton().setBounds(50, 75, 30, 30);
        getNorthButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getWestButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getEastButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getSouthButton().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getNorthButton().addActionListener(gm.getActionHandler());
        getNorthButton().setActionCommand("move north");
        getSouthButton().addActionListener(gm.getActionHandler());
        getSouthButton().setActionCommand("move south");
        getWestButton().addActionListener(gm.getActionHandler());
        getWestButton().setActionCommand("move west");
        getEastButton().addActionListener(gm.getActionHandler());
        getEastButton().setActionCommand("move east");
        getDirectionPanel().add(getSouthButton());
        getDirectionPanel().add(getEastButton());
        getDirectionPanel().add(getNorthButton());
        getDirectionPanel().add(getWestButton());
        getDirectionPanel().setOpaque(false);

        setInvPanel(new JPanel());
        getInvPanel().setBounds((int) (.05 * windowWidth), (int) (.68 * windowHeight), (int) (.07 * windowWidth), (int) (.25 * windowHeight));
        getInvPanel().setBackground(Color.BLUE);
        getInvPanel().setLayout(null);
        getInvPanel().setOpaque(false);
        setInvBag(new JButton());
        getInvBag().setBounds(10, 0, 72, 72);
        getInvBag().setOpaque(false);
        getInvBag().setContentAreaFilled(false);
        getInvBag().setBorderPainted(false);
        getInvPanel().add(getInvBag());
        ImageIcon bagIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/bag.png")));
        getInvBag().setIcon(bagIcon);
        getInvBag().addActionListener(gm.getActionHandler());
        getInvBag().setActionCommand("inventory");
        setEquipWeapon(new JButton());
        getEquipWeapon().setBounds(10, 80, 72, 72);
        getEquipWeapon().setOpaque(false);
        getEquipWeapon().setContentAreaFilled(false);
        getEquipWeapon().setBorderPainted(false);
        getInvPanel().add(getEquipWeapon());
        ImageIcon eqIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/mapMarker.png")));
        getEquipWeapon().setIcon(eqIcon);
        getEquipWeapon().addActionListener(gm.getActionHandler());
        getEquipWeapon().setActionCommand("map");

        setTextPanel(new JPanel());
        getTextPanel().setBounds((int) (.05 * windowWidth), (int) (.03 * windowHeight), (int) (.42 * windowWidth), (int) (.05 * windowHeight));
        getTextPanel().setBackground(Color.BLUE);
        getTextPanel().setLayout(null);
        getTextPanel().setOpaque(false);

        setHpPanel(new JPanel());
        getHpPanel().setBounds((int) (.48 * windowWidth), (int) (.03 * windowHeight), (int) (.06 * windowWidth), (int) (.05 * windowHeight));
        getHpPanel().setBackground(Color.BLUE);
        getHpPanel().setLayout(null);
        getHpPanel().setOpaque(false);

        setCurrentEquipPanel(new JPanel());
        getCurrentEquipPanel().setBounds((int) (.54 * windowWidth), (int) (.03 * windowHeight), (int) (.28 * windowWidth), (int) (.05 * windowHeight));
        getCurrentEquipPanel().setBackground(Color.BLUE);
        getCurrentEquipPanel().setLayout(null);
        getCurrentEquipPanel().setOpaque(false);

        setStatusPanel(new JPanel());
        getStatusPanel().setBounds((int) (.13 * windowWidth), (int) (.68 * windowHeight), (int) (.62 * windowWidth), (int) (.05 * windowHeight));
        getStatusPanel().setBackground(Color.BLUE);
        getStatusPanel().setLayout(null);
        getStatusPanel().setOpaque(false);

        setArea(new JTextArea());
        getArea().setBounds((int) (0 * windowWidth), (int) (.02 * windowHeight), (int) (.62 * windowWidth), (int) (.05 * windowHeight));
        getArea().setEditable(false);
        getArea().setLineWrap(true);
        getArea().setWrapStyleWord(true);
        getArea().setOpaque(false);
        getArea().setForeground(Color.black);
        getArea().setFont(getOldRetro().deriveFont(Font.ITALIC, 12));
        getTextPanel().add(getArea());

        setHp(new JTextArea());
        getHp().setBounds((int) (0 * windowWidth), (int) (.02 * windowHeight), (int) (.62 * windowWidth), (int) (.05 * windowHeight));
        getHp().setEditable(false);
        getHp().setLineWrap(true);
        getHp().setWrapStyleWord(true);
        getHp().setOpaque(false);
        getHp().setForeground(Color.black);
        getHp().setFont(getOldRetro().deriveFont(Font.ITALIC, 12));
        getHpPanel().add(getHp());

        setCurrentWeapon(new JTextArea());
        getCurrentWeapon().setBounds((int) (0 * windowWidth), (int) (.02 * windowHeight), (int) (.62 * windowWidth), (int) (.05 * windowHeight));
        getCurrentWeapon().setEditable(false);
        getCurrentWeapon().setLineWrap(true);
        getCurrentWeapon().setWrapStyleWord(true);
        getCurrentWeapon().setOpaque(false);
        getCurrentWeapon().setForeground(Color.black);
        getCurrentWeapon().setFont(getOldRetro().deriveFont(Font.ITALIC, 12));
        getCurrentEquipPanel().add(getCurrentWeapon());

        setNpcName(new JTextArea());
        getNpcName().setBounds((int) (0 * windowWidth), (int) (.02 * windowHeight), (int) (.62 * windowWidth), (int) (.05 * windowHeight));
        getNpcName().setEditable(false);
        getNpcName().setLineWrap(true);
        getNpcName().setWrapStyleWord(true);
        getNpcName().setOpaque(false);
        getNpcName().setForeground(Color.black);
        getNpcName().setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        getStatusPanel().add(getNpcName());

        setSettingPanel(new JPanel());
        getSettingPanel().setBounds((int) (.82 * windowWidth), (int) (0 * windowHeight), (int) (.10 * windowWidth), (int) (.08 * windowHeight));
        getSettingPanel().setBackground(Color.BLUE);
        getSettingPanel().setLayout(null);
        getSettingPanel().setOpaque(false);
        setSettingIcon(new JButton());
        getSettingIcon().setBounds(0, 0, 50, 50);
        getSettingIcon().setOpaque(false);
        getSettingIcon().setContentAreaFilled(false);
        getSettingIcon().setBorderPainted(false);
        getSettingPanel().add(getSettingIcon());
        ImageIcon sIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/gear.png")));
        getSettingIcon().setIcon(sIcon);
        getSettingIcon().addActionListener(gm.getActionHandler());
        getSettingIcon().setActionCommand("setting");
        setHelpIcon(new JButton());
        getHelpIcon().setBounds(60, 0, 50, 50);
        getHelpIcon().setOpaque(false);
        getHelpIcon().setContentAreaFilled(false);
        getHelpIcon().setBorderPainted(false);
        getSettingPanel().add(getHelpIcon());
        ImageIcon helpIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/help.png")));
        getHelpIcon().setIcon(helpIcon);
        getHelpIcon().addActionListener(gm.getActionHandler());
        getHelpIcon().setActionCommand("help");

        messageText = new JTextArea();
        messageText.setBounds((int) (.13 * windowWidth), (int) (.74 * windowHeight), (int) (.62 * windowWidth), (int) (.19 * windowHeight));
        messageText.setBackground(Color.BLUE);
        messageText.setForeground(Color.black);
        messageText.setOpaque(false);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(getOldRetro().deriveFont(Font.ITALIC, 12));

        window.add(getCurrentEquipPanel());
        window.add(getHpPanel());
        window.add(getTextPanel());
        window.add(getStatusPanel());
        window.add(getSettingPanel());
        window.add(getInvPanel());
        window.add(getDirectionPanel());
        window.add(messageText);
    }

    public void createBackground(int bgNum, String bgFileName, String target) {

        JPanel panel = new JPanel();
        setDirectionPanel(new JPanel());
        panel.setName(target);
        panel.setBounds((int) (.05 * windowWidth), (int) (.08 * windowHeight), (int) (.9 * windowWidth), (int) (.58 * windowHeight));
        panel.setLayout(null);
        panel.setOpaque(false);
        bgPanel.add(panel);
        window.add(panel);


        JLabel label = new JLabel();
        label.setName(String.valueOf(bgNum));
        label.setBounds(0, 0, (int) (.9 * windowWidth), (int) (.58 * windowHeight));
        bgLabel.add(label);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgFileName)));
        label.setIcon(bgIcon);
    }

    public void createSplashScreen(String bgFileName) {

        JPanel splash = new JPanel();
        splash.setBounds(0, 0, 1280, 720);
        splash.setBackground(Color.BLUE);
        splash.setLayout(null);
        splash.setName("Title");
        bgPanel.add(splash);
        window.add(splash);

        JLabel label = new JLabel();
        label.setBounds(0, 0, 1280, 720);
        bgLabel.add(label);

        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(bgFileName)));
        label.setIcon(bgIcon);
    }

    public void helpOption() {
        helpText = new JTextArea();
        helpText.setBounds(30, 20, 530, 360);
        //helpText.setBackground(Color.BLUE);
        helpText.setForeground(Color.black);
        helpText.setEditable(false);
        helpText.setLineWrap(true);
        helpText.setWrapStyleWord(true);
        helpText.setFont(getOldRetro().deriveFont(Font.ITALIC, 20));
        help.add(helpText);
    }

    public String textHelp() {
        String result = "   \n\n\n**  Left click to interact with object on the screen.\n\n" +
                "**   Gear icon to change music and sound effects.\n\n" +
                "**   Bag icon for inventory.\n\n" +
                "**   Directional movement N-North, W-West, S-South, E-East.\n";
        return result;
    }


    public void settingMenuOption() {
        setMusicPanel(new JPanel());
        getMusicPanel().setBounds(25, 5, 340, 180);
        getMusicPanel().setBackground(Color.white);
        String select[] = {"ON", "OFF"};
        setMusicStatus(new JComboBox(select));
        setSoundFXStatus(new JComboBox(select));
        setMusicLabel(new JLabel("Music"));
        setSoundFxLabel(new JLabel("SoundFX"));
        setVolumeDown(new JButton("Volume Down"));
        setVolumeUp(new JButton("Volume Up"));
        getVolumeDown().setBounds(80, 120, 150, 25);
        getVolumeUp().setBounds(80, 90, 150, 25);
        getVolumeDown().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.getMusic().volumeDown();
            }
        });
        getVolumeUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.getMusic().volumeUp();
            }
        });
        getMusicStatus().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String state = (String) getMusicStatus().getSelectedItem();
                if (state.equals("ON")) {
                    gm.getMusic().playMusic(getMusicFile());
                } else {
                    gm.getMusic().stopMusic(getMusicFile());
                }
            }
        });
        getSoundFXStatus().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String state = (String) getSoundFXStatus().getSelectedItem();
                if (state.equals("ON")) {
                    gm.getMusic().setFxOff(false);
                } else {
                    gm.getMusic().stopFx();
                }
            }
        });

        getMusicStatus().setFont(oldRetro.deriveFont(Font.ITALIC, 10));
        getSoundFXStatus().setFont(oldRetro.deriveFont(Font.ITALIC, 10));
        getMusicLabel().setFont(oldRetro.deriveFont(Font.ITALIC, 10));
        getSoundFxLabel().setFont(oldRetro.deriveFont(Font.ITALIC, 10));
        getMusicLabel().setBounds(80, 30, 75, 25);
        getSoundFxLabel().setBounds(80, 60, 75, 25);
        getMusicStatus().setBounds(150, 30, 85, 25);
        getSoundFXStatus().setBounds(150, 60, 85, 25);

        settings.add(getVolumeDown());
        settings.add(getVolumeUp());
        settings.add(getSoundFxLabel());
        settings.add(getMusicLabel());
        settings.add(getMusicStatus());
        settings.add(getSoundFXStatus());
        settings.add(musicPanel);
    }

    public JPanel eventPanel(int x, int y, int width, int height, String target) {
        JPanel panelBuilder = new JPanel();
        panelBuilder.setBounds(x, y, width, height);
        panelBuilder.setBackground(Color.white);
        panelBuilder.setLayout(null);
        panelBuilder.setName(target);
        panelBuilder.setOpaque(true);

        window.add(panelBuilder);

        JButton exitButton = new JButton("X");
        exitButton.setForeground(Color.black);
        exitButton.setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        exitButton.setOpaque(false);
        exitButton.setBounds(width - 30, 0, 30, 30);
        exitButton.setBackground(Color.GRAY);
        exitButton.addActionListener(gm.getActionHandler());
        exitButton.setActionCommand("close " + target);
        panelBuilder.add(exitButton);

        return panelBuilder;
    }


    public void inventoryListBuilder() {

        inventoryList.setModel(inventory);
        inventoryList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        inventoryList.setLayoutOrientation(JList.VERTICAL);
        inventoryList.setVisibleRowCount(-1);
        inventoryList.setBounds(50, 50, 250, 80);
        inventoryList.setOpaque(true);
        inventoryList.setFont(oldRetro.deriveFont(Font.ITALIC, 10));
        JScrollPane listScroller = new JScrollPane(inventoryList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem[] = new JMenuItem[4];
        menuItem[0] = new JMenuItem("drop");
        menuItem[0].addActionListener(gm.getActionHandler());
//        menuItem[0].setActionCommand("drop " + inventoryList.getSelectedValue());
        menuItem[0].setName(inventoryList.getSelectedValue());
        popupMenu.add(menuItem[0]);

        menuItem[1] = new JMenuItem("eat");
        menuItem[1].addActionListener(gm.getActionHandler());
        menuItem[1].setName(inventoryList.getSelectedValue());
        popupMenu.add(menuItem[1]);


        menuItem[2] = new JMenuItem("equip");
        menuItem[2].addActionListener(gm.getActionHandler());
        menuItem[2].setName(inventoryList.getSelectedValue());
        popupMenu.add(menuItem[2]);

        menuItem[3] = new JMenuItem("inspect");
        menuItem[3].addActionListener(gm.getActionHandler());
        menuItem[3].setName(inventoryList.getSelectedValue());
        popupMenu.add(menuItem[3]);

        ListSelectionListener listSelect = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    inventoryList.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (SwingUtilities.isLeftMouseButton(e)) {

                                menuItem[0].setActionCommand("drop " + inventoryList.getSelectedValue());

                                menuItem[1].setActionCommand("eat " + inventoryList.getSelectedValue());

                                menuItem[2].setActionCommand("equip " + inventoryList.getSelectedValue());

                                menuItem[3].setActionCommand("inspect " + inventoryList.getSelectedValue());

                                inventoryList.getSelectedValue();
                                popupMenu.show(inventoryList, e.getX(), e.getY());
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
                }
            }
        };

        inventoryList.addListSelectionListener(listSelect);

        playerBag.add(inventoryList);

    }


    private String selectItem() {
        return "drop " + getSelectedItem();
    }


    public void eventPanelClose(String name) {
        switch (name) {
            case "playerBag":
                playerBag.setVisible(false);
                break;
            case "playerMap":
                playerMap.setVisible(false);
                playerMap.removeAll();
                break;
            case "gambleGame":
                gambleGame.setVisible(false);
                break;
            case "settings":
                settings.setVisible(false);
                break;
            case "help":
                help.setVisible(false);
        }
    }

    public void createObject(int bgNum, int objX, int objY, int objWidth, int ObjHeight, String objFile, String type, String target) {
        String choice1 = null;
        String choice2 = null;
        String choice3 = null;
        String choice4 = null;
        int choices = 0;

        switch (type) {
            case "questItem":
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
            case "quest":
                choice1 = "talk";
                choice2 = "look";
                choice3 = "fight";
                choices = 3;
                break;
            case "enemy":
                choice1 = "look";
                choice2 = "fight";
                choices = 2;
                break;
            case "shop":
            case "gamble":
                choice1 = "talk";
                choice2 = "gamble";
                choice3 = "fight";
                choices = 3;
                break;
            case "boat":
                choice1 = "talk";
                choice2 = "sail Mango Island";
                choice3 = "sail Monkey Island";
                choice4 = "sail Skull Island";
                choices = 4;
                break;
            default:
                choice1 = "talk";
                choice2 = "look";
                choices = 2;
                break;
        }

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem[] = new JMenuItem[4];
        menuItem[0] = new JMenuItem(choice1);
        menuItem[0].addActionListener(gm.getActionHandler());
        menuItem[0].setActionCommand(choice1 + " " + target);
        popupMenu.add(menuItem[0]);

        menuItem[1] = new JMenuItem(choice2);
        menuItem[1].addActionListener(gm.getActionHandler());
        menuItem[1].setActionCommand(choice2 + " " + target);
        menuItem[1].setName(target);
        popupMenu.add(menuItem[1]);


        if (choices == 3) {
            menuItem[2] = new JMenuItem(choice3);
            menuItem[2].addActionListener(gm.getActionHandler());
            menuItem[2].setActionCommand(choice3 + " " + target);
            menuItem[2].setName(target);
            popupMenu.add(menuItem[2]);
        }

        if (choices == 4) {
            menuItem[2] = new JMenuItem(choice3);
            menuItem[2].addActionListener(gm.getActionHandler());
            menuItem[2].setActionCommand(choice3 + " " + target);
            menuItem[2].setName(target);
            popupMenu.add(menuItem[2]);

            menuItem[3] = new JMenuItem(choice4);
            menuItem[3].addActionListener(gm.getActionHandler());
            menuItem[3].setActionCommand(choice4 + " " + target);
            menuItem[3].setName(target);
            popupMenu.add(menuItem[3]);
        }

        if (type.equals("boat")){
//            menuItem[1] = new JMenuItem(choice2);
//            menuItem[1].addActionListener(gm.getActionHandler());
            menuItem[1].setActionCommand(choice2);
            menuItem[1].setName(target);
            //popupMenu.add(menuItem[1]);

//            menuItem[2] = new JMenuItem(choice3);
//            menuItem[2].addActionListener(gm.getActionHandler());
            menuItem[2].setActionCommand(choice3);
            menuItem[2].setName(target);
            //popupMenu.add(menuItem[2]);

//            menuItem[3] = new JMenuItem(choice4);
//            menuItem[3].addActionListener(gm.getActionHandler());
            menuItem[3].setActionCommand(choice4);
            menuItem[3].setName(target);
            //popupMenu.add(menuItem[3]);
        }


        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objX, objY, objWidth, ObjHeight);
        ImageIcon objectIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(objFile)));
        objectLabel.setIcon(objectIcon);
        objectLabel.setName(target);

        objectLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
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


    public void createArrowButton(int bgNum, int x, int y, int width, int height, String arrowFileName, String command, String target) {
        ImageIcon arrowIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(arrowFileName)));
        JButton arrowButton = new JButton();
        arrowButton.setBounds(x, y, width, height);
        arrowButton.setBackground(null);
        arrowButton.setContentAreaFilled(false);
        arrowButton.setFocusPainted(false);
        arrowButton.setBorderPainted(false);
        arrowButton.setIcon(arrowIcon);
        arrowButton.addActionListener(gm.getActionHandler());
        arrowButton.setActionCommand(command + " " + target);
        bgPanel.get(bgNum).add(arrowButton);
    }

    public void createStartButton(int x, int y, int width, int height, String command, String target) {
        JButton startButton = new JButton("Press Start");
        startButton.setForeground(Color.black);
        startButton.setFont(getOldRetro().deriveFont(Font.ITALIC, 15));
        startButton.setOpaque(false);
        startButton.setBounds(x, y, width, height);
        startButton.setBackground(Color.GRAY);
        startButton.addActionListener(gm.getActionHandler());
        startButton.setActionCommand(command + " " + target);
        bgPanel.get(0).add(startButton);

    }

    public void deleteObject(String name) {
        int panel = findPanelIndex(gm.getPlayer().getCurrentRoom());
        bgPanel.get(panel).remove(findLabelIndex(panel, name));
        gm.getSc().showScreen(panel);
    }

    public void addObject(String name) {
        int panel = findPanelIndex(gm.getPlayer().getCurrentRoom());
        Item itemBuild = gm.getGame().getItems().stream().filter(itemSeek -> itemSeek.getName().equals(name)).findFirst().orElse(null);
        gm.getUi().createObject(panel, itemBuild.getXaxis(), itemBuild.getYaxis(), itemBuild.getWidth(), itemBuild.getHeight(),
                itemBuild.getImg(), itemBuild.getType(), itemBuild.getName());
        gm.getSc().showScreen(panel);
        gm.getUi().bgPanel.get(panel).add(bgLabel.get(panel));
    }

    public int findLabelIndex(int panel, String name) {
        // find length of array
        int len = bgPanel.get(panel).getComponentCount();
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (bgPanel.get(panel).getComponent(i).getName().equals(name)) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }

    public int findPanelIndex(String name) {
        // find length of array

        int len = bgPanel.size();
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (bgPanel.get(i).getName().equals(name)) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }

    public void generate() {
        int i = 1;
        for (Location location : gm.getGame().getLocations()
        ) {
            generateScenes(i, location.getImg(), location.getItems(), location.getNPC(), location.getDirections(), location.getName());
            i++;
        }

    }

    public void generateScenes(int sceneNum, String img, List<String> items, List<String> npcs, Map<String, String> directions, String location) {
        gm.getUi().createBackground(sceneNum, img, location);

        for (String item : items
        ) {
            Item itemBuild = gm.getGame().getItems().stream().filter(itemSeek -> itemSeek.getName().equals(item)).findFirst().orElse(null);
            gm.getUi().createObject(sceneNum, itemBuild.getXaxis(), itemBuild.getYaxis(), itemBuild.getWidth(), itemBuild.getHeight(),
                    itemBuild.getImg(), itemBuild.getType(), itemBuild.getName());
        }

        for (String npc : npcs
        ) {
            Character characterBuild = gm.getGame().getCharacters().stream().filter(characterSeek -> characterSeek.getName().equals(npc)).findFirst().orElse(null);
            String type = characterBuild.getType();
//            if (characterBuild.isFriendly()){
//                type = "Friendly";
//            }
            gm.getUi().createObject(sceneNum, characterBuild.getXaxis(), characterBuild.getYaxis(), characterBuild.getWidth(), characterBuild.getHeight(),
                    characterBuild.getImg(), type, characterBuild.getName());
        }

        gm.getUi().bgPanel.get(sceneNum).add(bgLabel.get(sceneNum));

    }

    public void generateSplashScene() {
        createSplashScreen("img/splashscreen.png");
        createStartButton(550, 550, 200, 50, "start", "1");
        bgPanel.get(0).add(bgLabel.get(0));
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

    public JPanel getStatusPanel() {
        return statusPanel;
    }

    public void setStatusPanel(JPanel statusPanel) {
        this.statusPanel = statusPanel;
    }

    public JPanel getTextPanel() {
        return textPanel;
    }

    public void setTextPanel(JPanel textPanel) {
        this.textPanel = textPanel;
    }

    public JTextArea getArea() {
        return area;
    }

    public void setArea(JTextArea area) {
        this.area = area;
    }

    public JPanel getHpPanel() {
        return hpPanel;
    }

    public void setHpPanel(JPanel hpPanel) {
        this.hpPanel = hpPanel;
    }

    public JPanel getCurrentEquipPanel() {
        return currentEquipPanel;
    }

    public void setCurrentEquipPanel(JPanel currentEquipPanel) {
        this.currentEquipPanel = currentEquipPanel;
    }

    public JTextArea getHp() {
        return hp;
    }

    public void setHp(JTextArea hp) {
        this.hp = hp;
    }

    public JTextArea getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(JTextArea currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public JTextArea getNpcName() {
        return npcName;
    }

    public void setNpcName(JTextArea npcName) {
        this.npcName = npcName;
    }

    public DefaultListModel getInventory() {
        return inventory;
    }

    public void setInventory(DefaultListModel inventory) {
        this.inventory = inventory;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public JToggleButton getMusicToggle() {
        return musicToggle;
    }

    public void setMusicToggle(JToggleButton musicToggle) {
        this.musicToggle = musicToggle;
    }

    public String getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(String musicFile) {
        this.musicFile = musicFile;
    }

    public JPanel getMusicPanel() {
        return musicPanel;
    }

    public void setMusicPanel(JPanel musicPanel) {
        this.musicPanel = musicPanel;
    }

    public JComboBox getMusicStatus() {
        return musicStatus;
    }

    public void setMusicStatus(JComboBox musicStatus) {
        this.musicStatus = musicStatus;
    }

    public JComboBox getSoundFXStatus() {
        return soundFXStatus;
    }

    public void setSoundFXStatus(JComboBox soundFXStatus) {
        this.soundFXStatus = soundFXStatus;
    }

    public JLabel getMusicLabel() {
        return musicLabel;
    }

    public void setMusicLabel(JLabel musicLabel) {
        this.musicLabel = musicLabel;
    }

    public JLabel getSoundFxLabel() {
        return soundFxLabel;
    }

    public void setSoundFxLabel(JLabel soundFxLabel) {
        this.soundFxLabel = soundFxLabel;
    }

    public JButton getVolumeUp() {
        return volumeUp;
    }

    public void setVolumeUp(JButton volumeUp) {
        this.volumeUp = volumeUp;
    }

    public JButton getVolumeDown() {
        return volumeDown;
    }

    public void setVolumeDown(JButton volumeDown) {
        this.volumeDown = volumeDown;
    }

    public JPanel getGambleGame() {
        return gambleGame;
    }

    public void setGambleGame(JPanel gambleGame) {
        this.gambleGame = gambleGame;

    }

    public JTextArea getMessageText() {
        return messageText;
    }

    public void setMessageText(JTextArea messageText) {
        this.messageText = messageText;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public GameMain getGm() {
        return gm;
    }

    public void setGm(GameMain gm) {
        this.gm = gm;
    }

    public JTextArea getHelpText() {
        return helpText;
    }

    public void setHelpText(JTextArea helpText) {
        this.helpText = helpText;
    }

    public JPanel getWinPanel() {
        return winPanel;
    }

    public void setWinPanel(JPanel winPanel) {
        this.winPanel = winPanel;
    }

    public JPanel getPlayerBag() {
        return playerBag;
    }

    public void setPlayerBag(JPanel playerBag) {
        this.playerBag = playerBag;
    }

    public JPanel getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(JPanel playerMap) {
        this.playerMap = playerMap;
    }

    public JPanel getHelp() {
        return help;
    }

    public void setHelp(JPanel help) {
        this.help = help;
    }

    public JPanel getSettings() {
        return settings;
    }

    public void setSettings(JPanel settings) {
        this.settings = settings;
    }


    public JList<String> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(JList<String> inventoryList) {
        this.inventoryList = inventoryList;
    }
}