package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Magician extends PowerEngine {
    public Magician(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void callCharacterCardAction(Player player) {
        io.println(player.getName() + " uses his power ...");
        List<Player> players = new ArrayList<>(listOfPlayers);
        players.remove(player);
        Player chooseByMagician = player.magicianMove(players);

        if(chooseByMagician!=null){
            giveDeckToMagician(player, chooseByMagician);
            io.println(player.getName() + " take the deck of " + chooseByMagician.getName());
            io.printDistrictCardsInHandOf(player);
        }
        else{
            changeCardMagician(player);
        }
    }

    @Override
    public void giveDeckToMagician(Player player, Player chooseByMagician) {
        List<DistrictCard> temp = player.getDistrictCardsInHand();
        player.setDistrictCardsInHand(chooseByMagician.getDistrictCardsInHand());
        chooseByMagician.setDistrictCardsInHand(temp);
    }

    @Override
    public void changeCardMagician(Player player) {
        DistrictCard cardToChange = player.changeCardToOther();
        if(cardToChange!=null){
            io.println(player.getName() + " choose to change the card : " + cardToChange);
            gameEngine.giveCard(player);
        }
    }
}
