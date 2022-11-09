package com.company.models;

import com.company.client.GameMain;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.stream.Collectors;

public class PlayerTest extends TestCase {

    GameMain gm = new GameMain();
    Player p1 = new Player(gm);
    Game game = new Game(gm);


    @Test
    public void testUseItem() {
        String direction = "south";
        gm.sc.screenPicker(direction);
        p1.setCurrentRoom("Mango Jungle North");
        System.out.println(gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(p1.getCurrentRoom())).findFirst().orElse(null).getName());
        System.out.println(gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(p1.getCurrentRoom())).findFirst().orElse(null).getItems());
        System.out.println(p1.hp);
        p1.useItem("Mango");
        System.out.println("used Mango");
        System.out.println(gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(p1.getCurrentRoom())).findFirst().orElse(null).getItems());
        System.out.println(p1.hp);
        assertEquals(15,p1.hp);
    }

    @Test
    public void testGrabItemWhenNoReq(){
        String direction = "south";
        gm.sc.screenPicker(direction);
        System.out.println(gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(p1.getCurrentRoom())).findFirst().orElse(null).getName());
        System.out.println(gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(p1.getCurrentRoom())).findFirst().orElse(null).getItems());
        System.out.println(p1.getInventory());
        p1.grabItem("Mango");
        System.out.println("grabbed Mango");
        System.out.println(gm.getGame().locations.stream().filter(locationFind -> locationFind.getName().equals(p1.getCurrentRoom())).findFirst().orElse(null).getItems());
        System.out.println(p1.getInventory());
        ArrayList<String> result = new ArrayList<>();
        result.add("Mango");
        System.out.println(result);
        System.out.println(p1.getInventory());
        assertEquals(result, p1.getInventory());
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
}