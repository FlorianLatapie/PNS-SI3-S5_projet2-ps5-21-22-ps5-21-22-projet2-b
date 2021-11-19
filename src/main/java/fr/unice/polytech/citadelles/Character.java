package fr.unice.polytech.citadelles;

public class Character {
    private CharacterNames characterName;

    public Character(CharacterNames characterName){
        this.characterName = characterName;
    }

    @Override
    public String toString() {
        return characterName.toString();
    }
}