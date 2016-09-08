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

    private Calculator sm;

    @Before
    public void setup() {
        sm = new Calculator();
        System.out.println("Ready for testing!");
    }

    @After
    public void cleanup() {
        System.out.println("Done with unit test!");
    }

    @BeforeClass
    public static void testClassSetup() {
        System.out.println("Getting test class ready");
    }

    @AfterClass
    public static void testClassCleanup() {
        System.out.println("Done with tests");
    }

    @Test
    public void testAdd() {
        sm = new Calculator();
        int total = sm.add(4, 5);
        assertEquals("Calculator is not adding correctly", 9, total);
    }

    @Test
    public void testDiff() {
        sm = new Calculator();
        int total = sm.diff(9, 2);
        assertEquals("Calculator is not subtracting correctly", 7, total);
    }

    @Test
    public void testDiv() {
        sm = new Calculator();
        double total = sm.div(9, 3);
        assertEquals("Calculator is not dividing correctly", 3.0, total, 0.0);
    }

    @Ignore
    @Test(expected = java.lang.ArithmeticException.class)
    public void testDivWithZeroDivisor() {
        sm = new Calculator();
        double total = sm.div(9, 0);
        assertEquals("Calculator is not handling division by zero correctly", 0.0, total, 0.0);
    }
}