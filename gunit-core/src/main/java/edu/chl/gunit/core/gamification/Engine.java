package edu.chl.gunit.core.gamification;

import edu.chl.gunit.core.gamification.rules.Rule;
import edu.chl.gunit.core.gamification.rules.RuleFactory;

import java.util.List;

/**
 * Created by davida on 23.2.2015.
 */
public class Engine {

    List<Rule> rules;

    public Engine() {
        RuleFactory factory = new RuleFactory();
        rules = factory.getRules();
    }

    public void calculatePoints(GamificationContext ctx) {

    }
}
