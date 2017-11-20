package in.ravidsrk.sample;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MainActivityRobotiumTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }

    @Ignore
    @Test
    public void testPushClickMe() {
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("MainActivity is not displayed", MainActivity.class);
        assertTrue("This is a test in EditText is not displayed",
                solo.searchText("this is a test"));
        solo.clickOnButton("Click Me");
        assertTrue("You clicked me text is not displayed in the EditText",
                solo.searchText("you clicked me!"));
    }
}
