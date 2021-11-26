package fr.unice.polytech.citadelles;

import java.util.List;

public interface Strategy {
        public abstract CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame);

        public abstract boolean getCoinsOverDrawingACard();

        public abstract boolean getTaxesAtBeginingOfTurn();

        public abstract boolean buildDistrict();

        public abstract void init(Player player);

        // public abstract boolean usePower();
}