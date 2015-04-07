package edu.chl.gunit.core.gamification.rules;

import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.gamification.rules.annotations.PostRule;

/**
 * Created by davida on 23.2.2015.
 */
public class Rule {


    public Rule(RuleRecord record, RuleStrategy strategy) {
        this.record = record;
        this.strategy = strategy;
    }

    private final RuleStrategy strategy;
    private final RuleRecord record;

    public RuleStrategy getStrategy() {
        return this.strategy;
    }

    public RuleRecord getRecord() {
        return this.record;
    }

    public boolean isPostRule() {
        return this.strategy.getClass().isAssignableFrom(PostRuleStrategy.class);
    }

    public boolean isPreRule() {
        return this.strategy.getClass().isAssignableFrom(PreRuleStrategy.class);
    }
}
