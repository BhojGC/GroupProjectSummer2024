package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author gcbho
 */
public class Start {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numOfPlayers = 0;
        boolean validPassword = false;
        PasswordValidator passwordValidator = new PasswordValidator();

        // Loop to ensure valid number of players is entered
        while (true) {
            System.out.println("Enter Number of Players (1 or 2): ");
            if (scanner.hasNextInt()) {
                numOfPlayers = scanner.nextInt();
                if (numOfPlayers == 1 || numOfPlayers == 2) {
                    break;
                } else {
                    System.out.println("Number of Players must be either 1 or 2.");
                }
            } else {
                System.out.println("Please Enter a Valid Number (1 or 2)");
                scanner.next(); // consume invalid input
            }
        }

        // Array to store players
        RummyPlayer[] players = new RummyPlayer[numOfPlayers];
        
        for (int i = 0; i < numOfPlayers; i++) {
            scanner.nextLine(); // Consume newline left-over

            System.out.println("Enter Name for Player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();

            // Loop to ensure valid password is entered
            while (!validPassword) {
                System.out.println("Enter Password for Player " + (i + 1) + ": ");
                String password = scanner.next();

                // Check password criteria
                if (passwordValidator.checkLength(password)
                        && passwordValidator.checkUpperCase(password)
                        && passwordValidator.checkSpecialCharacter(password)) {
                    
                    System.out.println("Valid Password");
                    validPassword = true;
                } else {
                    System.out.println("Invalid password. Criteria:");
                    System.out.println(" - At least 8 characters long");
                    System.out.println(" - At least one uppercase letter");
                    System.out.println(" - At least one special character");
                }
            }

            // Create player and store in the array
            players[i] = new RummyPlayer(playerName);
            validPassword = false; // Reset for the next player
            
        }
        
       TenCardRummyGame rummyGame = new TenCardRummyGame("Ten Card Rummy Game");
        
        rummyGame.dealCards(players[0], numOfPlayers > 1 ? players[1] : null);
        rummyGame.arrangeCards(players[0]);
        if (numOfPlayers > 1) {
            rummyGame.arrangeCards(players[1]);
        }
        int player1PointsInHand = rummyGame.evaluatePointsInHand(players[0]);
        System.out.println("Player 1 Points in hand: "+player1PointsInHand);
        if(numOfPlayers >1){
           int player2PointsInHand =  rummyGame.evaluatePointsInHand(players[1]);
           System.out.println("Player 2 Points in hand: "+ player2PointsInHand);
        }
       // Check for pure sequences in player 1's hand
        List<Card> player1PureSequences = players[0].getHand().getPureSequences();
        if (!player1PureSequences.isEmpty()) {
            System.out.println("Player 1 has pure sequences: " + player1PureSequences);
        } else {
            System.out.println("Player 1 does not have any pure sequences.");
        }
// Check for impure Sequence in player 1's hand
        List<Card> player1ImpureSequence = players[0].getHand().getImpureSequence();
        if (!player1ImpureSequence.isEmpty()) {
            System.out.println("Player 1 has impure sequences: " + player1ImpureSequence);
        } else {
            System.out.println("Player 1 does not have any impure Sequences.");
        }

// Check for pure sequences in player 2's hand (if player 2 exists)
        if (numOfPlayers > 1) {
            List<Card> player2PureSequences = players[1].getHand().getPureSequences();
            if (!player2PureSequences.isEmpty()) {
                System.out.println("Player 2 has pure sequences: " + player2PureSequences);
            } else {
                System.out.println("Player 2 does not have any pure sequences.");
            }

            // Check for impure Sequence in player 2's hand
            List<Card> player2ImpureSequence = players[1].getHand().getImpureSequence();
            if (!player2ImpureSequence.isEmpty()) {
                System.out.println("Player 2 has impure sequences: " + player2ImpureSequence);
            } else {
                System.out.println("Player 2 does not have any impure Sequences.");
            }
             //Drawing a card from the deck for player 1
        Card player1DrawnCard = rummyGame.drawCardFromDeck(players[0]);
        if(player1DrawnCard != null){
            players[0].getHand().addCard(player1DrawnCard);
            System.out.println("Player 1 drew from the deck: "+ player1DrawnCard);
            players[0].getHand().discardCard(player1DrawnCard);
            rummyGame.discardToPile(player[0], player1DrawnCard);
            System.out.println("Player 1 discarded card : "+player1DrawnCard);
        }
        if(numOfPlayers >1){
            //Drawing a card from the discard pile for player 2
            Card player2DrawnCard = rummyGame.drawCardFromDiscardPile();
            if(player2DrawnCard != null){
                players[1].getHand().addCard(player2DrawnCard);
                System.out.println("Player 2 drew from the Discard Pile:  "+ player2DrawnCard);
            }
        }
        
        
        }
        
        scanner.close();
    }
}
