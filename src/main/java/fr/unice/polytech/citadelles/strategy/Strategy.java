package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;
import java.util.Random;

public abstract class Strategy {
    Player player;
    Random random;

    public abstract CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame);

    public abstract boolean getCoinsOverDrawingACard();

    public abstract boolean getTaxesAtBeginningOfTurn();

    public abstract DistrictCard buildDistrict();

    public void init(Player player) {
        this.player = player;
        this.random = player.getRandom();
    }

    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards) {
        return killableCharacterCards.get(random.nextInt(0, killableCharacterCards.size()));
    }

    public CharacterCard stealCharacterCard(List<CharacterCard> ableToStealCharacterCards){
        return ableToStealCharacterCards.get(random.nextInt(0, ableToStealCharacterCards.size()));
    }
}