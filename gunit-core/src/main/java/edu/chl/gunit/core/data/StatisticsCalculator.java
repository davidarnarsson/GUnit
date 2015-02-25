package edu.chl.gunit.core.data;

import com.google.inject.Inject;
import edu.chl.gunit.commons.JaCoCoResult;
import edu.chl.gunit.commons.TestCase;
import edu.chl.gunit.commons.TestSuiteResults;
import edu.chl.gunit.core.data.tables.records.*;
import org.jooq.Record4;
import org.jooq.Result;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Created by davida on 24.2.2015.
 */
public class StatisticsCalculator {

    @Inject
    edu.chl.gunit.core.services.TestCaseService testCaseService;

    /**
     * Calculates statistics based on the coverage and test suite info passed in.
     * This information has not been saved to the database as of yet.
     * */
    public Statistics calculateStatistics(List<JaCoCoResult> coverageList,
                                    List<TestSuiteResults> suiteList) {
        Statistics statistics = new Statistics();

        double branchCoverage = calculateCoverage(coverageList, JaCoCoResult::getBranchCovered,JaCoCoResult::getBranchMissed);
        double instructionCoverage = calculateCoverage(coverageList, JaCoCoResult::getInstructionCovered, JaCoCoResult::getInstructionMissed);
        double lineCoverage = calculateCoverage(coverageList, JaCoCoResult::getLineCovered, JaCoCoResult::getLineMissed);

        Result<Record4<Integer, Integer, String, String>> existingTestCases = testCaseService.getTestCasesByAuthor();

        // Collect all the test cases -> find those test cases which do not already exist and return them.
        List<TestCase> nonExistingTestCases = suiteList.stream()
                .map(x -> x.getTestCases())
                .flatMap(x -> x.stream())
                .filter(c -> !existingTestCases
                        .stream()
                        .anyMatch(p -> p.value3().equals(c.getName()) && p.value4().equals(c.getClassName()))
                ).collect(Collectors.toList());

        statistics.setBranchCoverage(branchCoverage);
        statistics.setInstructionCoverage(instructionCoverage);
        statistics.setLineCoverage(lineCoverage);
        statistics.setNewTestCases(nonExistingTestCases);


        return statistics;
    }

    private double calculateCoverage(List<JaCoCoResult> coverageList,
                                     ToDoubleFunction<? super JaCoCoResult> coverageFunc,
                                     ToDoubleFunction<? super JaCoCoResult> missFunc) {
        double covered = coverageList.stream().mapToDouble(coverageFunc).sum();
        double missed = coverageList.stream().mapToDouble(missFunc).sum();
        return covered / (covered + missed);
    }
}
