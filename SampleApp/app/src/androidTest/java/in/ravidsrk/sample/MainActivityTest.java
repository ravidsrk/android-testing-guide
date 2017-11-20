package in.ravidsrk.sample;

import android.app.Activity;
import android.os.Build;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.filters.RequiresDevice;
import android.support.test.filters.SdkSuppress;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testUI() {
        Activity activity = activityTestRule.getActivity();
        assertNotNull(activity.findViewById(R.id.text_hello));
        TextView helloView = activity.findViewById(R.id.text_hello);
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
    @SdkSuppress(minSdkVersion = 15)
    public void testMinSdkVersion() {
        Log.d("Test Filters", "Checking for min sdk >= 15");
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

    @Test
    public void testPressBackButton() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack();
    }

    @Test
    public void testUiDevice() throws RemoteException {
        UiDevice device = UiDevice.getInstance(
                InstrumentationRegistry.getInstrumentation());
        if (device.isScreenOn()) {
            device.setOrientationLeft();
            device.openNotification();
        }
    }

    @Ignore
    @Test
    public void testUiAutomatorAPI() throws UiObjectNotFoundException, InterruptedException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        UiSelector editTextSelector = new UiSelector().className("android.widget.EditText").text("this is a test").focusable(true);
        UiObject editTextWidget = device.findObject(editTextSelector);
        editTextWidget.setText("this is new text");

        Thread.sleep(2000);

        UiSelector buttonSelector = new UiSelector().className("android.widget.Button").text("CLICK ME").clickable(true);
        UiObject buttonWidget = device.findObject(buttonSelector);
        buttonWidget.click();

        Thread.sleep(2000);
    }

    @Test
    public void testEspresso() {
        ViewInteraction interaction =
                onView(allOf(withId(R.id.editText),
                        withText("this is a test"),
                        hasFocus()));
        interaction.perform(replaceText("how about some new text"));
        ViewInteraction interaction2 =
                onView(allOf(withId(R.id.editText),
                        withText("how about some new text")));
        interaction2.check(matches(hasFocus()));
    }

    @Test
    public void testEspressoSimplified() {
        onView(allOf(withId(R.id.editText),
                withText("this is a test"),
                hasFocus())).perform(replaceText("how about some new text"));
        onView(allOf(withId(R.id.editText),
                withText("how about some new text"))).check(matches(hasFocus()));
    }

    @Test
    public void testAdd() {
        Button addButton = mock(Button.class);
        when(addButton.getId()).thenReturn(R.id.buttonAdd);

        MainActivity activity = activityTestRule.getActivity();
        activity.buttonClicked(addButton);

    }

    @Test
    public void testRemove() {
        Button removeButton = mock(Button.class);
        when(removeButton.getId()).thenReturn(R.id.buttonRemove);

        MainActivity activity = activityTestRule.getActivity();
        activity.buttonClicked(removeButton);
    }
}
