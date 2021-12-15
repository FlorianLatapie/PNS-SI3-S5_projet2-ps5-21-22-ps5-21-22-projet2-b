package fr.unice.polytech.citadelles.card.unique_districts;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;

public class SchoolOfMagic extends UniqueDistrictsEngine{

    public SchoolOfMagic(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void useUniqueDistrictPower(Player player) {
        List<CharacterName> characterNameList = List.of(CharacterName.KING, CharacterName.BISHOP, CharacterName.MERCHANT, CharacterName.WARLORD);
        if(characterNameList.contains(player.getCharacterCard().getCharacterName())){
            io.println(player.getName() + " uses his School of Magic card power ...");
            io.println(player.getName() + " change the color of the School of Magic to " + player.getCharacterCard().getColor());
            player.receiveCoins(1);
            io.println(player.getName() + " receive 1 coin because he is " + player.getCharacterCard().getCharacterName());
        }
    }
}
