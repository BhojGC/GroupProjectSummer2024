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
    private int player1totalPoints;
    private int player2totalPoints;
    
    public TenCardRummyGame(String name){
        super(name);
        intiializeGame();
    }
    
    public void intiializeGame(){
        ArrayList<PlayingCard> cardList = createDeck();
        this.deck = new GroupOfCards(cardList.size());
        this.deck.getCards().addAll(cardList);
        
        this.discardPile = new GroupOfCards(0);
        
        this.deck.shuffle();
        
        this.player1 = new RummyPlayer("player1");
        this.player2 = new RummyPlayer("player2");
        
        dealCards(player1, player2);
    
    
}
    
public ArrayList<PlayingCard> createDeck(){
 ArrayList<PlayingCard> deck = new ArrayList<>();
    for(Suit suit : Suit.values()){
        for(Value value : Value.values()){
            deck.add(new PlayingCard(suit, value));
        }
    }
    System.out.println("Deck Created");
    for(PlayingCard card: deck){
        System.out.println(card);
    }
    return deck;
     
}

public void dealCards(RummyPlayer player1, RummyPlayer player2) {
        for (int i = 0; i < 20; i++) {
            Card card = deck.getCards().get(i);

            if (i % 2 != 0) { // Odd index - deal to player1
                player1.getHand().addCard(card);
                System.out.println("Player 1 Hand");
                for(Card c : player1.getHand().getCards()){
                    System.out.println(c);
                }
            } else { // Even index - deal to player2
                player2.getHand().addCard(card);
                System.out.println("Player 2 Hand");
                for(Card cd : player2.getHand().getCards()){
                    System.out.println(cd);
                }
            }
        }
    }

@Override
public void play(){
    
}

public boolean declareHand(RummyPlayer player){
    return player.getHand().isValidHand();
}

public boolean verifyDeclaration(RummyPlayer player){
    //verifying if the player declared hand is valid
    boolean isValid = declareHand(player);
    if(isValid){
        return true;
    }else{
        return false;
    }
    
}

public void endRound(RummyPlayer player){
    boolean isValid = verifyDeclaration(player);
    if(isValid){
        System.out.println(player.getName()+" has declard a valid hand");
    }else{
        System.out.println(player.getName() +" has delared a invalid hand");
    }
    
}

@Override
public void declareWinner(){
    
}
    
}
