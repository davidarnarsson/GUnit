package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by davida on 4.3.2015.
 */
public class ApiRule {
    private int ruleId;

    private String successMessage;

    private int points;

    @JsonProperty
    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    @JsonProperty
    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    @JsonProperty
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
