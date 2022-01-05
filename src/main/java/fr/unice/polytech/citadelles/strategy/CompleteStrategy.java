package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.strategy.buildstrats.BuildStrat;
import fr.unice.polytech.citadelles.strategy.characterstrats.CharacterStrat;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CompleteStrategy implements Strategy {
    Player player;
    Random random;
    CharacterStrat characterStrat;
    BuildStrat buildStrat;

    public CompleteStrategy(CharacterStrat characterStrat, BuildStrat buildStrat) {
        this.characterStrat = characterStrat;
        this.buildStrat = buildStrat;
    }

    @Override
    public void init(Player player){
        this.player = player;
        this.random = player.getRandom();
        characterStrat.init(player);
        buildStrat.init(player);
    }

    @Override
    public boolean getCoinsOverDrawingACard() {
        return buildStrat.getCoinsOverDrawingACard();
    }

    @Override
    public boolean getTaxesAtBeginningOfTurn() {
        return buildStrat.getTaxesAtBeginningOfTurn();
    }

    @Override
    public DistrictCard buildDistrict() {
        return buildStrat.buildDistrict();
    }

    @Override
    public DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards) {
        return buildStrat.chooseBestDistrictCard(districtCards);
    }

    @Override
    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards) {
        return characterStrat.killCharacterCard(killableCharacterCards);
    }

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        return characterStrat.chooseCharacter(characterCardDeckOfTheGame);
    }

    @Override
    public CharacterCard stealCharacterCard(List<CharacterCard> ableToStealCharacterCards) {
        return characterStrat.stealCharacterCard(ableToStealCharacterCards);
    }

    @Override
    public Player chooseAPlayer(List<Player> players) {
        return characterStrat.chooseAPlayer(players);
    }

    @Override
    public Player magicianMove(List<Player> players) {
        return characterStrat.magicianMove(players);
    }

    @Override
    public DistrictCard warlordChooseDistrictToDestroy(List<DistrictCard> districtCardsThatCanBeDestroy) {
        return characterStrat.warlordChooseDistrictToDestroy(districtCardsThatCanBeDestroy);
    }

    @Override
    public DistrictCard changeCardToOther() {
        return characterStrat.changeCardToOther();
    }

    @Override
    public DistrictCard repairDistrict(List<DistrictCard> destroyedDistricts) {
        return characterStrat.repairDistrict(destroyedDistricts);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompleteStrategy that = (CompleteStrategy) o;
        return characterStrat.equals(that.characterStrat) && buildStrat.equals(that.buildStrat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterStrat, buildStrat);
    }

    @Override
    public String toString() {
        return "CompleteStrategy{" +
                "characterStrat=" + characterStrat +
                ", buildStrat=" + buildStrat +
                '}';
    }
}
