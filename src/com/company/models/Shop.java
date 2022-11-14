package com.company.models;

import com.company.client.GameMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Shop {

    private GameMain gm;
    private JPanel shop = new JPanel();
    private int qty;
    private int itemCost;
    private String itemSelected;
    private String economyText;
    //economy only influences prices on these items
    private String[] economyChoices = {"Mango", "Banana", "Cracker"};



    public Shop(GameMain gm) {
        this.gm = gm;

    }

    public void buildShop() {

        //this builds the shop window
        JButton addQty = new JButton("+ qty");
        addQty.setForeground(Color.black);
        addQty.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        addQty.setBounds(20,80,100,50);
        addQty.setBackground(Color.GRAY);

        addQty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setQty(getQty()+1);
                int itemsOwned = 0;
                if (gm.getPlayer().getInventory().contains(getItemSelected())){
                    for (String item:gm.getPlayer().getInventory()
                    ) {
                        if (item.equals(getItemSelected())){
                            itemsOwned = itemsOwned + 1;
                        }
                    }
                }
                gm.getUi().getMessageText().setText("Selecting " + getQty()+" "+ getItemSelected()+", at a current price of "+ getItemCost()+" each!\n"
                +"Currently you own "+ itemsOwned+"!");
            }
        });

        JButton subQty = new JButton("- qty");
        subQty.setForeground(Color.black);
        subQty.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        subQty.setBounds(20,130,100,50);
        subQty.setBackground(Color.GRAY);

        subQty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getQty()>1){
                    setQty(getQty()-1);
                    gm.getUi().getMessageText().setText("qty: " + getQty());
                }
            }
        });

        JButton buy = new JButton("Buy");
        buy.setForeground(Color.black);
        buy.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        buy.setBounds(20,180,100,50);
        buy.setBackground(Color.GRAY);

        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyItems();
            }
        });

        JButton sell = new JButton("Sell");
        sell.setForeground(Color.black);
        sell.setFont(gm.getUi().getOldRetro().deriveFont(Font.ITALIC, 15));
        sell.setBounds(20,230,100,50);
        sell.setBackground(Color.GRAY);

        sell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellItems();
            }
        });

        gm.getUi().getStorePanel().add(addQty);
        gm.getUi().getStorePanel().add(subQty);
        gm.getUi().getStorePanel().add(buy);
        gm.getUi().getStorePanel().add(sell);

        //this adds The 'Black Pearl' sword...
        //...it is not influenced by economy which is why this list is different from the global variable up top
        String[] marketChoices = { "Mango", "Banana", "Cracker", "Black Pearl"};

        final JComboBox<String> cb = new JComboBox<String>(marketChoices);
        cb.setMaximumSize(cb.getPreferredSize()); // added code
        cb.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
        cb.setBounds(20,20,150,50);
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setItemSelected(cb.getSelectedItem().toString());
                Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(itemSelected)).findFirst().orElse(null);
                setItemCost(itemInstance.getCost());
                gm.getUi().getMessageText().setText(itemSelected +" is currently priced at "+ itemInstance.getCost()+ " gold! "
                        +itemInstance.getQuote().get("market")+
                        "\nhow many do you want to buy or sell at this price?");
            }
        });

        gm.getUi().getStorePanel().add(cb);

    }


    public void shiftEconomy(){
        //loops through top variable economy items to change prices, simulates shipments coming and going
        for (String item:economyChoices
             ) {
            Item itemInstance = gm.getGame().getItems().stream().filter(itemFind -> itemFind.getName().equals(item)).findFirst().orElse(null);
            Random r = new Random();
            int randInt = r.nextInt(20-1) + 1;
            if (randInt>15){
                itemInstance.setCost(itemInstance.getCost()*2);
                itemInstance.getQuote().put("market", "This item is in demand and price has increased!");
            } else if (randInt<5){
                //this else if addresses items that drop to zero price... this makes sure an item is always worth at least one gold
                if (itemInstance.getCost()<=1){
                    itemInstance.setCost(2);
                }
                itemInstance.setCost((int) (itemInstance.getCost()*.5));
                itemInstance.getQuote().put("market", "This item is no longer in demand, price has decreased!");
            } else {
                itemInstance.getQuote().put("market", "This item has not seen a price change since the last shipment!");
            }
        }
    }

    public void buyItems() {
        int purchaseTotal = qty*itemCost;
        if (gm.getPlayer().getGold()>=purchaseTotal){
            gm.getPlayer().setGold(gm.getPlayer().getGold()-purchaseTotal);
            while (qty>0
                 ) {
                gm.getPlayer().getInventory().add(itemSelected);
                gm.getUi().getInventory().addElement(itemSelected);
                setQty(getQty()-1);
            }
            gm.getUi().getMessageText().setText("Thank you for your purchase!\nYour new gold balance: "+ gm.getPlayer().getGold());
        }
        else {
            gm.getUi().getMessageText().setText("You're too poor, go get more gold and come back later");
        }
    }

    public void sellItems() {
        int i=0;
        while (qty>0
            ) {
            //If an item is sold for over the player's quantity, this method ensures that player can not sell excess items
            if (gm.getPlayer().getInventory().contains(itemSelected)){
                i=i+1;
                gm.getPlayer().getInventory().remove(itemSelected);
                gm.getUi().getInventory().removeElement(itemSelected);
                gm.getPlayer().setGold(gm.getPlayer().getGold()+itemCost);
                gm.getUi().getMessageText().setText("Thank you for selling me "+ i +" "+itemSelected+ "(s)!\nYour new gold balance: "+ gm.getPlayer().getGold());
            }
            setQty(getQty()-1);
            }

        }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getItemCost() {
        return itemCost;
    }

    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(String itemSelected) {
        this.itemSelected = itemSelected;
    }


}