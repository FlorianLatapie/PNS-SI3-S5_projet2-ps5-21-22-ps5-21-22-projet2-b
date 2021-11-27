package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.CharacterCard;
import fr.unice.polytech.citadelles.Player;

import java.util.List;

public interface Strategy {
    CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame);

    boolean getCoinsOverDrawingACard();

    boolean getTaxesAtBeginingOfTurn();

    boolean buildDistrict();

    void init(Player player);

    // public abstract boolean usePower();
}