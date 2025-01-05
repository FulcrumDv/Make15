package userInterface;

public class MenuDisplay {
    public static void clearDisplay() {
        System.out.println("\r\n".repeat(50));
    }

    public static void WelcomeMessage(){
        System.out.println("""
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                ~   __  __      _         _ ___   ~
                ~  |  \\/  |__ _| |_____  / | __|  ~
                ~  | |\\/| / _` | / / -_) | |__ \\  ~
                ~  |_|  |_\\__,_|_\\_\\___| |_|___/  ~
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                """);
    }

    public static void mainMenu(){
        System.out.println("Welcome to Make 15! Select an option from the menu by typing its corresponding number");

        System.out.println("1. Play new game");
        System.out.println("2. View Leaderboard");
        System.out.println("3. Replay a previous game");
        System.out.println("4. Help");
        System.out.println("5. Exit");
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
