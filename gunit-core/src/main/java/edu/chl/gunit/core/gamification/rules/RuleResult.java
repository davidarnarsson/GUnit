package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.data.tables.Badge;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.data.tables.records.RuleRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 24.2.2015.
 */
public class RuleResult {

    public RuleResult() {
        awardedBadges = new ArrayList<>();
    }
    private List<BadgeRecord> awardedBadges;

    public RuleRecord getRule() {
        return rule;
    }

    public void setRule(RuleRecord rule) {
        this.rule = rule;
    }

    private RuleRecord rule;
    private int pointsAwarded = 0;
    private Integer regardingUserId;

    public Integer getRegardingBadgeId() {
        return regardingBadgeId;
    }

    public void setRegardingBadgeId(Integer regardingBadgeId) {
        this.regardingBadgeId = regardingBadgeId;
    }

    public Integer getRegardingUserId() {
        return regardingUserId;
    }

    public void setRegardingUserId(Integer regardingUserId) {
        this.regardingUserId = regardingUserId;
    }

    private Integer regardingBadgeId;

    private String message;

    public List<BadgeRecord> getAwardedBadges() {
        return awardedBadges;
    }

    public void setAwardedBadges(List<BadgeRecord> awardedBadges) {
        this.awardedBadges = awardedBadges;
    }

    public int getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(int pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
