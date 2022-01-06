package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.player.PlayerTools;

import java.util.List;

public class OffensiveStrategy extends CharacterStrat {
    PlayerTools playerTools;

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame){
        if(characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.ASSASSIN))){
            return new CharacterCard(CharacterName.ASSASSIN);
        }
        else if(characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.WARLORD))){
            return new CharacterCard(CharacterName.WARLORD);
        }
        else if(characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.MAGICIAN))){
            return new CharacterCard(CharacterName.MAGICIAN);
        }
        else if(characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.KING))){
            return new CharacterCard(CharacterName.KING);
        }
        else{
            return super.chooseCharacter(characterCardDeckOfTheGame);
        }
    }

    @Override
    public CharacterCard killCharacterCard(List<CharacterCard> characterCardDeckOfTheGame){
        if(characterCardDeckOfTheGame.contains(new CharacterCard(CharacterName.MERCHANT))){
            return new CharacterCard(CharacterName.MERCHANT);
        }
        else{
            return super.killCharacterCard(characterCardDeckOfTheGame);
        }
    }
}
