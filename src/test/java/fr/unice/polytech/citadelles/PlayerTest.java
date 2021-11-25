package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerTest {
    Random rand;
    Player p;
    String player1 = "Player_1";
    List<DistrictCard> districtCards;


    @BeforeEach
    void setUp() {
        districtCards = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, i));
        }
        rand = new Random();
        p = new Player(player1, districtCards, Integer.MAX_VALUE, rand);
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
    void receiveCoinsTest() {
        Player playerWith4Coins = new Player(player1, districtCards);
        playerWith4Coins.receiveCoins(2);
        assertEquals(4, playerWith4Coins.getCoins());
    }

    @Test
    void removeCoinsTest() {
        Player playerWithNoCoins = new Player(player1, districtCards);
        playerWithNoCoins.removeCoins(2);
        assertEquals(0, playerWithNoCoins.getCoins());

        Player hasToThrowError = new Player(player1, districtCards);
        assertThrows(RuntimeException.class, () -> hasToThrowError.removeCoins(Integer.MAX_VALUE + 1));
    }

    @Test
    void canBuildDistrictTest() {
        Player playerWith2Coins = new Player(player1, districtCards);
        assertEquals(true, playerWith2Coins.canBuildDistrict(new DistrictCard(Color.GREY, DistrictName.NONE, 2)));
    }

    @Test
    void chooseToBuildDistrictTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        List<DistrictCard> districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        List<DistrictCard> expectedBuilt = new ArrayList<>();
        expectedBuilt.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player playerWithCoins = new Player("1", districtCards, 100, mockRandom);

        assertTrue(playerWithCoins.chooseToBuildDistrict());
        assertEquals(expectedBuilt, playerWithCoins.getDistrictCardsBuilt());
        assertTrue(playerWithCoins.getDistrictCardsInHand().isEmpty());
    }

    @Test
    void chooseToBuildDistrictTest2() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        List<DistrictCard> districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player playerWithNoCoins = new Player("1", districtCards, 0, mockRandom);

        assertFalse(playerWithNoCoins.chooseToBuildDistrict());
        assertTrue(playerWithNoCoins.getDistrictCardsBuilt().isEmpty());
        assertFalse(playerWithNoCoins.getDistrictCardsInHand().isEmpty());
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


    @Test
    void chooseCharacterTest() {
        assertThrows(RuntimeException.class, () -> {
            p.chooseCharacter(new ArrayList<>());
        });

        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> cardsOfTheRound = doc.getNewCharacterCards();
        Player playerWithMock = new Player(player1, districtCards, 2, mockRandom);

        assertEquals(new DeckOfCards().getNewCharacterCards().get(0), playerWithMock.chooseCharacter(cardsOfTheRound));
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), playerWithMock.getCharacterCard());
    }

    @Test
    void chooseToGetTaxesAtBeginingOfTurnTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        Player playerWithMock = new Player(player1, districtCards, 2, mockRandom);
        assertTrue(playerWithMock.chooseToGetTaxesAtBeginingOfTurn());
        assertFalse(playerWithMock.chooseToGetTaxesAtBeginingOfTurn());
    }

    @Test
    void toStringTest() {
        assertEquals("Player Player_1{" + System.lineSeparator() +
                "districtCardsInHand=[NONE(1 coin, GREY), NONE(2 coins, GREY), NONE(3 coins, GREY), NONE(4 coins, GREY)]," + System.lineSeparator() +
                "districtCardsBuilt=[]," + System.lineSeparator() +
                "coins=2147483647," + System.lineSeparator() +
                "random=" + rand.toString() + "," + System.lineSeparator() +
                "characterCard=null" + System.lineSeparator() +
                "}", p.toString());
    }
}