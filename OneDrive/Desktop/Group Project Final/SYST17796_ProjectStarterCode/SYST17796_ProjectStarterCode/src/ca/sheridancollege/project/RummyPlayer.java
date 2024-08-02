/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author gcbho
 */
public class RummyPlayer extends Player {
    
     private Hand hand;
    
    public RummyPlayer(String name){
        super(name);
        hand = new Hand();
    }
    
    public Hand getHand(){
        return hand;
    }
    
    @Override
    public void play(){
        
    }
    
    
}
