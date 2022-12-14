package fr.unice.polytech.citadelles.io;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import fr.unice.polytech.citadelles.enums.Color;
import fr.unice.polytech.citadelles.player.Player;
import fr.unice.polytech.citadelles.player.PlayerTools;

import java.io.*;
import java.util.*;

public class IOforStats extends IO {
    private String resultsDOTcsv = "results.csv";
    private String resultsComputedDOTtxt = "resultsComputed.txt";
    //private final static Logger LOGGER = Logger.getLogger(IO.class.getName());

    /*public IOforStats(){
        LOGGER.setLevel(Level.WARNING);
    }*/
    @Override
    public void println(Object o) {
    }

    public void log(Object o) {
        System.out.println(o);
        //LOGGER.log(Level.SEVERE, o.toString());
    }

    public void errorlog(Object o) {
        System.err.println(o);
        //LOGGER.log(Level.WARNING, o.toString());
    }

    public void saveAndPrintStats(List<List<Player>> winnersOfEachGame, Player... players) throws IOException {
        String CSVFilePath = createFile(resultsDOTcsv, false);
        String resultsComputedPath = createFile(resultsComputedDOTtxt, true);

        appendStatsToCSV(winnersOfEachGame, CSVFilePath);
        readAndComputeStatsAndPrintThem(CSVFilePath, resultsComputedPath, players);
    }

    public void appendStatsToCSV(List<List<Player>> winnersOfEachGame, String CSVFilePath) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(CSVFilePath, true), ';');

        for (List<Player> winnersForThisGame : winnersOfEachGame) {
            for (Player winner : winnersForThisGame) {
                String line;
                int isWinner;
                if (winnersForThisGame.get(0).equals(winner)) {
                    isWinner = 1;
                } else {
                    isWinner = 0;
                }
                PlayerTools playerTools = new PlayerTools(winner);
                line = winner.getName()
                        + ";" + isWinner
                        + ";" + winner.getNbOfPoints()
                        + ";" + winner.getNbOfBonusPoints()
                        + ";" + winner.getCoins()
                        + ";" + playerTools.averagePriceOfBuiltDistricts()
                        + ";" + playerTools.getAllCardsWithThisColor(Color.RED).size()
                        + ";" + playerTools.getAllCardsWithThisColor(Color.GREEN).size()
                        + ";" + playerTools.getAllCardsWithThisColor(Color.BLUE).size()
                        + ";" + playerTools.getAllCardsWithThisColor(Color.YELLOW).size()
                        + ";" + playerTools.getAllCardsWithThisColor(Color.GREY).size()
                        + ";" + playerTools.getAllCardsWithThisColor(Color.PURPLE).size();
                String[] lineArray = line.split(";");
                writer.writeNext(lineArray);
            }
        }
        writer.close();
    }

    public Map<Player, List<Double>> readAndComputeStatsAndPrintThem(String csvFilePath, String computedStatsPath, Player... players) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(csvFilePath), ';', '"', 0);

        List<String[]> allRows = reader.readAll();
        double numberOfLines = allRows.size();
        double numberOfGames = numberOfLines / players.length;

        Map<Player, List<Double>> statsForEachPlayer = new HashMap<>();
        // value at 0 : number of win
        // value at 1 : sum of the points of the player
        // value at 2 : sum of the bonus points of the player
        // value at 3 : sum of coins of the player
        // value at 4 : sum of the average price of built districts
        // value at 5 : sum of Red cards built
        // value at 6 : sum of Green cards built
        // value at 7 : sum of Blue cards built
        // value at 8 : sum of Yellow cards built
        // value at 9 : sum of Grey cards built
        // value at 10 : sum of Purple cards built
        for (Player p : players) {
            statsForEachPlayer.put(p, new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)));
        }

        int j = 1;
        for (String[] line : allRows) {
            for (Player p : players) {
                if (line[0].equals(p.getName())) {
                    for (int i = 0; i < statsForEachPlayer.get(p).size(); i++) {
                        try {
                            statsForEachPlayer.get(p).set(i, statsForEachPlayer.get(p).get(i) + Double.parseDouble(line[i + 1]));
                        } catch (Exception e) {
                            String message = "Error while parsing " + resultsDOTcsv + " please check if line " + j + " has exactly a player name then 11 numbers";
                            errorlog(message);
                            return null;
                        }
                    }
                }
            }
            j++;
        }

        printAndSaveStats(numberOfLines, numberOfGames, statsForEachPlayer, computedStatsPath, players);

        return statsForEachPlayer;
    }

    private void printAndSaveStats(double numberOfLines, double numberOfGames, Map<
            Player, List<Double>> statsForEachPlayer, String computedStatsPath, Player... players) throws IOException {
        for (Player p : players) {
            log(p.getName() + " has won " + statsForEachPlayer.get(p).get(0) + " games out of " + numberOfGames + ", average: " + statsForEachPlayer.get(p).get(1) / numberOfLines * players.length + " points");
            saveComputedStats(computedStatsPath, p.getName() + " has won " + statsForEachPlayer.get(p).get(0) + " games out of " + numberOfGames + " games" +
                    System.lineSeparator() + "\taverage: " + statsForEachPlayer.get(p).get(1) / numberOfLines * players.length + " points" +
                    System.lineSeparator() + "\taverage: " + statsForEachPlayer.get(p).get(2) / numberOfLines * players.length + " bonus points" +
                    System.lineSeparator() + "\taverage: " + statsForEachPlayer.get(p).get(3) / numberOfLines * players.length + " coins" +
                    System.lineSeparator() + "\taverage price of built districts: " + statsForEachPlayer.get(p).get(4) / numberOfLines * players.length + " coins" +
                    System.lineSeparator() + "\taverage Red cards built: " + statsForEachPlayer.get(p).get(5) / numberOfLines * players.length +
                    System.lineSeparator() + "\taverage Green cards built: " + statsForEachPlayer.get(p).get(6) / numberOfLines * players.length +
                    System.lineSeparator() + "\taverage Blue cards built: " + statsForEachPlayer.get(p).get(7) / numberOfLines * players.length +
                    System.lineSeparator() + "\taverage Yellow cards built: " + statsForEachPlayer.get(p).get(8) / numberOfLines * players.length +
                    System.lineSeparator() + "\taverage Grey cards built: " + statsForEachPlayer.get(p).get(9) / numberOfLines * players.length +
                    System.lineSeparator() + "\taverage Purple cards built: " + statsForEachPlayer.get(p).get(10) / numberOfLines * players.length +
                    System.lineSeparator() + "\twith strategy built: " + p.getStrategy() + System.lineSeparator() + System.lineSeparator());
        }
    }

    public void saveComputedStats(String computedStatsPath, String s) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(computedStatsPath, true));
            writer.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert writer != null;
            writer.close();
        }
    }

    public String createFile(String filename, boolean deletePrevious) throws IOException {
        String saveFolderPath = System.getProperty("user.dir") + "/save/";
        File saveFolder = new File(saveFolderPath);

        if (!saveFolder.exists()) {
            saveFolder.mkdir();
            log("\"" + saveFolderPath + "\" created");
        }
        String CSVFilePath = saveFolderPath + filename;
        File CSVFile = new File(CSVFilePath);

        if (deletePrevious) {
            CSVFile.delete();
        }

        if (!CSVFile.exists()) {
            if (CSVFile.createNewFile()) {
                log("" + saveFolderPath + filename + " created");
            } else {
                errorlog("error while creating the file : " + CSVFilePath + "");
            }
        }
        return CSVFilePath;
    }
}
