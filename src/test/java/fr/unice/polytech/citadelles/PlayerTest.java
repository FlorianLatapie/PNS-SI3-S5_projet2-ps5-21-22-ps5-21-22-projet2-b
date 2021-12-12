package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;
import fr.unice.polytech.citadelles.strategy.RandomStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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
    void equalsTest() {
        assertEquals(p, new Player(player1, districtCards, Integer.MAX_VALUE, rand));
        assertNotEquals(p, 1); // wrong order of arguments to test the .equals method of p and not the other object
        assertEquals(p.getDistrictCardsInHand(), new Player(player1, districtCards).getDistrictCardsInHand());
    }

    @Test
    void hashCodeTest() {
        assertNotNull( new Player(player1, districtCards, Integer.MAX_VALUE, new Random()).hashCode());
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
    void receiveCardTest() {
        Player player = new Player(player1);
        player.receiveCard(new DistrictCard(Color.GREY, DistrictName.NONE, 2));
        assertEquals(List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 2)), player.getDistrictCardsInHand());
        assertEquals(1, player.getDistrictCardsInHand().size());
    }

    @Test
    void removeCoinsTest() {
        Player playerWithNoCoins = new Player(player1, districtCards);
        playerWithNoCoins.removeCoins(2);
        assertEquals(0, playerWithNoCoins.getCoins());

        Player hasToThrowError = new Player(player1, districtCards);
        Exception exception = assertThrows(Exception.class, () -> hasToThrowError.removeCoins(Integer.MAX_VALUE));
        assertEquals("It is impossible to remove coins because the player Player_1 does not have enough coins: 2-2147483647 = -2147483645 is less than 0", exception.getMessage());
    }

    @Test
    void canBuildDistrictTest() {
        Player playerWith2Coins = new Player(player1, districtCards);
        assertTrue(playerWith2Coins.canBuildDistrict(new DistrictCard(Color.GREY, DistrictName.NONE, 2)));
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

        assertEquals(new DistrictCard(Color.GREY, DistrictName.NONE, 1), playerWithCoins.chooseToBuildDistrict());
        assertEquals(expectedBuilt, playerWithCoins.getDistrictCardsBuilt());
        assertTrue(playerWithCoins.getDistrictCardsInHand().isEmpty());

        Player playerWithNoDistrictCards = new Player(player1, new ArrayList<>());
        assertNull(playerWithNoDistrictCards.chooseToBuildDistrict());
    }

    @Test
    void chooseToBuildDistrictTest2() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        List<DistrictCard> districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player playerWithNoCoins = new Player("1", districtCards, 0, mockRandom);

        assertNull(playerWithNoCoins.chooseToBuildDistrict());
        assertTrue(playerWithNoCoins.getDistrictCardsBuilt().isEmpty());
        assertFalse(playerWithNoCoins.getDistrictCardsInHand().isEmpty());
    }

    @Test
    void drawADistrictCardTest(){
        List<DistrictCard> expectedCardInHand = new ArrayList<>();
        expectedCardInHand.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        Player p = new Player("Player");
        assertEquals(0, p.getDistrictCardsInHand().size());
        assertTrue(p.drawADistrictCard(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));

        assertEquals(1, p.getDistrictCardsInHand().size());
        assertEquals(expectedCardInHand, p.getDistrictCardsInHand());
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
        Exception exception = assertThrows(Exception.class, () -> p.chooseCharacter(new ArrayList<>()));
        assertEquals("Character deck of card is empty, Player_1 cannot choose a card", exception.getMessage());
    }

    @Test
    void toStringTest() {
        assertEquals("Player Player_1{" + System.lineSeparator() +
                "districtCardsInHand=[NONE(1 coin, GREY), NONE(2 coins, GREY), NONE(3 coins, GREY), NONE(4 coins, GREY)]," + System.lineSeparator() +
                "districtCardsBuilt=[]," + System.lineSeparator() +
                "destroyedDistricts=[]," + System.lineSeparator() +
                "coins=2147483647," + System.lineSeparator() +
                "random=" + rand.toString() + "," + System.lineSeparator() +
                "characterCard=null," + System.lineSeparator() +
                "strategy=RandomStrategy," + System.lineSeparator() +
                "bonusPoints=0" + System.lineSeparator() +
                "}", p.toString());
    }

    @Test
    void isAllowedToBuildDistrictTest() {
        assertTrue(p.isAllowedToBuildDistrict(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 10)));

        p.buildDistrictCardsInHand(p.getDistrictCardsInHand().get(0));
        assertFalse(p.isAllowedToBuildDistrict(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));
    }

    @Test
    void warlordChooseDistrictToDestroyTest(){
        Player player = new Player("player1");
        Player warlord = new Player("warlord", new ArrayList<DistrictCard>(), 2);
        List<DistrictCard> dc = new ArrayList<>();
        dc.add(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 2));
        player.setDistrictCardsBuilt(dc);
        assertEquals(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 2), warlord.warlordChooseDistrictToDestroy(player));
        warlord.removeCoins(2);
        assertNull(warlord.warlordChooseDistrictToDestroy(player));
    }

    @Test
    void numberOfDistrictCardsBuiltByColor(){
        List<DistrictCard> dc = new ArrayList<>();
        dc.add(new DistrictCard(Color.BLUE, DistrictName.CHURCH, 2));

        Player player = new Player("Player 1",dc);
        player.buildDistrictCardsInHand(dc.get(0));
        PlayerTools playerTools = new PlayerTools(player);

        Map<Color, Integer> expected = new HashMap<>();
        expected.put(Color.GREY, 0);
        expected.put(Color.BLUE, 1);
        expected.put(Color.RED, 0);
        expected.put(Color.GREEN, 0);
        expected.put(Color.YELLOW, 0);
        expected.put(Color.PURPLE, 0);

        assertEquals(expected, playerTools.numberOfDistrictCardsBuiltByColor());
    }

    @Test
    void addPointsTest(){
        Player p = new Player("a");
        assertEquals(0 , p.getNbOfPoints());
        p.addPoints(4);
        assertEquals(4 , p.getNbOfPoints());
    }

    @Test
    void chooseToRepairDistrictTest(){
        RandomStrategy mockStrat = mock(RandomStrategy.class);
        DistrictCard destroyedDistrict = new DistrictCard(Color.GREY, DistrictName.NONE, 1);
        when(mockStrat.repairDistrict(anyList())).thenReturn(null, destroyedDistrict);
        Player p = new Player("a", new ArrayList<>(), 2, new Random(), mockStrat);
        p.setDestroyedDistricts(new ArrayList<>(List.of(destroyedDistrict)));
        p.setCharacterCard(new CharacterCard(CharacterName.THIEF));

        assertNull(p.chooseToRepairDistrict());

        assertEquals(destroyedDistrict, p.chooseToRepairDistrict());
        assertTrue(p.getDestroyedDistricts().isEmpty());
        assertEquals(List.of(destroyedDistrict),p.getDistrictCardsBuilt());

        p.removeCoins(1);

        assertNull(p.chooseToRepairDistrict());
    }

    @Test
    void chooseToRepairDistrictTest2(){
        RandomStrategy mockStrat = mock(RandomStrategy.class);
        DistrictCard destroyedDistrict = new DistrictCard(Color.GREY, DistrictName.NONE, 1);
        when(mockStrat.repairDistrict(anyList())).thenReturn(null, destroyedDistrict);
        Player p = new Player("Player", new ArrayList<>(), 2, new Random(), mockStrat);
        p.setDestroyedDistricts(new ArrayList<>(List.of(destroyedDistrict)));

        Exception exception = assertThrows(Exception.class, () -> p.chooseToRepairDistrict());
        assertEquals("Player has no character card, therefore he cannot repair a district", exception.getMessage());

        p.setCharacterCard(new CharacterCard(CharacterName.WARLORD));

        assertNull(p.chooseToRepairDistrict());
    }
}