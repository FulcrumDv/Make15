package userInterface;

import entities.Card;
import entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class gameUI {
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(gameUI.class.getName());

    public gameUI(){
        this.scanner = new Scanner(System.in);
    }

    // First stage of the game is a welcome message and ask for the player's name
    public void initialMessage(Player player){
        System.out.println("-------------------------------");
        System.out.println("Welcome to make15, lets play!");
    }

    // Display the player's hand
    public void displayHand(Player player){
        System.out.println("You have been dealt the following cards: ");
        player.showHand();
        System.out.println();
    }

    // display invalid move
    public void displayInvalidMove(){
        System.out.println("Not a valid move!");
    }

    // shows the card dealt by the computer
    public void displayComputerCard(Card computerCard){
        System.out.println("Computer has dealt: " + computerCard);
        System.out.println();
    }

    public Card askPlayerForCard(Player player){
        // Always display the updated hand just before asking
        // displayHand(player);  <-- You can call this here if you'd like, but let's assume it's called in GameManager

        List<Card> playerHand = player.getPlayerHand();
        System.out.println("Please enter the card you would like to play: ");
        while (true) {
            try{
                String input = scanner.nextLine();
                int userInput = Integer.parseInt(input.trim());
                if (userInput >= 1 && userInput <= playerHand.size()){
                    Card chosenCard = playerHand.get(userInput - 1);
                    return chosenCard;
                } else {
                    logger.info("Invalid selection. Please enter a number between 1 and " + playerHand.size());
                    System.out.println("Invalid selection. Please try again.");
                }
            } catch(NumberFormatException e){
                logger.info("Invalid input. Enter a numeric index of a card in your hand.");
                System.out.println("Invalid input. Please enter a valid number for the card index.");
            }
        }
    }



    // if the player has picture cards in their hand, ask if they would like to replace them
    public List<Card> askToReplacePictureCards(Player player) {
        List<Card> playerHand = player.getPlayerHand();
        while (true) {
            String response = scanner.nextLine().trim().toUpperCase();
            if (response.equals("N")) {
                return new ArrayList<>();
            } else if (response.equals("Y")) {
                System.out.println("Enter the corresponding number of the picture card you wish to replace: ");
                try {
                    String userInput = scanner.nextLine();
                    String[] cardsToReplace = userInput.split("\\s+");
                    List<Card> cards = player.getCardsToExchange(cardsToReplace);
                    if (cards.isEmpty()) {
                        logger.info("No valid picture cards selected for replacement.");
                        System.out.println("No valid picture cards selected. Please try again.");
                    } else {
                        return cards;
                    }
                } catch (NumberFormatException e) {
                    logger.info("Invalid input. Enter the numbers of the cards you would like to replace separated by a space.");
                    System.out.println("Invalid input. Please enter valid numbers corresponding to your cards.");
                }
            } else {
                System.out.println("Invalid response. Please enter 'Y' for yes or 'N' for no.");
            }
        }
    }


    // if the player makes 15
    public void displayMaking15(){
        System.out.println("You made 15! You get 1 point");
    }

    // if the player has a card that is the same suit as the computer's card
    public void displayCardSameSuit(Card card){
        System.out.println("You have played " + card + " which is the same suit as the computer's card! " +
                "The game will continue.");
    }

    // display the player's current score
    public void displayScore(Player player){
        System.out.println("current score: " + player.getScore());
    }

    // game over message
    public static void displayDeckEmpty(){
        System.out.println("Deck is empty! Game over!");
    }
    public static void displayFinalScore(int score){
        System.out.println("Your final score is: " + score);
    }








}
