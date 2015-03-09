import edu.chl.gunit.commons.api.ApiTestCase;
import edu.chl.gunit.core.data.Statistics;
import edu.chl.gunit.core.data.tables.records.*;
import edu.chl.gunit.core.gamification.GamificationContext;
import edu.chl.gunit.core.gamification.rules.BronzeBadgeRule;
import edu.chl.gunit.core.gamification.rules.RuleResult;
import edu.chl.gunit.core.services.BadgeService;
import edu.chl.gunit.core.services.UserBadgeService;
import edu.chl.gunit.core.services.UserService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by davida on 9.3.2015.
 */
public class TestBronzeBadgeRule {

    @Test
    public void testBronzeBadgeGetBadge() {
        UserService mockUserService = mock(UserService.class);
        UserBadgeService mockUserBadgeService = mock(UserBadgeService.class);
        BadgeService mockBadgeService = mock(BadgeService.class);

        when(mockUserService.get(1)).thenReturn(new UserRecord(1,"test", 10,null,9,0.0,0.0));
        when(mockUserBadgeService.getUserBadges(1)).thenReturn(new ArrayList<UserbadgesRecord>());
        when(mockBadgeService.get(2)).thenReturn(new BadgeRecord(2, "TestBadge", null));

        GamificationContext ctx = mock(GamificationContext.class);
        Statistics s = new Statistics();
        s.getNewTestCases().add(new ApiTestCase("testTestcase"));
        when(ctx.getStatistics()).thenReturn(s);
        when(ctx.getSession()).thenReturn(new SessionRecord(0,1,null,0.0,0.0,0.0,0,0,0,0));
        RuleRecord r = new RuleRecord(3,null,10,"yay",2, null);

        BronzeBadgeRule rule = new BronzeBadgeRule(mockUserService,mockUserBadgeService, mockBadgeService);
        RuleResult result = rule.calculate(ctx, r);

        assertEquals("points should be 10", 10, result.getPointsAwarded());
        assertEquals("message should be yay", "yay", result.getMessage());
        assertEquals("badge should be TestBadge", "TestBadge", result.getAwardedBadges().get(0).getName());
    }
}
