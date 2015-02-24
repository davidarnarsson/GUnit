package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.data.tables.Badge;

import java.util.List;

/**
 * Created by davida on 24.2.2015.
 */
public class RuleResult {

    private List<Badge> awardedBadges;

    private int pointsAwarded;

    private String message;

    public List<Badge> getAwardedBadges() {
        return awardedBadges;
    }

    public void setAwardedBadges(List<Badge> awardedBadges) {
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
