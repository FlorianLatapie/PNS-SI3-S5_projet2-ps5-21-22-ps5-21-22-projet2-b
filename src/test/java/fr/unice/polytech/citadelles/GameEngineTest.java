package fr.unice.polytech.citadelles;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameEngineTest {


    @Test
    void askToChooseCharacterTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(),anyInt())).thenReturn(0);
        GameEngine ge = new GameEngine(4, mockRandom);

        DeckOfCards doc = new DeckOfCards();

        List<CharacterCard> listChar = doc.getNewCharacterCards();
        assertEquals(new CharacterCard(CharacterName.ASSASSIN), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
        assertEquals(new CharacterCard(CharacterName.THIEF), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
        assertEquals(new CharacterCard(CharacterName.MAGICIAN), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
        assertEquals(new CharacterCard(CharacterName.KING), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
        assertEquals(new CharacterCard(CharacterName.BISHOP), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
        assertEquals(new CharacterCard(CharacterName.MERCHANT), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
        assertEquals(new CharacterCard(CharacterName.ARCHITECT), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
        assertEquals(new CharacterCard(CharacterName.WARLORD), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));

        assertThrows(IllegalArgumentException.class, () -> ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));

    }

    @Test
    void sortPlayerListByCharacterSequenceTest(){
        Player player1 = new Player("Player_1", List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));
        Player player2 = new Player("Player_2", List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));
        Player player3 = new Player("Player_3", List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));
        Player player4 = new Player("Player_4", List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));


        player1.setCharacterCard(new CharacterCard(CharacterName.ARCHITECT));
        player2.setCharacterCard(new CharacterCard(CharacterName.BISHOP));
        player3.setCharacterCard(new CharacterCard(CharacterName.THIEF));
        player4.setCharacterCard(new CharacterCard(CharacterName.ASSASSIN));


        GameEngine ge = new GameEngine(new Random(), player1, player2, player3, player4);
        assertEquals(List.of(player4,player3,player2,player1), ge.sortPlayerListByCharacterSequence());

    }





}