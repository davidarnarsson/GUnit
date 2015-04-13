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
        s.setTestSmells(record.getTotaltestsmells());
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
        rule.setBadgeId(r.getBadgeid());
        rule.setPoints(r.getPoints());
        rule.setRuleId(r.getId());
        rule.setDescription(r.getDescription());
        rule.setSuccessMessage(r.getSuccessmessage());
        return rule;
    }

    public static ApiJaCoCoResult from(JacocoresultRecord in) {
        if (in == null) return null;

        ApiJaCoCoResult r = new ApiJaCoCoResult();
        r.setBranchCovered(in.getBranchcovered());
        r.setBranchMissed(in.getBranchmissed());
        r.setClassName(in.getClassname());
        r.setComplexityCovered(in.getComplexitycovered());
        r.setComplexityMissed(in.getComplexitymissed());
        r.setGroupName(in.getGroupname());
        r.setInstructionCovered(in.getInstructioncovered());
        r.setInstructionMissed(in.getInstructionmissed());
        r.setLineCovered(in.getLinecovered());
        r.setLineMissed(in.getLinemissed());
        r.setMethodCovered(in.getMethodcovered());
        r.setMethodMissed(in.getMethodmissed());
        r.setPackageName(in.getPackagename());

        return r;
    }

    public static ApiRuleResult from(RuleresultRecord i) {
        ApiRuleResult r = new ApiRuleResult();

        r.setMessage(i.getMessage());
        r.setPoints(i.getPoints());
        r.setRuleId(i.getRuleid());
        r.setSessionId(i.getSessionid());
        r.setRegardingBadgeId(i.getRegardingbadgeid());
        r.setRegardingUserId(i.getRegardinguserid());

        return r;
    }

    public static ApiClassSetupUsage from(ClasssetupusageRecord r) {
        if (r == null) return null;

        ApiClassSetupUsage u = new ApiClassSetupUsage();
        u.setClassName(r.getClassname());
        u.setId(r.getId());
        u.setSessionId(r.getSessionid());
        u.setTestSmellCount(r.getTestsmells());

        return u;
    }

    public static ApiTestSmell from(TestsmellRecord r) {
        if (r == null) {
            return null;
        }

        ApiTestSmell sm = new ApiTestSmell();
        sm.setId(r.getId());
        sm.setClassSetupUsageId(r.getClasssetupusageid());
        sm.setName(r.getTestcasename());
        sm.setType(r.getType());

        return sm;

    }
}
