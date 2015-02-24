package edu.chl.gunit.core.data;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.records.JacocoresultRecord;
import edu.chl.gunit.core.data.tables.records.SuitetestcaseRecord;
import edu.chl.gunit.core.data.tables.records.TestsuiteresultRecord;
import edu.chl.gunit.core.services.TestCaseService;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

/**
 * Created by davida on 24.2.2015.
 */
public class StatisticsCalculator {

    @Inject
    TestCaseService testCaseService;

    public void calculateStatistics(List<JacocoresultRecord> coverageList,
                                    List<TestsuiteresultRecord> suiteList,
                                    List<SuitetestcaseRecord> testCaseList) {

        double branchCoverage = calculateCoverage(coverageList, JacocoresultRecord::getBranchcovered,JacocoresultRecord::getBranchmissed);
        double instructionCoverage = calculateCoverage(coverageList, JacocoresultRecord::getInstructioncovered, JacocoresultRecord::getInstructionmissed);
        double lineCoverage = calculateCoverage(coverageList, JacocoresultRecord::getLinecovered, JacocoresultRecord::getLinemissed);



    }

    private double calculateCoverage(List<JacocoresultRecord> coverageList,
                                     ToDoubleFunction<? super JacocoresultRecord> coverageFunc,
                                     ToDoubleFunction<? super JacocoresultRecord> missFunc) {
        double covered = coverageList.stream().mapToDouble(coverageFunc).sum();
        double missed = coverageList.stream().mapToDouble(missFunc).sum();
        return covered / (covered + missed);
    }
}
