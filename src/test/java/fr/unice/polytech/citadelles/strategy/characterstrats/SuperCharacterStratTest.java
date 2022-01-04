package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.GameEngine;
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
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class SuperCharacterStratTest {
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
        districtCards.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 2));

        districtCardsV2 = new ArrayList<>();
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.PALACE, 5));
        districtCardsV2.add(new DistrictCard(Color.RED, DistrictName.FORTRESS, 5));
        districtCardsV2.add(new DistrictCard(Color.GREEN, DistrictName.TAVERN, 1));
        districtCardsV2.add(new DistrictCard(Color.YELLOW, DistrictName.MANSION, 3));
    }


    @Test
    void chooseKingTest(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildMaxDistrictStrategy());
        Player player = new Player("Player", strat);
        player.setDistrictCardsBuilt(districtCards);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", strat);
        Player player3 = new Player("Player 3", strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        assertEquals(new CharacterCard(CharacterName.KING),strat.chooseKing(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.KING));

        assertEquals(new CharacterCard(CharacterName.ASSASSIN), strat.chooseKing(characterCardsOfTheRound));

        characterCardsOfTheRound.remove(new CharacterCard(CharacterName.ASSASSIN));

        assertEquals(null, strat.chooseKing(characterCardsOfTheRound));

    }


    @Test
    void isAboutToWinWithKingTest(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player", strat);
        player.setDistrictCardsBuilt(districtCards);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", strat);
        Player player3 = new Player("Player 3", strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);


        assertEquals(player, strat.isAboutToWinWithKing());
    }

    @Test
    void chooseCharacterTest(){
        GameEngine mockGE = mock(GameEngine.class);
        SuperCharacterStrat superstrat = new SuperCharacterStrat(mockGE);
        CompleteStrategy strat = new CompleteStrategy(superstrat, new BuildStrat());
        Player player = new Player("Player", strat);
        player.setDistrictCardsBuilt(districtCards);
        player.receiveCoins(1);
        Player player2 = new Player("Player 2", strat);
        Player player3 = new Player("Player 3", strat);

        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(player);
        listPlayer.add(player2);
        listPlayer.add(player3);
        superstrat.setListOfPlayers(listPlayer);

        DeckOfCards doc = new DeckOfCards();
        List<CharacterCard> characterCardsOfTheRound = doc.getNewCharacterCards();

        assertEquals(strat.chooseKing(characterCardsOfTheRound), strat.chooseCharacter(characterCardsOfTheRound));
    }

}
