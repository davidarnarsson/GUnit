package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by davida on 4.3.2015.
 */
public class ApiRule {
    private int ruleId;

    private String successMessage;

    private int points;
    private Integer badgeId;

    @JsonProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    @JsonProperty
    public Integer getRuleId() {
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

    public void setBadgeId(Integer badgeId) {
        this.badgeId = badgeId;
    }

    @JsonProperty
    public Integer getBadgeId() {
        return badgeId;
    }
}
