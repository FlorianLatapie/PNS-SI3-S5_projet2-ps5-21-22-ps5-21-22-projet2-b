package fr.unice.polytech.citadelles.character;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;

public class Assassin extends PowerEngine {

    public Assassin(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void callCharacterCardAction(Player player) {
        io.println(player.getName() + " uses his power ...");
        List<CharacterCard> killableCharacterCards = deckOfCards.getNewCharacterCards();
        killableCharacterCards.remove(new CharacterCard(CharacterName.ASSASSIN)); // cannot suicide
        characterKilled = player.killCharacterCard(killableCharacterCards);

        gameEngine.updatePlayersThatCantPlay(characterKilled);

        io.println(player.getName() + " killed " + characterKilled);
    }
}
