package in.ravidsrk.sample;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ravindra Kumar on 08/09/16.
 */

public class HamcrestTest {

    @Test
    public void testWithAsserts() {
        List<String> list = generateStingList();
        assertTrue(list.contains("android"));
        assertTrue(list.contains("context"));
        assertTrue(list.size() > 4);
        assertTrue(list.size() < 13);
    }

    @Test
    public void testWithBigAssert() {
        List<String> list = generateStingList();
        assertTrue(list.contains("android") && list.contains("context") && list.size() > 3 && list.size() < 12);
    }

    @Test
    public void testWithHamcrest() {
        List<String> list = generateStingList();
        assertThat(list, (hasItems("android", "context")));
        assertThat(list, allOf(hasSize(greaterThan(3)), hasSize(lessThan(12))));
    }

    @Test
    public void testFailureWithAsserts() {
        List<String> list = generateStingList();
        assertTrue(list.contains("android"));
        assertTrue(list.contains("service"));
        assertTrue(list.size() > 3);
        assertTrue(list.size() < 12);
    }

    @Test
    public void testFailureWithHamcrest() {
        List<String> list = generateStingList();
        assertThat(list, (hasItems("android", "service")));
        assertThat(list, allOf(hasSize(greaterThan(3)), hasSize(lessThan(12))));
    }

    @Test
    public void testTypeSafety() {
//        assertThat("123", equalTo(123));
//        assertThat(123, equalTo("123"));
    }

    private List<String> generateStingList() {
        String[] sentence = {"android", "context", "service", "manifest", "layout", "resource", "broadcast", "receiver", "gradle"};
        return Arrays.asList(sentence);
    }
}
