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

        // Sort cards by suit, then by value
        List<PlayingCard> sortedCards = getCards().stream()
                .map(card -> (PlayingCard) card)
                .sorted(Comparator.comparing(PlayingCard::getSuit)
                        .thenComparing(card -> card.getValue().getPoints()))
                .collect(Collectors.toList());

        List<Card> currentSequence = new ArrayList<>();
        Suit currentSuit = null;

        for (PlayingCard card : sortedCards) {
            if (currentSuit == null || card.getSuit() != currentSuit
                    || !isConsecutive(card, (PlayingCard) currentSequence.get(currentSequence.size() - 1))) {
                // If the suit changes or the sequence breaks, process the current sequence
                if (currentSequence.size() >= 3) {
                    pureSequences.addAll(currentSequence);
                }
                // Start a new sequence
                currentSequence.clear();
                currentSuit = card.getSuit();
            }
            currentSequence.add(card);
        }

        // Check the last sequence
        if (currentSequence.size() >= 3) {
            pureSequences.addAll(currentSequence);
        }

        return pureSequences.stream().distinct().collect(Collectors.toList());
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
                || (seqValue1 == 13 && seqValue2 == 1);

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

        // Ensure there is at least one pure sequence of 3 or more cards
        if (pureSequences.size() >= 3) {
            // Create a list of all cards used in pure and impure sequences
            List<Card> usedCards = new ArrayList<>(pureSequences);
            usedCards.addAll(impureSequences);

            // Create a list of remaining cards in the hand
            List<Card> remainingCards = new ArrayList<>(getCards());
            remainingCards.removeAll(usedCards);

            // If there are any remaining cards, they must form valid impure sequences or sets
            if (canFormValidCombinations(remainingCards)) {
                return true;
            }
        }

        // If the conditions aren't met, the hand is not valid
        return false;
    }

    /**
     * The following method checks if the remaining cards can form valid
     * combinations. Valid combinations include sets (3 or more cards of the
     * same rank) or sequences.
     *
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
        List<List<Card>> sequences = findSequences(remainingCards);

        // Check if sequences or sets are valid
        boolean hasValidSequences = sequences.stream().anyMatch(seq -> seq.size() >= 3);

        return canFormSets || hasValidSequences;
    }

    /**
     * Helper method to find all possible sequences (runs) in the given list of
     * cards.
     */
    private List<List<Card>> findSequences(List<Card> cards) {
        List<List<Card>> sequences = new ArrayList<>();
        List<Card> currentSequence = new ArrayList<>();

        for (int i = 0; i < cards.size(); i++) {
            if (currentSequence.isEmpty()) {
                currentSequence.add(cards.get(i));
            } else {
                Card lastCard = currentSequence.get(currentSequence.size() - 1);
                if (isConsecutive((PlayingCard) cards.get(i), (PlayingCard) lastCard)) {
                    currentSequence.add(cards.get(i));
                } else {
                    if (currentSequence.size() >= 3) {
                        sequences.add(new ArrayList<>(currentSequence)); // Add the current sequence
                    }
                    currentSequence.clear();
                    currentSequence.add(cards.get(i));
                }
            }
        }

        // Add the last sequence if it's valid
        if (currentSequence.size() >= 3) {
            sequences.add(currentSequence);
        }

        return sequences;
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

    /*public boolean canForm() {
        List<Card> pureSequences = getPureSequences();
        List<Card> impureSequences = getImpureSequence();

        if (pureSequences.size() % 3 == 0 && pureSequences.size() > 0) 1{
            return true;
        } else if (pureSequences.size() == 6 && impureSequences.size() == 4) 2{
            return true;
        } else if (pureSequences.size() == 5 && impureSequences.size() == 5)3 {
            return true;
        } else if (pureSequences.size() == 4 && impureSequences.size() == 6)4 {
            return true;
        } else if (pureSequences.size() == 7 && impureSequences.size() == 3)5 {
            return true;
        } else if (pureSequences.size() == 3 && impureSequences.size() == 7) 6{
            return true;
        }
        return false;
    }
     */
    public boolean canForm() {
        List<Card> pureSequences = getPureSequences();
        List<Card> impureSequences = getImpureSequence();

        // Count pure sequences of at least 3 cards
        int pureCount = pureSequences.size();
        int impureCount = impureSequences.size();

        // Check for valid combinations
        if (pureCount >= 3 && (pureCount + impureCount) == 10) {
            return true;
        }

        return false;
    }

}
