package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.BadgeService;
import edu.chl.gunit.core.services.UserBadgeService;
import edu.chl.gunit.core.services.UserService;

/**
 * Created by davida on 9.3.2015.
 */
public class GoldBadgeRule extends BadgeRule {

    @Inject
    public GoldBadgeRule(UserService userService, UserBadgeService userBadgeService, BadgeService badgeService) {
        super(userService, userBadgeService, badgeService);
    }

    @Override
    protected boolean shouldGetBadge(UserRecord user, GamificationContext ctx, RuleRecord self) {
        return user.getTotalwrittentests() + ctx.getStatistics().getNewTestCases().size() >= 50;
    }

    @Override
    protected boolean onlyGetOnce() {
        return true;
    }
}
