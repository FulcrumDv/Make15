package recordingTest;

import org.junit.jupiter.api.*;
import recording.Leaderboard;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LeaderboardTest {

    private Leaderboard leaderboard;
    private final String testFilePath = "src/test/resources/test_highscores.json";

    @BeforeEach
    void setUp() {
        // Create a new leaderboard instance with a test file path
        leaderboard = new Leaderboard(testFilePath);

        // Clear the file if it exists
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testKeepOnlyTop5() {
        leaderboard.addEntry("Player1", 10);
        leaderboard.addEntry("Player2", 15);
        leaderboard.addEntry("Player3", 20);
        leaderboard.addEntry("Player4", 25);
        leaderboard.addEntry("Player5", 30);
        leaderboard.addEntry("Player6", 5);

        Map<String, Integer> scores = leaderboard.highScores;

        assertEquals(5, scores.size(), "The leaderboard should have only 5 entries.");
        assertFalse(scores.values().contains(5), "The lowest score (5) should have been removed.");
    }

    @Test
    void testDisplayScores() {
        leaderboard.addEntry("Player1", 10);
        leaderboard.addEntry("Player2", 25);
        leaderboard.addEntry("Player3", 15);

        // Manually validate the console output if needed
        assertDoesNotThrow(() -> leaderboard.displayScores(), "Display scores should not throw an exception.");
    }

    @Test
    void testLoadScores() {
        // Add entries to simulate saving
        leaderboard.addEntry("Player1", 20);
        leaderboard.addEntry("Player2", 10);

        // Create a new leaderboard to simulate loading
        Leaderboard loadedLeaderboard = new Leaderboard(testFilePath);

        Map<String, Integer> scores = loadedLeaderboard.highScores;

        assertEquals(2, scores.size(), "The loaded leaderboard should have 2 entries.");
        assertTrue(scores.values().contains(20), "Score 20 should be loaded.");
        assertTrue(scores.values().contains(10), "Score 10 should be loaded.");
    }

    @AfterAll
    void cleanUp() {
        // Delete the test file after all tests
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
