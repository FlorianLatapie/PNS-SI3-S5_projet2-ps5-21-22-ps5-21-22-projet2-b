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
    public DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards) {
        int choice = random.nextInt(0, districtCards.size()-1);
        return districtCards.get(choice);
    }

    @Override
    public boolean getTaxesAtBeginningOfTurn() {
        return random.nextBoolean();
    }

    @Override
    public DistrictCard buildDistrict() {
        List<DistrictCard> districtCardsInHand = player.getDistrictCardsInHand();

        boolean choice = random.nextBoolean();

        if (districtCardsInHand.isEmpty()) {
            return null;
        }

        DistrictCard district = districtCardsInHand.get(random.nextInt(0, districtCardsInHand.size()));

        if (!player.canBuildDistrict(district)) {
            return null;
        } else {
            if (choice) {
                player.buildDistrictCardsInHand(district);
            }
        }
        return district;
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
