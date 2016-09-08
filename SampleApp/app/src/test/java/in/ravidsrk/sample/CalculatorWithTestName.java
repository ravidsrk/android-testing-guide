package in.ravidsrk.sample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ravindra Kumar on 08/09/16.
 */
public class CalculatorWithTestName {

    @Rule
    public TestName name = new TestName();

    @Test
    public void testAdd() {
        Calculator calculator = new Calculator();
        int total = calculator.add(4, 5);
        assertEquals(name.getMethodName() + " adding incorrectly", 9, total);
    }

    @Test
    public void testDiff() {
        Calculator calculator = new Calculator();
        int total = calculator.diff(12, 7);
        assertEquals(name.getMethodName() + " subtracting incorrectly", 5, total);
    }
}