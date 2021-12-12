package fr.unice.polytech.citadelles.card.unique_districts;

import fr.unice.polytech.citadelles.GameEngine;
import fr.unice.polytech.citadelles.card.DistrictCard;
import fr.unice.polytech.citadelles.player.Player;

public class Graveyard extends UniqueDistrictsEngine{
    public Graveyard(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void useUniqueDistrictPower(Player player) {
        io.println(player.getName() + " uses his Graveyard district  ...");
        DistrictCard choice = player.chooseToRepairDistrict();
        if (choice != null){
            io.println(player.getName() + " repaired " + choice);
            io.printDistrictCardsBuiltBy(player);
        } else {
            io.println(player.getName() + " cannot repair his districts " + player.getDestroyedDistricts());
        }
    }
}
