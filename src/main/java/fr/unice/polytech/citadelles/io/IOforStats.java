package fr.unice.polytech.citadelles.io;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelles.player.Player;

import java.io.*;
import java.util.*;

/**
 * @author Florian Latapie
 */
public class IOforStats extends IO {
    @Override
    public void println(Object o) {
    }

    public void log(Object o) {
        System.out.println(o);
    }

    public void errorlog(Object o) {
        System.err.println(o);
    }

    public void saveAndPrintStats(List<List<Player>> winnersOfEachGame, double numberOfGames, Player... players) throws IOException {
        String CSVFilePath = createCSVFile();
        CSVWriter writer = new CSVWriter(new FileWriter(CSVFilePath, true), ';');

        for (List<Player> winnersForThisGame : winnersOfEachGame) {
            for (Player winner : winnersForThisGame) {
                String line;
                if (winnersForThisGame.get(0).equals(winner)) {
                    line = winner.getName() + ";" + 1 + ";" + winner.getNbOfPoints();
                } else {
                    line = winner.getName() + ";" + 0 + ";" + winner.getNbOfPoints();
                }

                String[] lineArray = line.split(";");
                writer.writeNext(lineArray);
            }
        }
        writer.close();

        readAndComputeStats(CSVFilePath, players);
    }

    public Map<Player, List<Integer>> readAndComputeStats(String csvFilePath, Player... players) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFilePath), ';', '"', 0);

        List<String[]> allRows = reader.readAll();
        double numberOfLines = allRows.size();
        Map<Player, List<Integer>> statsForEachPlayer = new HashMap<>();
        // value at 0 : number of win
        // value at 1 : sum of the points of the player
        for (Player p : players) {
            statsForEachPlayer.put(p, new ArrayList<>(List.of(0, 0)));
        }

        for (String[] line : allRows) {
            for (Player p : players) {
                if (line[0].equals(p.getName())) {
                    statsForEachPlayer.get(p).set(0, statsForEachPlayer.get(p).get(0) + Integer.parseInt(line[1]));
                    statsForEachPlayer.get(p).set(1, statsForEachPlayer.get(p).get(1) + Integer.parseInt(line[2]));
                }
            }
        }

        for (Player p : players) {
            log(p.getName() + " has won " +
                    statsForEachPlayer.get(p).get(0) + " games, " +
                    "average : " + statsForEachPlayer.get(p).get(1) /  numberOfLines * players.length + " points" +
                    " with strategy : " + p.getStrategy());
        }

        return statsForEachPlayer;
    }

    public String createCSVFile() throws IOException {
        String saveFolderPath = System.getProperty("user.dir") + "/save/";
        File saveFolder = new File(saveFolderPath);
        if (!saveFolder.exists()) {
            saveFolder.mkdir();
            log("\"" + saveFolderPath + "\" created");
        }
        String CSVFilePath = saveFolderPath + "results.csv";
        File CSVFile = new File(CSVFilePath);
        if (!CSVFile.exists()) {
            if (CSVFile.createNewFile()) {
                log("\"" + saveFolderPath + "results.csv" + "\" created");
            } else {
                errorlog("error while creating the file : \"" + CSVFilePath + "\"");
            }
        }
        return CSVFilePath;
    }
}
