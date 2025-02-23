package cz.vsb;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
    private static final String FILE_NAME = "leaderboard.csv";

    // get the path
    private Path getFilePath() {
        String appDataPath = System.getenv("LOCALAPPDATA");  // Windows-specific
        if (appDataPath == null) {
            appDataPath = System.getenv("APPDATA");  // Windows Roaming AppData
        }
        if (appDataPath == null) {
            // Fallback for Linux/macOS
            appDataPath = System.getProperty("user.home");  // e.g., /home/user or /Users/user
        }

        Path directory = Paths.get(appDataPath, "BombermanByPablo");
        try {
            Files.createDirectories(directory);  // Ensure the directory exists
        } catch (IOException e) {
            System.err.println("Error creating directory: " + directory);
            e.printStackTrace();
        }

        return directory.resolve(FILE_NAME);
    }


    public void saveWin(String playerName) {
        if (playerName.equals("Player 1") || playerName.equals("Player 2")) {
            return; // if it is Player1/2 does not save
        }
        Map<String, Integer> winCounts = loadWinCounts();
        winCounts.put(playerName, winCounts.getOrDefault(playerName, 0) + 1);
        saveWinCounts(winCounts);
    }

    private Map<String, Integer> loadWinCounts() {
        Map<String, Integer> winCounts = new HashMap<>();
        Path filePath = getFilePath();  // get file path
        File file = filePath.toFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + filePath);
                e.printStackTrace();
                return winCounts; // empty map when error
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    try {
                        String name = parts[0];
                        int count = Integer.parseInt(parts[1]);
                        winCounts.put(name, count);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid data in file: " + line);
                    }
                } else {
                    System.err.println("Malformed line in file: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return winCounts;
    }

    private void saveWinCounts(Map<String, Integer> winCounts) {
        Path filePath = getFilePath();  // file path
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            for (Map.Entry<String, Integer> entry : winCounts.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath);
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getWinCounts() {
        return loadWinCounts();
    }
}
