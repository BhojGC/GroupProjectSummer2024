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
    for (List<PlayingCard> suitCards : cardsBySuit.values()) {
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
                currentSequence = new ArrayList<>();
                currentSequence.add(card);
            }
        }
        // Check the last sequence
        addValidSequence(pureSequences, currentSequence);
    }

    return pureSequences.stream().distinct().collect(Collectors.toList());
}


    /**
     * Checks if two cards are consecutive.
     *
     * @param card1 the first card
     * @param card2 the second card
     * @return true if the cards are consecutive, false otherwise
     */
    private boolean isConsecutive(PlayingCard card1, PlayingCard card2) {
        Value value1 = card1.getValue();
        Value value2 = card2.getValue();

        // Convert face cards to their sequential values
        int seqValue1 = getSequentialValue(value1);
        int seqValue2 = getSequentialValue(value2);

        // Case where Ace is treated as "1"
        if ((seqValue1 == 1 && seqValue2 == 2) || (seqValue1 == 2 && seqValue2 == 1)) {
            return true;
        }

        // Case where Ace is treated as high after King
        if ((seqValue1 == 13 && seqValue2 == 1) || (seqValue1 == 1 && seqValue2 == 13)) {
            return true;
        }

        // General case to check if the cards are numerically consecutive
        return Math.abs(seqValue1 - seqValue2) == 1;
    }

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

        // Sort all cards regardless of suit
        allCards.sort(Comparator.comparingInt(card -> ((PlayingCard) card).getValue().getPoints()));

        // Find impure sequences within the sorted list
        List<Card> currentSequence = new ArrayList<>();
        for (Card card : allCards) {
            if (currentSequence.isEmpty() || isImpureConsecutive((PlayingCard) card, (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                currentSequence.add(card);
            } else {
                // If sequence breaks, check if it is valid and reset
                if (currentSequence.size() >= 3 && !isPureSequence(currentSequence)) {
                    impureSequence.addAll(currentSequence);
                }
                currentSequence = new ArrayList<>();
                currentSequence.add(card);
            }
        }
        // Check the last sequence
        if (currentSequence.size() >= 3 && !isPureSequence(currentSequence)) {
            impureSequence.addAll(currentSequence);
        }

        // Check cards with the same value irrespective of suit
        Map<Value, List<PlayingCard>> cardsByValue = allCards.stream()
                .map(card -> (PlayingCard) card)
                .collect(Collectors.groupingBy(PlayingCard::getValue));

        for (List<PlayingCard> sameValueCards : cardsByValue.values()) {
            if (sameValueCards.size() >= 3) {
                impureSequence.addAll(sameValueCards);
            }
        }

        return impureSequence.stream().distinct().collect(Collectors.toList());
    }

    private boolean isPureSequence(List<Card> sequence) {
        Suit suit = ((PlayingCard) sequence.get(0)).getSuit();
        for (Card card : sequence) {
            if (((PlayingCard) card).getSuit() != suit) {
                return false;
            }
        }
        return true;
    }

    private boolean isImpureConsecutive(PlayingCard card1, PlayingCard card2) {
        Value value1 = card1.getValue();
        Value value2 = card2.getValue();

        // Convert face cards to their sequential values
        int seqValue1 = getSequentialValue(value1);
        int seqValue2 = getSequentialValue(value2);

        // General case to check if the cards are numerically consecutive
        if (Math.abs(seqValue1 - seqValue2) == 1) {
            return true;
        }

        // Case where Ace is treated as "1"
        if (seqValue1 == 1 && seqValue2 == 2) {
            return true;
        }

        // Case where Ace is treated as high after King
        if (seqValue1 == 13 && seqValue2 == 1) {
            return true;
        }

        // Case where face cards are treated as consecutive
        return (seqValue1 == 11 && seqValue2 == 12)
                || (seqValue1 == 12 && seqValue2 == 13)
                || (seqValue1 == 13 && seqValue2 == 11);
    }

    public boolean isValidHand() {
        List<Card> pureCards = getPureSequences(); // This should return List<Card>
        List<Card> impureCards = getImpureSequence(); // This should return List<Card>

        // Check for at least one pure sequence of 3 or 4 cards
        boolean hasPureSequence = pureCards.size() >= 3 && pureCards.size() <= 4;

        // Check for at least one impure sequence of 3 or 4 cards
        boolean hasFirstImpureSequence = impureCards.size() >= 3 && impureCards.size() <= 4;

        // Check for at least one additional impure sequence of exactly 3 cards
        boolean hasSecondImpureSequence = impureCards.size() == 3;

        // A valid hand must have:
        // - At least one pure sequence of 3 or 4 cards
        // - At least one impure sequence of 3 or 4 cards
        // - At least one additional impure sequence of exactly 3 cards
        return hasPureSequence && hasFirstImpureSequence && hasSecondImpureSequence;
    }

    /*
    public boolean isValidHand() {
        List<Card> pureSequences = getPureSequences();
        List<Card> impureSequences = getImpureSequence();
        for(Card seq : pureSequences){
            if(seq.s)
        }

        //checking for at least one pure sequence
        boolean hasPureSequence = !pureSequences.isEmpty();

        //checking if at least one valid additional sequence
        boolean hasAdditionalSequence = (pureSequences.size() >= 4 || impureSequences.isEmpty() || impureSequences.size() >= 3);

        //check if there is at least one valid additional sequence(pure sequence or impure sequence or group of 3 value cards
        boolean hasAdditionalValidSequence = hasPureSequence || !impureSequences.isEmpty() || impureSequences.size() >= 3;

        return hasPureSequence && hasAdditionalSequence && hasAdditionalValidSequence;
    }*/
    @Override
    public String toString() {

        return null;
    }

}
