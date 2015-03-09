package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by davida on 2.3.2015.
 */
public class ApiSession {

    private Integer badgesEarned;
    private Integer userId;
    private SessionStatus sessionStatus;
    private Integer sessionId;
    private Integer pointsCollected;
    private Integer newTests;
    private Double lineCoverage;
    private Timestamp date;
    private Double instructionCoverage;
    private Double branchCoverage;



    public void setBadgesEarned(Integer badgesEarned) {
        this.badgesEarned = badgesEarned;
    }

    @JsonProperty
    public Integer getBadgesEarned() {
        return badgesEarned;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty
    public Integer getUserId() {
        return userId;
    }

    public void setSessionStatus(Integer sessionStatus) {
        this.sessionStatus = SessionStatus.from(sessionStatus);
    }

    @JsonProperty
    public Integer getSessionStatus() {
        return sessionStatus.getStatusCode();
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    @JsonProperty
    public Integer getSessionId() {
        return sessionId;
    }

    public void setPointsCollected(Integer pointsCollected) {
        this.pointsCollected = pointsCollected;
    }

    @JsonProperty
    public Integer getPointsCollected() {
        return pointsCollected;
    }

    public void setNewTests(Integer newTests) {
        this.newTests = newTests;
    }

    @JsonProperty
    public Integer getNewTests() {
        return newTests;
    }

    public void setLineCoverage(Double lineCoverage) {
        this.lineCoverage = lineCoverage;
    }

    @JsonProperty
    public Double getLineCoverage() {
        return lineCoverage;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @JsonProperty
    public Timestamp getDate() {
        return date;
    }

    public void setInstructionCoverage(Double instructionCoverage) {
        this.instructionCoverage = instructionCoverage;
    }

    @JsonProperty
    public Double getInstructionCoverage() {
        return instructionCoverage;
    }

    public void setBranchCoverage(Double branchCoverage) {
        this.branchCoverage = branchCoverage;
    }

    @JsonProperty
    public Double getBranchCoverage() {
        return branchCoverage;
    }
}
