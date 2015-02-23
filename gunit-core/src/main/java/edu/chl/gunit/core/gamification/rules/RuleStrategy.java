package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.gamification.GamificationContext;

/**
 * Created by davida on 23.2.2015.
 */
public interface RuleStrategy {

    public int calculate(GamificationContext ctx);
}
