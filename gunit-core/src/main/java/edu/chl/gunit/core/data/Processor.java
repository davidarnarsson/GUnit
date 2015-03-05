package edu.chl.gunit.core.data;

import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.commons.api.ApiTestSuiteResults;
import edu.chl.gunit.core.data.tables.records.*;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.rules.RuleResult;
import edu.chl.gunit.core.services.UserBadgeService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by davida on 24.2.2015.
 */

public class Processor {

    @Inject
    edu.chl.gunit.core.services.SessionService sessionService;

    @Inject
    edu.chl.gunit.core.services.UserService userService;

    @Inject
    edu.chl.gunit.core.services.TestSuiteService testSuiteService;

    @Inject
    edu.chl.gunit.core.services.JaCoCoResultService jaCoCoResultService;

    @Inject
    UserBadgeService userBadgeService;

    @Inject
    edu.chl.gunit.core.services.TestCaseService testCaseService;

    @Inject
    StatisticsCalculator calculator;

    public SessionRecord createNewProcessSession(String user) {
        UserRecord userRecord = userService.getOrCreate(user);
        SessionRecord sessionRecord = sessionService.create(userRecord.getId());

        return sessionRecord;
    }

    public GamificationContext process(SessionRecord session, List<ApiJaCoCoResult> coverageResults, List<ApiTestSuiteResults> testSuiteResults) {


        // calculate statistics and update session accordingly
        Statistics statistics = calculator.calculateStatistics(coverageResults, testSuiteResults);

        // save session
        session.setLinecoverage(statistics.getLineCoverage());
        session.setBranchcoverage(statistics.getBranchCoverage());
        session.setInstructioncoverage(statistics.getInstructionCoverage());
        session.setNewtests(statistics.getNewTestCases().size());

        sessionService.update(session);
        List<JacocoresultRecord> jacocoresultRecords = new ArrayList<>();
        List<TestsuiteresultRecord> testsuiteresultRecords = new ArrayList<>();
        List<SuitetestcaseRecord> suitetestcaseRecords = new ArrayList<>();
        if (coverageResults != null && coverageResults.size() > 0) {

            jacocoresultRecords = coverageResults.stream().map(x -> jaCoCoResultService.createFromResult(x, session)).collect(Collectors.toList());
        }

        if (testSuiteResults != null && testSuiteResults.size() > 0) {
            testsuiteresultRecords = testSuiteResults.stream().map(x -> {
                TestsuiteresultRecord rc = testSuiteService.createResult(x, session);


                suitetestcaseRecords.addAll(x.getTestCases().stream()
                        .map(y -> testCaseService.createTestCase(y, rc.getId()))
                        .collect(Collectors.toList()));

                return rc;
            }).collect(Collectors.toList());
        }

        return new GamificationContext(statistics, session, jacocoresultRecords, testsuiteresultRecords, suitetestcaseRecords);
    }

    public void markSessionAsProcessed(SessionRecord session) {
        sessionService.setProcessed(session);
    }

    public void markSessionAsFailed(SessionRecord session) {
        sessionService.setFailed(session);
    }

    public void awardBadge(Integer userid, BadgeRecord b, Timestamp date, RuleRecord rule, int sessionId) {
        userBadgeService.awardBadge(userid, b.getId(), date, rule.getId(), sessionId);
    }

    public void updateUserStatistics(Statistics ctx, List<RuleResult> results, SessionRecord session) {
        int pointsForSession = 0;
        int badgesEarned = 0;
        for (RuleResult result : results) {
            // award earned badges
            for (BadgeRecord b : result.getAwardedBadges()) {
                awardBadge(session.getUserid(), b, session.getDate(), result.getRule(), session.getSessionid());
            }

            badgesEarned += result.getAwardedBadges().size();

            pointsForSession += result.getPointsAwarded();
        }
        // update session with data
        session.setPointscollected(pointsForSession);
        session.setBadgesearned(badgesEarned);
        sessionService.update(session);

        // update user with metadata
        UserRecord user = userService.get(session.getUserid());
        user.setLastbranchcoverage(ctx.getBranchCoverage());
        user.setLastinstructioncoverage(ctx.getInstructionCoverage());
        user.setTotalwrittentests(user.getTotalwrittentests() + ctx.getNewTestCases().size());
        user.setPoints(user.getPoints() + pointsForSession);

        userService.update(user);
    }
}
