package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
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

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardList){
         if(chooseKing(characterCardList)!=null){
             return chooseKing(characterCardList);
         }
         else{
             return super.chooseCharacter(characterCardList);
         }
    }

    public CharacterCard chooseKing(List<CharacterCard> characterCardList) {
        if(isAboutToWinWithKing() != null){
            if(characterCardList.contains(new CharacterCard(CharacterName.KING))){
                return new CharacterCard(CharacterName.KING);
            }
            else if(characterCardList.contains(new CharacterCard(CharacterName.ASSASSIN))){
                return new CharacterCard(CharacterName.ASSASSIN);
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    public Player isAboutToWinWithKing() {
         for(Player player : listOfPlayers){
             if((player.getCoins()>=1 || player.getDistrictCardsInHand().size()>=1) && player.getDistrictCardsBuilt().size()==6){
                 return player;
             }
         }
         return null;
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }
}
