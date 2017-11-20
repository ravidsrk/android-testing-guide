# Android Testing Guide [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Testing%20Guide-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/6473) [![Backers on Open Collective](https://opencollective.com/android-testing-guide/backers/badge.svg)](#backers) [![Sponsors on Open Collective](https://opencollective.com/android-testing-guide/sponsors/badge.svg)](#sponsors) [![Join the chat at https://gitter.im/android-testing-guide/Lobby](https://badges.gitter.im/android-testing-guide/Lobby.svg)](https://gitter.im/android-testing-guide/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

### Show some :heart:
[![GitHub stars](https://img.shields.io/github/stars/ravidsrk/android-testing-guide.svg?style=social&label=Star)](https://github.com/ravidsrk/android-testing-guide) [![GitHub forks](https://img.shields.io/github/forks/ravidsrk/android-testing-guide.svg?style=social&label=Fork)](https://github.com/ravidsrk/android-testing-guide/fork) [![GitHub watchers](https://img.shields.io/github/watchers/ravidsrk/android-testing-guide.svg?style=social&label=Watch)](https://github.com/ravidsrk/android-testing-guide) [![GitHub followers](https://img.shields.io/github/followers/ravidsrk.svg?style=social&label=Follow)](https://github.com/ravidsrk/android-testing-guide) 
[![Twitter Follow](https://img.shields.io/twitter/follow/ravidsrk.svg?style=social)](https://twitter.com/ravidsrk)

> Complete reference for Android Testing with examples.

## Contents

- [Introduction](#introduction)
    - [Why testing?](#why-testing)
    - [Why unit test?](#why-unit-test)
    - [Instrumented tests](#instrumented-tests)
- [Local Tests](#local-tests)
    - [JUnit basics](#junit-basics)
    - [Beyond JUnit basics](#beyond-junit-basics)
    - [Assertions](#assertions)
    - [Hamcrest](#hamcrest)
    - [Assertj](#assertj)
    - [Rules](#rules)
    - [Mockito](#mockito)
    - [PowerMock](#powermock)
    - [EasyMock](#easymock)
    - [WireMock](#wiremock)
    - [RESTMock](#restmock)
- [Android](#android)
    - [Android test rules](#android-test-rules)
        - [Rule to test Android Activity](#rule-to-test-android-activity)
        - [Rule to test Android Service](#rule-to-test-android-service)
        - [Rule to test Android Intents](#rule-to-test-android-intents)
    - [Android instrumented tests](#android-instrumented-tests)
    - [Test filtering](#test-filtering)
    - [Espresso](#espresso)
        - [Basics](#basics)
        - [Testing RecyclerView](#testing-recyclerview)
        - [Testing Toast](#testing-toast)
        - [Testing Intents](#testing-intents)
        - [Testing Synchronization with background jobs](#idlingresource)
    - [Robolectric](#robolectric)
        - [Testing Activities](#testing-activities)
        - [Testing Fragments](#testing-fragments)
        - [Testing sqlite](#testing-sqlite)
    - [Robotium](#robotium)
    - [UI testing and UI Automator](#ui-testing-and-ui-automator)
    - [MonkeyRunner](#monkeyrunner)
- [References](#references)

## Introduction

### Why testing?

* Testing forces you to think in a different way and implicitly makes your code cleaner in the process.
* You feel more confident about your code if it has tests.
* Shiny green status bars and cool reports detailing how much of your code is covered are both consequences of writing tests.
* Regression testing is made a lot easier, as automated tests would pick up the bugs first.

### Why unit test?

A unit test generally exercises the functionality of the smallest possible unit of code (which could be a method, class, or component) in a repeatable way.

Tools that are used to do this testing:
* [JUnit](http://junit.org/junit4/) – normal test assertions.
* [Mockito](http://mockito.org/) – mocking out other classes that are not under test.
* [PowerMock](https://github.com/jayway/powermock) – mocking out static classes such as Android Environment class etc.

### Instrumented tests

A UI Test or Instrumentation Test mocks typical user interactions with your app. Clicking on buttons, typing in text are some of the things UI Tests can complete.

* [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/) –  Used for testing within your app, selecting items, making sure something is visible.
* [UIAutomator](https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html) – Used for testing interaction between different apps.

There are other tools that are available for this kind of testing such as [Robotium](http://robotium.com/), [Appium](http://appium.io/), [Calabash](http://calaba.sh/), [Robolectric](http://robolectric.org/).

## Local Tests

### JUnit basics

[Calculator.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/main/java/in/ravidsrk/sample/Calculator.java)

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
```

[CalculatorTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/test/java/in/ravidsrk/sample/CalculatorTest.java)

```java
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
}

```

### Beyond JUnit basics

[CalculatorTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/test/java/in/ravidsrk/sample/CalculatorTest.java#L62)

```java
@Ignore
@Test(expected = java.lang.ArithmeticException.class)
public void testDivWithZeroDivisor() {
    calculator = new Calculator();
    double total = calculator.div(9, 0);
    assertEquals("Calculator is not handling division by zero correctly", 0.0, total, 0.0);
}
```

### Assertions

JUnit provides overloaded assertion methods for all primitive types and Objects and arrays (of primitives or Objects). The parameter order is expected value followed by actual value. Optionally the first parameter can be a String message that is output on failure. There is a slightly different assertion, assertThat that has parameters of the optional failure message, the actual value, and a Matcher object. Note that expected and actual are reversed compared to the other assert methods.

[AssertTests.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/test/java/in/ravidsrk/sample/AssertTests.java)

```java
public class AssertTests {
  @Test
  public void testAssertArrayEquals() {
    byte[] expected = "trial".getBytes();
    byte[] actual = "trial".getBytes();
    assertArrayEquals("failure - byte arrays not same", expected, actual);
  }

  @Test
  public void testAssertEquals() {
    assertEquals("failure - strings are not equal", "text", "text");
  }

  @Test
  public void testAssertFalse() {
    assertFalse("failure - should be false", false);
  }

  @Test
  public void testAssertNotNull() {
    assertNotNull("should not be null", new Object());
  }

  @Test
  public void testAssertNotSame() {
    assertNotSame("should not be same Object", new Object(), new Object());
  }

  @Test
  public void testAssertNull() {
    assertNull("should be null", null);
  }

  @Test
  public void testAssertSame() {
    Integer aNumber = Integer.valueOf(768);
    assertSame("should be same", aNumber, aNumber);
  }

  // JUnit Matchers assertThat
  @Test
  public void testAssertThatBothContainsString() {
    assertThat("albumen", both(containsString("a")).and(containsString("b")));
  }

  @Test
  public void testAssertThatHasItems() {
    assertThat(Arrays.asList("one", "two", "three"), hasItems("one", "three"));
  }

  @Test
  public void testAssertThatEveryItemContainsString() {
    assertThat(Arrays.asList(new String[] { "fun", "ban", "net" }), everyItem(containsString("n")));
  }

  // Core Hamcrest Matchers with assertThat
  @Test
  public void testAssertThatHamcrestCoreMatchers() {
    assertThat("good", allOf(equalTo("good"), startsWith("good")));
    assertThat("good", not(allOf(equalTo("bad"), equalTo("good"))));
    assertThat("good", anyOf(equalTo("bad"), equalTo("good")));
    assertThat(7, not(CombinableMatcher.<Integer> either(equalTo(3)).or(equalTo(4))));
    assertThat(new Object(), not(sameInstance(new Object())));
  }

  @Test
  public void testAssertTrue() {
    assertTrue("failure - should be true", true);
  }
}
```

### Hamcrest

[HamcrestTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/test/java/in/ravidsrk/sample/HamcrestTest.java)

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

[CalculatorWithTestName.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/test/java/in/ravidsrk/sample/CalculatorWithTestName.java)

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

## RESTMock

RESTMock is a library working on top of Square's [okhttp/MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver). It allows you to specify [Hamcrest](https://github.com/hamcrest/JavaHamcrest) matchers to match HTTP requests and specify what response to return. It is as easy as:

```java
RESTMockServer.whenGET(pathContains("users/defunkt"))
            .thenReturnFile(200, "users/defunkt.json");
```

#### Step 1: Start the server
It's good to start server before the tested application starts, there are few methods:

##### a) RESTMockTestRunner
To make it simple you can just use the predefined `RESTMockTestRunner` in your UI tests. It extends `AndroidJUnitRunner`:

```groovy
defaultConfig {
        ...
        testInstrumentationRunner 'io.appflate.restmock.android.RESTMockTestRunner'
    }
```
##### b) RESTMockServerStarter
If you have your custom test runner and you can't extend `RESTMockTestRunner`, you can always just call the `RESTMockServerStarter`. Actually `RESTMockTestRunner` is doing exactly the same thing:

```java
public class MyAppTestRunner extends AndroidJUnitRunner {
    ...
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        RESTMockServerStarter.startSync(new AndroidAssetsFileParser(getContext()),new AndroidLogger());
        ...
    }
    ...
}

```


#### Step 2: Specify Mocks

##### a) Files
By default, the `RESTMockTestRunner` uses `AndroidAssetsFileParser` as a mocks file parser, which reads the files from the assets folder. To make them visible for the RESTMock you have to put them in the correct folder in your project, for example:

    .../src/androidTest/assets/users/defunkt.json
This can be accessed like this:

```java
RESTMockServer.whenGET(pathContains("users/defunkt"))
            .thenReturnFile(200, "users/defunkt.json");
```

##### b) Strings
If the response You wish to return is simple, you can just specify a string:

```java
RESTMockServer.whenGET(pathContains("users/defunkt"))
            .thenReturnString(200, "{}");
```
##### c) MockResponse
If you wish to have a greater control over the response, you can pass the `MockResponse`
```java
RESTMockServer.whenGET(pathContains("users/defunkt")).thenReturn(new MockResponse().setBody("").setResponseCode(401).addHeader("Header","Value"));
```

#### Step 3: Request Matchers
You can either use some of the predefined matchers from `RequestMatchers` util class, or create your own. remember to extend from `RequestMatcher`

#### Step 4: Specify API Endpoint
The most important step, in order for your app to communicate with the testServer, you have to specify it as an endpoint for all your API calls. For that, you can use the ` RESTMockServer.getUrl()`. If you use Retrofit, it is as easy as:

```java
RestAdapter adapter = new RestAdapter.Builder()
                .baseUrl(RESTMockServer.getUrl())
                ...
                .build();
```
#### Request verification
It is possible to verify which requests were called and how many times thanks to `RequestsVerifier`. All you have to do is call one of these:

```java
//cheks if the GET request was invoked exactly 2 times
RequestsVerifier.verifyGET(pathEndsWith("users")).exactly(2);

//cheks if the GET request was invoked at least 3 times
RequestsVerifier.verifyGET(pathEndsWith("users")).atLeast(3);

//cheks if the GET request was invoked exactly 1 time
RequestsVerifier.verifyGET(pathEndsWith("users")).invoked();

//cheks if the GET request was never invoked
RequestsVerifier.verifyGET(pathEndsWith("users")).never();
```

#### Logging
RESTMock supports logging events. You just have to provide the RESTMock with the implementation of `RESTMockLogger`. For Android there is an `AndroidLogger` implemented already. All you have to do is use the `RESTMockTestRunner` or call

```java
RESTMockServerStarter.startSync(new AndroidAssetsFileParser(getContext()),new AndroidLogger());
```

or

```java
RESTMockServer.enableLogging(RESTMockLogger)
RESTMockServer.disableLogging()
```

## Android

### Android test rules

#### Rule to test Android Activity

[MainActivityTestRule.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/MainActivityTestRule.java)

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

#### Rule to test Android Service

[SampleServiceTestRule.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/SampleServiceTest.java)

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

### Android instrumented tests
#### Testing Android Activity

[MainActivityTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/MainActivityTest.java)

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

#### Testing Android Service

[SampleServiceTest](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/SampleServiceTest.java)

```java
@RunWith(AndroidJUnit4.class)
public class SampleServiceTest {

    @Rule
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

### Test filtering

[MainActivityTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/MainActivityTest.java#L61)

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

[MainActivityTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/MainActivityTest.java#L134)

```java
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

```

### Robolectric

[MainActivityRoboelectricTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/test/java/in/ravidsrk/sample/MainActivityRoboelectricTest.java)

```java
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
```

### Robotium

[MainActivityRobotiumTest.java](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/MainActivityRobotiumTest.java)

```java
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
```

### UI testing and UI Automator

[MainActivityTest](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/app/src/androidTest/java/in/ravidsrk/sample/MainActivityTest.java#L101)

```java
@Test
public void testPressBackButton() {
    UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack();
}

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

[sampletest.py](https://github.com/ravidsrk/android-testing-guide/blob/master/SampleApp/sampletest.py)

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
* <https://github.com/junit-team/junit4/wiki/assertions>
* <https://github.com/googlesamples/android-testing>
* <https://riggaroo.co.za/introduction-automated-android-testing/>
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

## Contributors

This project exists thanks to all the people who contribute.
<a href="graphs/contributors"><img src="https://opencollective.com/android-testing-guide/contributors.svg?width=890" /></a>


## Backers

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/android-testing-guide#backer)]

<a href="https://opencollective.com/android-testing-guide#backers" target="_blank"><img src="https://opencollective.com/android-testing-guide/backers.svg?width=890"></a>


## Sponsors

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/android-testing-guide#sponsor)]

<a href="https://opencollective.com/android-testing-guide/sponsor/0/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/0/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/1/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/1/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/2/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/2/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/3/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/3/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/4/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/4/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/5/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/5/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/6/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/6/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/7/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/7/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/8/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/8/avatar.svg"></a>
<a href="https://opencollective.com/android-testing-guide/sponsor/9/website" target="_blank"><img src="https://opencollective.com/android-testing-guide/sponsor/9/avatar.svg"></a>


