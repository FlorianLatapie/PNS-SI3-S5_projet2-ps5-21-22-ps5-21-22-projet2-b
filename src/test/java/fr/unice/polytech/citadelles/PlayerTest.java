package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player p;
    String player1 = "Player_1";
    List<DistrictCard> districtCards;


    @BeforeEach
    void setUp() {
        districtCards = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCards.add(new DistrictCard(i));
        }
        p = new Player(player1, districtCards);
    }

    @Test
    void districtCardTest() {
        assertEquals(p, new Player(player1, districtCards));
        assertNotEquals(p, 1); // wrong order of arguments to test the .equals method of p and not the other object
        assertEquals(p.getDistrictCardsInHand(), new Player(player1, districtCards).getDistrictCardsInHand());
    }

    @Test
    void buildDistrictCardTest() {
        Player pCopy = new Player(player1, p.getDistrictCardsInHand());
        pCopy.buildDistrictCardsInHand(p.getDistrictCardsInHand().get(0));
        assertNotEquals(p.getDistrictCardsInHand(), pCopy.getDistrictCardsInHand()); //check if card has been removed
        assertEquals(p.getDistrictCardsInHand().get(0), pCopy.getDistrictCardsBuilt().get(0)); //check if card has been build
    }

    @Test
    void receiveCoinsTest(){
        Player playerWith4Coins = new Player(player1, districtCards);
        playerWith4Coins.receiveCoins(2);
        assertEquals(4, playerWith4Coins.getCoins());
    }

    @Test
    public void removeCoinsTest(){
        Player playerWithNoCoins = new Player(player1, districtCards);
        playerWithNoCoins.removeCoins(2);
        assertEquals(0, playerWithNoCoins.getCoins());
    }

    @Test
    public void canBuildDistrictTest(){
        Player playerWith2Coins = new Player(player1, districtCards);
        assertEquals(true, playerWith2Coins.canBuildDistrict(new DistrictCard(2)));
    }

    @Test
    void chooseToBuildDistrictTest() {
        assertTrue((Boolean) p.chooseToBuildDistrict() instanceof Boolean);
    }

    @Test
    void getSumOfCardsBuiltTest() {
        assertEquals(0, p.getSumOfCardsBuilt());
        p.buildDistrictCardsInHand(districtCards.get(3));
        assertEquals(4, p.getSumOfCardsBuilt());
    }

    @Test
    void getNbOfPointsTest() {
        assertEquals(0, p.getNbOfPoints());
        p.buildDistrictCardsInHand(districtCards.get(3));
        assertEquals(4, p.getNbOfPoints());
    }
}