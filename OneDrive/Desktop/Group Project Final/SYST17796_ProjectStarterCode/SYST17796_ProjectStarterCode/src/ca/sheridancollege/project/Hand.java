/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author gcbho
 */
public class Hand extends GroupOfCards {
    
    private List<Card> cards;
    private List<Value> value;
    
    public Hand(){
        super(0);
        
    }
    
    public void addCard(Card card){
        getCards().add(card);
    }
    
    public void discardCard(Card card){
        getCards().remove(card);
        setSize(getSize()-1);
    }
    
public List<Card> getPureSequence(){
    List<Card> pureSequences = new ArrayList<>();
    //Group cards by suit
    Map<Suit, List<Card>> cardsBySuit = cards.stream()
                .collect(Collectors.groupingBy(Card::getSuit));
    //Iterating through each suit to find sequence
        for (Map.Entry<Suit, List<Card>> entry : cardsBySuit.entrySet()) {
            Suit suit = entry.getKey();
            List<Card> suitCards = entry.getValue();

            // Sort the cards in ascending order by their value
            Collections.sort(suitCards, (c1, c2) -> Integer.compare(c1.getValue().getPoints(), c2.getValue().getPoints()));

            // Find sequences within the sorted list
            List<Card> currentSequence = new ArrayList<>();
            for (int i = 0; i < suitCards.size(); i++) {
                if (currentSequence.isEmpty()) {
                    currentSequence.add(suitCards.get(i));
                } else {
                    Card lastCard = currentSequence.get(currentSequence.size() - 1);
                    Card currentCard = suitCards.get(i);

                    // Check if the current card is the next consecutive value
                    if (currentCard.getValue().getPoints() == lastCard.getValue().getPoints() + 1) {
                        currentSequence.add(currentCard);
                    } else {
                        // If sequence breaks, check if it is valid and reset
                        if (currentSequence.size() >= 3) {
                            pureSequences.addAll(currentSequence);
                        }
                        currentSequence.clear();
                        currentSequence.add(currentCard);
                    }
                }
            }

            // Check the last sequence
            if (currentSequence.size() >= 3) {
                pureSequences.addAll(currentSequence);
            }
        }

        return pureSequences;
    }


public List<Card> getImpureSequence(){
    
    return null;
}

public boolean isValidHand(){
    return false;
}

@Override
public String toString(){
    
    
    return null;
}
    
}
