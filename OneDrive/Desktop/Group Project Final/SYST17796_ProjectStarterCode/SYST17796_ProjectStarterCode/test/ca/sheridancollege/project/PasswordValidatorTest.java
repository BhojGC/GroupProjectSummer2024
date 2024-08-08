/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ca.sheridancollege.project;

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
public class PasswordValidatorTest {

    public PasswordValidatorTest() {
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

    @Test
    public void testCheckLengthGood() {
        System.out.println("checkLength");
        String password = "sheridan";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = true;
        boolean result = instance.checkLength(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testCheckLengthBad() {
        System.out.println("checkLength");
        String password = "sherida";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = false;
        boolean result = instance.checkLength(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testCheckLengthBoundry() {
        System.out.println("checkLength");
        String password = "";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = false;
        boolean result = instance.checkLength(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkUpper method, of class ICE4_JunitTest.
     */
    @Test
    public void testCheckUpperBorderGood() {
        System.out.println("checkUpper");
        String password = "sheRidan";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = true;
        boolean result = instance.checkUpperCase(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testCheckUpperBorderBad() {
        System.out.println("checkUpper");
        String password = "sheridan";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = false;
        boolean result = instance.checkUpperCase(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Test
    public void testCheckUpperBorderBoundry() {
        System.out.println("checkUpper");
        String password = "";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = false;
        boolean result = instance.checkUpperCase(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of checkSpecialCharacter method, of class ICE4_JunitTest.
     */
    @Test
    public void testCheckSpecialCharacterGood() {
        System.out.println("checkSpecialCharacter");
        String password = "@Sheridan";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = true;
        boolean result = instance.checkSpecialCharacter(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Test
    public void testCheckSpecialCharacterBad() {
        System.out.println("checkSpecialCharacter");
        String password = "sheridan";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = false;
        boolean result = instance.checkSpecialCharacter(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Test
    public void testCheckSpecialCharacterBoundry() {
        System.out.println("checkSpecialCharacter");
        String password = "";
        PasswordValidator instance = new PasswordValidator();
        boolean expResult = false;
        boolean result = instance.checkSpecialCharacter(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

}
