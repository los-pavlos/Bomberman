package cz.vsb;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
    private static final String FILE_NAME = "leaderboard.csv";

    public static void saveWin(String playerName) {
        Map<String, Integer> winCounts = loadWinCounts();
        winCounts.put(playerName, winCounts.getOrDefault(playerName, 0) + 1);
        saveWinCounts(winCounts);
    }

    private static Map<String, Integer> loadWinCounts() {
        Map<String, Integer> winCounts = new HashMap<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + FILE_NAME);
                e.printStackTrace();
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    winCounts.put(name, count);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + FILE_NAME);
            e.printStackTrace();
        }
        return winCounts;
    }

    private static void saveWinCounts(Map<String, Integer> winCounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : winCounts.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + FILE_NAME);
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> getWinCounts() {
        return loadWinCounts();
    }
}