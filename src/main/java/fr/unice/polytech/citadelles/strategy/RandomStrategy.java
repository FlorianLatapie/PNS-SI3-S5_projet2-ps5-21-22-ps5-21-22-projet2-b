package fr.unice.polytech.citadelles.strategy;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;

import java.util.List;
import java.util.Objects;

public class RandomStrategy extends Strategy {

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        return characterCardDeckOfTheGame.get(random.nextInt(0, characterCardDeckOfTheGame.size()));
    }

    @Override
    public boolean getCoinsOverDrawingACard() {
        return random.nextBoolean();
    }

    @Override
    public boolean getTaxesAtBeginningOfTurn() {
        return random.nextBoolean();
    }

    @Override
    public boolean buildDistrict() {
        List<DistrictCard> districtCardsInHand = player.getDistrictCardsInHand();

        boolean choice = random.nextBoolean();

        if (districtCardsInHand.isEmpty()) {
            return false;
        }

        DistrictCard district = districtCardsInHand.get(random.nextInt(0, districtCardsInHand.size()));

        if (!player.canBuildDistrict(district)) {
            return false;
        } else {
            if (choice) {
                player.buildDistrictCardsInHand(district);
            }
        }
        return choice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RandomStrategy)) return false;
        RandomStrategy that = (RandomStrategy) o;
        return Objects.equals(player, that.player) && Objects.equals(random, that.random);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, random);
    }
}
