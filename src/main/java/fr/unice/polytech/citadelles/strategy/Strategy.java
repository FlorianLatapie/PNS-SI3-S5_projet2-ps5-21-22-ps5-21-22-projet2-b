package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;

import java.util.List;
import java.util.Random;

public interface Strategy {

    public void init(Player player, Random random, CharacterStrat characterStrat, BuildStrat buildStrat);

    public abstract CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame);

    public abstract boolean getCoinsOverDrawingACard();

    public abstract boolean getTaxesAtBeginningOfTurn();

    public abstract DistrictCard buildDistrict();

    public abstract DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards);

    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards);

    public CharacterCard stealCharacterCard(List<CharacterCard> ableToStealCharacterCards);

    public Player getSometimesRandomPlayer(List<Player> players);

    public Player magicianMove(List<Player> players);

    public DistrictCard warlordChooseDistrictToDestroy(List<DistrictCard> districtCardsThatCanBeDestroy);

    public DistrictCard changeCardToOther();

    public DistrictCard repairDistrict(List<DistrictCard> destroyedDistricts);

    public boolean chooseToExchangeCoinsForCards();
}