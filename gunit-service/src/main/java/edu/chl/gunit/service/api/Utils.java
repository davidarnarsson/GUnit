package edu.chl.gunit.service.api;

import edu.chl.gunit.commons.api.*;
import edu.chl.gunit.core.data.tables.records.*;

/**
 * Created by davida on 4.3.2015.
 */
public class Utils {
    public static ApiBadge from(BadgeRecord record) {
        if (record == null) return null;

        ApiBadge b = new ApiBadge();

        b.setId(record.getId());
        b.setImage(record.getImage());
        b.setName(record.getName());
        return b;
    }

    public static ApiUserBadge from (UserbadgesRecord r) {
        if (r == null) {
            return null;
        }

        ApiUserBadge badge = new ApiUserBadge();
        badge.setUserId(r.getUserid());
        badge.setEarnedDate(r.getEarneddate());
        badge.setSessionId(r.getSessionid());
        return badge;
    }

    public static ApiSession from(SessionRecord record) {
        if (record == null) return null;

        ApiSession s = new ApiSession();
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

    public static ApiUser from(UserRecord r) {
        if (r == null) return null;

        ApiUser u = new ApiUser(r.getId(), r.getLastbranchcoverage(), r.getLastinstructioncoverage(),r.getLastwrittentest(),r.getName(),r.getPoints(),r.getTotalwrittentests());
        return u;
    }

    public static ApiRule from(RuleRecord r) {
        if (r == null) return null;

        ApiRule rule = new ApiRule();
        rule.setPoints(r.getPoints());
        rule.setRuleId(r.getId());
        rule.setSuccessMessage(r.getSuccessmessage());
        return rule;
    }
}