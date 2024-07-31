/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.Scanner;

/**
 *
 * @author gcbho
 */
public class Main {
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        int numOfPlayers = 0;
        boolean validPassword = false;
        PasswordValidator passwordValidator = new PasswordValidator();

        // Loop to ensure valid number of players is entered
        do {
            System.out.println("Enter Number of Players: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Please Enter a Valid Number 1 or 2");
                scanner.next(); // consume invalid input
            } else {
                numOfPlayers = scanner.nextInt();
                if (numOfPlayers <= 1) {
                    System.out.println("Number of Players must be more than 1.");
                } else if (numOfPlayers > 2) {
                    System.out.println("Number of Players Cannot be more than 2.");
                }
            }
        } while (numOfPlayers != 2);

        // Loop to ensure valid password is entered
        do {
            System.out.println("Enter Desired Name: ");
            scanner.next(); // consume player name input

            System.out.println("Enter Your Password: ");
            String password = scanner.next();

            // Check password criteria
            if (passwordValidator.checkLength(password) && 
                passwordValidator.checkUpperCase(password) && 
                passwordValidator.checkSpecialCharacter(password)) {
                
                System.out.println("Valid Password");
                validPassword = true;
            } else {
                System.out.println("Not a Valid password");
                System.out.println("Criteria:");
                System.out.println(" - At least 8 characters long");
                System.out.println(" - At least one uppercase letter");
                System.out.println(" - At least one special character");
            }
        } while (!validPassword);

        scanner.close();
        // testing purpose only
        ArrayList <PlayingCard> deck = createDeck();
        for(PlayingCard card : deck){
            System.out.println(card);
        }
        
        
    }
        
    }
    

