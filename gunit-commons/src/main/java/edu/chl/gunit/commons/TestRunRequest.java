package edu.chl.gunit.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestSuiteResults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 23.2.2015.
 */

public class TestRunRequest implements Serializable{

    private String user;

    private List<TestSuiteResults> testResults = new ArrayList<>();

    private List<JaCoCoResult> coverageResults = new ArrayList<>();

    public TestRunRequest() { }

    @JsonProperty
    public List<JaCoCoResult> getCoverageResults() {
        return coverageResults;
    }

    public void setCoverageResults(List<JaCoCoResult> coverageResults) {
        this.coverageResults = coverageResults;
    }

    @JsonProperty
    public List<TestSuiteResults> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestSuiteResults> testResults) {
        this.testResults = testResults;
    }

    @JsonProperty
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
