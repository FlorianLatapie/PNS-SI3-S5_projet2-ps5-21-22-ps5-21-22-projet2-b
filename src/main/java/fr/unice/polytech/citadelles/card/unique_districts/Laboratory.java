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
        DistrictCard cardToDiscard = player.chooseCardToDiscard();

        if(cardToDiscard != null){
            io.println(player.getName() + " has chosen to destroy : " + cardToDiscard.getDistrictName());
            //discard it
            discardCard(player, cardsInHand, cardToDiscard);
            //reward player with a coin

            gameEngine.giveCoins(player, 1);
            io.println(player.getName() + " receives 1 extra coin.");

        } else {
            io.println(player.getName() + " doesn't have any card in hand to discard.");
        }
    }

    public boolean discardCard(Player player, List<DistrictCard> cardsInHand, DistrictCard districtCard){
        boolean result = cardsInHand.remove(districtCard);
        deckOfCards.putDistrictCardInDeck(districtCard);
        player.setDistrictCardsInHand(cardsInHand);
        return result;
    }
}
