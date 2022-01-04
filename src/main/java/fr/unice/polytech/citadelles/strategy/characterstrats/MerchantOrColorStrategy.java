package fr.unice.polytech.citadelles.strategy.characterstrats;

import fr.unice.polytech.citadelles.card.CharacterCard;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.CharacterName;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;

import java.util.List;
import java.util.Objects;

public class MerchantOrColorStrategy extends CharacterStrat{
    PlayerTools playerTools;

    public MerchantOrColorStrategy(){
    }

    public void init(Player player){
        super.init(player);
        this.playerTools = new PlayerTools(player);
    }

    @Override
    public CharacterCard chooseCharacter(List<CharacterCard> characterCardDeckOfTheGame) {
        CharacterCard favChar = new CharacterCard(CharacterName.MERCHANT);
        List<DistrictCard> builtDistricts = player.getDistrictCardsBuilt();

        if (characterCardDeckOfTheGame.contains(favChar)) {
            return favChar;
        } else {
            if (!builtDistricts.isEmpty()) {
                Color mostFrequentColor = playerTools.mostCommonColorInBuiltDistricts();
                for (CharacterCard characterCard : characterCardDeckOfTheGame) {
                    if (characterCard.getColor().equals(mostFrequentColor)) {
                        return characterCard;
                    }
                }
            }
            return characterCardDeckOfTheGame.get(random.nextInt(0, characterCardDeckOfTheGame.size()));
        }
    }

    @Override
    public String toString() {
        return "Merchant or most common color Strategy" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MerchantOrColorStrategy)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 4;
    }
}
