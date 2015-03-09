package edu.chl.gunit.service.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import edu.chl.gunit.commons.api.ApiSession;
import edu.chl.gunit.core.ServiceFacade;
import edu.chl.gunit.core.data.tables.records.SessionRecord;
import edu.chl.gunit.core.services.Service;
import edu.chl.gunit.core.services.SessionService;
import edu.chl.gunit.service.api.Utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Created by davida on 5.3.2015.
 */
@Path("/api/session")
@Produces("application/json")
public class SessionResource extends AbstractResource<SessionRecord, SessionService, ApiSession> {


    private final SessionService sessionService;

    @Inject
    public SessionResource(SessionService service) {
        super(in -> Utils.from(in));
        this.sessionService = service;
    }

    @Override
    protected Service<SessionRecord> getService() {
        return sessionService;
    }

    @Path("/latest")
    @GET
    @Timed
    public ApiSession getLatest(@QueryParam("username") String userName) {
        return getMapper().map(sessionService.getLatestSession(userName));
    }
}
