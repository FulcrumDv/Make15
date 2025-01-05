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
        this.highScores = new LinkedHashMap<>(); 
        initializeFile(); 
        loadScores();   
    }

    // If there is no file found it will automatically create
    private void initializeFile() {
        try {
            File file = new File(resourcePath);
            if (!file.exists()) {
                System.out.println("File not found: " + resourcePath + ". Creating a new file!");
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs(); 
                }
                file.createNewFile(); 
                saveScores(); 
            }
        } catch (Exception e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    private void loadScores() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(resourcePath);

            if (file.length() == 0) {
                System.out.println("File is empty. Initializing an empty leaderboard.");
                return;
            }

            // Loads the exisiting names with their scores
            this.highScores = objectMapper.readValue(file,objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, Integer.class));
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


    /* 
     *
     * 
     * */
    public void addEntry(String name, int score) {
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
        System.out.println();
        System.out.println("============ LEADERBOARD ============");
        System.out.printf("%-15s | %-5s%n", "Player Name", "Score");
        System.out.println("-------------------------------------");

        highScores.forEach((uniqueKey, score) -> {
            String name = uniqueKey.contains("_") ? uniqueKey.substring(0, uniqueKey.lastIndexOf("_")) : uniqueKey;
            System.out.printf("%-15s | %-5d%n", name, score);
        });

        System.out.println("=====================================");
        System.out.println();
    }

}
