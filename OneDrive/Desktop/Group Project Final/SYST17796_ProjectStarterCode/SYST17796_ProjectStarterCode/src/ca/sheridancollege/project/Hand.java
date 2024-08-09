/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author gcbho
 */
public class Hand extends GroupOfCards {

    public Hand() {
        super(0);

    }

    /**
     * method used to add card either to the players hand or the discard pile.
     *
     * @param card
     */
    public void addCard(Card card) {
        getCards().add(card);
        setSize(getCards().size());
    }

    /**
     * method used to discard the selected card to the discard pile.
     *
     * @param card
     */
    public void discardCard(Card card) {
        getCards().remove(card);
        setSize(getSize() - 1);
    }

    /*
    /**
     * This method is responsible for getting all pure sequences from the
     * players hand. A pure sequence will consist of cards of the same suit in
     * consecutive order.
     *
     * @returns A list of pure sequence cards.
     */
    public List<Card> getPureSequences() {
        List<Card> pureSequences = new ArrayList<>();
        List<Card> allCards = new ArrayList<>(getCards());

        // Sort the cards by suit and value
        List<PlayingCard> sortedCards = allCards.stream()
                .map(card -> (PlayingCard) card)
                .sorted(Comparator.comparing(PlayingCard::getSuit)
                        .thenComparing(card -> card.getValue().getPoints()))
                .collect(Collectors.toList());

        // Identify pure sequences
        for (int i = 0; i < sortedCards.size() - 2; i++) {
            for (int j = i + 1; j < sortedCards.size() - 1; j++) {
                for (int k = j + 1; k < sortedCards.size(); k++) {
                    PlayingCard card1 = sortedCards.get(i);
                    PlayingCard card2 = sortedCards.get(j);
                    PlayingCard card3 = sortedCards.get(k);

                    // Check if cards form a pure sequence
                    if (card1.getSuit() == card2.getSuit()
                            && card2.getSuit() == card3.getSuit()
                            && isConsecutive(card1, card2)
                            && isConsecutive(card2, card3)) {
                        if (!pureSequences.contains(card1)) {
                            pureSequences.add(card1);
                        }
                        if (!pureSequences.contains(card2)) {
                            pureSequences.add(card2);
                        }
                        if (!pureSequences.contains(card3)) {
                            pureSequences.add(card3);
                        }
                    }
                }
            }
        }

        return pureSequences;
    }

    /**
     * Method to check if two cards are consecutive Considers Ace as both high
     * and low cards
     *
     * @param card1 the first card
     * @param card2 the second card
     * @return true if the cards are consecutive, false otherwise.
     */
    public boolean isConsecutive(PlayingCard card1, PlayingCard card2) {
        int seqValue1 = getSequentialValue(card1.getValue());
        int seqValue2 = getSequentialValue(card2.getValue());

        // Ensure cards are of the same suit
        if (card1.getSuit() != card2.getSuit()) {
            return false;
        }

        // Check if the cards are consecutive in value
        return Math.abs(seqValue1 - seqValue2) == 1
                || (seqValue1 == 1 && seqValue2 == 13) // Ace high
                || (seqValue1 == 13 && seqValue2 == 1); // Ace low
    }

    /**
     * This method converts a card to its sequential numerical value
     *
     * @param value the value of the card.
     * @return the sequential value of the card.
     */
    private int getSequentialValue(Value value) {
        switch (value) {
            case ACE:
                return 1; // Ace as "1"
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case JACK:
                return 11;
            case QUEEN:
                return 12;
            case KING:
                return 13;
            default:
                throw new IllegalArgumentException("Unknown card value: " + value);
        }
    }

    /**
     * This method gets all the impure sequences from the hand. An impure
     * sequence may consist of consecutive cards in value but from different
     * suits.
     *
     * @return
     */
    public List<Card> getImpureSequence() {
        List<Card> impureSequences = new ArrayList<>();
        List<Card> allCards = getCards();

        // Get the pure sequences first
        List<Card> pureSequences = getPureSequences();
        Set<Card> pureSet = new HashSet<>(pureSequences);

        // All cards not part of any pure sequence are considered impure
        for (Card card : allCards) {
            if (!pureSet.contains(card)) {
                impureSequences.add(card);
            }
        }

        return impureSequences;
    }

