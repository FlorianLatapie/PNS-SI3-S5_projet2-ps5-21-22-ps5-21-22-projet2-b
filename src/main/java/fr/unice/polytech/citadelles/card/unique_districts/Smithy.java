package fr.unice.polytech.citadelles.card.unique_districts;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;

import java.util.ArrayList;

public class Smithy extends UniqueDistrictsEngine{
    private ArrayList<DistrictCard> cardsToAdd = new ArrayList<>();

    public Smithy(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void useUniqueDistrictPower(Player player) {
        if(player.getCoins()>=3){
            io.println(player.getName() + " uses his Smithy district  ...");
            giveThreeCardsAndRemoveThree(player);
            io.println(player.getName() + " exchanges 3 coins for 3 DistrictCards :");
            cardsToAdd.forEach(c -> io.println("\t"+c));
        }
    }

    private void giveThreeCardsAndRemoveThree(Player player){
        for(int i = 0; i<3; i++) {
            DistrictCard cardToAdd = deckOfCards.getRandomDistrictCard();
            cardsToAdd.add(cardToAdd);
            player.removeCoins(1);
            player.getDistrictCardsInHand().add(cardToAdd);
        }
    }

}
