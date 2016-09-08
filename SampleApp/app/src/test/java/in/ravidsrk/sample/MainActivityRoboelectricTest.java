package in.ravidsrk.sample;

import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityRoboelectricTest {

    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void clickButton() {
        Button button = (Button) activity.findViewById(R.id.button);
        assertNotNull("test button could not be found", button);
        assertTrue("button does not contain text 'Click Me!'", "Click Me".equals(button.getText()));
    }

}
