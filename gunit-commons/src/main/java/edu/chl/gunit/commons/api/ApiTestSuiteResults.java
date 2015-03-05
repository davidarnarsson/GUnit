package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by davida on 4.2.2015.
 */
public class ApiTestSuiteResults {
    public ApiTestSuiteResults(String name, double time, int tests, int errors, int skipped, int failures) {
        this.name = name;
        this.timeElapsed = time;
        this.tests = tests;
        this.errors = errors;
        this.skipped = skipped;
        this.failures = failures;
    }

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
    private final Map<String, String> properties = new HashMap<String, String>();
    private List<ApiTestCase> testCases = new ArrayList<ApiTestCase>();

    public ApiTestSuiteResults() {
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    @JsonProperty
    public List<ApiTestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<ApiTestCase> testCases) {
        this.testCases = testCases;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public double getTimeElapsed() {
        return timeElapsed;
    }

    @JsonProperty
    public int getTests() {
        return tests;
    }

    @JsonProperty
    public int getErrors() {
        return errors;
    }

    @JsonProperty
    public int getSkipped() {
        return skipped;
    }

    @JsonProperty
    public int getFailures() {
        return failures;
    }

    public void addTestCase(ApiTestCase testCase) {
        this.testCases.add(testCase);
    }
}
