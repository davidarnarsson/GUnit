package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

/**
 * Created by davida on 4.3.2015.
 */
public class ApiUserBadge {

    @JsonProperty
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    private ApiBadge badge;

    private Timestamp earnedDate;

    private ApiRule fromRule;

    @JsonProperty
    public ApiBadge getBadge() {
        return badge;
    }

    public void setBadge(ApiBadge badge) {
        this.badge = badge;
    }

    @JsonProperty
    public Timestamp getEarnedDate() {
        return earnedDate;
    }

    public void setEarnedDate(Timestamp earnedDate) {
        this.earnedDate = earnedDate;
    }

    @JsonProperty
    public ApiRule getFromRule() {
        return fromRule;
    }

    public void setFromRule(ApiRule fromRule) {
        this.fromRule = fromRule;
    }

    @JsonProperty
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    private int sessionId;
}
