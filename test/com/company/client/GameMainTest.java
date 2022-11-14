package com.company.client;

import com.company.models.Game;
import com.company.models.Player;
import junit.framework.TestCase;

public class GameMainTest extends TestCase {


    public void testGetPlayerName() {
        GameMain gm = new GameMain();
        assertEquals("wilson", gm.getPlayer().getPlayerName());
    }

    public void testConstructorCreatesCharacters() {
        GameMain gm = new GameMain();
        assertEquals("Sad Traveler", gm.getGame().getCharacters().get(0).getName());

    }
}