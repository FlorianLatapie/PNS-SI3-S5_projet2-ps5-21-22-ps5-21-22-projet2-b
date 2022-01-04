package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;

import java.util.List;

public class SuperCharacterStrat extends CharacterStrat {
    GameEngine gameEngine;
    PlayerTools playerTools;
    int nbPlayers;
    List<Player> listOfPlayers;
    Player kingOfTheLastRound;
    Integer nbOfDistrictsToWin;

     public SuperCharacterStrat(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        nbPlayers = gameEngine.getNbPlayers();
        listOfPlayers = gameEngine.getListOfPlayers();
        kingOfTheLastRound = gameEngine.getKingOfTheLastRound();
        nbOfDistrictsToWin = gameEngine.getNbOfDistrictsToWin();
    }

    public void init(Player player){
        super.init(player);
        this.playerTools = new PlayerTools(player);
    }
}
