package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.SessionService;

import javax.inject.Inject;

/**
 * Created by davida on 7.3.2015.
 *
 *
 * Branch Coverage

     0-10%   < 	: 	1 stig
     > 10-20% < 	: 	2 stig
     > 20-30% < 	:	4 stig
     > 30-40% < 	:	6 stig
     > 40-50% < 	:	8 stig
     >  50-60% < 	:	11 stig
     > 60%		:	15 stig
 */
public class BranchCoveragePreRule implements PreRuleStrategy {

    SessionService sessionService;

    @Inject
    public BranchCoveragePreRule(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public RuleResult calculate(GamificationContext ctx, RuleRecord self) {
        RuleResult result = new RuleResult();

        SessionRecord last = sessionService.getLatestSession(ctx.getSession().getUserid(), SessionStatus.Processed);

        double currentCoverage = ctx.getStatistics().getBranchCoverage();
        double lastCoverage = 0;

        if (last != null) {
            lastCoverage = last.getBranchcoverage();
        }

        int percent = (int)Math.floor(currentCoverage / 10.0);
        int lastPercent =  (int)Math.floor(lastCoverage / 10.0);

        result.setPointsAwarded(calculatePoints(percent,lastPercent));

        if (result.getPointsAwarded() > 0) {
            result.setMessage(String.format("Þú hækkaðir branch coverage upp í %.2f%%! Vel gert!", currentCoverage));
        } else if (result.getPointsAwarded() < 0) {
            result.setMessage(String.format("Branch coverage lækkaði úr %.2f%% í %.2f%%!", lastCoverage, currentCoverage));
        }

        return result;
    }

    /**
     *   0 - 10 1
         10 - 20 2
         20 - 30 4
         30 - 40 6
         40 - 50 8
         50 - 60 11
         60 >    15

         60 - 50 -15
         50 - 40 -11
         40 - 30 -8
         30 - 20 -6
         20 - 10 -4
         10 - 0  -2
     *
     *
     * */
    private int calculatePoints(int current, int last) {
        if (current > last) {
            return getPoints(current);
        } else if (last > current) {
            return -getPoints(last);
        }
        return 0;
    }

    private int getPoints(int perc) {
        switch (perc) {
            case 0: return 0;
            case 1: return 1;
            case 2: return 2;
            case 3: return 4;
            case 4: return 6;
            case 5: return 8;
            case 6: return 11;
            default: return 15;
        }
    }
}
