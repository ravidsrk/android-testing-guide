# android-testing-guide
Complete reference for Android Testing with examples.

## Contents

- [Introduction](#introduction)
    - [Why unit test?](#)
    - [How much unit testing](#)
    - [Unit testing tools](#)
    - [Local and instrumented tests](#)
- [Local Tests](#local-tests)
    - [JUnit basics](#)
    - [Beyond JUnit basics](#)
    - [Local test setup and execution](#)
    - [Adding local tests and failure](#)
    - [Assertions](#)
    - [Hamcrest](#)
    - [Rules](#)
    - [Categories](#)
- [Android](#android)
    - [Android instrumented tests](#)
    - [Android test rules](#)
    - [Test filtering](#)
    - [Espresso](#)
    - [Robolectric](#)
    - [Robotium](#)
    - [UI testing and UI Automator](#)
    - [MonkeyRunner](#)
- [References](#references)

## Introduction

### Why unit test?
### How much unit testing
### Unit testing tools
### Local and instrumented tests

## Local

### JUnit basics

```java
public class Calculator {

    public int add(int op1, int op2) {
        return op1 + op2;
    }

    public int diff(int op1, int op2) {
        return op1 - op2;
    }

    public double div(int op1, int op2) {
        // if (op2 == 0) return 0;
        return op1 / op2;
    }
}

// Unit tests
public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setup() {
        calculator = new Calculator();
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

    @Ignore
    @Test(expected = java.lang.ArithmeticException.class)
    public void testDivWithZeroDivisor() {
        calculator = new Calculator();
        double total = calculator.div(9, 0);
        assertEquals("Calculator is not handling division by zero correctly", 0.0, total, 0.0);
    }
}

```



### Beyond JUnit basics
### Local test setup and execution
### Adding local tests and failure
### Assertions
### Hamcrest

```java
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
        // assertThat("123", equalTo(123));
        // assertThat(123, equalTo("123"));
    }

    private List<String> generateStingList() {
        String[] sentence = {"android", "context", "service", "manifest", "layout", "resource", "broadcast", "receiver", "gradle"};
        return Arrays.asList(sentence);
    }
}
```

### Rules

```java
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
```

### Categories

## Android
### Android instrumented tests

```java
public class MainActivityTestRule<A extends Activity> extends ActivityTestRule<A> {

    public MainActivityTestRule(Class<A> activityClass) {
        super(activityClass);
    }
    @Override
    protected Intent getActivityIntent() {
        Log.e("MainActivityTestRule", "Prepare the activity's intent");
        return super.getActivityIntent();
    }

    @Override
    protected void beforeActivityLaunched() {
        Log.e("MainActivityTestRule", "Execute before the activity is launched");
        super.beforeActivityLaunched();
    }

    @Override
    protected void afterActivityLaunched() {
        Log.e("MainActivityTestRule", "Execute after the activity has been launched");
        super.afterActivityLaunched();
    }

    @Override
    protected void afterActivityFinished() {
        Log.e("MainActivityTestRule", "Cleanup after it has finished");
        super.afterActivityFinished();
    }

    @Override
    public A launchActivity(Intent startIntent) {
        Log.e("MainActivityTestRule", "Launching the activity");
        return super.launchActivity(startIntent);
    }
}
```

```java

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
```

### Android test rules
```java
@RunWith(AndroidJUnit4.class)
public class SampleServiceTest {

    @Rule
    //public ServiceTestRule myServiceRule = new ServiceTestRule();
    public SampleServiceTestRule myServiceRule = new SampleServiceTestRule();

    @Test
    public void testService() throws TimeoutException {
        myServiceRule.startService(new Intent(InstrumentationRegistry.getTargetContext(), SampleService.class));
    }

    @Test
    public void testBoundService() throws TimeoutException {
        IBinder binder = myServiceRule.bindService(
                new Intent(InstrumentationRegistry.getTargetContext(), SampleService.class));
        SampleService service = ((SampleService.LocalBinder) binder).getService();
        // Do work with the service
        assertNotNull("Bound service is null", service);
    }
}
```

```java
public class SampleServiceTestRule extends ServiceTestRule {

    @Override
    public void startService(Intent intent) throws TimeoutException {
        Log.e("SampleServiceTestRule", "start the service");
        super.startService(intent);
    }

    @Override
    public IBinder bindService(Intent intent) throws TimeoutException {
        Log.e("SampleServiceTestRule", "binding the service");
        return super.bindService(intent);
    }

    @Override
    protected void beforeService() {
        Log.e("SampleServiceTestRule", "work before the service starts");
        super.beforeService();
    }

    @Override
    protected void afterService() {
        Log.e("SampleServiceTestRule", "work after the service has started");
        super.afterService();
    }
}
```

### Test filtering
```java
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
```

### Espresso
```java

```
### Robolectric
### Robotium
### UI testing and UI Automator

```java
@Test
@Ignore
public void testUiDevice() throws RemoteException {
    UiDevice device = UiDevice.getInstance(
            InstrumentationRegistry.getInstrumentation());
    if (device.isScreenOn()) {
        device.setOrientationLeft();
        device.openNotification();
    }
}

@Test
public void testUiAutomatorAPI() throws UiObjectNotFoundException, InterruptedException {
    UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    UiSelector editTextSelector = new UiSelector().className("android.widget.EditText").text("this is a test").focusable(true);
    UiObject editTextWidget = device.findObject(editTextSelector);
    editTextWidget.setText("this is new text");

    Thread.sleep(2000);

    UiSelector buttonSelector = new UiSelector().className("android.widget.Button").text("Click Me").clickable(true);
    UiObject buttonWidget = device.findObject(buttonSelector);
    buttonWidget.click();

    Thread.sleep(2000);

}
```

### MonkeyRunner
```python
# Imports the monkeyrunner modules
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice, MonkeyImage

# Alert the user a MonkeyRunner script is about to execute
MonkeyRunner.alert("Monkeyrunner about to execute","Monkeyrunner","OK")

# Connects to the current emulator
emulator= MonkeyRunner.waitForConnection()

# Install the Android app package and test package
emulator.installPackage('./app/build/outputs/apk/app-debug-unaligned.apk')
emulator.installPackage('./app/build/outputs/apk/app-debug-androidTest-unaligned.apk')

# sets a variable with the package's internal name
package = 'in.ravidsrk.sample'

# sets a variable with the name of an Activity in the package
activity = 'in.ravidsrk.sample.MainActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# Runs the component
emulator.startActivity(runComponent)

# wait for the screen to fully come up
MonkeyRunner.sleep(2.0)

# Takes a screenshot
snapshot = emulator.takeSnapshot()

# Writes the screenshot to a file
snapshot.writeToFile('mainactivity.png','png')

# Alert the user a testing is about to be run by MonkeyRunner
MonkeyRunner.alert("Instrumented test about to execute","Monkeyrunner","OK")

#kick off the instrumented test
emulator.shell('am instrument -w in.ravidsrk.sample.test/android.support.test.runner.AndroidJUnitRunner')

# return to the emulator home screen
emulator.press('KEYCODE_HOME','DOWN_AND_UP')

```

## References
* <http://robolectric.org>
* <https://github.com/robolectric/robolectric>
* <https://www.bignerdranch.com/blog/triumph-android-studio-1-2-sneaks-in-full-testing-support>
* <https://github.com/mutexkid/android-studio-robolectric-example>
* <http://blog.nikhaldimann.com/2013/10/10/robolectric-2-2-some-pages-from-the-missing-manual>
* <https://corner.squareup.com/2013/04/the-resurrection-of-testing-for-android.html>
* <http://simpleprogrammer.com/2010/07/27/the-best-way-to-unit-test-in-android/>
* <https://youtu.be/f7ihSQ44WO0?t=15m11s>
* <https://code.google.com/p/android-test-kit>
* <https://developer.android.com/training/testing/ui-testing/espresso-testing.html>
* <https://github.com/vgrec/EspressoExamples>
* <https://github.com/designatednerd/Wino>
* <http://chiuki.github.io/advanced-android-espresso/#/>
* <http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html>
