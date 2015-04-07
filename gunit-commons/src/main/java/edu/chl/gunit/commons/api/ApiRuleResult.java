package edu.chl.gunit.commons.api;

/**
 * Created by davida on 30.3.2015.
 */
public class ApiRuleResult {
    private ApiRule rule;
    private ApiBadge badge;
    private ApiUser regardingUser;
    public ApiSession getSession() {
        return session;
    }

    public void setSession(ApiSession session) {
        this.session = session;
    }

    private ApiSession session;
    private int ruleId;
    private int sessionId;
    private String message;
    private int points;
    private Integer regardingUserId;
    private Integer regardingBadgeId;

    public ApiBadge getBadge() {
        return badge;
    }

    public void setBadge(ApiBadge badge) {
        this.badge = badge;
    }

    public ApiUser getRegardingUser() {
        return regardingUser;
    }

    public void setRegardingUser(ApiUser regardingUser) {
        this.regardingUser = regardingUser;
    }

    public Integer getRegardingUserId() {
        return regardingUserId;
    }

    public void setRegardingUserId(Integer regardingUserId) {
        this.regardingUserId = regardingUserId;
    }

    public Integer getRegardingBadgeId() {
        return regardingBadgeId;
    }

    public void setRegardingBadgeId(Integer regardingBadgeId) {
        this.regardingBadgeId = regardingBadgeId;
    }

    public ApiRule getRule() {
        return rule;
    }

    public void setRule(ApiRule rule) {
        this.rule = rule;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
