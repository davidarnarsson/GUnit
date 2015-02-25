package edu.chl.gunit.core.gamification;

import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestSuiteResults;

import java.util.List;

/**
 * Created by davida on 23.2.2015.
 */

public class TestRunRequest {

    private String user;

    private List<TestSuiteResults> testResults;

    private List<JaCoCoResult> coverageResults;

    public List<JaCoCoResult> getCoverageResults() {
        return coverageResults;
    }

    public void setCoverageResults(List<JaCoCoResult> coverageResults) {
        this.coverageResults = coverageResults;
    }

    public List<TestSuiteResults> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestSuiteResults> testResults) {
        this.testResults = testResults;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
