package edu.chl.gunit.core.data;

import com.google.inject.Inject;
import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.data.tables.Badge;
import edu.chl.gunit.core.data.tables.records.*;
import edu.chl.gunit.core.gamification.GamificationContext;

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
    edu.chl.gunit.core.services.TestCaseService testCaseService;

    public SessionRecord createNewProcessSession(String user) {
        UserRecord userRecord = userService.getOrCreate(user);
        SessionRecord sessionRecord = sessionService.create(userRecord.getId());

        return sessionRecord;
    }

    public GamificationContext process(SessionRecord session, List<JaCoCoResult> coverageResults, List<TestSuiteResults> testSuiteResults) {


        // calculate statistics and update session accordingly
        StatisticsCalculator calculator = new StatisticsCalculator();
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
}
