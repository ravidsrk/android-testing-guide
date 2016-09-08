package in.ravidsrk.sample;

import android.app.Activity;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.RequiresDevice;
import android.support.test.filters.SdkSuppress;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public MainActivityTestRule<MainActivity> mainActivityActivityTestRule = new MainActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testUI() {
        Activity activity = mainActivityActivityTestRule.getActivity();
        assertNotNull(activity.findViewById(R.id.text_hello));
        TextView helloView = (TextView) activity.findViewById(R.id.text_hello);
        assertTrue(helloView.isShown());
        assertEquals("Hello World!", helloView.getText());
        assertEquals(InstrumentationRegistry.getTargetContext().getString(R.string.hello_world), helloView.getText());
        assertNull(activity.findViewById(android.R.id.button1));
    }

    @Test
    @RequiresDevice
    public void testRequiresDevice() {
        Log.d("Test Filters", "This test requires a device");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @SdkSuppress(minSdkVersion = 30)
    public void testMinSdkVersion() {
        Log.d("Test Filters", "Checking for min sdk >= 30");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.LOLLIPOP)
    public void testMinBuild() {
        Log.d("Test Filters", "Checking for min build > Lollipop");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @SmallTest
    public void testSmallTest() {
        Log.d("Test Filters", "this is a small test");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }

    @Test
    @LargeTest
    public void testLargeTest() {
        Log.d("Test Filters", "This is a large test");
        Activity activity = activityTestRule.getActivity();
        assertNotNull("MainActivity is not available", activity);
    }
}
