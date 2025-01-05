package userInterface;

import entities.Card;
import entities.Player;
import gameLogic.Rules;

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
        System.out.println("-----------------------------------------------------");
        System.out.println("               Welcome to make15, lets play!");
        System.out.println("-----------------------------------------------------");
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
        System.out.print("Please enter the card you would like to play: ");
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
        System.out.println("Would you like to replace any picture cards? (Y/N)");

        List<Card> playerHand = player.getPlayerHand(); // Get the player's current hand

        while (true) {
            String response = scanner.nextLine().trim().toUpperCase();

            if (response.equals("N")) {
                return new ArrayList<>(); // Return an empty list if the player doesn't want to replace cards
            } else if (response.equals("Y")) {
                System.out.println("Enter the corresponding numbers of the picture cards to replace, separated by spaces:");
                String userInput = scanner.nextLine().trim();

                if (userInput.isEmpty()) {
                    System.out.println("No input detected. Please enter numbers corresponding to your cards.");
                    continue;
                }

                String[] cardIndices = userInput.split("\\s+");
                List<Card> selectedCards = new ArrayList<>();

                for (String indexStr : cardIndices) {
                    try {
                        int index = Integer.parseInt(indexStr) - 1; // Convert to zero-based index

                        if (index >= 0 && index < playerHand.size()) {
                            Card card = playerHand.get(index);
                            if (Rules.isPictureCard(card)) {
                                selectedCards.add(card); // Add the valid picture card to the list
                            } else {
                                System.out.println(card + " is not a picture card. Ignoring.");
                            }
                        } else {
                            System.out.println("Invalid card number: " + (index + 1));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input: " + indexStr + ". Please enter valid numbers.");
                    }
                }

                if (selectedCards.isEmpty()) {
                    System.out.println("No valid picture cards selected. Please try again.");
                } else {
                    return selectedCards; // Return the selected picture cards
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
