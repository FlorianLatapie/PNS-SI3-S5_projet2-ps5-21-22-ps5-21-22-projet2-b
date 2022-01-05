package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
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
    int nbDistrictsOfArchitectPower = 3;
    int minPriceFor2DistrictCards = 2;

    public SuperCharacterStrat(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        nbPlayers = gameEngine.getNbPlayers();
        listOfPlayers = gameEngine.getListOfPlayers();
        kingOfTheLastRound = gameEngine.getKingOfTheLastRound();
        nbOfDistrictsToWin = gameEngine.getNbOfDistrictsToWin();
    }

    public void init(Player player) {
        super.init(player);
        this.playerTools = new PlayerTools(player);
    }

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardList) {
        if (chooseKing(characterCardList) != null) {
            return chooseKing(characterCardList);
        } else if (chooseArchitect(characterCardList) != null) {
            return chooseArchitect(characterCardList);
        } else {
            return super.chooseCharacter(characterCardList);
        }
    }

    public CharacterCard chooseKing(List<CharacterCard> characterCardList) {
        if (isAboutToWinWithKing() != null) {
            if (characterCardList.contains(new CharacterCard(CharacterName.KING))) {
                return new CharacterCard(CharacterName.KING);
            } else if (characterCardList.contains(new CharacterCard(CharacterName.ASSASSIN))) {
                return new CharacterCard(CharacterName.ASSASSIN);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Player isAboutToWinWithKing() {
        for (Player player : listOfPlayers) {
            if ((player.getCoins() >= 1 || player.getDistrictCardsInHand().size() >= 1) && player.getDistrictCardsBuilt().size() == 6) {
                return player;
            }
        }
        return null;
    }


    CharacterCard chooseArchitect(List<CharacterCard> characterCardDeckOfTheGame) {
        if (shouldIChooseArchitect() && characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.ARCHITECT))) {
            return new CharacterCard(CharacterName.ARCHITECT);
        } else if (shouldIChooseArchitect() && characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.ASSASSIN))) {
            return new CharacterCard(CharacterName.ASSASSIN);
        } else {
            return null;
        }
    }

    boolean shouldIChooseArchitect() {
        //si c'est un autre joueur qui est sur le point de gagner
        if (isAboutToWinArchitect(listOfPlayers) != player && isAboutToWinArchitect(listOfPlayers) != null) {
            return true;
        }
        //si c'est moi qui gagne => verifier si je suis sur le point de gagner
        else if (isAboutToWinArchitect(listOfPlayers) == player) {
            if (canArchitectWin(player)) {
                return true;
            } else {
                return false;
            }
        }
        //sinon aucun joueur n'est sur le point de gagner
        else {
            return false;
        }
    }

    boolean canArchitectWin(Player player) {
        int totalPriceOfCheapestCardsInHand = 0;
        List<DistrictCard> sortedCards = playerTools.getDistrictCardsInHandSorted();

        if (player != null && sortedCards.size() >= nbDistrictsOfArchitectPower) {
            for (int i = 0; i < nbDistrictsOfArchitectPower; i++) {
                totalPriceOfCheapestCardsInHand += sortedCards.get(i).getPriceToBuild();
            }
        } else {
            return false;
        }

        if (totalPriceOfCheapestCardsInHand <= player.getCoins() && totalPriceOfCheapestCardsInHand != minPriceFor2DistrictCards) {
            return true;
        } else {
            return false;
        }
    }

    Player isAboutToWinArchitect(List<Player> listOfPlayers) {
        for (Player player : listOfPlayers) {
            if (player.getCoins() > 4 &&
                    player.getDistrictCardsBuilt().size() == gameEngine.getNbOfDistrictsToWin() - nbDistrictsOfArchitectPower &&
                    player.getDistrictCardsInHand().size() > 1) {
                return player;
            }
        }
        return null;
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }
}
