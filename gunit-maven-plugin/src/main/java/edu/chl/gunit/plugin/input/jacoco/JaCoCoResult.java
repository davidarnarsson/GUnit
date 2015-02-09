package edu.chl.gunit.plugin.input.jacoco;

/**
 * Contains the JaCoCo CSV results.
 * Created by davida on 06/02/15.
 */
public class JaCoCoResult {

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


    public JaCoCoResult(String className, String groupName, String packageName) {
        this.className = className;
        this.groupName = groupName;
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getClassName() {
        return className;
    }

    public int getInstructionMissed() {
        return instructionMissed;
    }

    public void setInstructionMissed(int instructionMissed) {
        this.instructionMissed = instructionMissed;
    }

    public int getInstructionCovered() {
        return instructionCovered;
    }

    public void setInstructionCovered(int instructionCovered) {
        this.instructionCovered = instructionCovered;
    }

    public int getBranchMissed() {
        return branchMissed;
    }

    public void setBranchMissed(int branchMissed) {
        this.branchMissed = branchMissed;
    }

    public int getBranchCovered() {
        return branchCovered;
    }

    public void setBranchCovered(int branchCovered) {
        this.branchCovered = branchCovered;
    }

    public int getLineMissed() {
        return lineMissed;
    }

    public void setLineMissed(int lineMissed) {
        this.lineMissed = lineMissed;
    }

    public int getLineCovered() {
        return lineCovered;
    }

    public void setLineCovered(int lineCovered) {
        this.lineCovered = lineCovered;
    }

    public int getComplexityMissed() {
        return complexityMissed;
    }

    public void setComplexityMissed(int complexityMissed) {
        this.complexityMissed = complexityMissed;
    }

    public int getComplexityCovered() {
        return complexityCovered;
    }

    public void setComplexityCovered(int complexityCovered) {
        this.complexityCovered = complexityCovered;
    }

    public int getMethodMissed() {
        return methodMissed;
    }

    public void setMethodMissed(int methodMissed) {
        this.methodMissed = methodMissed;
    }

    public int getMethodCovered() {
        return methodCovered;
    }

    public void setMethodCovered(int methodCovered) {
        this.methodCovered = methodCovered;
    }
}
