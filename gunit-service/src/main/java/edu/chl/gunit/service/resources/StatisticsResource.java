package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import edu.chl.gunit.commons.api.*;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.data.tables.records.UserRecord;
import edu.chl.gunit.core.gamification.TestDataRunner;
import edu.chl.gunit.service.api.PagedResult;
import edu.chl.gunit.service.api.Utils;


import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

import static edu.chl.gunit.core.data.Tables.SESSION;

/**
 * Created by davida on 2.3.2015.
 */
@Path("/api/statistics")
@Produces("application/json")
public class StatisticsResource {

    private final ServiceFacade facade;

    public StatisticsResource(ServiceFacade facade) {
        this.facade = facade;
    }

    @GET
    @Path("{id}")
    @Timed
    public SessionStatistics getSessionStatistics(@PathParam("id") int sessionId) {
        SessionStatistics statistics = new SessionStatistics();

        SessionRecord session = facade.sessionService().get(sessionId);
        if (session != null) {
            statistics.setSession(Utils.from(session));

            UserRecord userRecord = facade.userService().get(session.getUserid());

            statistics.setUser(Utils.from(userRecord));

            statistics.setBadgesEarned(facade.badgeService()
                    .getBadgesForUserSession(sessionId).stream()
                    .map(Utils::<ApiBadge>from)
                    .collect(Collectors.toList()));
            return statistics;
        }
        return null;
    }

    @POST
    @Consumes("application/json")
    @Timed
    public int submitTestRun(TestRunRequest rs) {

        TestDataRunner runner = facade.getInjector().getInstance(TestDataRunner.class);

        int sessionid = runner.initialize(rs);

        Thread runThread = new Thread(runner);
        runThread.start();

        /**
         * 1. submit everything to the database
         * 2. create a unique message key
         * 3. collect all the new ids and create a message queue message
         * 4. return the message key for the user to query at a later date
         * */

        return sessionid;
    }

    @GET
    @Path("/processing/{userId}")
    @Timed
    public List<ApiSession> getProcessingSession(@PathParam("userId")int userId) {
        return facade.sessionService().getList(
                SESSION.SESSIONSTATUS.eq(SessionStatus.New.getStatusCode()).and(SESSION.USERID.eq(userId))).stream()
                .map(x -> Utils.from(x))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/by-user/{userId}")
    @Timed
    public PagedResult<List<ApiSession>> getSessionsByUser(@PathParam("userId")int userId, @QueryParam("page") int page) {
        int count = 20;
        List<ApiSession> sessions = facade.sessionService().getList(page * count, count,
                SESSION.USERID.eq(userId)
        ).stream().map(Utils::<ApiSession>from).collect(Collectors.<ApiSession>toList());

        int totalCount = facade.sessionService().count(SESSION.USERID.eq(userId));
        return new PagedResult<>(page * count, count, totalCount, sessions);
    }

}

