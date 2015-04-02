package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.GamificationResult;

import java.util.List;

/**
 * Created by davida on 31.3.2015.
 */
public interface PostRuleStrategy extends RuleStrategy {
    public RuleResult calculate(GamificationContext ctx, List<RuleResult> previousResults);
}
