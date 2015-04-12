package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 23.2.2015.
 */

public class TestRunRequest implements Serializable{

    private String user;

    private List<ApiTestSuiteResults> testResults = new ArrayList<ApiTestSuiteResults>();

    private List<ApiJaCoCoResult> coverageResults = new ArrayList<ApiJaCoCoResult>();

    public TestRunRequest() { }

    @JsonProperty
    public List<ApiJaCoCoResult> getCoverageResults() {
        return coverageResults;
    }

    public void setCoverageResults(List<ApiJaCoCoResult> coverageResults) {
        this.coverageResults = coverageResults;
    }

    @JsonProperty
    public List<ApiTestSuiteResults> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<ApiTestSuiteResults> testResults) {
        this.testResults = testResults;
    }

    @JsonProperty
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private List<TesthoundResult> testhoundResults = new ArrayList<TesthoundResult>();

    @JsonProperty
    public List<TesthoundResult> getTesthoundResults() {
        return testhoundResults;
    }

    public void setTesthoundResults(List<TesthoundResult> testhoundResults) {
        this.testhoundResults = testhoundResults;
    }
}
