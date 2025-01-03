package userInterface;

import entities.Card;
import entities.Player;

import java.util.Scanner;

public class gameUI {
    private Scanner scanner;

    public gameUI(){
        this.scanner = new Scanner(System.in);
    }

    // initial message to get the player's name and welcome them to the game
    public void initialMessage(Player player){
        System.out.println("Welcome to make15! Please enter your name: ");
        String name = scanner.nextLine();
        player.setName(name);
    }

    public Card askPlayerForCard(Player player){
        System.out.println("Please enter the card you would like to play: ");
        String card = scanner.nextLine();
        while (true) {
            try{
                int userInput = scanner.nextInt();
                if (userInput = )
        }
    }

    // show all the cards in the player's hand
    public void displayHand(Player player){
        System.out.println("Your hand: ");
        player.showHand();
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

    // shows the card dealt by the computer
    public void displayComputerCard(Card computerCard){
        System.out.println("Computer has dealt: " + computerCard);
    }

    // game over message
    void displayFinalMessage(Player player){
        System.out.println("Deck Empty Game Over! ");
        System.out.println("Your final score is: " + player.getScore());
    }






}
