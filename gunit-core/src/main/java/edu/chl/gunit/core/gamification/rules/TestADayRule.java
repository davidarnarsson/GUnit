package edu.chl.gunit.core.gamification.rules;

import com.google.inject.Inject;
import edu.chl.gunit.core.data.tables.Userbadges;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.services.BadgeService;
import edu.chl.gunit.core.services.UserBadgeService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by davida on 26.2.2015.
 */
public class TestADayRule implements RuleStrategy {

    @Inject
    UserBadgeService userBadgeService;

    @Inject
    BadgeService badgeService;

    @Override
    public RuleResult calculate(GamificationContext ctx, RuleRecord selfRecord) {
        List<UserbadgesRecord> badges = userBadgeService.getUserBadges(ctx.getSession().getUserid());


        Instant midnight = Instant.now().truncatedTo(ChronoUnit.DAYS);
        RuleResult r = new RuleResult();

        // only awarded once a day
        if (badges.stream().anyMatch(p ->p.getBadgeid() == selfRecord.getBadgeid() && p.getEarneddate().after(Date.from(midnight)))) {
            return r;
        }

        if (ctx.getStatistics().getNewTestCases().size() > 0) {
            r.setPointsAwarded(selfRecord.getPoints());
            r.setMessage(selfRecord.getSuccessmessage());
            r.getAwardedBadges().add(badgeService.get(selfRecord.getBadgeid()));
        }

        return r;
    }
}
