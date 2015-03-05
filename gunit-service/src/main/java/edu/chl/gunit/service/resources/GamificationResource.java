package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import edu.chl.gunit.commons.api.*;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.Tables;
import edu.chl.gunit.core.data.tables.records.BadgeRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.data.tables.records.UserbadgesRecord;
import edu.chl.gunit.core.services.BadgeService;
import edu.chl.gunit.core.services.RuleService;
import edu.chl.gunit.core.services.UserBadgeService;
import edu.chl.gunit.core.services.UserService;
import edu.chl.gunit.service.api.Utils;
import org.jooq.TableField;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.chl.gunit.core.data.Tables.BADGE;
import static edu.chl.gunit.core.data.Tables.SESSION;
import static edu.chl.gunit.core.data.Tables.USER;

/**
 * Created by davida on 2.3.2015.
 */
@Path("/api/game")
@Produces("application/json")
public class GamificationResource {


    private UserService userService;

    private BadgeService badgeService;

    private RuleService ruleService;
    private final UserBadgeService userBadgeService;


    @Inject
    public GamificationResource(UserService userService, BadgeService badgeService, RuleService ruleService, UserBadgeService userBadgeService) {
        this.userService = userService;
        this.badgeService = badgeService;
        this.ruleService = ruleService;
        this.userBadgeService = userBadgeService;
    }

    @GET
    @Path("/leaderboard")
    @Timed
    public List<ApiUser> getLeaderboard(@QueryParam("orderby") String orderBy) {
        Optional<TableField<UserRecord, ?>> orderByClause = Optional.empty();
        if ("branch".equals(orderBy)) {
            orderByClause = Optional.of(USER.LASTBRANCHCOVERAGE);
        } else if ("instruction".equals(orderBy)) {
            orderByClause = Optional.of(USER.LASTINSTRUCTIONCOVERAGE);
        } else if ("totaltests".equals(orderBy)) {
            orderByClause = Optional.of(USER.TOTALWRITTENTESTS);
        } else if ("lasttest".equals(orderBy)) {
            orderByClause = Optional.of(USER.LASTWRITTENTEST);
        }

        return userService.getLeaderboard(orderByClause).into(UserRecord.class).stream()
                .map(Utils::from)
                .collect(Collectors.toList());
    }







}
