/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public TenCardRummyGame(String name) {
        super(name);
        intiializeGame();
    }

    public void intiializeGame() {
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
        arrangeCards(player2);
        evaluatePointsInHand(player2);
        
Card topCard = this.deck.getCards().remove(0);
this.discardPile.getCards().add(topCard);
this.discardPile.setSize(1);
System.out.println(discardPile.getCards().get(0));

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
        return deck;

    }

    public void dealCards(RummyPlayer player1, RummyPlayer player2) {
        for (int i = 0; i < 20; i++) {
            Card card = deck.getCards().get(i);

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

    public int evaluatePointsInHand(RummyPlayer player) {
        Hand hand = player.getHand();

        //checking for pure sequence
        List<Card> pureSequences = hand.getPureSequences();
        boolean hasPureSequence = !pureSequences.isEmpty();
        int pointsInHand = 0;

        if (hasPureSequence == true) {
            //checking for impure sequence
            List<Card> impureSequences = hand.getImpureSequence();
            pointsInHand = pureSequences.stream()
                    .mapToInt(card -> ((PlayingCard) card).getValue().getPoints())
                    .sum();
            pointsInHand += impureSequences.stream()
                    .mapToInt(card -> ((PlayingCard) card).getValue().getPoints())
                    .sum();
            
        } else {
            pointsInHand =100;
        }
        if (player == player1) {
                player1PointsInHand = pointsInHand;
            } else if (player == player2) {
                player2PointsInHand = pointsInHand;
            }
        System.out.println(player.getName()+" points in hand: "+pointsInHand);
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

    public Card drawCardFromDeck() {
        if (deck.getSize() > 0) {
            Card drawnCard = deck.getCards().remove(0);
            deck.setSize(deck.getSize() - 1);
            return drawnCard;
        } else {
            System.out.println("Deck is empty");
            return null;
        }
    }
    
    public void discardToPile(RummyPlayer player, Card card){
        //Removing the card from the player's hand
        player.getHand().discardCard(card);
        //adding the discarded card to the discardPile
        discardPile.getCards().add(0, card);
        discardPile.setSize(discardPile.getSize()+1);
        System.out.println(player.getName()+" discarded "+ card);
    }

    @Override
    public void play() {

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
}
