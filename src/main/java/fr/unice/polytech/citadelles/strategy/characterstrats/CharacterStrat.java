package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;
import java.util.Random;

public class CharacterStrat {
    Player player;
    Random random;

    public CharacterStrat() {
    }

    public void init(Player player) {
        this.player = player;
        this.random = player.getRandom();
    }


    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame){
        return characterCardDeckOfTheGame.get(random.nextInt(0, characterCardDeckOfTheGame.size()));
    }

    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards) {
        return killableCharacterCards.get(random.nextInt(0, killableCharacterCards.size()));
    }

    public CharacterCard stealCharacterCard(List<CharacterCard> ableToStealCharacterCards) {
        return ableToStealCharacterCards.get(random.nextInt(0, ableToStealCharacterCards.size()));
    }

    public Player getSometimesRandomPlayer(List<Player> players) {
        if (random.nextBoolean() && !players.isEmpty()) {
            return players.get(random.nextInt(0, players.size()));
        } else {
            return null;
        }
    }

    public Player magicianMove(List<Player> players) {
        if (random.nextBoolean()) {
            return players.get(random.nextInt(0, players.size()));
        } else {
            return null;
        }
    }

    public DistrictCard warlordChooseDistrictToDestroy(List<DistrictCard> districtCardsThatCanBeDestroy) {
        if (!districtCardsThatCanBeDestroy.isEmpty()) {
            return districtCardsThatCanBeDestroy.get(random.nextInt(0, districtCardsThatCanBeDestroy.size()));
        }
        return null;
    }

    public DistrictCard changeCardToOther() {
        List<DistrictCard> cards = player.getDistrictCardsInHand();
        DistrictCard res;
        if (!cards.isEmpty()) {
            res = cards.get(random.nextInt(0, cards.size()));
            cards.remove(res);
        } else {
            res = null;
        }

        player.setDistrictCardsInHand(cards);
        return res;
    }

    public DistrictCard repairDistrict(List<DistrictCard> destroyedDistricts) {
        if (player.getDestroyedDistricts().isEmpty()) {
            return null;
        } else {
            return destroyedDistricts.get(random.nextInt(0, destroyedDistricts.size()));
        }
    }

    public CharacterCard chooseKing(List<CharacterCard> characterCardList){
        return null;
    }

    public Player isAboutToWinWithKing(){
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterStrat)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 3;
    }

    @Override
    public String toString() {
        return "random Character Strategy";
    }
}
