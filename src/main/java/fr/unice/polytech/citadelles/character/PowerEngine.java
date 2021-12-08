package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.IO;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DeckOfCards;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;
import java.util.Random;

public abstract class PowerEngine extends GameEngine {
    GameEngine gameEngine;
    IO io;

    int nbPlayers;

    List<Player> listOfPlayers;
    List<Player> playersWhoBuilt8Cards;

    Player playerThatCantPlay = null;
    Player kingOfTheLastRound;
    Player kingByDefault;

    DeckOfCards deckOfCards;

    CharacterCard characterKilled;
    CharacterCard stolenCharacter;

    Random random;

    private PowerEngine(){};

    public PowerEngine(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        io = gameEngine.getIO();

        nbPlayers = gameEngine.getNbPlayers();

        listOfPlayers = gameEngine.getListOfPlayers();
        playersWhoBuilt8Cards = gameEngine.getPlayersWhoBuilt8Cards();

        playerThatCantPlay = gameEngine.getPlayerThatCantPlay();
        kingOfTheLastRound = gameEngine.getKingOfTheLastRound();
        kingByDefault = gameEngine.getKingByDefault();

        deckOfCards = gameEngine.getDeckOfCards();

        characterKilled = gameEngine.getCharacterKilled();
        stolenCharacter = gameEngine.getStolenCharacter();

        random = gameEngine.getRandom();
    }

    public abstract void callCharacterCardAction(Player player);
}
