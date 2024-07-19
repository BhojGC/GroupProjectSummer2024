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
    
    
}
    
public ArrayList<Card> createDeck(){
    return null;
}

private void dealCards(){
    
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