package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.character.Warlord;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    CharacterCard characterToKill;
    Player playerToDestroy;
    List<Player> listOfPlayersSorted;
    private int haveALotOfCard = 4;
    private int hasALotOfCoins = 4;
    private int nbOfRoundsToChooseMerchant = 4;

    public SuperCharacterStrat(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        nbPlayers = gameEngine.getNbPlayers();
        listOfPlayers = gameEngine.getListOfPlayers();
        kingOfTheLastRound = gameEngine.getKingOfTheLastRound();
        nbOfDistrictsToWin = gameEngine.getNbOfDistrictsToWin();
        listOfPlayersSorted = gameEngine.getListOfPlayersSorted();
    }

    public void init(Player player) {
        super.init(player);
        this.playerTools = new PlayerTools(player);
    }

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardList) {
        if (chooseAssassin(characterCardList)) {
            return new CharacterCard(CharacterName.ASSASSIN);
        } else if (chooseKing(characterCardList) != null) {
            return chooseKing(characterCardList);
        } else if (chooseArchitect(characterCardList) != null) {
            return chooseArchitect(characterCardList);
        } else if(chooseMerchant(characterCardList) != null){
            return chooseMerchant(characterCardList);
        } else if (chooseMagician(characterCardList)!= null){
            return chooseMagician(characterCardList);
        } else if (chooseWarlord(characterCardList)) {
            return  new CharacterCard(CharacterName.WARLORD);
        } else {
            return super.chooseCharacter(characterCardList);
        }
            
    }


    public Player isAboutToWin(){
        List<Player> playersToCheck = new ArrayList<>(listOfPlayers);
        playersToCheck.remove(this.player);
        for(Player playerToCheck : playersToCheck){
            if(playerToCheck.getDistrictCardsBuilt().size() == nbOfDistrictsToWin-1){
                return playerToCheck;
            }
        }
        return null;
    }

    public CharacterCard chooseMagician(List<CharacterCard> characterCardList) {
        if(player.getDistrictCardsInHand().isEmpty() && characterCardList.contains(new CharacterCard(CharacterName.MAGICIAN))){
            return new CharacterCard(CharacterName.MAGICIAN);
        }
        else{
            return null;
        }
    }

    public CharacterCard chooseKing(List<CharacterCard> characterCardList) {
        if (isAboutToWinWithKing() != null) {
            if (characterCardList.contains(new CharacterCard(CharacterName.KING))) {
                return new CharacterCard(CharacterName.KING);
            } else if (characterCardList.contains(new CharacterCard(CharacterName.ASSASSIN))) {
                return new CharacterCard(CharacterName.ASSASSIN);
            }
            else if(characterCardList.contains(new CharacterCard(CharacterName.WARLORD))){
                return new CharacterCard(CharacterName.WARLORD);
            }
            else if(characterCardList.contains(new CharacterCard(CharacterName.BISHOP))){
                return new CharacterCard(CharacterName.BISHOP);
            }
            else{
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

    //MERCHANT
    //A choisir en dernier
    CharacterCard chooseMerchant(List<CharacterCard> characterCardList){
        if(shouldIChooseMerchant() && characterCardList.contains(new CharacterCard(CharacterName.MERCHANT))){
            return new CharacterCard(CharacterName.MERCHANT);
        } else {
            return null;
        }
    }

    boolean shouldIChooseMerchant(){
        //Le choisir en d??but de partie
        if(gameEngine.getRound() <= nbOfRoundsToChooseMerchant){
            return true;
        }
        //Ne pas le prendre si on a suffisament de pi??ces
        if(player.getCoins() > hasALotOfCoins) {
            return false;
        }
        return false;
    }

    @Override
    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards){
        if(characterToKill!=null){
            if(killableCharacterCards.contains(characterToKill)) return characterToKill;
        }
         if(isAboutToWinWithKing()!=null && killableCharacterCards.contains(new CharacterCard(CharacterName.KING))){
             return new CharacterCard(CharacterName.KING);
         }
         else{
             return super.killCharacterCard(killableCharacterCards);
         }
    }

    @Override
    public Player chooseAPlayer(List<Player> players){
         if(isAboutToWinWithKing() != null && isAboutToWinWithKing()!=player){
             return isAboutToWinWithKing();
         }
         else if (isAboutToWin()!= null){
             return isAboutToWin();
         }
         else{
             return super.chooseAPlayer(players);
         }
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    //ASSASSIN

    public boolean chooseAssassin(List<CharacterCard> characterCardDeckOfTheGame){
        characterToKill = null;
        if(!characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.ASSASSIN))) return false;

        if(isAbleToWinWithAssassin()== null || !isAbleToWinWithAssassin().equals(this.player)){
            if(lastToChooseCharacter(this.player) ) return false;
        }

        if(haveALotOfCard() && characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.MAGICIAN))) {
            characterToKill= new CharacterCard(CharacterName.MAGICIAN);
            return true;
        }

        if(isAbleToWinWithAssassin()==this.player){
            characterToKill= new CharacterCard(CharacterName.WARLORD);
            return true;
        }else if(isAbleToWinWithAssassin()!=null) {
            return true;
        }

        if(!districtCardsBuiltAndCoinsDisparity()) return false;
        return false;

    }

    public Player isAbleToWinWithAssassin(){
        for(Player playerToCheck: listOfPlayers){
            if(playerToCheck.getDistrictCardsBuilt().size()==nbOfDistrictsToWin-1){
                return playerToCheck;
            }
        }
        return null;
    }

    public boolean haveALotOfCard(){
        List<Player> playersToCheckCopy = new ArrayList<Player>(listOfPlayers);
        playersToCheckCopy.remove(this.player);
        for(Player playerToCheck: playersToCheckCopy){
            if(!checkDisparity(this.player.getDistrictCardsInHand().size(), playerToCheck.getDistrictCardsInHand().size(), haveALotOfCard)){ //si pas de grosse diff??rence entre deux joueurs alors le joueur n'a pas bcp de cartes
                return false;
            }
        }
        return true;
    }

    public void setHaveALotOfCard(int value){
         haveALotOfCard = value;
    }

    public boolean lastToChooseCharacter(Player playerToCheck){
         if(listOfPlayersSorted!=null && listOfPlayersSorted.size()>0) return listOfPlayersSorted.get(listOfPlayersSorted.size()-1).equals(playerToCheck);
        return false;
    }

    public boolean districtCardsBuiltAndCoinsDisparity(){
         List<Player> playersToCheckCopy = new ArrayList<Player>(listOfPlayers);
         playersToCheckCopy.remove(this.player);
        for (Player playerToCheck: playersToCheckCopy){
            if(checkDisparity(this.player.getCoins(),playerToCheck.getCoins(),2) || checkDisparity(this.player.getDistrictCardsBuilt().size(),playerToCheck.getDistrictCardsBuilt().size(),2)){
                return true;
            }
        }
        return false;
    }

    public boolean checkDisparity(int reference, int toCheck, int sizeDisparity){
        return (reference-sizeDisparity>toCheck || toCheck>reference+sizeDisparity); //Diff??rence si toCheck est hors limite de + ou - 1
    }


    //WARLORD
    public boolean chooseWarlord(List<CharacterCard> characterCardDeckOfTheGame){
        if(!characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.WARLORD))) return false;
        playerToDestroy = isAboutToWin();
        if(playerToDestroy != null) {
            Warlord warlord = new Warlord(gameEngine);
            if(warlord.canWarlordDestroyACardFromPlayer(this.player, playerToDestroy)){
                return true;
            };
            return false;
        }
        return !lastToChooseCharacter(this.player);
    }


    @Override
    public Player chooseAPlayerForWarlord(List<Player> players){
        if(!players.isEmpty()){
            if(players.contains(isAboutToWin())) return isAboutToWin();
            else return players.get(random.nextInt(0, players.size()));
        }else return null;

    }

    @Override
    public String toString() {
        return "Super character strategy";
    }
}

