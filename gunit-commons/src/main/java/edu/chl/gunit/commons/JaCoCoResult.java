package edu.chl.gunit.commons;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Contains the JaCoCo CSV results.
 * Created by davida on 06/02/15.
 */
public class JaCoCoResult {

    public void setClassName(String className) {
        this.className = className;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    private String className;
    private String groupName;
    private String packageName;

    private int instructionMissed;
    private int instructionCovered;
    private int branchMissed;
    private int branchCovered;
    private int lineMissed;
    private int lineCovered;
    private int complexityMissed;
    private int complexityCovered;
    private int methodMissed;
    private int methodCovered;

    private Date date;

    public JaCoCoResult() {
    }

    public JaCoCoResult(String className, String groupName, String packageName) {
        this.className = className;
        this.groupName = groupName;
        this.packageName = packageName;
    }

    @JsonProperty
    public String getPackageName() {
        return packageName;
    }

    @JsonProperty
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty
    public String getClassName() {
        return className;
    }

    @JsonProperty
    public int getInstructionMissed() {
        return instructionMissed;
    }

    public void setInstructionMissed(int instructionMissed) {
        this.instructionMissed = instructionMissed;
    }

    @JsonProperty
    public int getInstructionCovered() {
        return instructionCovered;
    }

    public void setInstructionCovered(int instructionCovered) {
        this.instructionCovered = instructionCovered;
    }

    @JsonProperty
    public int getBranchMissed() {
        return branchMissed;
    }

    public void setBranchMissed(int branchMissed) {
        this.branchMissed = branchMissed;
    }

    @JsonProperty
    public int getBranchCovered() {
        return branchCovered;
    }

    public void setBranchCovered(int branchCovered) {
        this.branchCovered = branchCovered;
    }

    @JsonProperty
    public int getLineMissed() {
        return lineMissed;
    }

    public void setLineMissed(int lineMissed) {
        this.lineMissed = lineMissed;
    }

    @JsonProperty
    public int getLineCovered() {
        return lineCovered;
    }

    public void setLineCovered(int lineCovered) {
        this.lineCovered = lineCovered;
    }

    @JsonProperty
    public int getComplexityMissed() {
        return complexityMissed;
    }

    public void setComplexityMissed(int complexityMissed) {
        this.complexityMissed = complexityMissed;
    }

    @JsonProperty
    public int getComplexityCovered() {
        return complexityCovered;
    }

    public void setComplexityCovered(int complexityCovered) {
        this.complexityCovered = complexityCovered;
    }

    @JsonProperty
    public int getMethodMissed() {
        return methodMissed;
    }

    public void setMethodMissed(int methodMissed) {
        this.methodMissed = methodMissed;
    }

    @JsonProperty
    public int getMethodCovered() {
        return methodCovered;
    }

    public void setMethodCovered(int methodCovered) {
        this.methodCovered = methodCovered;
    }

    @JsonProperty
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
