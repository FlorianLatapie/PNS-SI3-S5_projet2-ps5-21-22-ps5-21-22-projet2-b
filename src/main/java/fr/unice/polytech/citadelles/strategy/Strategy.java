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

    public abstract DistrictCard chooseBestDistrictCard(List<DistrictCard> districtCards);

    public CharacterCard killCharacterCard(List<CharacterCard> killableCharacterCards) {
        return killableCharacterCards.get(random.nextInt(0, killableCharacterCards.size()));
    }

    public CharacterCard stealCharacterCard(List<CharacterCard> ableToStealCharacterCards) {
        return ableToStealCharacterCards.get(random.nextInt(0, ableToStealCharacterCards.size()));
    }

    public Player getSometimesRandomPlayer(List<Player> players) {
        if (random.nextBoolean() && players.size() > 0) {
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
        if (districtCardsThatCanBeDestroy.size() > 0) {
            return districtCardsThatCanBeDestroy.get(random.nextInt(0, districtCardsThatCanBeDestroy.size()));
        }
        return null;
    }

    public DistrictCard changeCardToOther() {
        List<DistrictCard> cards = player.getDistrictCardsInHand();
        DistrictCard res;
        if (cards.size() > 0) {
            res = cards.get(random.nextInt(0, cards.size()));
            cards.remove(res);
        } else {
            res = null;
        }

        player.setDistrictCardsInHand(cards);
        return res;
    }
}