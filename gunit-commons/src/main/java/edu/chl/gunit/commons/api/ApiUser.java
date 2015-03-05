package edu.chl.gunit.commons.api;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.sql.Timestamp;

/**
 * Created by davida on 2.3.2015.
 */
public class ApiUser {

    private Integer totalWrittenTests;

    @JsonProperty
    public Integer getTotalWrittenTests() {
        return totalWrittenTests;
    }

    public void setTotalWrittenTests(Integer totalWrittenTests) {
        this.totalWrittenTests = totalWrittenTests;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty
    public Double getLastBranchCoverage() {
        return lastBranchCoverage;
    }

    public void setLastBranchCoverage(Double lastBranchCoverage) {
        this.lastBranchCoverage = lastBranchCoverage;
    }

    @JsonProperty
    public Double getLastInstructionCoverage() {
        return lastInstructionCoverage;
    }

    public void setLastInstructionCoverage(Double lastInstructionCoverage) {
        this.lastInstructionCoverage = lastInstructionCoverage;
    }

    @JsonProperty
    public Timestamp getLastWrittenTest() {
        return lastWrittenTest;
    }

    public void setLastWrittenTest(Timestamp lastWrittenTest) {
        this.lastWrittenTest = lastWrittenTest;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    private Integer id;
    private Double lastBranchCoverage;
    private Double lastInstructionCoverage;
    private Timestamp lastWrittenTest;
    private String name;
    private Integer points;

    public ApiUser() {}

    public ApiUser(Integer id, Double lastbranchcoverage, Double lastinstructioncoverage, Timestamp lastwrittentest, String name, Integer points, Integer totalwrittentests) {
        this.id = id;
        this.lastBranchCoverage = lastbranchcoverage;
        this.lastInstructionCoverage =lastinstructioncoverage;
        this.lastWrittenTest = lastwrittentest;
        this.name = name;
        this.points = points;
        this.totalWrittenTests = totalwrittentests;
    }


}
