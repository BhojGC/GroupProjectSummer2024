/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

/**
 *
 * @author gcbho
 */
public class TenCardRummyGame extends Game{
     private GroupOfCards deck;
    private GroupOfCards discardPile;
    private RummyPlayer player1;
    private RummyPlayer player2;
    private int player1Points;
    private int player2Points;
    
    public TenCardRummyGame(String name){
        super(name);
        intiializeGame();
    }
    
    public void intiializeGame(){
        ArrayList<PlayingCard> cardList = createDeck();
        this.deck = new GroupOfCards(cardList);
        
        this.discardPile = new GroupOfCards(new ArrayList<>());
          
    
    
}
    
public ArrayList<PlayingCard> createDeck(){
 ArrayList<PlayingCard> deck = new ArrayList<>();
    for(Suit suit : Suit.values()){
        for(Value value : Value.values()){
            deck.add(new PlayingCard(suit, value));
        }
    }
    return deck;
}

public void dealCards(RummyPlayer player1, RummyPlayer player2) {
        for (int i = 0; i < 20; i++) {
            Card card = deck.getCards().get(i);

            if (i % 2 != 0) { // Odd index - deal to player1
                player1.getHand().addCard(card);
            } else { // Even index - deal to player2
                player2.getHand().addCard(card);
            }
        }
    }

@Override
public void play(){
    
}

public boolean declareHand(RummyPlayer player){
    return false;
}

public boolean verifyDeclaration(RummyPlayer player){
    return false;
}

public void endRound(RummyPlayer player){
    
}

@Override
public void declareWinner(){
    
}
    
}
