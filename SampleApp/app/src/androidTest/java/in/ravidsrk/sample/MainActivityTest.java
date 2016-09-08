package in.ravidsrk.sample;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
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
}
