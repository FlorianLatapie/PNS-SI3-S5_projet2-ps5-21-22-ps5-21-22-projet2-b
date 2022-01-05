package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;

public class Thief extends PowerEngine {

    public Thief(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void callCharacterCardAction(Player player) {
        io.println(player.getName() + " uses his power ...");
        List<CharacterCard> ableToStealCharacterCards = deckOfCards.getNewCharacterCards();
        ableToStealCharacterCards.remove(new CharacterCard(CharacterName.ASSASSIN));
        ableToStealCharacterCards.remove(new CharacterCard(CharacterName.THIEF));
        ableToStealCharacterCards.remove(characterKilled);
        stolenCharacter = player.stealCharacterCard(ableToStealCharacterCards);

        giveMoneyToThief(player, gameEngine.getPlayerWithCharacter(stolenCharacter));

        io.println(player.getName() + " stole " + stolenCharacter);
    }

    public void giveMoneyToThief(Player thief, Player player) {
        if (player != null) {
            thief.receiveCoins(player.getCoins());
        }
    }
}
