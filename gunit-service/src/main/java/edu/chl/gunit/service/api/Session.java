package edu.chl.gunit.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.chl.gunit.core.data.SessionStatus;
import edu.chl.gunit.core.data.tables.records.SessionRecord;

import java.sql.Timestamp;

/**
 * Created by davida on 2.3.2015.
 */
public class Session {

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

    public static Session from(SessionRecord record) {
        if (record == null) return null;

        Session s = new Session();
        s.setBadgesEarned(record.getBadgesearned());
        s.setBranchCoverage(record.getBranchcoverage());
        s.setInstructionCoverage(record.getInstructioncoverage());
        s.setDate(record.getDate());
        s.setLineCoverage(record.getLinecoverage());
        s.setNewTests(record.getNewtests());
        s.setPointsCollected(record.getPointscollected());
        s.setSessionId(record.getSessionid());
        s.setSessionStatus(record.getSessionstatus());
        s.setUserId(record.getUserid());
        return s;
    }

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
    public SessionStatus getSessionStatus() {
        return sessionStatus;
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
