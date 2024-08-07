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
    private RummyPlayer player1;
    private RummyPlayer player2;
    private int player1totalPoints;
    private int player2totalPoints;
    private int player1PointsInHand;
    private int player2PointsInHand;
    private Scanner scanner;

    public TenCardRummyGame(String name) {
        super(name);
        this.scanner = new Scanner(System.in);
        intializeGame();
    }

    public void intializeGame() {
        ArrayList<PlayingCard> cardList = createDeck();

        this.deck = new GroupOfCards(cardList.size());
        this.deck.getCards().addAll(cardList);

        this.discardPile = new GroupOfCards(0);

        this.deck.shuffle();

        this.player1 = new RummyPlayer("player1");
        this.player2 = new RummyPlayer("player2");

        dealCards(player1, player2);
        arrangeCards(player1);
        evaluatePointsInHand(player1);
        printSequences(player1);

        arrangeCards(player2);
        evaluatePointsInHand(player2);
        printSequences(player2);

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
            RummyPlayer currentPlayer = (turn == 0) ? player1 : player2;
            System.out.println("Player " + (turn + 1) + " Turn:");
            printHand(currentPlayer);
            printSequences(currentPlayer);
            arrangeCards(currentPlayer);
            evaluatePointsInHand(currentPlayer);
            System.out.println("PRESS 1 TO PICK FROM DECK");
            System.out.println("PRESS 2 TO PICK FROM DISCARD PILE");

            int drawCardChoice = scanner.nextInt();
            switch (drawCardChoice) {
                case 1:
                    Card drawnFromDeck = drawCardFromDeck(currentPlayer);
                    if (drawnFromDeck != null) {
                        currentPlayer.getHand().addCard(drawnFromDeck);
                    }
                    break;
                case 2:
                    Card drawnFromDiscardPile = drawCardFromDiscardPile();
                    if (drawnFromDiscardPile != null) {
                        currentPlayer.getHand().addCard(drawnFromDiscardPile);
                    }
                    break;
                default:
                    System.out.println("Invalid Choice. Try again.");
                    continue;

            }
            printHand(currentPlayer);
            arrangeCards(currentPlayer);
            evaluatePointsInHand(currentPlayer);
            printSequences(currentPlayer);
            System.out.println("Enter a Card to discard:");
            System.out.println("Enter 1 to discard the First Card.");
            System.out.println("Enter 2 to discard the Second Card.");
            System.out.println("Enter 3 to discard the Third Card.");
            System.out.println("Enter 4 to discard the Fourth Card.");
            System.out.println("Enter 5 to discard the Fifth Card.");
            System.out.println("Enter 6 to discard the Sixth Card.");
            System.out.println("Enter 7 to discard the Seventh Card.");
            System.out.println("Enter 8 to discard the Eighth Card.");
            System.out.println("Enter 9 to discard the Ninth Card.");
            System.out.println("Enter 10 to discard the Tenth Card.");

            int discardCardChoice = scanner.nextInt();
            Card discardedCard = null;
            switch (discardCardChoice) {
                case 1:
                    discardedCard = currentPlayer.getHand().getCards().get(0);
                    break;
                case 2:
                    discardedCard = currentPlayer.getHand().getCards().get(1);
                    break;
                case 3:
                    discardedCard = currentPlayer.getHand().getCards().get(2);
                    break;
                case 4:
                    discardedCard = currentPlayer.getHand().getCards().get(3);
                    break;
                case 5:
                    discardedCard = currentPlayer.getHand().getCards().get(4);
                    break;
                case 6:
                    discardedCard = currentPlayer.getHand().getCards().get(5);
                    break;
                case 7:
                    discardedCard = currentPlayer.getHand().getCards().get(6);
                    break;
                case 8:
                    discardedCard = currentPlayer.getHand().getCards().get(7);
                    break;
                case 9:
                    discardedCard = currentPlayer.getHand().getCards().get(8);
                    break;
                case 10:
                    discardedCard = currentPlayer.getHand().getCards().get(9);
                    break;
                case 11:
                    discardedCard = currentPlayer.getHand().getCards().get(10);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.");
                    return;

            }
            if (discardedCard != null) {
                currentPlayer.getHand().discardCard(discardedCard);
                discardPile.getCards().add(0, discardedCard);
                discardPile.setSize(discardPile.getSize() + 1);
                System.out.println(currentPlayer.getName() + " Discarded " + discardedCard);
            } else {
                System.out.println("Failed to discard Card. Please try Again.");
            }
            if (declareHand(currentPlayer) == true) {
                System.out.println(currentPlayer.getName() + " has 1 pure and 3 impure sequences\n Would you like to declare.");
                System.out.println("Enter D to declare.");
                String userIput = scanner.next();
                if ("D".equalsIgnoreCase(userIput)) {
                    System.out.println(currentPlayer.getName() + " has declared.");
                     verifyDeclaration(currentPlayer);
                     calculateTotalPoints();
                     endRound(currentPlayer);
                     declareWinner();
                     gameOnGoing = false;
                      
                     
                }else{
                    System.out.println("Decleration canceled. Continuing with the game");
                }

            }

            turn = (turn + 1) % 2;

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
        for (PlayingCard card : deck) {
            System.out.println(card);

        }
        System.out.println(deck.size());
        return deck;

    }

    public void dealCards(RummyPlayer player1, RummyPlayer player2) {
        for (int i = 0; i < 20; i++) {
            Card card = deck.getCards().remove(0);

            if (i % 2 != 0) { // Odd index - deal to player1
                player1.getHand().addCard(card);

                //System.out.println("Player 1 Hand");
                //for(Card c : player1.getHand().getCards()){
                // System.out.println(c);
                //}
            } else { // Even index - deal to player2
                player2.getHand().addCard(card);

                //System.out.println("Player 2 Hand");
                //for(Card cd : player2.getHand().getCards()){
                //  System.out.println(cd);
                //}
            }
        }
    }

    private int calculatePoints(List<Card> cards) {
        return cards.stream()
                .mapToInt(card -> ((PlayingCard) card).getValue().getPoints())
                .sum();
    }

    public void calculateTotalPoints() {
        // Calculate total points for Player 1
        player1totalPoints = player1totalPoints + player1PointsInHand;
        System.out.println(player1.getName() + " Total Points: " + player1totalPoints);

        // Calculate total points for Player 2
        player2totalPoints = player2totalPoints + player2PointsInHand;
        System.out.println(player2.getName() + " Total Points: " + player2totalPoints);
    }

    public int evaluatePointsInHand(RummyPlayer player) {
    Hand hand = player.getHand();
    int pointsInHand = 0; // Start with 0 points

    List<Card> pureSequences = hand.getPureSequences();
    List<Card> impureSequences = hand.getImpureSequence();

    // Calculate points for pure sequences
    for (Card card : pureSequences) {
        pointsInHand -= card.getPoints(); // Subtract points for pure sequences
    }

    // Calculate points for impure sequences
    for (Card card : impureSequences) {
        pointsInHand -= card.getPoints(); // Subtract points for impure sequences
    }

    // Calculate points for remaining cards (those not in sequences)
    List<Card> remainingCards = new ArrayList<>(hand.getCards());
    remainingCards.removeAll(pureSequences);
    remainingCards.removeAll(impureSequences);
    for (Card card : remainingCards) {
        pointsInHand += card.getPoints(); // Add points for remaining cards
    }

    if (player == player1) {
        player1PointsInHand = pointsInHand;
    } else if (player == player2) {
        player2PointsInHand = pointsInHand;
    }

    System.out.println(player.getName() + " points in hand: " + pointsInHand);
    return pointsInHand;
}


    public Card drawCardFromDiscardPile() {
        if (discardPile.getSize() > 0) {
            Card drawnCard = discardPile.getCards().remove(0);
            discardPile.setSize(discardPile.getSize() - 1);
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

    public boolean declareHand(RummyPlayer player) {
        return player.getHand().isValidHand();
    }

    public boolean verifyDeclaration(RummyPlayer player) {
        //verifying if the player declared hand is valid
        boolean isValid = declareHand(player);
        if (isValid) {
            return true;
        } else {
            return false;
        }

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
        if (player1totalPoints < player2totalPoints) {
            System.out.println(player1.getName() + " wins with " + player1totalPoints + " points!");
        } else if (player2totalPoints < player1totalPoints) {
            System.out.println(player2.getName() + " wins with " + player2totalPoints + " points!");
        } else {
            System.out.println("It's a tie! Both players have " + player1totalPoints + " points!");
        }

    }

    /**
     * @return the player1pointsInHand
     */
    public int getPlayer1pointsInHand() {
        return player1PointsInHand;
    }

    /**
     * @param player1pointsInHand the player1pointsInHand to set
     */
    public void setPlayer1pointsInHand(int player1PointsInHand) {
        this.player1PointsInHand = player1PointsInHand;
    }

    /**
     * @return the player2pointsInHand
     */
    public int getPlayer2pointsInHand() {
        return player2PointsInHand;
    }

    /**
     * @param player2pointsInHand the player2pointsInHand to set
     */
    public void setPlayer2pointsInHand(int player2PointsInHand) {
        this.player2PointsInHand = player2PointsInHand;
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
