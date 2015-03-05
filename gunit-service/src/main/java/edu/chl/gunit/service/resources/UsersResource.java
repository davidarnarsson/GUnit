package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiBadge;
import edu.chl.gunit.commons.api.ApiRule;
import edu.chl.gunit.commons.api.ApiUserBadge;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.commons.api.ApiUser;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;
import edu.chl.gunit.core.services.*;
import edu.chl.gunit.service.api.Utils;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static edu.chl.gunit.core.data.Tables.BADGE;

/**
 * Created by davida on 2.3.2015.
 */
@Path("/api/users")
@Produces("application/json")
public class UsersResource extends AbstractResource<UserRecord, UserService, ApiUser> {

    private UserBadgeService userBadgeService;
    private BadgeService badgeService;
    private RuleService ruleService;
    private UserService userService;

    @Inject
    public UsersResource(UserBadgeService userBadgeService, BadgeService badgeService, RuleService ruleService, UserService userService) {
        super(in -> Utils.from(in));

        this.userBadgeService = userBadgeService;
        this.badgeService = badgeService;
        this.ruleService = ruleService;
        this.userService = userService;
    }


    @GET
    @Timed
    public List<ApiUser> getUsers() {
        return userService
                .getList()
                .stream()
                .map(Utils::from)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/by-name")
    @Timed
    public ApiUser getUser(@QueryParam("name") String name) {
        if (!(name == null || "".equals(name))) {
            return Utils.from(userService.getUser(name));
        }

        return null;
    }
    @GET
    @Path("/badges/{id}")
    public List<ApiUserBadge> getUserBadges(@PathParam("id") int userId) {
        List<UserbadgesRecord> badges = userBadgeService.getUserBadges(userId);

        List<Integer> badgeIds = badges.stream().mapToInt(p -> p.getBadgeid())
                .distinct()
                .boxed()
                .collect(Collectors.toList());

        List<Integer> ruleIds = badges.stream().mapToInt(p -> p.getFromruleid())
                .distinct()
                .boxed()
                .collect(Collectors.toList());

        Map<Integer, ApiBadge> badgesById = badgeService.getList(BADGE.ID.in(badgeIds)).stream()
                .map(Utils::from)
                .collect(Collectors.toMap(k -> k.getId(), v -> v));

        Map<Integer, ApiRule> rulesById = ruleService.getList(Tables.RULE.ID.in(ruleIds)).stream()
                .map(Utils::from)
                .collect(Collectors.toMap(p -> p.getRuleId(), v -> v));


        List<ApiUserBadge> userBadges = new ArrayList<>();

        for (UserbadgesRecord r : badges) {
            ApiUserBadge userBadge = Utils.from(r);
            userBadge.setBadge(badgesById.getOrDefault(r.getBadgeid(), null));
            userBadge.setFromRule(rulesById.getOrDefault(r.getFromruleid(), null));
            userBadges.add(userBadge);
        }

        return userBadges;
        /*Map<Integer, ApiSession> sessions = facade.sessionService().getList(SESSION.SESSIONID.in(sessionIds)).stream()
                .map(Utils::from)
                .collect(Collectors.toMap(m -> m.getSessionId(), k -> k));*/
    }

    @Override
    protected Service<UserRecord> getService() {
        return userService;
    }
}
