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

    private boolean isConsecutive(PlayingCard card1, PlayingCard card2) {
        int seqValue1 = getSequentialValue(card1.getValue());
        int seqValue2 = getSequentialValue(card2.getValue());

        // Check if the two cards are consecutive
        return Math.abs(seqValue1 - seqValue2) == 1
                || (seqValue1 == 1 && seqValue2 == 13)
                || (seqValue1 == 13 && seqValue2 == 1);
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

    private void addValidSequence(List<Card> pureSequences, List<Card> sequence) {
        if (sequence.size() >= 3) {
            pureSequences.addAll(sequence);
        }

    }

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

    private boolean isImpureConsecutive(PlayingCard card1, PlayingCard card2) {
        int seqValue1 = getSequentialValue(card1.getValue());
        int seqValue2 = getSequentialValue(card2.getValue());

        // Cards must be consecutive in value and from different suits
        return Math.abs(seqValue1 - seqValue2) == 1
                || (seqValue1 == 1 && seqValue2 == 13) // Ace high/low
                || (seqValue1 == 13 && seqValue2 == 1)
                && card1.getSuit() != card2.getSuit(); // Ensure different suits
    }

    public boolean isValidHand() {
        List<Card> pureSequences = getPureSequences();
        List<Card> impureSequences = getImpureSequence();

        // Check for at least one pure sequence of 3 or more cards
        boolean hasPureSequence = pureSequences.size() >= 3;

        // Check if we can divide impure sequences into two valid groups
        boolean hasValidImpureSequences = false;
        if (impureSequences.size() >= 7) { // Needs at least 7 cards to form two sequences (4 + 3)
            int sequenceOfFourCount = 0;
            int sequenceOfThreeCount = 0;

            // Count sequences of 4 and 3 cards in impure sequences
            for (int i = 0; i < impureSequences.size(); i++) {
                List<Card> currentSequence = new ArrayList<>();
                currentSequence.add(impureSequences.get(i));

                for (int j = i + 1; j < impureSequences.size(); j++) {
                    if (isImpureConsecutive((PlayingCard) impureSequences.get(j), (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                        currentSequence.add(impureSequences.get(j));
                    }
                }

                if (currentSequence.size() >= 3) {
                    if (currentSequence.size() == 4) {
                        sequenceOfFourCount++;
                    } else if (currentSequence.size() == 3) {
                        sequenceOfThreeCount++;
                    }
                }
            }

            // Check if we have one sequence of 4 cards and one sequence of 3 cards
            hasValidImpureSequences = sequenceOfFourCount >= 1 && sequenceOfThreeCount >= 1;
        }

        // A valid hand must have:
        // - At least one pure sequence of 3 or more cards
        // - One sequence of 4 cards and one sequence of 3 cards from impure sequences
        return hasPureSequence && hasValidImpureSequences;
    }
    
    public boolean declareHand() {
        return isValidHand();
    }

    @Override
    public String toString() {

        return null;
    }

}
