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
        //Sorting all cards regardless of suit
        allCards.sort(Comparator.comparingInt(card -> ((PlayingCard) card)
                .getValue()
                .getPoints()));
        //find impuresequences within the sorted List
        List<Card> currentSequence = new ArrayList<>();
        for (Card card : allCards) {
            if (currentSequence.isEmpty() || isImpureConsecutive((PlayingCard) card, (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                currentSequence.add(card);
            } else {
                //If sequence breaks, check if it is valid and reset

                if (currentSequence.size() >= 3 && !isPureSequence(currentSequence)) {
                    impureSequence.addAll(currentSequence);
                }
                currentSequence.clear();
                currentSequence.add(card);
            }
        }
        // check the last sequence
        if (currentSequence.size() >= 3 && !isPureSequence(currentSequence)) {
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
           if (Math.abs(seqValue1 - seqValue2) == 1) {
            return true;
        }

        // Case where Ace is treated as "1"
        if (seqValue1 == 1 && seqValue2 == 2) {
            return true;
        }
        //case where Ace is treated as 1 and checking consecutive with face cards
        if (seqValue1 == 1 && seqValue2 == 14) {
            return true;
        }
        //Case where Ace is Treated as high after King
        if (seqValue1 == 14 && seqValue2 == 1) {
            return true;
        }
        //case where Jack, Queen and King are treated as 11, 12, 13
        if (seqValue1 == 11 && seqValue2 == 12) {
            return true;
        }
        if (seqValue1 == 12 && seqValue2 == 13) {
            return true;
        }

        if (seqValue1 == 13 && seqValue2 == 11) {
            return true;
        }
     
        return false;

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
    List<List<Card>> pureSequences = getPureSequences();
    List<List<Card>> impureSequences = getImpureSequence();

    // Check for at least one valid sequence of 3 or 4 cards
    boolean hasValidSequence = false;
    for (List<Card> seq : pureSequences) {
        if (seq.size() == 3 || seq.size() == 4) {
            hasValidSequence = true;
            break;
        }
    }
    if (!hasValidSequence) {
        for (List<Card> seq : impureSequences) {
            if (seq.size() == 3 || seq.size() == 4) {
                hasValidSequence = true;
                break;
            }
        }
    }

    // Check for one impure sequence of 4 cards
    boolean hasImpureSequenceOf4 = false;
    for (List<Card> seq : impureSequences) {
        if (seq.size() == 4) {
            hasImpureSequenceOf4 = true;
            break;
        }
    }

    // Check for one impure sequence of 3 cards
    boolean hasImpureSequenceOf3 = false;
    for (List<Card> seq : impureSequences) {
        if (seq.size() == 3) {
            hasImpureSequenceOf3 = true;
            break;
        }
    }

    // Return true if all conditions are met
    return hasValidSequence && hasImpureSequenceOf4 && hasImpureSequenceOf3;
}



    @Override
    public String toString() {

        return null;
    }

}
