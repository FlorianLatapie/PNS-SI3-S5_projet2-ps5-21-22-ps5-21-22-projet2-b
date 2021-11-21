package fr.unice.polytech.citadelles;

public class CharacterCard extends Card{
    private CharacterName characterName;

    public CharacterCard(CharacterName characterName) {
        this.characterName = characterName;

    }

    public CharacterName getCharacterName() {
        return characterName;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CharacterCard) {
            CharacterCard cardToCompare = (CharacterCard) obj;
            return this.getCharacterName().equals(cardToCompare.getCharacterName());
        }
        return false;
    }

    @Override
    public String toString() {
        return characterName.toString();
    }
}