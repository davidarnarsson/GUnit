package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.gamification.GamificationContext;

/**
 * Created by davida on 12.3.2015.
 */
public class ExecutionRule implements RuleStrategy{
    @Override
    public RuleResult calculate(GamificationContext ctx, RuleRecord self) {
        RuleResult result = new RuleResult();
        result.setPointsAwarded(1 + ctx.getStatistics().getNewTestCases().size() * 2);

        if (ctx.getStatistics().getNewTestCases().size() > 0) {
            result.setMessage(String.format("Þú fékkst %d stig fyrir að skrifa %d ný próf, auk þess að fá 1 stig fyrir að keyra prófin!", ctx.getStatistics().getNewTestCases().size() * 2, ctx.getStatistics().getNewTestCases().size()));
        }
        if (ctx.getStatistics().getNewTestCases().size() == 0) {
            result.setMessage(String.format("Þú fékkst 1 stig fyrir að keyra prófin."));
        }

        result.setRule(self);

        return result;
    }
}
