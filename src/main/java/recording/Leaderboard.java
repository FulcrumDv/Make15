package recording;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

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

            // Load existing name → score pairs
            this.highScores = objectMapper.readValue(
                    file,
                    objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, Integer.class)
            );
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

    /**
     * Adds a new entry for the same name without overwriting the old one
     * by creating a unique key for each new score.
     *
     * Then keeps only the top 5 entries.
     */
    public void addEntry(String name, int score) {
        // 1. Create a unique key so the same 'name' won't overwrite old entries.
        //    Example: "Jack_1662302947392" => 4
        String uniqueKey = name + "_" + System.currentTimeMillis();

        // 2. Put this new entry in the map
        highScores.put(uniqueKey, score);

        // 3. Keep only the top 5 entries
        keepOnlyTop5();

        // 4. Save after each addition
        saveScores();
    }

    /**
     * Sort the entire map in descending order by score and keep only top 5.
     */
    private void keepOnlyTop5() {
        // Convert the map entries to a list
        List<Entry<String, Integer>> list = new ArrayList<>(highScores.entrySet());

        // Sort by score in descending order
        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Keep only the top 5
        if (list.size() > 5) {
            list = list.subList(0, 5);
        }

        // Rebuild the linked map with only the top 5
        LinkedHashMap<String, Integer> newMap = new LinkedHashMap<>();
        for (Entry<String, Integer> e : list) {
            newMap.put(e.getKey(), e.getValue());
        }

        // Update the highScores reference
        highScores = newMap;
    }

    public void displayScores() {
        System.out.println("Leaderboard:");
        highScores.forEach((uniqueKey, score) -> {
            // If you want to strip out the suffix, parse the part before '_'
            String name = uniqueKey.contains("_") ? uniqueKey.substring(0, uniqueKey.lastIndexOf("_")) : uniqueKey;
            System.out.printf("%-10s | %d%n", name, score);
        });
    }
}
