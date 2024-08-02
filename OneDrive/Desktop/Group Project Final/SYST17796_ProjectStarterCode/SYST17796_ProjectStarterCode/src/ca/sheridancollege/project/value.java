package ca.sheridancollege.project;

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
    
    private final int points;
    
    private Value(int points) {
        this.points = points;
    }
    
    public int getPoints() {
        return points;
    }
}
