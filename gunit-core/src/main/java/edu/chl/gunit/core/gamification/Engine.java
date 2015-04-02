package edu.chl.gunit.core.gamification;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.Badge;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.gamification.rules.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * This needs some refactoring ...
 * Created by davida on 23.2.2015.
 */
public class Engine {

    List<Rule> rules;

    @Inject
    public Engine(RuleFactory factory) {
        rules = factory.getRules();
    }

    public List<RuleResult> calculatePoints(GamificationContext ctx) {

        List<RuleResult> out = new ArrayList<>();
        for (Rule r : rules.stream().filter(Rule::isPreRule).collect(Collectors.toList())) {
            RuleResult result = ((PreRuleStrategy) r.getStrategy()).calculate(ctx, r.getRecord());
            result.setRule(r.getRecord());

            out.add(result);
        }

        for (Rule r : rules.stream().filter(Rule::isPostRule).collect(Collectors.toList())) {
            RuleResult rr = ((PostRuleStrategy) r.getStrategy()).calculate(ctx, out);

            out.add(rr);
        }
        return out;
    }
}
