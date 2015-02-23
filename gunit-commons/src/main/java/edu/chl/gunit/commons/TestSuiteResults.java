package edu.chl.gunit.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by davida on 4.2.2015.
 */
public class TestSuiteResults {
    public void setName(String name) {
        this.name = name;
    }

    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    private String name;
    private double timeElapsed;
    private int tests;
    private int errors;
    private int skipped;
    private int failures;
    private final Map<String, String> properties = new HashMap<>();
    private final List<TestCase> testCases = new ArrayList<TestCase>();


    public TestSuiteResults() {
    }

    public TestSuiteResults(String name, double timeElapsed, int tests, int errors, int skipped, int failures) {
        this.name = name;
        this.timeElapsed = timeElapsed;
        this.tests = tests;
        this.errors = errors;
        this.skipped = skipped;
        this.failures = failures;
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
    }

    public void addTestCases(List<TestCase> testCase) {
        testCases.addAll(testCase);
    }

    public String getName() {
        return name;
    }

    public double getTimeElapsed() {
        return timeElapsed;
    }

    public int getTests() {
        return tests;
    }

    public int getErrors() {
        return errors;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getFailures() {
        return failures;
    }
}