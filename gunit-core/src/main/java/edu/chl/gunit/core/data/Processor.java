package edu.chl.gunit.core.data;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.Facade;
import edu.chl.gunit.core.data.tables.records.*;
import edu.chl.gunit.core.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by davida on 24.2.2015.
 */

public class Processor {

    @Inject
    SessionService sessionService;

    @Inject
    UserService userService;

    @Inject
    TestSuiteService testSuiteService;

    @Inject
    JaCoCoResultService jaCoCoResultService;

    @Inject
    TestCaseService testCaseService;

    public int process(String user, List<JaCoCoResult> coverageResults, List<TestSuiteResults> testSuiteResults) {
        UserRecord userRecord = userService.getOrCreate(user);
        SessionRecord sessionRecord = sessionService.create(userRecord.getId());

        List<JacocoresultRecord> coverageRecords = new ArrayList<>();
        List<TestsuiteresultRecord> testsuiteresultRecords = new ArrayList<>();
        List<SuitetestcaseRecord> testcaseRecords = new ArrayList<>();

        if (coverageResults != null && coverageResults.size() > 0) {
            coverageRecords = coverageResults.stream().map(x -> jaCoCoResultService.createFromResult(x, sessionRecord)).collect(Collectors.toList());
        }

        if (testSuiteResults != null && testSuiteResults.size() > 0) {
            testsuiteresultRecords = testSuiteResults.stream().map(x -> {
                TestsuiteresultRecord rc = testSuiteService.createResult(x, sessionRecord);

                testcaseRecords.addAll(
                        x.getTestCases().stream()
                            .map(y -> testCaseService.createTestCase(y, rc.getId()))
                            .collect(Collectors.toList())
                );

                return rc;
            }).collect(Collectors.toList());
        }

        StatisticsCalculator calculator = new StatisticsCalculator();
        calculator.calculateStatistics(coverageRecords,testsuiteresultRecords,testcaseRecords);

        return sessionRecord.getSessionid();
    }
}
