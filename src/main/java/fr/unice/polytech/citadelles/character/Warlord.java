package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian Latapie
 */
public class Warlord extends PowerEngine {
    public Warlord(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void callCharacterCardAction(Player player) {
        io.println(player.getName() + " uses his power ...");
        List<Player> listWarlordPlayers = new ArrayList<>(listOfPlayers);

        listWarlordPlayers.remove(player);
        listWarlordPlayers = canWarlordDestroyACardFromCharacter(player, listWarlordPlayers);
        Player playerWhoIsBishop = gameEngine.getPlayerWithCharacter(new CharacterCard(CharacterName.BISHOP));
        if((playerWhoIsBishop != null)){
            if(canThisPlayerPlay(playerWhoIsBishop)){
                listWarlordPlayers.remove(playerWhoIsBishop);
            }
        }
        Player playerChosenByWarlord = player.warlordChoosePlayer(listWarlordPlayers);
        warlordRemoveDistrictCardOfPlayer(player, playerChosenByWarlord);
    }

    public List<Player> canWarlordDestroyACardFromCharacter(Player warlord, List<Player> players){
        Boolean canDestroy = false;
        List<Player> playerThatHasDestructibleDistricts = new ArrayList<>();
        for(Player player : players){
            canDestroy = false;
            if(player.getDistrictCardsBuilt() != null){
                for(DistrictCard card : player.getDistrictCardsBuilt()){
                    if(card.getPriceToBuild()-1 <= warlord.getCoins()) canDestroy = true;
                }
                if(canDestroy) playerThatHasDestructibleDistricts.add(player);
            }
        }
        return playerThatHasDestructibleDistricts;
    }
    public void warlordRemoveDistrictCardOfPlayer(Player warlord, Player playerChooseByWarlord){
        if(playerChooseByWarlord != null){
            DistrictCard districtCardChooseByWarLord = warlord.warlordChooseDistrictToDestroy(playerChooseByWarlord);
            warlord.removeCoins(districtCardChooseByWarLord.getPriceToBuild()-1);
            playerChooseByWarlord.removeDistrictCardBuilt(districtCardChooseByWarLord);
            System.out.println(warlord.getName() + " destroys "+ districtCardChooseByWarLord.getDistrictName()+ " of "+playerChooseByWarlord.getName()+". It costs him: "+ (districtCardChooseByWarLord.getPriceToBuild()-1)+ " gold");
        }
        else {
            System.out.println("Warlord don't use his power");
        }
    }
}
