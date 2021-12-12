package fr.unice.polytech.citadelles.card.unique_districts;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;

public class Laboratory extends UniqueDistrictsEngine{

    public Laboratory(GameEngine gameEngine){
        super(gameEngine);
    }

    @Override
    public void useUniqueDistrictPower(Player player){
        io.println(player.getName() + " uses his Laboratory district  ...");
        //get cards in hand of player
        List<DistrictCard> cardsInHand = player.getDistrictCardsInHand();
        //choose a card
        DistrictCard cardToDestroy = player.chooseCardToDestroy();

        if(cardToDestroy != null){
            io.println(player.getName() + " has chosen to destroy : " + cardToDestroy.getDistrictName());
            //destroy it
            boolean cardDestroyed = destroyCard(player, cardsInHand, cardToDestroy);
            //reward player with a coin
            if(cardDestroyed){
                gameEngine.giveCoins(player, 1);
                io.println(player.getName() + " receives 1 extra coin.");
            } else {
                io.println(player.getName() + " couldn't destroy card " + cardToDestroy.getDistrictName());
            }
        } else {
            io.println(player.getName() + "doesn't have any card in hand to destroy.");
        }
    }

    public boolean destroyCard(Player player, List<DistrictCard> cardsInHand, DistrictCard districtCard){
        boolean result = cardsInHand.remove(districtCard);
        player.setDistrictCardsInHand(cardsInHand);
        return result;
    }

}
