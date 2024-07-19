/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gcbho
 */
class Hand extends GroupOfCards {
    
    public Hand(){
        super(0);
       // this.setCard(new ArrayList<>());
    }
    
    public void addCard(Card card){
        getCards().add(card);
    }
    
    public void discardCard(Card card){
        getCards().remove(card);
        setSize(getSize()-1);
    }
    
public List<Card> getPureSequence(){
    
    return null;
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
    
    
    

