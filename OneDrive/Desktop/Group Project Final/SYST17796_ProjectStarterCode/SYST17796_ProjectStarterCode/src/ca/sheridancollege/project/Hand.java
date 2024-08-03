/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author gcbho
 */
public class Hand extends GroupOfCards {

    public Hand() {
        super(0);

    }

    public void addCard(Card card) {
        getCards().add(card);
        setSize(getCards().size());
    }

    public void discardCard(Card card) {
        getCards().remove(card);
        setSize(getSize() - 1);
    }

    /**
     * Returns a list of cards that form pure sequences. A pure sequence is a
     * sequence of three or more cards of the same suit in consecutive order.
     *
     * @return a list of cards that form pure sequences
     */
    public List<Card> getPureSequences() {
        List<Card> pureSequences = new ArrayList<>();

        // Group cards by suit
        Map<Suit, List<PlayingCard>> cardsBySuit = getCards().stream()
                .map(card -> (PlayingCard) card)
                .collect(Collectors.groupingBy(PlayingCard::getSuit));

        // Iterate through each suit to find sequences
        for (Map.Entry<Suit, List<PlayingCard>> entry : cardsBySuit.entrySet()) {
            List<PlayingCard> suitCards = entry.getValue();

            // Sort the cards in ascending order by their value
            suitCards.sort(Comparator.comparingInt(card -> ((PlayingCard) card).getValue().getPoints()));

            // Find sequences within the sorted list
            List<Card> currentSequence = new ArrayList<>();
            for (Card card : suitCards) {
                if (currentSequence.isEmpty() || isConsecutive((PlayingCard) card, (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                    currentSequence.add(card);
                } else {
                    // If sequence breaks, check if it is valid and reset
                    addValidSequence(pureSequences, currentSequence);
                    currentSequence.clear();
                    currentSequence.add(card);
                }
            }

            // Check the last sequence
            addValidSequence(pureSequences, currentSequence);
        }

        return pureSequences;
    }

    /**
     * Checks if two cards are consecutive.
     *
     * @param card1 the first card
     * @param card2 the second card
     * @return true if the cards are consecutive, false otherwise
     */
    private boolean isConsecutive(PlayingCard card1, PlayingCard card2) {
        return card1.getValue().getPoints() == card2.getValue().getPoints() + 1;
    }

    /**
     * Adds a valid sequence to the list of pure sequences.
     *
     * @param pureSequences the list of pure sequences
     * @param sequence the sequence to add
     */
    private void addValidSequence(List<Card> pureSequences, List<Card> sequence) {
        if (sequence.size() >= 3) {
            pureSequences.addAll(sequence);
        }
    }

    public List<Card> getImpureSequence() {
        List<Card> impureSequence = new ArrayList<>();
        List<Card> allCards = getCards()
                .stream()
                .map(card -> (PlayingCard) card)
                .collect(Collectors.toList());
        //Sorting all cards regardless of suit
        allCards.sort(Comparator.comparingInt(card -> ((PlayingCard) card)
                .getValue()
                .getPoints()));
        //find impuresequences within the sorted List
        List<Card> currentSequence = new ArrayList<>();
        for (Card card : allCards) {
            if (currentSequence.isEmpty() || isConsecutive((PlayingCard) card, (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                currentSequence.add(card);
            } else {
                //If sequence breaks, check if it is valid and reset

                if (currentSequence.size() >= 3) {
                    impureSequence.addAll(currentSequence);
                }
                currentSequence.clear();
                currentSequence.add(card);
            }
        }
        // check the last sequence
        if (currentSequence.size() >= 3) {
            impureSequence.addAll(currentSequence);
        }

        //Checking the cards with same value irrespective of suit
        Map<Value, List<PlayingCard>> cardsByValue = allCards.stream()
                .map(card -> (PlayingCard) card)
                .collect(Collectors.groupingBy(PlayingCard::getValue));

        for (Map.Entry<Value, List<PlayingCard>> entry : cardsByValue.entrySet()) {
            List<PlayingCard> sameValueCards = entry.getValue();

            if (sameValueCards.size() >= 3) {
                impureSequence.addAll(sameValueCards);
            }
        }

        return impureSequence;
    }

    /*public boolean isValidHand(){
    List<Card> pureSequences = getPureSequences();
    List<Card> impureSequences = getImpureSequence();
    
    // checking for at leat one pure sequence
    
    boolean hasPureSequence = !pureSequences.isEmpty();
    
    //chcking for at least two sequences (either pure or impure)
    boolean hasAtleastTwoSequences = (pureSequences.size() >=3 && impureSequences.size() >=3) ||
                                                            (pureSequences.size() >=6) || (impureSequences.size() >=6);
    
    // A valid hand must have at least one pure sequence and at least one additional sequence (pure or impure)
    return hasPureSequence && hasAtleastTwoSequences;
}
     */
    public boolean isValidHand() {
        List<Card> pureSequences = getPureSequences();
        List<Card> impureSequences = getImpureSequence();

        //checking for at least one pure sequence
        boolean hasPureSequence = !pureSequences.isEmpty();

        //checking if at least one valid additional sequence
        boolean hasAdditionalSequence = (pureSequences.size() >= 4 || impureSequences.isEmpty() || impureSequences.size() >= 3);

        //check if there is at least one valid additional sequence(pure sequence or impure sequence or group of 3 value cards
        boolean hasAdditionalValidSequence = hasPureSequence || !impureSequences.isEmpty() || impureSequences.size() >= 3;

        return hasPureSequence && hasAdditionalSequence && hasAdditionalValidSequence;
    }

    @Override
    public String toString() {

        return null;
    }

}
