package fr.unice.polytech.citadelles;

import java.util.Arrays;

public class CharacterCard extends Card{
    private CharacterName characterName;
    private int characterSequence;
    private Color characterColor;

    public CharacterCard(CharacterName characterName) {
        this.characterName = characterName;
        this.characterSequence = Arrays.asList(CharacterName.values()).indexOf(characterName)+1;
        this.characterColor = assignCharacterIndexColor();
    }

    public CharacterName getCharacterName() {
        return characterName;
    }

    public int getCharacterSequence(){return characterSequence;}

    public Color getCharacterColor(){return characterColor;}


    private Color assignCharacterIndexColor(){
        switch (this.characterSequence){
            case 4: return Color.YELLOW;
            case 5: return Color.BLUE;
            case 6: return Color.GREEN;
            case 8: return Color.RED;
            default: break;
        };
        return Color.GREY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CharacterCard) {
            CharacterCard cardToCompare = (CharacterCard) obj;
            return (this.getCharacterName().equals(cardToCompare.getCharacterName())
                    && this.getCharacterSequence() == (cardToCompare.getCharacterSequence())
                    && this.getCharacterColor().equals(cardToCompare.getCharacterColor())
            );
        }
        return false;
    }

    @Override
    public String toString() {
        return characterName.toString()+" [sequence: "+ characterSequence +", color: "+characterColor.toString()+"]";
    }
}

enum CharacterName {
    ASSASSIN,
    THIEF,
    MAGICIAN,
    KING,
    BISHOP,
    MERCHANT,
    ARCHITECT,
    WARLORD;
}