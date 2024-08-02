/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gcbho
 */
public enum Value {
    ACE(10), 
    TWO(2), 
    THREE(3), 
    FOUR(4), 
    FIVE(5), 
    SIX(6), 
    SEVEN(7), 
    EIGHT(8), 
    NINE(9), 
    TEN(10), 
    JACK(10), 
    QUEEN(10), 
    KING(10);
    
    public final int points;
    
    private Value(int points){
        this.points = points;
    }
    
    public int getPoints(){
        return points;
    }
    
}
