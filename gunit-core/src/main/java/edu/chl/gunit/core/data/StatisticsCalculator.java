package edu.chl.gunit.core.data;

import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiJaCoCoResult;
import edu.chl.gunit.commons.api.ApiTestCase;
import edu.chl.gunit.commons.api.ApiTestSuiteResults;
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
    public Statistics calculateStatistics(List<ApiJaCoCoResult> coverageList,
                                    List<ApiTestSuiteResults> suiteList) {
        Statistics statistics = new Statistics();

        double branchCoverage = calculateCoverage(coverageList, ApiJaCoCoResult::getBranchCovered, ApiJaCoCoResult::getBranchMissed);
        double instructionCoverage = calculateCoverage(coverageList, ApiJaCoCoResult::getInstructionCovered, ApiJaCoCoResult::getInstructionMissed);
        double lineCoverage = calculateCoverage(coverageList, ApiJaCoCoResult::getLineCovered, ApiJaCoCoResult::getLineMissed);

        Result<Record4<Integer, Integer, String, String>> existingTestCases = testCaseService.getTestCasesByAuthor();

        // Collect all the test cases -> find those test cases which do not already exist and return them.
        List<ApiTestCase> nonExistingTestCases = suiteList.stream()
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

    private double calculateCoverage(List<ApiJaCoCoResult> coverageList,
                                     ToDoubleFunction<? super ApiJaCoCoResult> coverageFunc,
                                     ToDoubleFunction<? super ApiJaCoCoResult> missFunc) {
        double covered = coverageList.stream().mapToDouble(coverageFunc).sum();
        double missed = coverageList.stream().mapToDouble(missFunc).sum();
        double val = (covered / (covered + missed));

        return Double.isNaN(val) ? 0: val;

    }
}
