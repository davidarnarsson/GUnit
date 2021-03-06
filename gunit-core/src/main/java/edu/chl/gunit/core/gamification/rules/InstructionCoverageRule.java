package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.commons.api.SessionStatus;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.SessionService;

import javax.inject.Inject;

/**
 * Created by davida on 7.3.2015.
 */
public class InstructionCoverageRule implements PreRuleStrategy {


    SessionService sessionService;


    @Inject
    public InstructionCoverageRule(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public RuleResult calculate(GamificationContext ctx, RuleRecord self) {
        RuleResult result = new RuleResult();

        SessionRecord last = sessionService.getLatestSession(ctx.getSession().getUserid(), SessionStatus.Processed);

        double currentCoverage = ctx.getStatistics().getInstructionCoverage();
        double lastCoverage = 0;

        if (last != null) {
            lastCoverage = last.getInstructioncoverage();
        }

        int percent = (int)Math.floor((currentCoverage * 100) / 10.0);
        int lastPercent =  (int)Math.floor((lastCoverage * 100) / 10.0);

        if (percent - lastPercent < 0) {
            result.setMessage(String.format("Instruction coverage lækkaði úr %.2f%% niður í %.2f%%!", lastCoverage * 100, currentCoverage * 100));
            result.setPointsAwarded(-lastPercent);
        } else if (percent - lastPercent > 0) {
            result.setMessage(String.format("Instruction coverage hækkaði úr %.2f%% í %.2f%%! Vel gert!", lastCoverage * 100, currentCoverage * 100));
            result.setPointsAwarded(percent);
        }

        return result;
    }
}
