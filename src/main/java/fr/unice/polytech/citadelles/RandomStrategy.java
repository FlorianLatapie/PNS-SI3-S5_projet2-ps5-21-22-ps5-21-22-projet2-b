package fr.unice.polytech.citadelles;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class RandomStrategy implements Strategy {
    private Player player;
    private Random random;

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        return characterCardDeckOfTheGame.get(random.nextInt(0, characterCardDeckOfTheGame.size()));
    }

    @Override
    public boolean getCoinsOverDrawingACard() {
        return random.nextBoolean();
    }

    @Override
    public boolean getTaxesAtBeginingOfTurn() {
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
    public void init(Player player) {
        this.player = player;
        this.random = player.getRandom();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RandomStrategy)) return false;
        RandomStrategy that = (RandomStrategy) o;
        return Objects.equals(player, that.player) && Objects.equals(random, that.random);
    }
}
