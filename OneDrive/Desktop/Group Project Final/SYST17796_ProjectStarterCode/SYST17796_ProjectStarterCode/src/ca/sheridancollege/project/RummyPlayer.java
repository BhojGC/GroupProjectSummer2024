/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author gcbho
 */
public class RummyPlayer extends Player {

    private Hand hand;

    public RummyPlayer(String name) {
        super(name);
        this.hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public Card drawCardFromDiscardPile(GroupOfCards discardPile) {
        if (discardPile.getSize() > 0) {
            Card drawnCard = discardPile.getCards().remove(0);
            discardPile.setSize(discardPile.getSize() - 1);
            return drawnCard;
        } else {
            System.out.println("Discard Pile is Empty");
            return null;
        }
    }

    public Card drawCardFromDeck(GroupOfCards deck) {
        if (deck.getSize() > 0) {
            Card drawnCard = deck.getCards().remove(0);
            deck.setSize(deck.getSize() - 1);
            return drawnCard;
        } else {
            System.out.println("Deck is empty");
            return null;
        }
    }

    @Override
    public void play() {

    }

}
