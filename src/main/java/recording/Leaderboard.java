package recording;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class Leaderboard {
    private Map<String, Integer> highScores;
    private final String resourcePath;

    public Leaderboard(String resourcePath) {
        this.resourcePath = resourcePath;
        this.highScores = new LinkedHashMap<>(); // Initialize an empty leaderboard
        initializeFile(); // Ensure the file exists
        loadScores();     // Load the scores from the file
    }

    private void initializeFile() {
        try {
            File file = new File(resourcePath);
            if (!file.exists()) {
                System.out.println("File not found: " + resourcePath + ". Creating a new file...");
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs(); // Create directories if necessary
                }
                file.createNewFile(); // Create the file
                saveScores(); // Save an empty leaderboard
            }
        } catch (Exception e) {
            System.err.println("Error creating file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadScores() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(resourcePath);

            if (file.length() == 0) { // If the file is empty, leave highScores as an empty map
                System.out.println("File is empty. Initializing an empty leaderboard.");
                return;
            }

            highScores = objectMapper.readValue(file, objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, Integer.class));
        } catch (Exception e) {
            System.err.println("Error loading leaderboard data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveScores() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(resourcePath), highScores);
        } catch (Exception e) {
            System.err.println("Error saving leaderboard data: " + e.getMessage());
        }
    }

    public void addEntry(String name, int score) {
        highScores.put(name, score);
    }

    public void displayScores() {
        System.out.println("Leaderboard:");
        highScores.forEach((name, score) -> System.out.printf("%-10s | %d%n", name, score));
    }
}