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
                || (seqValue1 == 13 && seqValue2 == 1)
                || (seqValue1 == 1 && seqValue2 == 2);
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
        boolean hasPureSequence = !pureSequences.isEmpty();

        // Remaining cards after forming pure sequences
        List<Card> remainingCards = new ArrayList<>(getCards());
        for (Card card : pureSequences) {
            remainingCards.remove(card);
        }

        // Check if the remaining cards can form valid impure sequences or sets
        boolean hasValidImpureSequences = false;
        if (remainingCards.size() >= 3) {
            // Simple check: can remaining cards be arranged into sequences or sets
            // Implement logic to verify sequences and sets in remainingCards
            hasValidImpureSequences = canFormValidCombinations(remainingCards);
        }

        // A valid hand must have:
        // - At least one pure sequence of 3 or more cards
        // - Remaining cards must be valid impure sequences or sets
        return hasPureSequence && hasValidImpureSequences;
    }

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

    public boolean declareHand() {
        return isValidHand();
    }

    @Override
    public String toString() {

        return null;
    }

}
