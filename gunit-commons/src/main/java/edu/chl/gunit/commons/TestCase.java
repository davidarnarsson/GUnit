package edu.chl.gunit.commons;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by davida on 4.2.2015.
 */
public class TestCase {
    public void setName(String name) {
        this.name = name;
    }

    public void setError(String error) {
        this.error = error;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getError() {
        return error;
    }

    private String name;
    private Boolean succeeded;
    private String error;
    private String stackTrace;
    private String errorType;
    private String className;
    private Double timeElapsed;

    public TestCase() {
    }

    public TestCase(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getErrorMessage() {
        return error;
    }

    public void setErrorMessage(String error) {
        this.error = error;
    }

    @JsonProperty
    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @JsonProperty
    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonProperty
    public Boolean getSucceeded() {
        return succeeded;
    }

    public void setSucceeded(Boolean succeeded) {
        this.succeeded = succeeded;
    }

    @JsonProperty
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty
    public Double getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(Double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
