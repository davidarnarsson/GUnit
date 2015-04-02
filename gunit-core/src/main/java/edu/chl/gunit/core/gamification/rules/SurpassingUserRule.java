package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.rules.annotations.PostRule;

import java.util.List;

/**
 * Created by davida on 31.3.2015.
 */

@PostRule
public class SurpassingUserRule implements PostRuleStrategy {

    @Override
    public RuleResult calculate(GamificationContext ctx, List<RuleResult> previousResults) {
        return null;
    }
}
