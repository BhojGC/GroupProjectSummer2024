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

    public TenCardRummyGame(String name) {
        super(name);
        this.scanner = new Scanner(System.in);
        this.players = new ArrayList<>();
        this.totalPoints = new int[2];
        this.pointsInHand = new int[2];

    }

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

    @Override
    public void play() {
        boolean gameOnGoing = true;
        int turn = 0;

        while (gameOnGoing) {
            RummyPlayer currentPlayer = players.get(turn);
            System.out.println(currentPlayer.getName() + " Turn:");

            playerTurn(currentPlayer, turn);
            printHand(currentPlayer);
            printSequences(currentPlayer);
            arrangeCards(currentPlayer);

            if (currentPlayer.getHand().declareHand()) {
                System.out.println(currentPlayer.getName() + " has a valid hand.");
                System.out.println("Enter 'D' to Declare.");
                String declareInput = scanner.next();
                if ("D".equalsIgnoreCase(declareInput)) {
                    verifyDeclaration(currentPlayer);
                    calculateTotalPoints();
                    declareWinner();
                    gameOnGoing = false;
                }
            }

            turn = (turn + 1) % players.size();
        }
    }

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

    public void arrangeAndEvaluateHand(RummyPlayer player, int playerIndex) {
        arrangeCards(player);
        evaluatePointsInHand(player, playerIndex);
        printSequences(player);
    }

    private int calculatePoints(List<Card> cards) {
        return cards.stream()
                .mapToInt(card -> ((PlayingCard) card).getValue().getPoints())
                .sum();
    }

    public void calculateTotalPoints() {
        for (int i = 0; i < players.size(); i++) {
            totalPoints[i] += pointsInHand[i];
            System.out.println(players.get(i).getName() + " Total Points: " + totalPoints[i]);
        }
    }

    public void playerTurn(RummyPlayer player, int playerIndex) {
        // printHand(player);
        //printSequences(player);
        //arrangeCards(player);
        arrangeAndEvaluateHand(player, playerIndex);

        System.out.println("Enter 1 to Pick From Deck.");
        System.out.println("Enter 2 to Pick FROM Discard Pile");
        int drawCardChoice = scanner.nextInt();

        switch (drawCardChoice) {
            case 1:
                Card drawnFromDeck = drawCardFromDeck(player);
                if (drawnFromDeck != null) {
                    player.getHand().addCard(drawnFromDeck);
                }
                break;
            case 2:
                Card drawnFromPile = drawCardFromDiscardPile(player);
                if (drawnFromPile != null) {
                    player.getHand().addCard(drawnFromPile);
                }
                break;
            default:
                System.out.println("Invalid Choice. Try again");

        }
        //printHand(player);
        //arrangeCards(player);
        //evaluatePointsInHand(player, playerIndex);
        //printSequences(player);
        arrangeAndEvaluateHand(player, playerIndex);
        System.out.println("Enter the number of the card you want to discard (1-" + player.getHand().getCards().size() + "):");
        int discardCardChoice = scanner.nextInt();

        if (discardCardChoice < 1 || discardCardChoice > player.getHand().getCards().size()) {
            System.out.println("Invalid choice. Please enter a valid number.");
            return;
        }

        // Get the card to discard
        Card cardToDiscard = player.getHand().getCards().get(discardCardChoice - 1);

        // Discard the card using discardToPile method
        discardToPile(player, cardToDiscard);
        arrangeAndEvaluateHand(player, playerIndex);

    }

    public int evaluatePointsInHand(RummyPlayer player, int playerIndex) {
        Hand hand = player.getHand();
        int pointsInHand = 100; // Default points if no sequences are found

        List<Card> pureSequences = hand.getPureSequences();
        List<Card> impureSequences = hand.getImpureSequence();

        if (!pureSequences.isEmpty()) {
            pointsInHand -= calculatePoints(pureSequences);
            if (!impureSequences.isEmpty()) {
                pointsInHand -= calculatePoints(impureSequences);
            }
        }

        this.pointsInHand[playerIndex] = pointsInHand;
        System.out.println(player.getName() + " points in hand: " + pointsInHand);
        return pointsInHand;
    }

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

    public void discardToPile(RummyPlayer player, Card card) {
        //Removing the card from the player's hand
        player.getHand().discardCard(card);
        //adding the discarded card to the discardPile
        discardPile.getCards().add(0, card);
        discardPile.setSize(discardPile.getSize() + 1);
        System.out.println(player.getName() + " discarded " + card);
    }

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

    public void endRound(RummyPlayer player) {
        boolean isValid = verifyDeclaration(player);
        if (isValid) {
            System.out.println(player.getName() + " has declard a valid hand");
        } else {
            System.out.println(player.getName() + " has delared a invalid hand");
        }

    }

    @Override
    public void declareWinner() {
        if (totalPoints[0] < totalPoints[1]) {
            System.out.println(players.get(0).getName() + " wins!");
        } else if (totalPoints[0] > totalPoints[1]) {
            System.out.println(players.get(1).getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

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

    public void printHand(RummyPlayer player) {
        Hand hand = player.getHand();
        if (hand.getCards().isEmpty()) {
            System.out.println("Hand is Empty");
        }
        for (Card card : hand.getCards()) {
            System.out.println(card);
        }
    }
}
