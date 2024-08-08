/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ca.sheridancollege.project;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gcbho
 */
public class HandTest {
    
    public HandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addCard method, of class Hand.
     */
    @Test
    public void isConsecutiveGood(){
        System.out.println("Testing Consecutive Good Sequence ");
        
        PlayingCard card1 = new PlayingCard(Suit.HEARTS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.HEARTS,Value.TWO);
        Hand hand = new Hand();
        boolean expResult = true;
        boolean result = hand.isConsecutive(card1, card2);
        assertEquals(expResult, result);               
    }
    
    @Test
    public void isConsecutiveBad(){
        System.out.println("Testing Consecutive Bad Sequence");
        
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        Hand hand = new Hand();
        boolean expResult = false;
        boolean result = hand.isConsecutive(card1, card2);
        assertEquals(expResult, result);
    }
    
    @Test
    public void isConsecutiveBoundry(){
        System.out.println("Testing Consecutive Boundry Sequence");
        
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card2 = new PlayingCard(Suit.SPADES, Value.THREE);
        Hand hand = new Hand();
        boolean expResult = true;
        boolean result = hand.isConsecutive(card1, card2);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void isImpureConsecutiveGood(){
        System.out.println("Testing for ImpureConsecutive Good");
        
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.DIAMONDS, Value.KING);
        Hand hand = new Hand();
        boolean expResult = true;
        boolean result = hand.isImpureConsecutive(card1, card2);
        assertEquals(expResult, result);
    }
    
    @Test
    public void isImpureConsecutiveBad(){
        System.out.println("Testing for ImpureConsecutive Bad ");
        
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.THREE);
        Hand hand = new Hand();
        boolean expResult = false;
        boolean result = hand.isImpureConsecutive(card1, card2);
        assertEquals(expResult, result);
    }
    
    @Test
    public void isImpureConsecutiveBoundry(){
        System.out.println("Testing for ImpureConsecutive Boundry ");
        
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.TWO);
        Hand hand = new Hand();
        boolean expResult = false;
        boolean result = hand.isImpureConsecutive(card1, card2);
        assertEquals(expResult, result);
        
    }
    
}
    
    

