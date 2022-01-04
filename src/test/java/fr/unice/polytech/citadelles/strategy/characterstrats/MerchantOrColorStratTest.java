package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildMaxDistrictStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MerchantOrColorStratTest {
    static List<DistrictCard> districtCards;
    static List<DistrictCard> districtCardsV2;

    @BeforeAll
    static void init() {
        districtCards = new ArrayList<>();
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MONASTERY, 4));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.SHOP, 3));
        districtCards.add(new DistrictCard(Color.GREY, DistrictName.MARKET, 2));
        districtCards.add(new DistrictCard(Color.RED, DistrictName.TAVERN, 1));
        districtCards.add(new DistrictCard(Color.BLUE, DistrictName.JAIL, 1));

        districtCardsV2 = new ArrayList<>();
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.PALACE, 5));
        districtCardsV2.add(new DistrictCard(Color.RED, DistrictName.FORTRESS, 5));
        districtCardsV2.add(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 1));
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 3));
    }

    @Test
    void chooseCharacterTest() {
        CompleteStrategy buildMaxDistrictStrategy = new CompleteStrategy(new MerchantOrColorStrategy(), new BuildMaxDistrictStrategy());
        Player player = new Player("Player 1", districtCards, 2, new Random(), buildMaxDistrictStrategy);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        CharacterCard characterMerchant = player.chooseCharacter(characterCardsOfTheRound);
        assertEquals(new CharacterCard(CharacterName.MERCHANT), characterMerchant);
    }

    @Test
    void chooseCharacterTestWithMostColorsInHand() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new MerchantOrColorStrategy(), new BuildMaxDistrictStrategy());
        Player player = new Player("Player 1", districtCards, 100, new Random(), districtStrategy);

        for (DistrictCard card : districtCards) {
            player.buildDistrictCardsInHand(card);
        }

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MERCHANT));

        CharacterCard character = player.chooseCharacter(characterCardsOfTheRound);
        //Takes the last character of the most common color in the hand
        assertEquals(new CharacterCard(CharacterName.WARLORD), character);
    }

    @Test
    void chooseCharacterTestWithMostColorsInHand2() {
        CompleteStrategy districtStrategy = new CompleteStrategy(new MerchantOrColorStrategy(), new BuildMaxDistrictStrategy());

        // just to be sure that is NOT a random choice
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(-1);

        Player player = new Player("Player 1", districtCardsV2, 100, mockRandom, districtStrategy);
        player.buildDistrictCardsInHand(districtCardsV2.get(1));
        player.buildDistrictCardsInHand(districtCardsV2.get(2));
        player.buildDistrictCardsInHand(districtCardsV2.get(3));

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();
        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.MERCHANT));

        CharacterCard character = player.chooseCharacter(characterCardsOfTheRound);
        //Takes the last character of the most common color in the hand
        assertEquals(new CharacterCard(CharacterName.WARLORD), character);
    }

    @Test
    void chooseCharacterTestRandom() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        CompleteStrategy districtStrategy = new CompleteStrategy(new MerchantOrColorStrategy(), new BuildMaxDistrictStrategy());
        Player player = new Player("Player 1", districtCards, 100, mockRandom, districtStrategy);

        for (DistrictCard card : districtCards) {
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
    void equalsTest(){
        MerchantOrColorStrategy strat1 = new MerchantOrColorStrategy();
        MerchantOrColorStrategy strat2 = new MerchantOrColorStrategy();

        assertEquals(strat1, strat2);
    }

    @Test
    void toStringTest(){
        MerchantOrColorStrategy strat = new MerchantOrColorStrategy();

        assertEquals("Merchant or most common color Strategy",strat.toString());
    }

    @Test
    void hashCodeTest() {
        Random random = new Random();
        Player player = new Player("Player 1", districtCards, 200, random, new CompleteStrategy(new MerchantOrColorStrategy(), new BuildStrat()));
        assertEquals(Objects.hash(new MerchantOrColorStrategy().hashCode(), new BuildStrat().hashCode()), player.getStrategy().hashCode());
    }
}
