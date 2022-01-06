package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.CompleteStrategy;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OffensiveStrategyTest {

    @Test
    void chooseCharacterTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);
        OffensiveStrategy strat = new OffensiveStrategy();
        Player player = new Player("Player", mockRandom, new CompleteStrategy(strat, new BuildStrat()));

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardList = doc.getNewCharacterCards();

        assertEquals(new CharacterCard(CharacterName.ASSASSIN), strat.chooseCharacter(characterCardList));

        characterCardList.remove(new CharacterCard(CharacterName.ASSASSIN));

        assertEquals(new CharacterCard(CharacterName.WARLORD), strat.chooseCharacter(characterCardList));

        characterCardList.remove(new CharacterCard(CharacterName.WARLORD));

        assertEquals(new CharacterCard(CharacterName.MAGICIAN), strat.chooseCharacter(characterCardList));

        characterCardList.remove(new CharacterCard(CharacterName.MAGICIAN));

        assertEquals(new CharacterCard(CharacterName.KING), strat.chooseCharacter(characterCardList));

        characterCardList.remove(new CharacterCard(CharacterName.KING));

        assertEquals(new CharacterCard(CharacterName.THIEF), strat.chooseCharacter(characterCardList));
    }

    @Test
    void killCharacterCardTest(){
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(),anyInt())).thenReturn(0);
        OffensiveStrategy strat = new OffensiveStrategy();
        Player player = new Player("Player", mockRandom, new CompleteStrategy(strat, new BuildStrat()));

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardList = doc.getNewCharacterCards();

        assertEquals(new CharacterCard(CharacterName.MERCHANT), strat.killCharacterCard(characterCardList));

        characterCardList.remove(new CharacterCard(CharacterName.MERCHANT));

        assertEquals(new CharacterCard(CharacterName.ASSASSIN), strat.killCharacterCard(characterCardList));
    }
}
