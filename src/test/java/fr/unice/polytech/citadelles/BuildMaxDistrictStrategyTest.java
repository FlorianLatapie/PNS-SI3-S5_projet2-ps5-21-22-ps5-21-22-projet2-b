package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.strategy.BuildMaxDistrictStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuildMaxDistrictStrategyTest {
    static List<DistrictCard> districtCards;
    static List<DistrictCard> districtCardsV2;
    static List<DistrictCard> districtCardsImmutable;
    static List<DistrictCard> districtCardsImmutableV2;

    @BeforeAll
    static void init(){
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MONASTERY, 4));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));

        districtCardsImmutable = Collections.unmodifiableList(districtCards);

        districtCardsV2 = new ArrayList<>();
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.PALACE, 5));
        districtCardsV2.add(new DistrictCard(Color.RED, DistrictName.FORTRESS, 5));
        districtCardsV2.add(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 1));
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 3));

        districtCardsImmutableV2 = Collections.unmodifiableList(districtCardsV2);
    }

    @Test
    public void chooseCharacterTest(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player = new Player("Player 1", districtCardsImmutable, 2, new Random(), districtStrategy);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        CharacterCard characterMerchant = player.chooseCharacter(characterCardsOfTheRound);
        assertEquals(new CharacterCard(CharacterName.MERCHANT), characterMerchant);
    }

    @Test
    public void chooseCharacterTestWithMostColorsInHand(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player = new Player("Player 1", districtCardsImmutable, 100, new Random(), districtStrategy);

        for (DistrictCard card : districtCardsImmutable) {
            player.buildDistrictCardsInHand(card);
        }

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MERCHANT));

        CharacterCard character = player.chooseCharacter(characterCardsOfTheRound);
        //Takes the last character of the most common color in the hand
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), character);
    }

    @Test
    public void chooseCharacterTestWithMostColorsInHand2(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();

        Player player = new Player("Player 1", districtCardsImmutableV2, 100, new Random(), districtStrategy);

        player.buildDistrictCardsInHand(districtCardsImmutableV2.get(2));
        player.buildDistrictCardsInHand(districtCardsImmutableV2.get(3));

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MERCHANT));

        CharacterCard character = player.chooseCharacter(characterCardsOfTheRound);
        //Takes the last character of the most common color in the hand
        assertEquals(new CharacterCard(CharacterName.KING), character);
    }

    @Test
    public void chooseCharacterTestRandom(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player = new Player("Player 1", districtCardsImmutable, 100, mockRandom, districtStrategy);

        for (DistrictCard card : districtCardsImmutable) {
            player.buildDistrictCardsInHand(card);
        }

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MERCHANT));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.ASSASSIN));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.THIEF));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MAGICIAN));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.ARCHITECT));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.WARLORD));
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.BISHOP));

        assertEquals(characterCardsOfTheRound.get(0), player.chooseCharacter(characterCardsOfTheRound));
    }

    @Test
    public void getCoinsOverDrawingACardTest(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player1 = new Player("Player 1", districtCardsImmutable, 0, new Random(), districtStrategy);
        assertEquals(true, districtStrategy.getCoinsOverDrawingACard());

        List<DistrictCard> emptyList = new ArrayList<>();
        Player player2 = new Player("Player 2", emptyList, 0, new Random(), districtStrategy);
        assertEquals(false, districtStrategy.getCoinsOverDrawingACard());
    }

    @Test
    public void getTaxesAtBeginingOfTurnTest(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player = new Player("Player 1", districtCardsImmutable, 2, new Random(), districtStrategy);
        //Always true (part of the strategy)
        assertEquals(true, districtStrategy.getTaxesAtBeginingOfTurn());
    }

    @Test
    public void mostCommonColorInBuiltDistrictsTest(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Color color = districtStrategy.mostCommonColorInBuiltDistricts(districtCardsImmutable);
        assertEquals(Color.GREY, color);
    }

    @Test void getCheapestDistrictCardTest(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        DistrictCard districtCard = districtStrategy.getCheapestDistrictCard(districtCardsImmutable);
        //Gets the first cheapest card
        assertEquals(DistrictName.TAVERN, districtCard.getDistrictName());
    }

    @Test
    public void buildDistrict(){
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player1 = new Player("Player 1", districtCardsImmutable, 0, new Random(), districtStrategy);
        //Not enough coins
        assertEquals(false, districtStrategy.buildDistrict());

        List<DistrictCard> emptyList = new ArrayList<>();
        Player player2 = new Player("Player 2", emptyList, 0, new Random(), districtStrategy);
        //Empty List
        assertEquals(false, districtStrategy.buildDistrict());

        Player player3 = new Player("Player 3", districtCardsImmutable, 1, new Random(), districtStrategy);
        assertEquals(true, districtStrategy.buildDistrict());
    }

    @Test
    void equalsTest() {
        BuildMaxDistrictStrategy districtStrategy = new BuildMaxDistrictStrategy();
        Player player1 = new Player("Player 1", districtCardsImmutable, 2, new Random(), districtStrategy);
        districtStrategy.init(player1);

        BuildMaxDistrictStrategy districtStrategy2 = new BuildMaxDistrictStrategy();
        Player player2 = new Player("Player 2", districtCardsImmutable, 3, new Random(), districtStrategy);
        districtStrategy2.init(player2);

        assertEquals(districtStrategy, districtStrategy2);
        assertNotEquals(districtStrategy, 1); // wrong order of arguments to test the .equals method of p and not the other object
    }
}
