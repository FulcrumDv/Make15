package userInterface;

public class MenuDisplay {
    public static void clearDisplay() {
        System.out.println("\r\n".repeat(50));
    }

    public static void WelcomeMessage(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "~   __  __     _     _  __ _____   _  ____    ~\n" +
                "~  |  \\/  |   / \\   | |/ /| ____| / || ___|   ~\n" +
                "~  | |\\/| |  / _ \\  | ' / |  _|   | ||___ \\   ~\n" +
                "~  | |  | | / ___ \\ | . \\ | |___  | | ___) |  ~\n" +
                "~  |_|  |_|/_/   \\_\\|_|\\_\\|_____| |_||____/   ~\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); }

    public static void mainMenu(){
        System.out.println();
        System.out.println("Welcome! Select an option from the menu by entering its corresponding number");

        System.out.println("1. Play new game");
        System.out.println("2. View Leaderboard");
        System.out.println("3. Help");
        System.out.println("4. Exit");
    }

    public static void helpMenu(){
        System.out.println("The game involves playing cards which you have 5 of to make 15 with the computer's \n " +
                "card or playing a card of the same suit as the computer's card.");
        System.out.println("If you make 15, you get a point. If you play a card of the same suit as the computer's card, the game continues.");
        System.out.println("If you have no valid moves left, the game ends.");
        System.out.println("You can also replace picture cards in your hand.");
        System.out.println("The game ends when the deck is empty or you have no valid moves left.");

    }






}
