package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;

public class Architect extends PowerEngine {
    public Architect(GameEngine gameEngine) {
        super(gameEngine);
    }
    @Override
    public void callCharacterCardAction(Player player) {
        io.println(player.getName() + " uses his power ...");
        io.println(player.getName() + " draws 2 more district cards...");
        give2DistrictCardsToArchitect(player);
        io.printDistrictCardsInHandOf(player);
        io.println(player.getName() + " can build 2 more districts...");
        io.println(player.getName() + " has " + player.getCoins() + " coins");
        askToBuildDistrict(player);
        askToBuildDistrict(player);
        io.printDistrictCardsBuiltBy(player);
    }

    @Override
    public void give2DistrictCardsToArchitect(Player player){
        DistrictCard c1 = deckOfCards.getRandomDistrictCard();
        if(c1 != null){
            player.drawADistrictCard(c1);
            DistrictCard c2 = deckOfCards.getRandomDistrictCard();
            if(c2 != null){
                player.drawADistrictCard(c2);
            } else {
                io.println(player.getName() + " can't draw a district card because the deck is empty.");
            }
        } else {
            io.println(player.getName() + " can't draw a district card because the deck is empty.");
        }
    }
}
