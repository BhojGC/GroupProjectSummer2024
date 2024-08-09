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
    public void isConsecutiveGood() {
        System.out.println("Testing Consecutive Good Sequence ");

        PlayingCard card1 = new PlayingCard(Suit.HEARTS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.HEARTS, Value.TWO);
        Hand hand = new Hand();
        boolean expResult = true;
        boolean result = hand.isConsecutive(card1, card2);
        assertEquals(expResult, result);
    }

    @Test
    public void isConsecutiveBad() {
        System.out.println("Testing Consecutive Bad Sequence");

        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        Hand hand = new Hand();
        boolean expResult = false;
        boolean result = hand.isConsecutive(card1, card2);
        assertEquals(expResult, result);
    }

    @Test
    public void isConsecutiveBoundry() {
        System.out.println("Testing Consecutive Boundry Sequence");

        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card2 = new PlayingCard(Suit.SPADES, Value.THREE);
        Hand hand = new Hand();
        boolean expResult = false;
        boolean result = hand.isConsecutive(card1, card2);
        assertEquals(expResult, result);

    }

    @Test
    public void isImpureConsecutiveGood() {
        System.out.println("Testing for ImpureConsecutive Good");

        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.DIAMONDS, Value.KING);
        Hand hand = new Hand();
        boolean expResult = true;
        boolean result = hand.isImpureConsecutive(card1, card2);
        assertEquals(expResult, result);
    }

    @Test
    public void isImpureConsecutiveBad() {
        System.out.println("Testing for ImpureConsecutive Bad ");

        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.THREE);
        Hand hand = new Hand();
        boolean expResult = false;
        boolean result = hand.isImpureConsecutive(card1, card2);
        assertEquals(expResult, result);
    }

    @Test
    public void isImpureConsecutiveBoundry() {
        System.out.println("Testing for ImpureConsecutive Boundry ");

        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.DIAMONDS, Value.THREE);
        Hand hand = new Hand();
        boolean expResult = false;
        boolean result = hand.isImpureConsecutive(card1, card2);
        assertEquals(expResult, result);

    }

    @Test
    public void isValidHandGood() {
        Hand hand = new Hand();

        System.out.println("Testing for is Valid Good");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        PlayingCard card5 = new PlayingCard(Suit.DIAMONDS, Value.FOUR);
        PlayingCard card6 = new PlayingCard(Suit.HEARTS, Value.FOUR);

        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);

        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card8 = new PlayingCard(Suit.DIAMONDS, Value.TWO);
        PlayingCard card9 = new PlayingCard(Suit.HEARTS, Value.THREE);
        PlayingCard card10 = new PlayingCard(Suit.SPADES, Value.FOUR);

        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.isValidHand();
        assertEquals(expResult, result);

    }

    @Test
    public void isValidHandBad() {
        Hand hand = new Hand();

        System.out.println("Testing for is Valid BAD");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        PlayingCard card5 = new PlayingCard(Suit.DIAMONDS, Value.FOUR);
        PlayingCard card6 = new PlayingCard(Suit.HEARTS, Value.FOUR);

        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);

        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card8 = new PlayingCard(Suit.DIAMONDS, Value.FIVE);
        PlayingCard card9 = new PlayingCard(Suit.HEARTS, Value.SIX);
        PlayingCard card10 = new PlayingCard(Suit.SPADES, Value.SEVEN);

        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = false;
        boolean result = hand.isValidHand();
        assertEquals(expResult, result);

    }

    @Test
    public void isValidHandBoundry() {
        Hand hand = new Hand();

        System.out.println("Testing for is Valid Boundry");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        PlayingCard card5 = new PlayingCard(Suit.DIAMONDS, Value.FOUR);
        PlayingCard card6 = new PlayingCard(Suit.HEARTS, Value.FOUR);

        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);

        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card9 = new PlayingCard(Suit.CLUBS, Value.THREE);
        PlayingCard card10 = new PlayingCard(Suit.CLUBS, Value.FOUR);

        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.isValidHand();
        assertEquals(expResult, result);

    }

    @Test
    public void canFormCase1() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case1");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card5 = new PlayingCard(Suit.CLUBS, Value.THREE);
        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);

        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);

        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.CLUBS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.CLUBS, Value.EIGHT);

        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }

    @Test
    public void canFormCase2() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case2");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card5 = new PlayingCard(Suit.CLUBS, Value.THREE);
        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);

        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);

        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.DIAMONDS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.DIAMONDS, Value.EIGHT);

        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }

    @Test
    public void canFormCase3() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case3");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);
        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card5 = new PlayingCard(Suit.CLUBS, Value.THREE);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);

        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.DIAMONDS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.DIAMONDS, Value.EIGHT);
        hand.addCard(card6);
        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }

    @Test
    public void canFormCase4() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case3");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);
        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);

        PlayingCard card5 = new PlayingCard(Suit.CLUBS, Value.THREE);
        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.DIAMONDS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.DIAMONDS, Value.EIGHT);
        hand.addCard(card5);
        hand.addCard(card6);
        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }

    @Test
    public void canFormCase5() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case3");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);
        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card5 = new PlayingCard(Suit.CLUBS, Value.THREE);
        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);
        hand.addCard(card7);

        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.DIAMONDS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.DIAMONDS, Value.EIGHT);

        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }

    @Test
    public void canFormCase6() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case6");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card5 = new PlayingCard(Suit.CLUBS, Value.THREE);
        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.DIAMONDS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.DIAMONDS, Value.EIGHT);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);
        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }

    @Test
    public void canFormCase7() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case6");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card5 = new PlayingCard(Suit.DIAMONDS, Value.THREE);
        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);

        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.CLUBS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.SPADES, Value.EIGHT);

        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = true;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }
    @Test
    public void canFormCase8() {
        Hand hand = new Hand();

        System.out.println("Testing for CanForm case6");
        PlayingCard card1 = new PlayingCard(Suit.CLUBS, Value.ACE);
        PlayingCard card2 = new PlayingCard(Suit.CLUBS, Value.KING);
        PlayingCard card3 = new PlayingCard(Suit.CLUBS, Value.QUEEN);

        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        PlayingCard card4 = new PlayingCard(Suit.CLUBS, Value.TWO);
        PlayingCard card5 = new PlayingCard(Suit.DIAMONDS, Value.THREE);
        PlayingCard card6 = new PlayingCard(Suit.CLUBS, Value.FOUR);
        hand.addCard(card4);
        hand.addCard(card5);
        hand.addCard(card6);

        PlayingCard card7 = new PlayingCard(Suit.CLUBS, Value.FIVE);
        PlayingCard card8 = new PlayingCard(Suit.CLUBS, Value.SIX);
        PlayingCard card9 = new PlayingCard(Suit.DIAMONDS, Value.SEVEN);
        PlayingCard card10 = new PlayingCard(Suit.SPADES, Value.TEN);

        hand.addCard(card7);
        hand.addCard(card8);
        hand.addCard(card9);
        hand.addCard(card10);

        boolean expResult = false;
        boolean result = hand.canForm();
        assertEquals(expResult, result);

    }

}
