package in.ravidsrk.sample;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ravindra Kumar on 08/09/16.
 */
public class CalculatorTest {

    private Calculator calculator;

    @BeforeClass
    public static void testClassSetup() {
        System.out.println("Getting test class ready");
    }

    @AfterClass
    public static void testClassCleanup() {
        System.out.println("Done with tests");
    }

    @Before
    public void setup() {
        calculator = new Calculator();
        System.out.println("Ready for testing!");
    }

    @After
    public void cleanup() {
        System.out.println("Done with unit test!");
    }

    @Test
    public void testAdd() {
        calculator = new Calculator();
        int total = calculator.add(4, 5);
        assertEquals("Calculator is not adding correctly", 9, total);
    }

    @Test
    public void testDiff() {
        calculator = new Calculator();
        int total = calculator.diff(9, 2);
        assertEquals("Calculator is not subtracting correctly", 7, total);
    }

    @Test
    public void testDiv() {
        calculator = new Calculator();
        double total = calculator.div(9, 3);
        assertEquals("Calculator is not dividing correctly", 3.0, total, 0.0);
    }

    @Test(expected = java.lang.ArithmeticException.class)
    public void testDivWithZeroDivisor() {
        calculator = new Calculator();
        double total = calculator.div(9, 0);
        assertEquals("Calculator is not handling division by zero correctly", 0.0, total, 0.0);
    }
}