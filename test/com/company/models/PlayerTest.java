package com.company.models;

import com.company.client.GameMain;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.stream.Collectors;

public class PlayerTest extends TestCase {

    GameMain gm = new GameMain();


    @Test
    public void testUseItem() {
        String direction = "south";
        gm.getSc().screenPicker(direction);
        gm.getPlayer().setCurrentRoom("Mango Jungle North");
        gm.getPlayer().useItem("Mango");
        assertEquals(15, gm.getPlayer().getHp());
    }

    @Test
    public void testGrabItemWhenNoReq(){
        String direction = "south";
        gm.getSc().screenPicker(direction);
        gm.getPlayer().grabItem("Mango");
        ArrayList<String> result = new ArrayList<>();
        result.add("Mango");
        assertEquals(result, gm.getPlayer().getInventory());
    }

    @Test(expected = EmptyStackException.class)
    public  void testFileInputStreamIfEmptyException() throws IOException {
        FileGetter emptyStream = new FileGetter();
        emptyStream.fileGetter("");
    }

    @Test
    public void testFileNotEmpty(){
        FileGetter fileGetter = new FileGetter();
        String expected = "Press_Start_2P/PressStart2P-Regular.ttf";
        InputStream is = new BufferedInputStream(fileGetter.fileGetter("test.text"));
        String result = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));

        assertEquals(expected,result);
    }

    @Test
    public void testDropItem(){
        String direction = "south";
        gm.getSc().screenPicker(direction);
        gm.getPlayer().setCurrentRoom("Mango Jungle North");
        System.out.println(gm.getPlayer().getInventory());
        gm.getPlayer().grabItem("Mango");
        System.out.println("grabbed Mango");
        System.out.println(gm.getPlayer().getInventory());
        ArrayList<String> inv = new ArrayList<>();
        String direction1 = "north";
        gm.getSc().screenPicker(direction1);
        gm.getPlayer().dropItem("Mango");
        System.out.println("dropped Mango");
        System.out.println(gm.getPlayer().getInventory());
        assertEquals(inv, gm.getPlayer().getInventory());

    }

    }