    /**
     * Checks if two cards are impure consecutive. cards must be consecutive in
     * value and from different suits.
     *
     * @param card1
     * @param card2
     * @return true if the cards are impure consecutive, false otherwise.
     */
    public boolean isImpureConsecutive(PlayingCard card1, PlayingCard card2) {
        int seqValue1 = getSequentialValue(card1.getValue());
        int seqValue2 = getSequentialValue(card2.getValue());

        // Cards must be consecutive in value and from different suits
        return Math.abs(seqValue1 - seqValue2) == 1
                || (seqValue1 == 1 && seqValue2 == 13) // Ace high/low
                || (seqValue1 == 13 && seqValue2 == 1)
                || (card1.getValue() == card2.getValue());

    }

    /**
     * Checks if the hand is valid. A valid hand must have at least one pure
     * sequence of 3 or more cards. Remaining cards must form valid impure
     * sequences or sets.
     *
     * @return true if the hand is valid, false otherwise.
     */
    public boolean isValidHand() {
        List<Card> pureSequences = getPureSequences();
        List<Card> impureSequences = getImpureSequence();

        //Count pure sequences of at least 3 cards
        int pureCount = pureSequences.size();
        int impureCount = impureSequences.size();
        if (pureCount >= 3 && (pureCount + impureCount) == 10) {
            return true;
        } else if (pureSequences.size() == 3 && impureSequences.size() == 7) {
            return true;
        } else if (pureSequences.size() == 6 && impureSequences.size() == 4) {
            return true;
        } else if (pureSequences.size() == 4 && impureSequences.size() == 6) {
            return true;
        }
        return false;

    }

      /**
     * method used by the player to declare hand.
     *
     * @return
     */
    public boolean declareHand() {
        return isValidHand();
    }

    @Override
    public String toString() {

        return null;
    }

    /*
    public boolean isValidHand() {
        List<Card> pureSequences = getPureSequences();
        List<Card> impureSequences = getImpure();

        // Count pure sequences of at least 3 cards
        int pureCount = pureSequences.size();
        int impureCount = impureSequences.size();

        // Check for valid combinations
        return pureCount >= 3 && (pureCount + impureCount) == 10;
    }*/
 /*
    public boolean isValidHand(){
         List<Card> pureSequences = getPureSequences();
         List<Card> impureSequences = getImpure();
        if (pureSequences.size() % 3 == 0 && pureSequences.size() > 0) {
        return true;
    } else if (pureSequences.size() == 6 && impureSequences.size() == 4) {
        return true;
    } else if (pureSequences.size() == 5 && impureSequences.size() == 5) {
        return true;
    } else if (pureSequences.size() == 4 && impureSequences.size() == 6) {
        return true;
    } else if (pureSequences.size() == 7 && impureSequences.size() == 3) {
        return true;
    } else if (pureSequences.size() == 3 && impureSequences.size() == 7) {
        return true;
    }
    return false;
    }
     */
    public List<Card> getImpure() {
        List<Card> impureSequence = new ArrayList<>();
        List<Card> allCards = new ArrayList<>(getCards());
        List<Card> pureSequences = getPureSequences();

        // Remove pure sequences from all cards
        allCards.removeAll(pureSequences);

        // Sort the remaining cards
        List<PlayingCard> sortedCards = allCards.stream()
                .map(card -> (PlayingCard) card)
                .sorted(Comparator.comparing(PlayingCard::getSuit)
                        .thenComparing(card -> card.getValue().getPoints()))
                .collect(Collectors.toList());

        // Identify impure sequences
        for (int i = 0; i < sortedCards.size() - 2; i++) {
            for (int j = i + 1; j < sortedCards.size() - 1; j++) {
                for (int k = j + 1; k < sortedCards.size(); k++) {
                    PlayingCard card1 = sortedCards.get(i);
                    PlayingCard card2 = sortedCards.get(j);
                    PlayingCard card3 = sortedCards.get(k);

                    // Check if cards form an impure sequence
                    if (isImpureConsecutive(card1, card2) && isImpureConsecutive(card2, card3)) {
                        if (!impureSequence.contains(card1)) {
                            impureSequence.add(card1);
                        }
                        if (!impureSequence.contains(card2)) {
                            impureSequence.add(card2);
                        }
                        if (!impureSequence.contains(card3)) {
                            impureSequence.add(card3);
                        }
                    }
                }
            }
        }

        return impureSequence;
    }

}
