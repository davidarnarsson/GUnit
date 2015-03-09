package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.BadgeService;
import edu.chl.gunit.core.services.UserBadgeService;
import edu.chl.gunit.core.services.UserService;

import java.util.List;

/**
 * Created by davida on 9.3.2015.
 */
public abstract class BadgeRule implements RuleStrategy {

    private final UserService userService;
    private final UserBadgeService userBadgeService;
    private final BadgeService badgeService;
    
    protected BadgeRule(UserService userService, UserBadgeService userBadgeService, BadgeService badgeService) {
        this.userService = userService;
        this.userBadgeService = userBadgeService;
        this.badgeService = badgeService;
    }

    @Override
    public RuleResult calculate(GamificationContext ctx, RuleRecord self) {

        UserRecord user = userService.get(ctx.getSession().getUserid());


        
        RuleResult result = new RuleResult();
        result.setRule(self);

        if (onlyGetOnce()) {
            List<UserbadgesRecord> badges = userBadgeService.getUserBadges(ctx.getSession().getUserid());
            if (badges.stream().anyMatch(p -> p.getBadgeid().equals(self.getBadgeid()))) {
                return result;
            }
        }
        
        if (shouldGetBadge(user, ctx, self)) {
            BadgeRecord badge = badgeService.get(self.getBadgeid());
            result.getAwardedBadges().add(badge);
            result.setMessage(self.getSuccessmessage());
            result.setPointsAwarded(self.getPoints());
        }

        return result;
    }

    protected abstract boolean shouldGetBadge(UserRecord user, GamificationContext ctx, RuleRecord self);


    protected abstract boolean onlyGetOnce();
}
