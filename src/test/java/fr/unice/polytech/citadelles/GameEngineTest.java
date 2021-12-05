package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.enums.DistrictName;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.BuildMaxDistrictStrategy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameEngineTest {
    static ByteArrayOutputStream outContent;
    static final PrintStream originalOut = System.out;

    @BeforeAll
    static void beforeAllSetup() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void setUp() {
        outContent.reset();
    }

    @Test
    void askToChooseCharacterTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);
        GameEngine ge = new GameEngine(4, mockRandom);

        DeckOfCards doc = new DeckOfCards();

        if (ge.getListOfPlayers().get(0).getStrategy() instanceof BuildMaxDistrictStrategy) {
            List<CharacterCard> listChar = doc.getNewCharacterCards();
            assertEquals(new CharacterCard(CharacterName.MERCHANT), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            listChar.clear();
            Exception exception = assertThrows(Exception.class, () -> ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals("characterCardDeckOfTheRound is empty: the player Player_1 cannot choose a character card.", exception.getMessage());

        } else {
            List<CharacterCard> listChar = doc.getNewCharacterCards();
            assertEquals(new CharacterCard(CharacterName.ASSASSIN), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals(new CharacterCard(CharacterName.THIEF), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals(new CharacterCard(CharacterName.MAGICIAN), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals(new CharacterCard(CharacterName.KING), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals(new CharacterCard(CharacterName.BISHOP), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals(new CharacterCard(CharacterName.MERCHANT), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals(new CharacterCard(CharacterName.ARCHITECT), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals(new CharacterCard(CharacterName.WARLORD), ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));

            Exception exception = assertThrows(Exception.class, () -> ge.askToChooseCharacter(ge.getListOfPlayers().get(0), listChar));
            assertEquals("Character card deck of the round is empty : the player can't choose a character card.", exception.getMessage());
        }
    }

    @Test
    void sortPlayerListByCharacterSequenceTest() {
        Player player1 = new Player("Player_1");
        Player player2 = new Player("Player_2");
        Player player3 = new Player("Player_3");
        Player player4 = new Player("Player_4");


        player1.setCharacterCard(new CharacterCard(CharacterName.ARCHITECT));
        player2.setCharacterCard(new CharacterCard(CharacterName.BISHOP));
        player3.setCharacterCard(new CharacterCard(CharacterName.THIEF));
        player4.setCharacterCard(new CharacterCard(CharacterName.ASSASSIN));


        GameEngine ge = new GameEngine(new Random(), player1, player2, player3, player4);
        assertEquals(List.of(player4, player3, player2, player1), ge.sortPlayerListByCharacterSequence());

    }

    @Test
    void askToGetTaxesNowTest() {
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.chooseToGetTaxesAtBeginningOfTurn()).thenReturn(true, false);

        GameEngine ge = new GameEngine();

        assertTrue(ge.askToGetTaxesNow(mockPlayer));
        assertFalse(ge.askToGetTaxesNow(mockPlayer));
    }

    @Test
    void askToBuildDistrictTest() {
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.chooseToBuildDistrict()).thenReturn(true, false);
        when(mockPlayer.getName()).thenReturn("mockPlayerName");
        when(mockPlayer.getDistrictCardsInHand()).thenReturn(List.of(new DistrictCard(Color.GREY, DistrictName.NONE, 1)));

        GameEngine ge = new GameEngine();

        assertTrue(ge.askToBuildDistrict(mockPlayer));
        assertEquals("mockPlayerName has chosen to build a district" + System.lineSeparator() +
                "mockPlayerName has the following district cards in hand          : [NONE(1 coin, GREY)]" + System.lineSeparator(), outContent.toString());
        assertFalse(ge.askToBuildDistrict(mockPlayer));
    }

    @Test
    void giveCoinsTest() {
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getName()).thenReturn("mockPlayerName");
        when(mockPlayer.getCoins()).thenReturn(2);

        GameEngine ge = new GameEngine();

        ge.giveCoins(mockPlayer);
        assertEquals("mockPlayerName receives 2 coins" + System.lineSeparator() +
                "mockPlayerName has 2 coins" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void askCoinsOrDraw2cardsTest() {
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getName()).thenReturn("mockPlayerName");
        when(mockPlayer.getCoins()).thenReturn(2);
        when(mockPlayer.chooseCoinsOverDrawingACard()).thenReturn(true, false);

        // add a "when()" when choice between the 2 options is implemented

        GameEngine ge = new GameEngine();

        ge.askToChooseCoinsOverDrawingACard(mockPlayer);
        assertEquals("mockPlayerName receives 2 coins" + System.lineSeparator() +
                "mockPlayerName has 2 coins" + System.lineSeparator(), outContent.toString());

        outContent.reset();
        ge.askToChooseCoinsOverDrawingACard(mockPlayer);
        assertTrue(outContent.toString().startsWith("mockPlayerName choose to draw a card" + System.lineSeparator() +
                "mockPlayerName draws: "));
    }

    @Test
    void getTaxesTest() {
        GameEngine ge = new GameEngine();

        // for color GREY

        Player greyPlayer = mock(Player.class);
        when(greyPlayer.getName()).thenReturn("grey player");
        when(greyPlayer.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.THIEF));

        ge.getTaxes(greyPlayer);
        assertEquals("grey player is GREY, no taxes to compute" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        // for color RED

        Player redPlayer = mock(Player.class);
        when(redPlayer.getName()).thenReturn("red player");
        when(redPlayer.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.WARLORD));
        when(redPlayer.getDistrictCardsBuilt()).thenReturn(
                List.of(
                        new DistrictCard(Color.RED, DistrictName.NONE, 1),
                        new DistrictCard(Color.RED, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREEN, DistrictName.NONE, 1),
                        new DistrictCard(Color.BLUE, DistrictName.NONE, 1),
                        new DistrictCard(Color.YELLOW, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREY, DistrictName.NONE, 1)
                )
        );

        ge.getTaxes(redPlayer);
        assertEquals("red player computing taxes ..." + System.lineSeparator() +
                "red player receives 1 coin for NONE(1 coin, RED)" + System.lineSeparator() +
                "red player receives 1 coin for NONE(1 coin, RED)" + System.lineSeparator() +
                "red player taxes earned 2 coins" + System.lineSeparator() +
                "red player has 0 coins" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        // for color GREEN

        Player greenPlayer = mock(Player.class);
        when(greenPlayer.getName()).thenReturn("green player");
        when(greenPlayer.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.MERCHANT));
        when(greenPlayer.getDistrictCardsBuilt()).thenReturn(
                List.of(
                        new DistrictCard(Color.RED, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREEN, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREEN, DistrictName.NONE, 2),
                        new DistrictCard(Color.GREEN, DistrictName.NONE, 3),
                        new DistrictCard(Color.BLUE, DistrictName.NONE, 1),
                        new DistrictCard(Color.YELLOW, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREY, DistrictName.NONE, 1)
                )
        );

        ge.getTaxes(greenPlayer);
        assertEquals("green player computing taxes ..." + System.lineSeparator() +
                "green player receives 1 coin for NONE(1 coin, GREEN)" + System.lineSeparator() +
                "green player receives 1 coin for NONE(2 coins, GREEN)" + System.lineSeparator() +
                "green player receives 1 coin for NONE(3 coins, GREEN)" + System.lineSeparator() +
                "green player taxes earned 3 coins" + System.lineSeparator() +
                "green player has 0 coins" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        // for color BLUE

        Player bluePlayer = mock(Player.class);
        when(bluePlayer.getName()).thenReturn("blue player");
        when(bluePlayer.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.BISHOP));
        when(bluePlayer.getDistrictCardsBuilt()).thenReturn(
                List.of(
                        new DistrictCard(Color.RED, DistrictName.NONE, 1),
                        new DistrictCard(Color.RED, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREEN, DistrictName.NONE, 1),
                        new DistrictCard(Color.BLUE, DistrictName.NONE, 1),
                        new DistrictCard(Color.YELLOW, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREY, DistrictName.NONE, 1)
                )
        );

        ge.getTaxes(bluePlayer);
        assertEquals("blue player computing taxes ..." + System.lineSeparator() +
                "blue player receives 1 coin for NONE(1 coin, BLUE)" + System.lineSeparator() +
                "blue player taxes earned 1 coin" + System.lineSeparator() +
                "blue player has 0 coins" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        // for color YELLOW

        Player yellowPlayer = mock(Player.class);
        when(yellowPlayer.getName()).thenReturn("yellow player");
        when(yellowPlayer.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.KING));
        when(yellowPlayer.getDistrictCardsBuilt()).thenReturn(
                List.of(
                        new DistrictCard(Color.RED, DistrictName.NONE, 1),
                        new DistrictCard(Color.RED, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREEN, DistrictName.NONE, 1),
                        new DistrictCard(Color.BLUE, DistrictName.NONE, 1),
                        new DistrictCard(Color.YELLOW, DistrictName.NONE, 1),
                        new DistrictCard(Color.GREY, DistrictName.NONE, 1)
                )
        );

        ge.getTaxes(yellowPlayer);
        assertEquals("yellow player computing taxes ..." + System.lineSeparator() +
                "yellow player receives 1 coin for NONE(1 coin, YELLOW)" + System.lineSeparator() +
                "yellow player taxes earned 1 coin" + System.lineSeparator() +
                "yellow player has 0 coins" + System.lineSeparator(), outContent.toString());
        outContent.reset();
    }

    @Test
    void getWinnerTest() {
        Player player1 = mock(Player.class);
        when(player1.getNbOfPoints()).thenReturn(1);
        when(player1.getName()).thenReturn("1");

        Player player2 = mock(Player.class);
        when(player2.getNbOfPoints()).thenReturn(10);
        when(player2.getName()).thenReturn("2");

        Player player3 = mock(Player.class);
        when(player3.getNbOfPoints()).thenReturn(5);
        when(player3.getName()).thenReturn("3");

        Player player4 = mock(Player.class);
        when(player4.getNbOfPoints()).thenReturn(1000);
        when(player4.getName()).thenReturn("4");


        GameEngine ge = new GameEngine(new Random(), player1, player2, player3, player4);
        assertEquals(List.of(player4, player2, player3, player1), ge.getWinner());
        assertEquals("The winners podium !" + System.lineSeparator() +
                "4 with 1000 points" + System.lineSeparator() +
                "2 with 10 points" + System.lineSeparator() +
                "3 with 5 points" + System.lineSeparator() +
                "1 with 1 point" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void updateKingTest() {
        Player player1 = new Player("Player_1");
        Player player2 = new Player("Player_2");
        Player player3 = new Player("Player_3");
        Player player4 = new Player("Player_4");

        player1.setCharacterCard(new CharacterCard(CharacterName.ARCHITECT));
        player2.setCharacterCard(new CharacterCard(CharacterName.BISHOP));
        player3.setCharacterCard(new CharacterCard(CharacterName.THIEF));
        player4.setCharacterCard(new CharacterCard(CharacterName.ASSASSIN));

        GameEngine ge = new GameEngine(new Random(), player1, player2, player3, player4);

        assertEquals(player1, ge.updateKing(List.of(player1, player2, player3, player4)));

        player4.setCharacterCard(new CharacterCard(CharacterName.KING));
        assertEquals(player4, ge.updateKing(List.of(player1, player2, player3, player4)));

        player4.setCharacterCard(new CharacterCard(CharacterName.WARLORD));
        assertEquals(player1, ge.updateKing(List.of(player1, player2, player3, player4)));

        player2.setCharacterCard(new CharacterCard(CharacterName.KING));
        assertEquals(player2, ge.updateKing(List.of(player1, player2, player3, player4)));

        player2.setCharacterCard(new CharacterCard(CharacterName.BISHOP));
        player3.setCharacterCard(new CharacterCard(CharacterName.KING));
        assertEquals(player3, ge.updateKing(List.of(player1, player2, player3, player4)));
    }

    @Test
    void askPlayersRoleAndSortThemByRole() {
        Player player1 = mock(Player.class);
        when(player1.getName()).thenReturn("1");
        when(player1.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.WARLORD));

        Player player2 = mock(Player.class);
        when(player2.getName()).thenReturn("2");
        when(player2.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.MERCHANT));

        Player player3 = mock(Player.class);
        when(player3.getName()).thenReturn("3");
        when(player3.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.KING));

        Player player4 = mock(Player.class);
        when(player4.getName()).thenReturn("4");
        when(player4.getCharacterCard()).thenReturn(new CharacterCard(CharacterName.THIEF));

        GameEngine ge = new GameEngine(new Random(), player1, player2, player3, player4);

        assertEquals(List.of(player4, player3, player2, player1), ge.askPlayersRoleAndSortThemByRole(ge.getDeckOfCards().getNewCharacterCards()));
        assertEquals(player3, ge.getKingOfTheLastRound());
    }

    @Test
    void askPlayersRoleAndSortThemByRole2() {
        Player player1 = mock(Player.class);
        when(player1.getName()).thenReturn("1");
        when(player1.toString()).thenReturn("1");
        when(player1.getCharacterCard()).thenReturn(
                new CharacterCard(CharacterName.WARLORD),
                new CharacterCard(CharacterName.WARLORD),
                new CharacterCard(CharacterName.WARLORD),
                new CharacterCard(CharacterName.WARLORD),
                new CharacterCard(CharacterName.THIEF)
        );

        Player player2 = mock(Player.class);
        when(player2.getName()).thenReturn("2");
        when(player2.toString()).thenReturn("2");
        when(player2.getCharacterCard()).thenReturn(
                new CharacterCard(CharacterName.MERCHANT),
                new CharacterCard(CharacterName.MERCHANT),
                new CharacterCard(CharacterName.MERCHANT),
                new CharacterCard(CharacterName.MERCHANT),
                new CharacterCard(CharacterName.KING)
        );

        Player player3 = mock(Player.class);
        when(player3.getName()).thenReturn("3");
        when(player3.toString()).thenReturn("3");
        when(player3.getCharacterCard()).thenReturn(
                new CharacterCard(CharacterName.BISHOP),
                new CharacterCard(CharacterName.BISHOP),
                new CharacterCard(CharacterName.BISHOP),
                new CharacterCard(CharacterName.BISHOP),
                new CharacterCard(CharacterName.MERCHANT)
        );

        Player player4 = mock(Player.class);
        when(player4.getName()).thenReturn("4");
        when(player4.toString()).thenReturn("4");
        when(player4.getCharacterCard()).thenReturn(
                new CharacterCard(CharacterName.THIEF),
                new CharacterCard(CharacterName.THIEF),
                new CharacterCard(CharacterName.THIEF),
                new CharacterCard(CharacterName.THIEF),
                new CharacterCard(CharacterName.WARLORD)
        );

        GameEngine ge = new GameEngine(new Random(), player1, player2, player3, player4);

        assertEquals(List.of(player4, player3, player2, player1), ge.askPlayersRoleAndSortThemByRole(ge.getDeckOfCards().getNewCharacterCards()));
        assertEquals(player1, ge.getKingOfTheLastRound());

        assertEquals(List.of(player1, player2, player3, player4), ge.askPlayersRoleAndSortThemByRole(ge.getDeckOfCards().getNewCharacterCards()));
        assertEquals(player2, ge.getKingOfTheLastRound());
    }

    @Test
    void hasThisPlayerPlaced8CardsTest() {
        Player mockPlayer1 = mock(Player.class);
        when(mockPlayer1.getName()).thenReturn("mockPlayer1");
        when(mockPlayer1.toString()).thenReturn("1");
        List<DistrictCard> listP1 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            listP1.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));
        }
        when(mockPlayer1.getDistrictCardsBuilt()).thenReturn(listP1);

        Player mockPlayer2 = mock(Player.class);
        when(mockPlayer2.getName()).thenReturn("mockPlayer2");
        when(mockPlayer2.toString()).thenReturn("2");
        List<DistrictCard> listP2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listP2.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));
        }
        when(mockPlayer2.getDistrictCardsBuilt()).thenReturn(listP2);

        Player mockPlayer3 = mock(Player.class);
        when(mockPlayer3.getName()).thenReturn("mockPlayer3");
        when(mockPlayer3.toString()).thenReturn("3");
        List<DistrictCard> listP3 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            listP3.add(new DistrictCard(Color.GREY, DistrictName.NONE, 1));
        }
        when(mockPlayer3.getDistrictCardsBuilt()).thenReturn(listP3);

        GameEngine ge = new GameEngine(new Random(), mockPlayer1, mockPlayer2, mockPlayer3);

        assertTrue(ge.hasThisPlayerPlaced8Cards(mockPlayer1));
        assertEquals(List.of(mockPlayer1), ge.getPlayersWhoBuilt8Cards());
        assertEquals("mockPlayer1 has placed 8 cards!" + System.lineSeparator(), outContent.toString());
        outContent.reset();
        assertFalse(ge.hasThisPlayerPlaced8Cards(mockPlayer2));
        assertEquals(List.of(mockPlayer1), ge.getPlayersWhoBuilt8Cards());
        assertEquals("", outContent.toString());
        outContent.reset();
        assertTrue(ge.hasThisPlayerPlaced8Cards(mockPlayer3));
        assertEquals(List.of(mockPlayer1, mockPlayer3), ge.getPlayersWhoBuilt8Cards());
        assertEquals("mockPlayer3 has placed 8 cards!" + System.lineSeparator(), outContent.toString());
        outContent.reset();
    }

    @Test
    void canThisPlayerPlayTest() {
        GameEngine ge = new GameEngine();

        Player player = new Player("player");
        Player player2 = new Player("player2");

        ge.setPlayerThatCantPlay(player);

        assertFalse(ge.canThisPlayerPlay(player));
        assertTrue(ge.canThisPlayerPlay(player2));

        ge.everyoneCanPlay();

        assertTrue(ge.canThisPlayerPlay(player));
        assertTrue(ge.canThisPlayerPlay(player2));
    }

    @Test
    void everyoneCanPlayTest() {
        GameEngine ge = new GameEngine();

        Player player = new Player("player");

        ge.setPlayerThatCantPlay(player);

        ge.everyoneCanPlay();
        assertNull(ge.getPlayerThatCantPlay());
    }

    @Test
    void callCharacterCardActionTest() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(anyInt(), anyInt())).thenReturn(0);

        Player player = new Player("player", new ArrayList<>(), 100, mockRandom);
        player.setCharacterCard(new CharacterCard(CharacterName.ASSASSIN));
        Player player2 = new Player("player2", new ArrayList<>(), 100, mockRandom);
        player2.setCharacterCard(new CharacterCard(CharacterName.THIEF));
        Player player3 = new Player("player3", new ArrayList<>(), 100, mockRandom);
        player3.setCharacterCard(new CharacterCard(CharacterName.MAGICIAN));
        Player player4 = new Player("player4", new ArrayList<>(), 100, mockRandom);
        player4.setCharacterCard(new CharacterCard(CharacterName.KING));
        Player player5 = new Player("player5", new ArrayList<>(), 100, mockRandom);
        player5.setCharacterCard(new CharacterCard(CharacterName.BISHOP));
        Player player6 = new Player("player6", new ArrayList<>(), 100, mockRandom);
        player6.setCharacterCard(new CharacterCard(CharacterName.MERCHANT));
        Player player7 = new Player("player7", new ArrayList<>(), 100, mockRandom);
        player7.setCharacterCard(new CharacterCard(CharacterName.ARCHITECT));
        Player player8 = new Player("player8", new ArrayList<>(), 100, mockRandom);
        player8.setCharacterCard(new CharacterCard(CharacterName.WARLORD));

        GameEngine ge = new GameEngine(mockRandom, player, player2, player3, player4, player5, player6, player7, player8);

        ge.callCharacterCardAction(player);
        assertEquals("player does his power ..." + System.lineSeparator() +
                "player killed THIEF [sequence: 2, color: GREY]" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        ge.callCharacterCardAction(player2);
        assertEquals("player2 does his power ..." + System.lineSeparator() +
                "player is THIEF which his power is not yet implemented !" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        ge.callCharacterCardAction(player3);
        assertEquals("player3 does his power ..." + System.lineSeparator() +
                "player is MAGICIAN which his power is not yet implemented !" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        ge.callCharacterCardAction(player4);
        assertEquals("player4 does his power ..." + System.lineSeparator() +
                "player is KING which his power is not yet implemented !" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        ge.callCharacterCardAction(player5);
        assertEquals("player5 does his power ..." + System.lineSeparator() +
                "player is BISHOP which his power is not yet implemented !" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        ge.callCharacterCardAction(player6);
        assertEquals("player6 does his power ..." + System.lineSeparator() +
                "player is MERCHANT which his power is not yet implemented !" + System.lineSeparator(), outContent.toString());
        outContent.reset();

        ge.callCharacterCardAction(player7);
        assertEquals("player7 does his power ..." + System.lineSeparator() +
                "player is ARCHITECT which his power is not yet implemented !" + System.lineSeparator(), outContent.toString());
        outContent.reset();
        ge.callCharacterCardAction(player8);
        assertEquals("player8 does his power ..." + System.lineSeparator() +
                "player is WARLORD which his power is not yet implemented !" + System.lineSeparator(), outContent.toString());
        outContent.reset();
    }

    @Test
    void updatePlayersThatCantPlayTest() {
        Player player = new Player("player");
        player.setCharacterCard(new CharacterCard(CharacterName.ASSASSIN));

        GameEngine ge = new GameEngine(new Random(), player);

        assertEquals(player, ge.updatePlayersThatCantPlay(new CharacterCard(CharacterName.ASSASSIN)));
        assertEquals(player, ge.getPlayerThatCantPlay());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
}