package edu.chl.gunit.commons;

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

    public String getErrorMessage() {
        return error;
    }

    public void setErrorMessage(String error) {
        this.error = error;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Boolean getSucceeded() {
        return succeeded;
    }

    public void setSucceeded(Boolean succeeded) {
        this.succeeded = succeeded;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Double getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(Double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
