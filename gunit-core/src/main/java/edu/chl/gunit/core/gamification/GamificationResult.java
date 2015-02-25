package edu.chl.gunit.core.gamification;

import edu.chl.gunit.core.data.tables.records.BadgeRecord;

import java.util.List;

/**
 * Created by davida on 25.2.2015.
 */
public class GamificationResult {

    public int getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(int pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public List<BadgeRecord> getBadgesAwarded() {
        return badgesAwarded;
    }

    public void setBadgesAwarded(List<BadgeRecord> badgesAwarded) {
        this.badgesAwarded = badgesAwarded;
    }

    public double getBranchCoverage() {
        return branchCoverage;
    }

    public void setBranchCoverage(double branchCoverage) {
        this.branchCoverage = branchCoverage;
    }

    public double getInstructionCoverage() {
        return instructionCoverage;
    }

    public void setInstructionCoverage(double instructionCoverage) {
        this.instructionCoverage = instructionCoverage;
    }

    public double getLineCoverage() {
        return lineCoverage;
    }

    public void setLineCoverage(double lineCoverage) {
        this.lineCoverage = lineCoverage;
    }

    private int pointsAwarded;

    private List<BadgeRecord> badgesAwarded;

    private double branchCoverage;

    private double instructionCoverage;

    private double lineCoverage;
}
