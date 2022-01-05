package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;

import java.util.List;
import java.util.Random;

public interface Strategy {

    void init(Player player);

    CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame);

    boolean getCoinsOverDrawingACard();

    boolean getTaxesAtBeginningOfTurn();

    DistrictCard buildDistrict();

    DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards);

    CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards);

    CharacterCard stealCharacterCard(List<CharacterCard> ableToStealCharacterCards);

    Player getSometimesRandomPlayer(List<Player> players);

    Player magicianMove(List<Player> players);

    DistrictCard warlordChooseDistrictToDestroy(List<DistrictCard> districtCardsThatCanBeDestroy);

    DistrictCard changeCardToOther();

    DistrictCard repairDistrict(List<DistrictCard> destroyedDistricts);
}