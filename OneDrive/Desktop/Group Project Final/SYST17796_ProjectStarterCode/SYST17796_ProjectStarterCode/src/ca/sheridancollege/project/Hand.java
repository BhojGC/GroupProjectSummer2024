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
/**
 * method used to add card either to the players hand or the discard pile.
 * @param card 
 */
    public void addCard(Card card) {
        getCards().add(card);
        setSize(getCards().size());
    }
/**
 * method used to discard the selected card to the discard pile.
 * @param card 
 */
    public void discardCard(Card card) {
        getCards().remove(card);
        setSize(getSize() - 1);
    }
    
    /**
     * This method is responsible for getting all pure sequences from the players hand.
     * A pure sequence will consist of cards of the same suit in consecutive order.
     * @returns A list of pure sequence cards.
     */

    public List<Card> getPureSequences() {
        List<Card> pureSequences = new ArrayList<>();

        // Group cards by suit
        Map<Suit, List<PlayingCard>> cardsBySuit = getCards().stream()
                .map(card -> (PlayingCard) card)
                .collect(Collectors.groupingBy(PlayingCard::getSuit));

        for (Map.Entry<Suit, List<PlayingCard>> entry : cardsBySuit.entrySet()) {
            List<PlayingCard> suitCards = entry.getValue();
            suitCards.sort(Comparator.comparingInt(card -> card.getValue().getPoints()));

            List<Card> currentSequence = new ArrayList<>();
            for (PlayingCard card : suitCards) {
                if (currentSequence.isEmpty() || isConsecutive(card, (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                    currentSequence.add(card);
                } else {
                    // Check if the current sequence is valid before clearing
                    addValidSequence(pureSequences, currentSequence);
                    currentSequence.clear();
                    currentSequence.add(card);
                }
            }
            // Check the last sequence after the loop
            addValidSequence(pureSequences, currentSequence);
        }

        // Return the pure sequences
        return pureSequences.stream().distinct().collect(Collectors.toList());
    }
    
    /**
     * Method to check if two cards are consecutive
     * Considers Ace as both high and low cards
     * @param card1 the first card
     * @param card2 the second card
     * @return  true if the cards are consecutive, false otherwise.
     */

    public boolean isConsecutive(PlayingCard card1, PlayingCard card2) {
        int seqValue1 = getSequentialValue(card1.getValue());
        int seqValue2 = getSequentialValue(card2.getValue());

        // Check if the two cards are consecutive
        return Math.abs(seqValue1 - seqValue2) == 1
                || (seqValue1 == 1 && seqValue2 == 13)
                || (seqValue1 == 13 && seqValue2 == 1)
                || (seqValue1 == 1 && seqValue2 == 2);
    }
    
    /**
     * This method converts a card to its sequential numerical value
     * @param value the value of the card.
     * @return  the sequential value of the card.
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
     * the following method adds a sequence to the list of pureSequences if it is valid.
     * A valid sequence has at least 3 cards.
     * @param pureSequences the list to add to the valid sequence.
     * @param sequence the sequence to be checked and added.
     */

    private void addValidSequence(List<Card> pureSequences, List<Card> sequence) {
        if (sequence.size() >= 3) {
            pureSequences.addAll(sequence);
        }

    }
    
    /**
     * This method gets all the impure sequences from the hand.
     * An impure sequence may consist of consecutive cards in value but from different suits.
     * @return 
     */

    public List<Card> getImpureSequence() {
        // Retrieve pure sequences and remove them from the hand
        List<Card> pureSequences = getPureSequences();
        List<Card> remainingCards = new ArrayList<>(getCards());
        remainingCards.removeAll(pureSequences);

        List<Card> impureSequences = new ArrayList<>();
        List<Card> currentSequence = new ArrayList<>();

        remainingCards.sort(Comparator.comparingInt(card -> getSequentialValue(((PlayingCard) card).getValue())));

        for (Card card : remainingCards) {
            if (currentSequence.isEmpty() || isImpureConsecutive((PlayingCard) card, (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                currentSequence.add(card);
            } else {
                // If sequence breaks, check if it's valid
                if (currentSequence.size() >= 3) {
                    impureSequences.addAll(currentSequence);
                }
                currentSequence.clear();
                currentSequence.add(card);
            }
        }

        // Check the last sequence
        if (currentSequence.size() >= 3) {
            impureSequences.addAll(currentSequence);
        }

        return impureSequences.stream().distinct().collect(Collectors.toList());
    }
    
    /**
     * Checks if two cards are impure consecutive.
     * cards must be consecutive in value and from different suits.
     * 
     * @param card1
     * @param card2
     * @return  true if the cards are impure consecutive, false otherwise.
     */

    private boolean isImpureConsecutive(PlayingCard card1, PlayingCard card2) {
        int seqValue1 = getSequentialValue(card1.getValue());
        int seqValue2 = getSequentialValue(card2.getValue());

        // Cards must be consecutive in value and from different suits
        return Math.abs(seqValue1 - seqValue2) == 1
                || (seqValue1 == 1 && seqValue2 == 13) // Ace high/low
                || (seqValue1 == 13 && seqValue2 == 1)
                && card1.getSuit() != card2.getSuit(); // Ensure different suits
    }
    
    /**
     * Checks if the hand is valid.
     * A valid hand must have at least one pure sequence of 3 or more cards.
     * Remaining cards must form valid impure sequences or sets.
     * @return true if the hand is valid, false otherwise.
     */

    public boolean isValidHand() {
    List<Card> pureSequences = getPureSequences();
    List<Card> impureSequences = getImpureSequence();

    // Check for at least one pure sequence of 3 or more cards
    boolean hasPureSequence = pureSequences.size() >= 3;

    // Remaining cards after forming pure sequences
    List<Card> remainingCards = new ArrayList<>(getCards());
    for (Card card : pureSequences) {
        remainingCards.remove(card);
    }

    // Check if the remaining cards can form valid impure sequences or sets
    boolean hasValidImpureSequences = false;
    if (!remainingCards.isEmpty()) {
        // Verify if remaining cards can form valid impure sequences or sets
        hasValidImpureSequences = canFormValidCombinations(remainingCards);
    }

    // A valid hand must have:
    // - At least one pure sequence of 3 or more cards
    // - Remaining cards must form valid impure sequences or sets
    return hasPureSequence && (remainingCards.isEmpty() || hasValidImpureSequences);
}
    /**
     * The following method checks if the remaining cards can form valid combinations.
     * Valid combinations include sets (3 or more cards of the same rank) or sequences.
     * @param remainingCards
     * @return 
     */


    private boolean canFormValidCombinations(List<Card> remainingCards) {
        if (remainingCards == null || remainingCards.size() < 3) {
            return false; // Not enough cards to form valid combinations
        }

        // Sort cards by rank
        remainingCards.sort(Comparator.comparingInt(card -> ((PlayingCard) card).getValue().ordinal()));

        // To form valid sets, cards should be grouped by their rank
        Map<Value, List<Card>> rankGroups = remainingCards.stream()
                .collect(Collectors.groupingBy(card -> ((PlayingCard) card).getValue()));

        // Check if we can form sets (at least 3 cards of the same rank)
        boolean canFormSets = rankGroups.values().stream()
                .anyMatch(group -> group.size() >= 3);

        // Check for valid sequences (runs)
        boolean canFormSequences = false;
        List<Card> sequence = new ArrayList<>();

        for (int i = 0; i < remainingCards.size(); i++) {
            if (sequence.isEmpty()) {
                sequence.add(remainingCards.get(i));
            } else {
                Card lastCard = sequence.get(sequence.size() - 1);
                if (isConsecutive((PlayingCard) remainingCards.get(i), (PlayingCard) lastCard)) {
                    sequence.add(remainingCards.get(i));
                } else {
                    if (sequence.size() >= 3) {
                        canFormSequences = true; // Found a valid sequence
                        break;
                    }
                    sequence.clear();
                    sequence.add(remainingCards.get(i));
                }
            }
        }

        // Check if the last sequence is valid
        if (sequence.size() >= 3) {
            canFormSequences = true;
        }

        return canFormSets || canFormSequences;
    }
    
    
    /**
     * method used by the player to declare hand.
     * @return 
     */

    public boolean declareHand() {
        return isValidHand();
    }

    @Override
    public String toString() {

        return null;
    }

}
