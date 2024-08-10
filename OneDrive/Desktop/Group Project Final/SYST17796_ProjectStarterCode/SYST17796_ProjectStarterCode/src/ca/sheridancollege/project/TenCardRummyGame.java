/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author gcbho
 */
public class TenCardRummyGame extends Game {

    private GroupOfCards deck;
    private GroupOfCards discardPile;
    private List<RummyPlayer> players;
    private int[] totalPoints;
    private int[] pointsInHand;
    private Scanner scanner;
    private static TenCardRummyGame instance = null;

    public TenCardRummyGame(String name) {
        super(name);
        this.scanner = new Scanner(System.in);
        this.players = new ArrayList<>();
        this.totalPoints = new int[2];
        this.pointsInHand = new int[2];

    }

    public static TenCardRummyGame getInstance(String name) {
        if (instance == null) {
            instance = new TenCardRummyGame(name);
        }
        return instance;
    }

    /**
     * the following method initializes the game by setting up players,
     * creating, shuffling and dealing cards and setting up the discard pile.
     *
     * @param gamePlayers
     */
    public void intializeGame(RummyPlayer[] gamePlayers) {
        this.players.addAll(List.of(gamePlayers));
        ArrayList<PlayingCard> cardList = createDeck();

        this.deck = new GroupOfCards(cardList.size());
        this.deck.getCards().addAll(cardList);

        this.discardPile = new GroupOfCards(0);

        this.deck.shuffle();
        dealCards();
        for (int i = 0; i < players.size(); i++) {
            arrangeAndEvaluateHand(players.get(i), i);
        }

        Card topCard = this.deck.getCards().remove(0);
        this.discardPile.getCards().add(topCard);
        this.discardPile.setSize(1);
        System.out.println("Discard Pile Card: " + discardPile.getCards().get(0));

    }

    /**
     * Main game loop that handles player turns, checks for valid hand, and
     * declares the winner. if valid hand is declared.
     */
    @Override
    public void play() {
        boolean gameOnGoing = true;
        int turn = 0;

        while (gameOnGoing) {
            RummyPlayer currentPlayer = players.get(turn);
            System.out.println(currentPlayer.getName() + "'s Turn:");
            arrangeAndEvaluateHand(currentPlayer, turn);
            playerTurn(currentPlayer, turn);
            //
            // Check if the player's hand is valid
            if (currentPlayer.getHand().isValidHand()) {
                System.out.println(currentPlayer.getName() + " has a valid hand.");
                System.out.println("Enter 'D' to Declare.");
                arrangeAndEvaluateHand(currentPlayer, turn);
                String declareInput = scanner.next();
                if ("D".equalsIgnoreCase(declareInput)) {
                    verifyDeclaration(currentPlayer); // Confirm the declaration
                    calculateTotalPoints(); // Calculate points for all players
                    declareWinner(); // Declare the winner based on points
                    gameOnGoing = false; // End the game
                }
            }
            // Move to the next player
            turn = (turn + 1) % players.size();
        }
    }

