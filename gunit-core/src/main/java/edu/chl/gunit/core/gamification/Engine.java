package edu.chl.gunit.core.gamification;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.Badge;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.gamification.rules.Rule;
import edu.chl.gunit.core.gamification.rules.RuleFactory;
import edu.chl.gunit.core.gamification.rules.RuleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 23.2.2015.
 */
public class Engine {

    List<Rule> rules;

    @Inject
    public Engine( RuleFactory factory) {
        rules = factory.getRules();
    }

    public List<RuleResult> calculatePoints(GamificationContext ctx) {

        List<RuleResult> out = new ArrayList<>();
        for (Rule r : rules) {
            RuleResult result = r.getStrategy().calculate(ctx, r.getRecord());
            result.setRule(r.getRecord());

            List<BadgeRecord> badges = result.getAwardedBadges();

            out.add(result);
        }

        return out;
    }
}
