package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.rules.annotations.PostRule;
import edu.chl.gunit.core.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * Created by davida on 31.3.2015.
 */

@PostRule
public class SurpassingUserRule implements PostRuleStrategy {

    @Inject
    UserService getUserService;

    @Override
    public RuleResult calculate(GamificationContext ctx, List<RuleResult> previousResults) {
        int points = previousResults.stream().mapToInt(RuleResult::getPointsAwarded).sum();

        List<UserRecord> users = getUserService.getList();
        Optional<UserRecord> me = users.stream().filter(x -> x.getId() == ctx.getSession().getUserid()).findFirst();

        if (!me.isPresent()) {
            return null;
        }

        int newTotalPoints = me.get().getPoints() + points;
        // get all users that are surpassed
        Stream<UserRecord> surpassedUsers = users.stream()
                .filter(x -> x.getPoints() >= me.get().getPoints() && x.getPoints() < newTotalPoints);

        if (surpassedUsers.count() > 0) {
            String usernames = surpassedUsers.reduce("", (a, b) -> String.format("%s, %s", a, b.getName()), (a, b) -> a + b);

            if (surpassedUsers.count() > 1) {
                int lastcomma = usernames.lastIndexOf(',');
                usernames = String.format("%s og%s", usernames.substring(0, lastcomma-1), usernames.substring(lastcomma));
            }

            RuleResult rr = new RuleResult();
            rr.setMessage(String.format("Vel gert! Þú fórst fram úr %s á stigaborðinu með %d stigin þín!", usernames, newTotalPoints));
            return rr;
        }

        return null;
    }
}
