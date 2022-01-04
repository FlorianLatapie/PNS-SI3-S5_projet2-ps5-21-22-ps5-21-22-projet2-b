package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.character.Thief;
import fr.unice.polytech.citadelles.player.Player;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThiefTest {
    @Test
    void giveMoneyToThiefTest() {
        Player player = new Player("player");
        Player player2 = new Player("player2");

        GameEngine ge = new GameEngine(new Random(), player, player2);

        player2.receiveCoins(3);

        assertEquals(2, player.getCoins());
        assertEquals(5, player2.getCoins());

        new Thief(ge).giveMoneyToThief(player, player2);

        assertEquals(7, player.getCoins());
        assertEquals(5, player2.getCoins());
    }
}
