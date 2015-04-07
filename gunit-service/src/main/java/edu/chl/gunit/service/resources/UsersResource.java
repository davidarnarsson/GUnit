package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import edu.chl.gunit.commons.api.*;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.RuleRecord;
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
    private final RuleResultService ruleResultService;
    private final SessionService sessionService;


    @Inject
    public UsersResource(UserBadgeService userBadgeService, BadgeService badgeService, RuleService ruleService, UserService userService, RuleResultService ruleResultService, SessionService sessionService) {
        super(in -> Utils.from(in));

        this.userBadgeService = userBadgeService;
        this.badgeService = badgeService;
        this.ruleService = ruleService;
        this.userService = userService;
        this.ruleResultService = ruleResultService;
        this.sessionService = sessionService;
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
    @Path("{id}/messages")
    public List<ApiRuleResult> getRuleResults(@PathParam("id") int id) {
        List<ApiRuleResult> r = ruleResultService.getRuleResults(id, false).stream().map(i -> Utils.from(i)).collect(Collectors.toList());

        Map<Integer, ApiRule> rules = r.stream()
                .mapToInt(ApiRuleResult::getRuleId)
                .distinct()
                .mapToObj(ruleService::get)
                .map(Utils::from)
                .collect(Collectors.toMap(ApiRule::getRuleId, v -> v));

        Map<Integer, ApiSession> sessions = r.stream()
                .mapToInt(ApiRuleResult::getSessionId)
                .distinct()
                .mapToObj(sessionService::get)
                .map(Utils::from)
                .collect(Collectors.toMap(ApiSession::getSessionId, v -> v));

        Map<Integer, ApiUser> users = getUsers().stream().collect(Collectors.toMap(ApiUser::getId, y -> y));

        Map<Integer, ApiBadge> badges = badgeService.getList().stream().map(Utils::from).collect(Collectors.toMap(ApiBadge::getId, v -> v));

        r.stream().forEach(ar -> {
            ar.setRule(rules.getOrDefault(ar.getRuleId(), null));
            ar.setSession(sessions.getOrDefault(ar.getSessionId(), null));
            ar.setBadge(badges.getOrDefault(ar.getRegardingBadgeId(),null));
            ar.setRegardingUser(users.getOrDefault(ar.getRegardingUserId(), null));
        });

        return r;
    }

    @GET
    @Path("{id}/badges")
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