    /**
     * Creates a deck of cards with all possible suits and values.
     *
     * @param gcbho
     */
    public ArrayList<PlayingCard> createDeck() {
        ArrayList<PlayingCard> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                deck.add(new PlayingCard(suit, value));
            }
        }
        System.out.println("Deck Created");
        return deck;
    }

    /**
     * This method will be responsible for dealing cards to the players.
     */
    public void dealCards() {
        for (int i = 0; i < 20; i++) {
            Card card = deck.getCards().remove(0);

            if (i % 2 != 0) { // Odd index - deal to player1
                players.get(0).getHand().addCard(card);
            } else { // Even index - deal to player2
                players.get(1).getHand().addCard(card);
            }
        }
    }

    /**
     * Arranges the cards in the players hand, evaluates points in hand, and
     * prints sequencces of cards.
     *
     * @param player
     * @param playerIndex
     */
    public void arrangeAndEvaluateHand(RummyPlayer player, int playerIndex) {
        arrangeCards(player);
        evaluatePointsInHand(player, playerIndex);
        printSequences(player);
    }

    /**
     * Calculates the total points for a list of cards
     *
     * @param cards
     * @return
     */
    public int calculatePoints(List<Card> cards) {
        // Ensure that all cards in the list are instances of PlayingCard
        return cards.stream()
                .filter(card -> card instanceof PlayingCard) // Check if the card is an instance of PlayingCard
                .map(card -> (PlayingCard) card) // Safely cast to PlayingCard
                .mapToInt(playingCard -> playingCard.getValue().getPoints()) // Get the points for each card
                .sum(); // Sum up the points
    }

    /**
     * Calculates the total points for the players at the end of the game. which
     * can be used to declare winner
     */
    public void calculateTotalPoints() {
        for (int i = 0; i < players.size(); i++) {
            getTotalPoints()[i] += getPointsInHand()[i];
            System.out.println(players.get(i).getName() + " Total Points: " + getTotalPoints()[i]);
        }
    }

    /**
     * Handles all the player turns, including drawing cards from the deck,
     * discarding and evaluating the hand.
     *
     * @param player
     * @param playerIndex
     */
    public void playerTurn(RummyPlayer player, int playerIndex) {
        boolean validDrawChoice = false;
        
        while (!validDrawChoice) {
            System.out.println("Enter 1 to Pick From Deck.");
            System.out.println("Enter 2 to Pick FROM Discard Pile");
            int drawCardChoice = scanner.nextInt();

            switch (drawCardChoice) {
                case 1:
                    Card drawnFromDeck = drawCardFromDeck(player);
                    if (drawnFromDeck != null) {
                        player.getHand().addCard(drawnFromDeck);
                        validDrawChoice = true;
                    }
                    break;
                case 2:
                    Card drawnFromPile = drawCardFromDiscardPile(player);
                    if (drawnFromPile != null) {
                        player.getHand().addCard(drawnFromPile);
                        validDrawChoice = true;
                    }
                    break;
                default:
                    System.out.println("Invalid Choice. Try again");

            }
        }
       public void arrangeAndEvaluateHand(player, playerIndex);
        boolean validDiscardChoice = false;
        while (!validDiscardChoice) {
            System.out.println("Enter the number of the card you want to discard (1-" + player.getHand().getCards().size() + "):");
            int discardCardChoice = scanner.nextInt();

            if (discardCardChoice >= 1 || discardCardChoice <= player.getHand().getCards().size()) {
                Card cardToDiscard = player.getHand().getCards().get(discardCardChoice - 1);
                discardToPile(player, cardToDiscard);
            validDiscardChoice = true;

            }else{
                System.out.println("Invalid choice.Please enter a valid number.");
            }
        }

           arrangeAndEvaluateHand(player,playerIndex);
    }

    /**
     * Evaluates the players card in hand and evaluates their points.
     */
    public void evaluatePointsInHand(RummyPlayer player, int playerIndex) {
        Hand hand = player.getHand();
        int pointsInHand = 0;
        // Get the pure and impure sequences
        List<Card> pureSequences = hand.getPureSequences();
        List<Card> impureSequences = hand.getImpureSequence();

        if (hand.getPureSequences().isEmpty()) {
            pointsInHand = 100; // Invalid hand, points are 100
        } else {

            // Collect all cards that are part of sequences
            List<Card> allSequences = new ArrayList<>();
            allSequences.addAll(pureSequences);
            allSequences.addAll(impureSequences);

            // Create a list of cards that are not part of any sequence
            List<Card> remainingCards = new ArrayList<>(hand.getCards());
            remainingCards.removeAll(allSequences);

            // Calculate points only for remaining cards
            pointsInHand = calculatePoints(remainingCards);

            // If there is a valid pure sequence, deduct points for impure sequences
            if (!pureSequences.isEmpty()) {
                pointsInHand -= calculatePoints(impureSequences);
                pointsInHand = Math.max(pointsInHand, 0); // Ensure points don't go negative
            }
        }

        // Update the points in hand for the player
        getPointsInHand()[playerIndex] = pointsInHand;

        // Print the points in hand for debugging
        System.out.println(player.getName() + "'s Points in Hand: " + pointsInHand);
    }

    /**
     * Method used to draw card from discard pile
     *
     * @param player
     * @return
     */
    public Card drawCardFromDiscardPile(RummyPlayer player) {
        if (discardPile.getSize() > 0) {
            Card drawnCard = discardPile.getCards().remove(0);
            discardPile.setSize(discardPile.getSize() - 1);
            System.out.println(player.getName() + " Drew " + drawnCard + " from Discard Pile.");
            return drawnCard;
        } else {
            System.out.println("Discard Pile is Empty");
            return null;
        }
    }

    /**
     *
     * Method used to draw card from deck.
     */
    public Card drawCardFromDeck(RummyPlayer player) {
        if (deck.getSize() > 0) {
            Card drawnCard = deck.getCards().remove(0);
            deck.setSize(deck.getSize() - 1);
            System.out.println(player.getName() + " Drew " + drawnCard + " from deck.");
            return drawnCard;

        } else {
            System.out.println("Deck is empty");
            return null;
        }
    }

    /**
     * Method used to discard the card to the discard pile
     *
     * @param player
     * @param card
     */
    public void discardToPile(RummyPlayer player, Card card) {
        //Removing the card from the player's hand
        player.getHand().discardCard(card);
        //adding the discarded card to the discardPile
        discardPile.getCards().add(0, card);
        discardPile.setSize(discardPile.getSize() + 1);
        System.out.println(player.getName() + " discarded " + card);
    }

    /**
     * Method analyzes the cards in the players hand and prints out the pure and
     * impure sequences
     *
     * @param player
     */
    public void printSequences(RummyPlayer player) {
        Hand hand = player.getHand();

        List<Card> pureSequence = hand.getPureSequences();
        if (pureSequence.isEmpty()) {
            System.out.println();
        } else {
            System.out.println(player.getName() + " Pure Sequences: ");
            for (Card sequence : pureSequence) {
                System.out.println(sequence);
            }
        }

        List<Card> impureSequence = hand.getImpureSequence();
        if (impureSequence.isEmpty()) {
            System.out.println();
        } else {
            System.out.println(player.getName() + " Impure Sequences: ");
            for (Card sequence : impureSequence) {
                System.out.println(sequence);
            }
        }
    }

    /**
     * This method is responsible to verify the players decleration.
     *
     * @param player
     * @return
     */
    public boolean verifyDeclaration(RummyPlayer player) {
        //verifying if the player declared hand is valid
        boolean isValid = player.getHand().isValidHand();
        if (isValid) {
            System.out.println(player.getName() + " has Declared a valid hand.");

        } else {
            System.out.println(player.getName() + " has Declared a Invalid hand.");
        }
        return isValid;

    }

    /**
     * Ends the current round
     *
     * @param player
     */
    public void endRound(RummyPlayer player) {
        boolean isValid = verifyDeclaration(player);
        if (isValid) {
            System.out.println(player.getName() + " has declard a valid hand");
        } else {
            System.out.println(player.getName() + " has delared a invalid hand");
        }

    }

    /**
     * this method is responsible for declaring the winner.
     */
    @Override
    public void declareWinner() {
        if (getTotalPoints()[0] < getTotalPoints()[1]) {
            System.out.println(players.get(0).getName() + " wins!");
        } else if (getTotalPoints()[0] > getTotalPoints()[1]) {
            System.out.println(players.get(1).getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    /**
     * This method arranges the cards in the players hand according to suit and
     * then by points.
     *
     * @param player
     */
    public void arrangeCards(RummyPlayer player) {
        Hand hand = player.getHand();
        //sort cards by siut first, then by value
        hand.getCards().sort(Comparator
                .comparing((Card card) -> ((PlayingCard) card).getSuit())
                .thenComparing(card -> ((PlayingCard) card).getValue().getPoints())
        );
        //collecting the sorted cards in a list
        List<String> sortedCards = hand.getCards().stream()
                .map(card -> card.toString())
                .collect(Collectors.toList());

        //printing the sorted cards Array
        System.out.println(player.getName() + "\n" + sortedCards);
    }

    /**
     * prints the cards in the players hand.
     *
     * @param player
     */
    public void printHand(RummyPlayer player) {
        Hand hand = player.getHand();
        if (hand.getCards().isEmpty()) {
            System.out.println("Hand is Empty");
        }
        for (Card card : hand.getCards()) {
            System.out.println(card);
        }
    }

    /**
     * @return the totalPoints
     */
    public int[] getTotalPoints() {
        return totalPoints;
    }

    /**
     * @param totalPoints the totalPoints to set
     */
    public void setTotalPoints(int[] totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * @return the pointsInHand
     */
    public int[] getPointsInHand() {
        return pointsInHand;
    }

    /**
     * @param pointsInHand the pointsInHand to set
     */
    public void setPointsInHand(int[] pointsInHand) {
        this.pointsInHand = pointsInHand;
    }

}
