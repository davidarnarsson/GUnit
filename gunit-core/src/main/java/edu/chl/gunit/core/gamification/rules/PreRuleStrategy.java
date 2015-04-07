package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.gamification.GamificationContext;

/**
 * Created by davida on 23.2.2015.
 */
public interface PreRuleStrategy extends RuleStrategy {
    public RuleResult calculate(GamificationContext ctx, RuleRecord self);
}
