package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;

public interface Strategy {
    CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame);

    boolean getCoinsOverDrawingACard();

    boolean getTaxesAtBeginingOfTurn();

    boolean buildDistrict();

    void init(Player player);

    // public abstract boolean usePower();
}