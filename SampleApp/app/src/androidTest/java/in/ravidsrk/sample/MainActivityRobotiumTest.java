package in.ravidsrk.sample;

import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class MainActivityRobotiumTest {
    private Solo solo;

    @Rule
    public MainActivityRobotiumTestRule<MainActivity> mActivityRule = new MainActivityRobotiumTestRule<>(MainActivity.class);

    public void setUp() {
        solo = new Solo(mActivityRule.getInstrumentation(), mActivityRule.getActivity());
    }

    public void tearDown() {
        solo.finishOpenedActivities();
    }

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
