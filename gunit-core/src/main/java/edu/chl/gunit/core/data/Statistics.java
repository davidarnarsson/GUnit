package edu.chl.gunit.core.data;

import edu.chl.gunit.commons.TestCase;

import java.util.List;

/**
 * Created by davida on 25.2.2015.
 */
public class Statistics {

    /* Any new tests detected in a given session */
    private List<TestCase> newTestCases;

    /* The total line coverage for a given session */
    private double lineCoverage;

    /* The total branch coverage for a given session */
    private double branchCoverage;

    /* The total instruction coverage for a given session */
    private double instructionCoverage;


    public double getInstructionCoverage() {
        return instructionCoverage;
    }

    public void setInstructionCoverage(double instructionCoverage) {
        this.instructionCoverage = instructionCoverage;
    }


    public double getBranchCoverage() {
        return branchCoverage;
    }

    public void setBranchCoverage(double branchCoverage) {
        this.branchCoverage = branchCoverage;
    }

    public double getLineCoverage() {
        return lineCoverage;
    }

    public void setLineCoverage(double lineCoverage) {
        this.lineCoverage = lineCoverage;
    }

    public List<TestCase> getNewTestCases() {
        return newTestCases;
    }

    public void setNewTestCases(List<TestCase> newTestCases) {
        this.newTestCases = newTestCases;
    }


}
