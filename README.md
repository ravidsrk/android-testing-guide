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
    - [UI Automator API](#)
    - [Stress testing apps with Monkey](#)
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
### Categories

## Android
### Android instrumented tests
### Android test rules
### Test filtering
### Espresso
### Robolectric
### Robotium
### UI testing and UI Automator
### UI Automator API
### Stress testing apps with Monkey
### MonkeyRunner

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
