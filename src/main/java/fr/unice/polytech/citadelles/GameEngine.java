package fr.unice.polytech.citadelles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * "GameEngine" or "ge" also known as "MJ" or "Moteur de Jeu" in French
 */
public class GameEngine {
    private int nbPlayers;
    private List<Player> listOfPlayers;
    private IO io;

    public GameEngine() {
        this(4);
    }

    public GameEngine(int nbPlayers) {
        this.nbPlayers = nbPlayers;
        listOfPlayers = new ArrayList<>();
        io = new IO();
        initPlayers();
    }

    private void initPlayers() {
        for (int i = 0; i < nbPlayers; i++) {
            List<DistrictCard> districtCards = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                districtCards.add(new DistrictCard(new Random().nextInt(1, 6)));
            }
            listOfPlayers.add(new Player(districtCards));
        }
    }

    public void launchGame() {
        for (Player player : listOfPlayers) {
            player.buildDistrictCardsInHand(player.getDistrictCardsInHand().get(0)); //because IA isn't implemented yet, and neither are rounds, only the first card is build
            io.printDistrictCardsBuildOf(player);
            io.printDistrictCardsInHandOf(player);
            io.printCoinsOf(player);
        }
    }
}
