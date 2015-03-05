import static edu.chl.gunit.core.data.Tables.BADGE;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import edu.chl.gunit.commons.api.ApiUserBadge;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;
import edu.chl.gunit.core.services.BadgeService;
import edu.chl.gunit.core.services.RuleService;
import edu.chl.gunit.core.services.UserBadgeService;
import edu.chl.gunit.core.services.UserService;
import edu.chl.gunit.service.resources.GamificationResource;
import edu.chl.gunit.service.resources.UsersResource;
import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by davida on 5.3.2015.
 */
public class TestUsersResource {
    final int userId = 1, badgeId = 2, ruleId = 3, sessionId = 4;

    private UserService mockUserService() {
        UserService mockUserService = mock(UserService.class);

        UserRecord user = new UserRecord(userId, "Mock user", 20, new Timestamp(new Date().getTime()), 1, 10.0, 10.0);

        when(mockUserService.get(userId)).thenReturn(user);
        return mockUserService;
    }

    private BadgeService mockBadgeService() {
        BadgeService mockBadgeService = mock(BadgeService.class);
        List<BadgeRecord> badges = new ArrayList<>();
        badges.add(new BadgeRecord(badgeId, "Mock badge", null));
        when(mockBadgeService.getList(BADGE.ID.in(new Integer[]{badgeId}))).thenReturn(badges);
        return mockBadgeService;
    }

    private RuleService mockRuleService() {
        RuleService mockRuleService = mock(RuleService.class);
        List<RuleRecord> rules = new ArrayList<>();
        rules.add(new RuleRecord(ruleId, "mock.class.name.Rule",20, "This is a mock rule!", badgeId));
        when(mockRuleService.getList(Tables.RULE.ID.in(new Integer[] { ruleId}))).thenReturn(rules);

        return mockRuleService;
    }

    private UserBadgeService mockUserBadgeService() {
        UserBadgeService mockUserBadgeService = mock(UserBadgeService.class);
        List<UserbadgesRecord> userBadges = new ArrayList<UserbadgesRecord>();
        userBadges.add(new UserbadgesRecord(userId,badgeId,new Timestamp(new Date().getTime()),ruleId,sessionId));
        when(mockUserBadgeService.getUserBadges(userId)).thenReturn(userBadges);
        return mockUserBadgeService;
    }

    @Test
    public void testGetUserBadges() {


        UsersResource resource = new UsersResource(
                mockUserBadgeService(),
                mockBadgeService(),
                mockRuleService(),
                mockUserService()
                );

        List<ApiUserBadge> actual = resource.getUserBadges(userId);

        assertThat("actual contains one thing", actual.size(), is(equalTo(1)));

        ApiUserBadge badge = actual.get(0);
        assertThat("actual user id is 1", badge.getUserId(), is(equalTo(userId)));
        assertThat("actual badge id is 2", badge.getBadge().getId(), is(equalTo(badgeId)));
        assertThat("actual rule id is 3", badge.getFromRule().getRuleId(), is(equalTo(ruleId)));
        assertThat("actual session id is 4", badge.getSessionId(), is(equalTo(sessionId)));
    }
}
