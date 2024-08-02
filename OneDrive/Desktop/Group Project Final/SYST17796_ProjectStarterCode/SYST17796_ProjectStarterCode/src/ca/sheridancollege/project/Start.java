package ca.sheridancollege.project;

import java.util.ArrayList;
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
                if (passwordValidator.checkLength(password) && 
                    passwordValidator.checkUpperCase(password) && 
                    passwordValidator.checkSpecialCharacter(password)) {
                    
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
    
 




        scanner.close();
    }
}
