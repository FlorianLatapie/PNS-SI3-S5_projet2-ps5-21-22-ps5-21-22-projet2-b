package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.RandomStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class RandomStrategyTest {
    List<DistrictCard> districtCards;
    @BeforeEach
    void init(){
        districtCards = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            districtCards.add(new DistrictCard(Color.GREY, DistrictName.NONE, i));
        }

    }

    @Test
    void chooseCharacterTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        RandomStrategy spy = spy(new RandomStrategy());
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> cardsOfTheRound = doc.getNewCharacterCards();
        assertEquals(new DeckOfCards().getNewCharacterCards().get(0), player.chooseCharacter(cardsOfTheRound));
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), player.getCharacterCard());

        verify(spy, times(1)).chooseCharacter(anyList());
    }

    @Test
    void getCoinsOverDrawingACardTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        RandomStrategy spy = spy(new RandomStrategy());
        Player player = new Player("Player 1", districtCards, 2, mockRandom, spy);

       assertTrue(player.chooseCoinsOverDrawingACard());
       assertFalse(player.chooseCoinsOverDrawingACard());

        verify(spy, times(2)).getCoinsOverDrawingACard();
    }

    @Test
    void getTaxesAtBeginingOfTurnTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        RandomStrategy spy = spy(new RandomStrategy());
        Player player = new Player("Player 1", districtCards, 2, mockRandom,  spy);

        assertTrue(player.chooseToGetTaxesAtBeginingOfTurn());
        assertFalse(player.chooseToGetTaxesAtBeginingOfTurn());

        verify(spy, times(2)).getTaxesAtBeginingOfTurn();
    }

    @Test
    void chooseToBuildDistrictTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextBoolean()).thenReturn(true, false);

        List<DistrictCard> dc = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        RandomStrategy spy = spy(new RandomStrategy());
        Player player = new Player("Player 1", dc, 200, mockRandom, spy);

        List<DistrictCard> expectedBuilt = List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1));

        assertTrue(player.chooseToBuildDistrict());
        assertEquals(expectedBuilt, player.getDistrictCardsBuilt());
        assertTrue(player.getDistrictCardsInHand().isEmpty());
        assertFalse(player.chooseToBuildDistrict());

        verify(spy, times(2)).buildDistrict();
    }

    @Test
    void equalsTest() {
        Player mockPlayer = mock(Player.class);
        Random r = new Random();
        when(mockPlayer.getRandom()).thenReturn(r);

        RandomStrategy randomStrategy = new RandomStrategy();
        randomStrategy.init(mockPlayer);

        RandomStrategy randomStrategy2 = new RandomStrategy();
        randomStrategy2.init(mockPlayer);

        assertEquals(randomStrategy, randomStrategy2);
        assertNotEquals(randomStrategy, 1); // wrong order of arguments to test the .equals method of p and not the other object
    }
}
