package userInterface;

import recording.Leaderboard;

public class RunApp {
  public static void main(String[] args) {
      Leaderboard leaderboard = new Leaderboard("src/main/java/resources/highscores.json");

      ConsoleManager consoleManager = new ConsoleManager(leaderboard);

      consoleManager.startMenu();

  }
}