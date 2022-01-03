package fr.unice.polytech.citadelles;

import fr.unice.polytech.citadelles.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Florian Latapie
 */
public class IOforStats extends IO {
    @Override
    public void println(Object o) {
        return;
    }

    public void printStats(List<Player> players, List<List<Player>> winnersOfEachGame) {
        Map<Player, Integer> statsForEachPlayer = new HashMap<>();
        for (Player p: players) {
            statsForEachPlayer.put(p,0);
        }


        for (List<Player> winnersForThisGame : winnersOfEachGame) {
            for (Player playerToSearch: players) {
                if (winnersForThisGame.get(0).getName().equals(playerToSearch.getName())){
                    statsForEachPlayer.put(playerToSearch, statsForEachPlayer.get(playerToSearch)+1);
                }
            }
        }

        for (Player p: players) {
            System.out.println(p.getName()+ " has won "+statsForEachPlayer.get(p) + " games with strategy :" + p.getStrategy());
        }
    }
}
