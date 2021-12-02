package fr.unice.polytech.citadelles.card;

import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;

import java.util.Arrays;
import java.util.Objects;

public class CharacterCard extends Card {
    private CharacterName characterName;
    private int characterSequence;

    public CharacterCard(CharacterName characterName) {
        this.characterName = characterName;
        this.characterSequence = Arrays.asList(CharacterName.values()).indexOf(characterName) + 1;
        super.setColor(assignCharacterIndexColor());
    }

    public CharacterName getCharacterName() {
        return characterName;
    }

    public int getCharacterSequence() {
        return characterSequence;
    }

    private Color assignCharacterIndexColor() {
        return switch (this.characterSequence) {
            case 4 -> Color.YELLOW;
            case 5 -> Color.BLUE;
            case 6 -> Color.GREEN;
            case 8 -> Color.RED;
            default -> Color.GREY;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterCard)) return false;
        CharacterCard that = (CharacterCard) o;
        return getCharacterSequence() == that.getCharacterSequence() && getCharacterName() == that.getCharacterName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCharacterName(), getCharacterSequence());
    }

    @Override
    public String toString() {
        return characterName.toString() + " [sequence: " + characterSequence + ", color: " + getColor().toString() + "]";
    }
}