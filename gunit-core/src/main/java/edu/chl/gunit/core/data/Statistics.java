package edu.chl.gunit.core.data;

import edu.chl.gunit.commons.api.ApiTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 25.2.2015.
 */
public class Statistics {

    /* Any new tests detected in a given session */
    private List<ApiTestCase> newTestCases;

    /* The total line coverage for a given session */
    private double lineCoverage;

    /* The total branch coverage for a given session */
    private double branchCoverage;

    /* The total instruction coverage for a given session */
    private double instructionCoverage;
    private int testSmells;


    public Statistics() {
        newTestCases = new ArrayList<>();
    }

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

    public List<ApiTestCase> getNewTestCases() {
        return newTestCases;
    }

    public void setNewTestCases(List<ApiTestCase> newTestCases) {
        this.newTestCases = newTestCases;
    }


    public void setTestSmells(int testSmells) {
        this.testSmells = testSmells;
    }

    public int getTestSmells() {
        return testSmells;
    }
}
