package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import edu.chl.gunit.commons.TestRunRequest;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.SessionStatus;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.gamification.TestDataRunner;
import edu.chl.gunit.service.api.Badge;
import edu.chl.gunit.service.api.Session;
import edu.chl.gunit.service.api.SessionStatistics;
import edu.chl.gunit.service.api.User;
import org.jooq.TableField;

import javax.ws.rs.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static edu.chl.gunit.core.data.Tables.SESSION;
import static edu.chl.gunit.core.data.Tables.USER;

/**
 * Created by davida on 2.3.2015.
 */
@Path("/api/game")
@Produces("application/json")
public class GamificationResource {

    private final ServiceFacade facade;


    public GamificationResource(ServiceFacade facade) {
        this.facade = facade;
    }

    @GET
    @Path("/leaderboard")
    @Timed
    public List<User> getLeaderboard(@QueryParam("orderby") String orderBy) {
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

        return facade.userService().getLeaderboard(orderByClause).into(UserRecord.class).stream()
                .map(User::from)
                .collect(Collectors.toList());
    }





}
