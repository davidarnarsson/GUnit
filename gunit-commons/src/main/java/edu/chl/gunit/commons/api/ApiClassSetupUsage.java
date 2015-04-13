package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 13.4.2015.
 */
public class ApiClassSetupUsage {
    private int id;

    private int sessionId;

    private String className;

    private int testSmellCount;

    private List<ApiTestSmell> testSmells = new ArrayList<ApiTestSmell>();

    @JsonProperty
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty
    public int getTestSmellCount() {
        return testSmellCount;
    }

    public void setTestSmellCount(int testSmellCount) {
        this.testSmellCount = testSmellCount;
    }

    @JsonProperty
    public List<ApiTestSmell> getTestSmells() {
        return testSmells;
    }

    public void setTestSmells(List<ApiTestSmell> testSmells) {
        this.testSmells = testSmells;
    }
}
