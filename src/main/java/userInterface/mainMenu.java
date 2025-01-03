package userInterface;

public class mainMenu {
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
        System.out.println("Welcome to Make 15! Select an option from the menu by typing its correspoding number");

        System.out.println("1. Play new game");
        System.out.println("2. View Leaderboard");
        System.out.println("3. Replay a previous game");
        System.out.println("4. Help");
        System.out.println("5. Exit");
    }

    public static void main(String[] args){
        clearDisplay();
        WelcomeMessage();
        mainMenu();
    }


}
