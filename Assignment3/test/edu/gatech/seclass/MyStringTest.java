package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 *
 * This class is provided to interpret your grades via junit tests
 * and as a reminder, should NOT be posted in any public repositories,
 * even after the class has ended.
 *
 */

public class MyStringTest {

    private MyStringInterface mystring;

    @Before
    public void setUp() {
        mystring = new MyString();
    }
    @After
    public void tearDown() {
        mystring = null;
    }

    @Test
    // Description: First count number example in the interface documentation
    public void testCountAlphabeticWords1() {
        mystring.setString("My numbers are 11, 96, and thirteen");
        assertEquals(5, mystring.countAlphabeticWords());
    }


    @Test
    // Description: 2nd example of counting numbers in the interface documentation
    public void testCountAlphabeticWords2() {
        mystring.setString("i#love 2 pr00gram.");
        assertEquals(4, mystring.countAlphabeticWords());
    }


    // Description: I set MyString as null, so it throws a NullPointerException.
    @Test(expected = NullPointerException.class)
    public void testCountAlphabeticWords3() {
        mystring.setString(null);
        assertEquals(NullPointerException.class, mystring.countAlphabeticWords());
    }

    @Test
    // Description: 3th count number example in the interface documentation
    public void testCountAlphabeticWords4() {
        mystring.setString("Done!");
        assertEquals(1, mystring.countAlphabeticWords());
    }

    @Test
    // Description: First number addition example in the interface documentation
    public void testAddNumber1() {
        mystring.setString("hello 90, bye 2");
        assertEquals("hello 92, bye 4", mystring.addNumber(2, false));
    }


    @Test
    // Description: 2nd  number addition example in the interface documentation
    public void testAddNumber2() {
        mystring.setString("-12345");
        assertEquals("-12355", mystring.addNumber(10, false));
    }

    @Test
    // Description: 3rd number addition example in the interface documentation
    public void testAddNumber3() {
        mystring.setString("12345");
        assertEquals("54321", mystring.addNumber(0, true));
    }


    // Description:  4th number addition example in the interface documentation
    @Test
    public void testAddNumber4() {
        mystring.setString("hello 90, bye 2");
        assertEquals("hello 17, bye 10", mystring.addNumber(8, true));
    }

    // Description: MyString has been set as null, so it throws a NullPointerException.
    @Test(expected = NullPointerException.class)
    public void testAddNumber5() {
        mystring.setString(null);
        assertEquals(NullPointerException.class, mystring.addNumber(100, true));
    }

    @Test(expected = IllegalArgumentException.class)
    // Description: MyString's n has been set as -1, so it throws an IllegalArgumentException.
    public void testAddNumber6() {
        mystring.setString("hello 2022, bye 2000");
        assertEquals(IllegalArgumentException.class, mystring.addNumber(-1, true));
    }

    @Test
    // Description: TThe first instnace of converting digits example in the interface documentation
    public void testConvertDigitsToNamesInSubstring1() {
        mystring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mystring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mystring.getString());
    }

    @Test
    // Description: 2nd convert digits example in the interface documentation
    public void testConvertDigitsToNamesInSubstring2() {
        mystring.setString("abc416d");
        mystring.convertDigitsToNamesInSubstring(2, 7);
        assertEquals("abcFourOneSixd", mystring.getString());
    }

    @Test
    // Description: 3rd convert digits example in the interface documentation
    public void testConvertDigitsToNamesInSubstring3() {
        mystring.setString("abc416d");
        mystring.convertDigitsToNamesInSubstring(2, 7);
        assertEquals("abcFourOneSixd", mystring.getString());
    }

    @Test(expected = NullPointerException.class)
    // Description: MyString has been set as null, so it throws a NullPointerException.
    public void testConvertDigitsToNamesInSubstring4() {
        mystring.setString(null);
        mystring.convertDigitsToNamesInSubstring(2, 7);
        assertEquals(NullPointerException.class, mystring.getString());
    }

    @Test(expected = IllegalArgumentException.class)
    // Description: MyString's firstPosition and finalPosition has been set as 7 and 2. 7 > 2 (firstPostion > finalPostion), so it throws an IllegalArgumentException.
    public void testConvertDigitsToNamesInSubstring5() {
        mystring.setString("abc216d");
        mystring.convertDigitsToNamesInSubstring(7, 2);
        assertEquals(IllegalArgumentException.class, mystring.getString());
    }

    // Description: MyString's finalPosition exceeded the length of MyString , so it throws a NullPointerException.
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring6() {
        mystring.setString("abc316d");
        mystring.convertDigitsToNamesInSubstring(2, 70000);
        assertEquals(MyIndexOutOfBoundsException.class, mystring.getString());
    }
}
