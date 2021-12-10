package fr.unice.polytech.citadelles.card.uniqueDistricts;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.player.Player;

import java.util.List;
import java.util.Map;

public class HauntedQuarter extends UniqueDistrictsEngine{

    private final int roundOfConstruction;
    private final DistrictCard hauntedQuarter;
    private final Player player;

    public HauntedQuarter(GameEngine gameEngine, int round, DistrictCard card, Player player){
        super(gameEngine);
        roundOfConstruction = round;
        hauntedQuarter = card;
        this.player = player;
    }

    public void useUniqueDistrictPower() {
        if(roundOfConstruction!=gameEngine.getRound()-1){
            io.println(player.getName() + " uses his Haunted Quarter card power ...");

            Map<Color,Integer> mapColorNumber = player.numberOfDistrictCardsBuiltByColor();
            mapColorNumber.remove(Color.GREY);
            mapColorNumber.remove(Color.PURPLE);

            List<Color> listOfColors = List.of(Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW);

            boolean colorChanged = false;
            for(Color color : listOfColors){
                if(mapColorNumber.get(color)==0){
                    colorChanged = true;
                    hauntedQuarter.setColor(color);
                    break;
                }
            }

            if(!colorChanged){
                hauntedQuarter.setColor(listOfColors.get(random.nextInt(0,listOfColors.size())));
            }

            io.println(player.getName() + " change the color of " + hauntedQuarter + " to " + hauntedQuarter.getColor());
        }
        else {
            io.println(player.getName() + ", unfortunately for you, the power of the card " +hauntedQuarter.toString() + " can't be used if you built it in the last round !");
        }
    }

    @Override
    public void useUniqueDistrictPower(Player player) {

    }
}
